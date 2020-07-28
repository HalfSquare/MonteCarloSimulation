let https = require('https');

function getToken () {
    let args = null;
    process.argv
        .slice(2, process.argv.length)
        .forEach( arg => {
            // long arg
            if (arg.slice(0,2) === '--') {
                const longArg = arg.split('=');
                const longArgFlag = longArg[0].slice(2,longArg[0].length);
                const longArgValue = longArg.length > 1 ? longArg[1] : true;
                args = longArgValue;
            }
        });
    return args;
}

const token = getToken();

const options = {
    hostname: "gitlab.ecs.vuw.ac.nz",
    path: "/api/v4/projects/6908/milestones?state=closed",
    method: 'GET',
    headers: {
        Authorization: 'Bearer '+token,
        contentType: 'application/x-www-form-urlencoded;charset=UTF-8'
    }
};

console.log(options)

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
