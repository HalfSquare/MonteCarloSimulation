const token = getToken();
// const token = "";
const FILE_NAME = "tags.json";


function getToken() {
    let args = null;
    process.argv
        .slice(2, process.argv.length)
        .forEach(arg => {
            // long arg
            if (arg.slice(0, 2) === '--') {
                const longArg = arg.split('=');
                longArg[0].slice(2, longArg[0].length);
                args = longArg.length > 1 ? longArg[1] : true;
            }
        });
    return args;
}


let https = require('https');

const options = {
    hostname: "gitlab.ecs.vuw.ac.nz",
    path: "/api/v4/projects/6908/repository/tags",
    method: 'GET',
    headers: {
        "Private-Token": token,
        contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
        sort: "asc"
    }
};

const req = https.request(options, (resp) => {
    let data = '';

    resp.on('data', (chunk) => {
        data += chunk;
    });

    resp.on('end', () => {
        let json = JSON.parse(data);
        let tags = [];

        json.forEach((item) => {
            let release;
            if (item.release && item.release.description) {
                //Get an array of the notes and remove the empty spaces
                let releases = item.release.description.split("\n").filter(x => x !== "");
                //Get rid of the title line
                releases.splice(0, 1);

                let description = [];
                for (let i = 0; i < releases.length; i += 2) {
                    description[i / 2] = releases[i] + "&#8195;" + releases[i + 1];
                }
                release = {
                    description: description
                }
            } else {
                release = {
                    description: [""]
                };
            }
            // let release = item.release ?? {description: ""}
            let tag = {
                "Tag": item.name,
                "Notes": release.description,
                "Link": "https://www.example.com"
            };
            tags.push(tag);
        })

        let fs = require('fs');

        fs.writeFile(FILE_NAME, JSON.stringify(tags.reverse()), (err) => {
            if (err) console.log("Error: " + err.message);
            console.log('Saved!');
            fs.readFile(FILE_NAME, (err, code) => {
                let admin = require("firebase-admin");

                let serviceAccount = "rocketboydeploy-firebase-adminsdk-3bq1k-a234d5bd98.json";

                admin.initializeApp({
                    credential: admin.credential.cert(serviceAccount),
                    databaseURL: "https://rocketboydeploy.firebaseio.com",
                    storageBucket: "gs://rocketboydeploy.appspot.com"
                });
                let storage = admin.storage();
                let bucket = storage.bucket();
                let options = {
                    destination: FILE_NAME,
                };
                bucket.upload(FILE_NAME, options, function(err, remoteFile) {
                    if (!err) {
                        console.log("Uploaded!");
                    } else {
                        console.log(err);
                    }
                });
            });
        })
    });

}).on("error", (err) => {
    console.log("Error: " + err.message);
});

req.end();