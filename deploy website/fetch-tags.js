const token = getToken();
// const token = "";
const FILE_NAME = "tags.json";


function getToken () {
    let args = null;
    process.argv
        .slice(2, process.argv.length)
        .forEach( arg => {
            // long arg
            if (arg.slice(0,2) === '--') {
                const longArg = arg.split('=');
                longArg[0].slice(2,longArg[0].length);
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
            let release = item.release ?? {description: ""}
            let tag = {
                "Tag": item.name,
                "Notes": [release.description],
                "Link": "https://www.example.com"
            };
            tags.push(tag);
        })

        let fs = require('fs');

        fs.writeFile(FILE_NAME, JSON.stringify(tags.reverse()), (err) => {
            if (err) console.log("Error: " + err.message);
            console.log('Saved!');
            fs.readFile(FILE_NAME, (err, code) => {
                console.log(JSON.stringify(code))
                var admin = require("firebase-admin");

                var serviceAccount = "rocketboydeploy-firebase-adminsdk-3bq1k-a234d5bd98.json";

                admin.initializeApp({
                    credential: admin.credential.cert(serviceAccount),
                    databaseURL: "https://rocketboydeploy.firebaseio.com",
                    storageBucket: "gs://rocketboydeploy.appspot.com"
                });
                let storage = admin.storage();
                var bucket = storage.bucket();
                var options = {
                    destination: FILE_NAME,
                    // resumable: false,
                    // metadata: {
                    //     metadata: metadata
                    // }
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
