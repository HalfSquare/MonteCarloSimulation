const FILE_NAME = "tags.json";
const JAR_FILE_PATH = "../Group15Program/Group15Program.jar";
const DOWNLOADS_FILE_NAME = "downloads.json"

const DOWNLOADS_JSON_URL = 'https://storage.googleapis.com/rocketboydeploy.appspot.com/downloads.json?GoogleAccessId=firebase-adminsdk-3bq1k%40rocketboydeploy.iam.gserviceaccount.com&Expires=16468311600&Signature=jypaKfHYhtH2FejTTZ8Kf2GJp36xLVAoSGBPdQl7%2BNuQ%2FkHVN2O7tZF1AqbfNefzE2TDPusdbEBJnm7Z1W5EY4r5N9EqLOorhr4aS6amTAvmqSJfxzLYii0tAB51AO5TJXdVwo%2BzN5XiF8dehrK5iFqHVSgMsaX3tMgP1xKkPGe9X25ya%2BMI9z38X3u29z6rUls2%2BSo8z6aQMF7JAB%2BJuwoKVIFBXsMRuyIqX1YSyNRLby6VRV2f80tQJGGmCUxo5E0B%2B%2BT8p6RqnenoVXQ9XbGEvEC8DQJ9Hdb8eDtX5c93Du3enP2dEDt37dTY3ERVgMIS8LLweNzacQHPuqx2wA%3D%3D'

const token = getToken();
const firebaseToken = getFirebaseToken();

let fs = require('fs');

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
        args = args.replace(/\\'/g, '"')
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
const fetch = require('node-fetch');

let tags = [];
let downloads;

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
        let settings = {method: "Get"};

        fetch(DOWNLOADS_JSON_URL, settings)
            .then(res => res.json())
            .then((downloadJson) => {
                downloads = JSON.parse(JSON.stringify(downloadJson))
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
                    let link = downloads[item.name].jar ?? ""
                    if (link === "") {
                        link = downloads[item.name] ?? ""
                    }
                    let guide = downloads[item.name].guide ?? ""
                    let tag = {
                        "Tag": item.name,
                        "Notes": release.description,
                        "Link": link,
                        "Guide": guide
                    };
                    tags.push(tag);
                })
                let tagName = tags[0].Tag
                uploadJar(tagName)
            });
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
                downloads[tagName] = { jar : url[0], guide: "https://firebasestorage.googleapis.com/v0/b/rocketboydeploy.appspot.com/o/platypus.pv20908.1z.pdf?alt=media&token=f150797b-40d7-45c4-938a-5f9afc8b2bc5" }
                uploadDownloads()

                fs.writeFile(FILE_NAME, JSON.stringify(tags), (err) => {
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

/**
 * Upload downloads.json to firebase that contains download url for each jar version
 */
function uploadDownloads() {
    fs.writeFile(DOWNLOADS_FILE_NAME, JSON.stringify(downloads), (err) => {
        if (err) console.log("Error: " + err.message);
        let options = {
            destination: DOWNLOADS_FILE_NAME,
        };
        bucket.upload(DOWNLOADS_FILE_NAME, options, function (err) {
            if (!err) {
                console.log(console.log(`Uploaded ${DOWNLOADS_FILE_NAME}`))
            }
        })
    })
}