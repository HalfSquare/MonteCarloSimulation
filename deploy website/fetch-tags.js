const token = getToken();
const FILE_NAME = "tags.json";
const firebaseToken = getFirebaseToken();

function getToken () {
    let args = null;
    let arg = process.argv.slice(2, process.argv.length)[0]
    if (arg.slice(0,2) === '--') {
        const longArg = arg.split('=');
        longArg[0].slice(2,longArg[0].length);
        args = longArg.length > 1 ? longArg[1] : true;
    }
    return args;
}

function getFirebaseToken() {
    let args = '';
    let arg = process.argv
        .slice(2, process.argv.length)[1]
    if (arg.slice(0,2) === '--') {
        const longArg = arg.split('=')
        longArg.shift()
        for (let arg in longArg) {
            args += longArg[arg] + "="
        }
        args = args.slice(0, -1)
        args = args.replace(/'/g, '"')
        args = args.replace(/\\s/g, ' ')
    }
    return JSON.parse(args);
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
            fs.readFile(FILE_NAME, (err) => {
                if (err != null) return;
                let admin = require("firebase-admin");

                admin.initializeApp({
                    credential: admin.credential.cert(firebaseToken),
                    databaseURL: "https://rocketboydeploy.firebaseio.com",
                    storageBucket: "gs://rocketboydeploy.appspot.com"
                });
                let storage = admin.storage();
                let bucket = storage.bucket();
                let options = {
                    destination: FILE_NAME,
                };
                bucket.upload(FILE_NAME, options, function(err) {
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
