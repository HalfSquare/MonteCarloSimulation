let https = require('https');

function getToken () {
    let args = null;
    process.argv
        .slice(2, process.argv.length)
        .forEach( arg => {
            if (arg.slice(0,2) === '--') {
                const longArg = arg.split('=');
                args = longArg[1];
            }
        });
    return args;
}

const token = getToken();
let tagName;
getTags();
getMilestone();

function getMilestone() {
    const options = {
        hostname: "gitlab.ecs.vuw.ac.nz",
        path: "/api/v4/projects/6908/milestones?state=closed",
        method: 'GET',
        headers: {
            Authorization: 'Bearer '+token,
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8'
        }
    };

    const req = https.request(options, (resp) => {
        let data = ''
        resp.on('data', (chunk) => {
            data += chunk;
        });

        resp.on('end', () => {
            let json = JSON.parse(data)
            let id = json[0]["id"]
            getIssues(id)
        });

    }).on("error", (err) => {
        console.log("Error: " + err.message);
    });

    req.end();
}

function getIssues(milestoneId) {
    const options = {
        hostname: "gitlab.ecs.vuw.ac.nz",
        path: `/api/v4/projects/6908/milestones/${milestoneId}/issues`,
        method: 'GET',
        headers: {
            Authorization: 'Bearer '+token,
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8'
        }
    };

    const req = https.request(options, (resp) => {
        let data = ''
        resp.on('data', (chunk) => {
            data += chunk;
        });

        resp.on('end', () => {
            let json = JSON.parse(data)

            let releaseNotes = "Release Notes (" + tagName + "):\n\n";
            for (let issue in json) {
                if (json.hasOwnProperty(issue)) {
                    releaseNotes += "- " + json[issue]["title"] + "\n"
                    let assignees = json[issue]["assignees"]
                    releaseNotes += "Contributor(s):"
                    for (let person in assignees) {
                        if (assignees.hasOwnProperty(person)) {
                            releaseNotes += " " + assignees[person]["name"] + ","
                        }
                    }
                    releaseNotes = releaseNotes.replace(/.$/, "\n\n")
                }
            }
            updateTagReleaseNotes(releaseNotes)
        });

    }).on("error", (err) => {
        console.log("Error: " + err.message);
    });

    req.end();
}

function updateTagReleaseNotes(releaseNotes) {
    const querystring = require('querystring');

    const postData = querystring.stringify({
        'description': releaseNotes
    });

    const options = {
        hostname: "gitlab.ecs.vuw.ac.nz",
        path: `/api/v4/projects/6908/repository/tags/${tagName}/release`,
        method: 'POST',
        headers: {
            Authorization: 'Bearer '+token
        }
    };

    const { exec } = require("child_process");

    const req = https.request(options, (res => {
        if (res.statusCode !== 201) {
            console.log(res.statusCode)
            return
        }
        exec(`node fetch-tags.js --token=$DEPLOY_API_TOKEN --firebase|$FIREBASE_API_KEY`, (error, stdout, stderr) => {
            if (error) {
                console.log(`error: ${error.message}`);
                return;
            }
            if (stderr) {
                console.log(`stderr: ${stderr}`);
                return;
            }
            console.log(`stdout: ${stdout}`);
        });
    })).on("error", (err) => {
        console.log("Error1: " + err.message);
    })

    req.write(postData)
    req.end();
}

function getTags() {

    const options = {
        hostname: "gitlab.ecs.vuw.ac.nz",
        path: `/api/v4/projects/6908/repository/tags`,
        method: 'GET',
        headers: {
            Authorization: 'Bearer '+token,
            contentType: 'application/x-www-form-urlencoded;charset=UTF-8'
        }
    };

    const req = https.request(options, (resp) => {
        let data = ''
        resp.on('data', (chunk) => {
            data += chunk;
        });

        resp.on('end', () => {
            let tag = JSON.parse(data)[0]
            tagName = tag["name"]
            deleteRelease()
        });

    }).on("error", (err) => {
        console.log("Error: " + err.message);
    });

    req.end();
}


function deleteRelease() {
    const options = {
        hostname: "gitlab.ecs.vuw.ac.nz",
        path: `/api/v4/projects/6908/releases/${tagName}`,
        method: 'DELETE',
        headers: {
            Authorization: 'Bearer '+token
        }
    };

    const req = https.request(options).on("error", (err) => {
        console.log("Error: " + err.message);
    });

    req.end();
}