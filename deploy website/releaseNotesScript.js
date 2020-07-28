let https = require('https');

const options = {
    hostname: "gitlab.ecs.vuw.ac.nz",
    path: "/api/v4/projects/6908/milestones?state=closed",
    method: 'GET',
    headers: {
        Authorization: "Bearer $CI_JOB_TOKEN",
        contentType: 'application/x-www-form-urlencoded;charset=UTF-8'
    }
};

const req = https.request(options, (resp) => {
    let data = '';

    resp.on('data', (chunk) => {
        data += chunk;
    });

    resp.on('end', () => {
        let json = JSON.parse(data)
        console.log(json[0]);
    });

}).on("error", (err) => {
    console.log("Error: " + err.message);
});

req.end();
