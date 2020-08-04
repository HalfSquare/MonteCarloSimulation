let https = require('https');

function getToken () {
    let args = null;
    process.argv
        .slice(2, process.argv.length)
        .forEach( arg => {
            if (arg.slice(0,2) === '--') {
                const longArg = arg.split('=');
                const longArgValue = longArg.length > 1 ? longArg[1] : true;
                args = longArgValue;
            }
        });
    return args;
}

const token = getToken();
const firebaseToken = "";
let tagName;
// getTags();
// getMilestone();

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

            var releaseNotes = "Release Notes (" + tagName + "):\n\n";
            for (issueNum in json) {
                let issue = json[issueNum]
                releaseNotes += "- " + issue["title"] + "\n"
                let assignees = issue["assignees"]
                releaseNotes += "Contributor(s):"
                for (person in assignees) {
                    releaseNotes += " " + assignees[person]["name"] + ","
                }
                releaseNotes = releaseNotes.replace(/.$/,"\n\n")
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

    var postData = querystring.stringify({
        'description' : releaseNotes
    });

    const options = {
        hostname: "gitlab.ecs.vuw.ac.nz",
        path: `/api/v4/projects/6908/repository/tags/${tagName}/release`,
        method: 'POST',
        headers: {
            Authorization: 'Bearer '+token
        }
    };

    const req = https.request(options).on("error", (err) => {
        console.log("Error: " + err.message);
    });

    req.write(postData)
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

var firebase = require("firebase/app");
var admin = require("firebase-admin");
require("firebase/storage")

var serviceAccount = "rocketboydeploy-firebase-adminsdk-3bq1k-a234d5bd98.json";

publishToFirebaseStorage()


function publishToFirebaseStorage() {
    firebase.initializeApp({
        credential: admin.credential.cert(serviceAccount),
        databaseURL: "https://rocketboydeploy.firebaseio.com"
    });
    let storage = firebase.storage()
    let path = storage.refFromURL("gs://rocketboydeploy.appspot.com")
    console.log()
}