const FILE_NAME = "tags.json";
const JAR_FILE_PATH = "../Group15Program/Group15Program-jar.jar";

const token = getToken();
const firebaseToken = getFirebaseToken();

/**
 * Parses argument for Gitlab api token
 * @returns String Gitlab api token
 */
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

/**
 * Parses argument for firebase security credentials
 * @returns JSON file to be used for initialisation of firebase
 */
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

let admin = require("firebase-admin"); // Required to upload file to firebase using Node

// Initialise Firebase with security credentials
admin.initializeApp({
    credential: admin.credential.cert(firebaseToken),
    databaseURL: "https://rocketboydeploy.firebaseio.com",
    storageBucket: "gs://rocketboydeploy.appspot.com"
});

// Firebase storage bucket
let bucket = admin.storage().bucket();

let https = require('https');

let tags = [];

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

        json.forEach((item) => {
            let release = item.release ?? {description: ""}
            let tag = {
                "Tag": item.name,
                "Notes": [release.description],
                "Link": "https://www.example.com"
            };
            tags.push(tag);
        })

        let tagName = tags[0].Tag
        uploadJar(tagName)
    });

}).on("error", (err) => {
    console.log("Error: " + err.message);
});

req.end();

/**
 * Upload tags.json to firebase
 */
function uploadTags() {
    let options = {
        destination: FILE_NAME,
    };
    bucket.upload(FILE_NAME, options, function(err) {
        if (!err) {
            console.log(`Uploaded ${FILE_NAME}`);
        } else {
            console.log(err);
            // Failed to upload to firebase
        }
    });
}

/**
 * Upload Jar file to firebase
 * @param tagName name of file
 */
function uploadJar(tagName) {
    let optionsJar = {
        destination: tagName+".jar",
    };
    bucket.upload(JAR_FILE_PATH, optionsJar, function(err) {
        if (!err) {
            console.log(`Uploaded ${tagName+".jar"}`);
            bucket.file(tagName+".jar").getSignedUrl({
                action: 'read',
                expires: '11-11-2491'
            }).then(url =>{
                function findTag(tag) {
                    return tag.Tag === tagName
                }

                let index = tags.findIndex(findTag)
                tags[index].Link = url[0]

                let fs = require('fs');

                fs.writeFile(FILE_NAME, JSON.stringify(tags.reverse()), (err) => {
                    if (err) console.log("Error: " + err.message);
                    uploadTags()
                })
            })
        } else {
            console.log(err);
            // Failed to upload to firebase
        }
    });
}