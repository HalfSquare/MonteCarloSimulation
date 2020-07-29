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

const token = getToken();


let https = require('https');

const options = {
    hostname: "gitlab.ecs.vuw.ac.nz",
    // path: "/api/v4/projects/6908/repository/tags",
    path: "/projects/6908/repository/tags",
    method: 'GET',
    headers: {
        Authorization: token,
        contentType: 'application/x-www-form-urlencoded;charset=UTF-8'
    }
};

const req = https.request(options, (resp) => {
    let data = '';

    resp.on('data', (chunk) => {
        data += chunk;
    });

    resp.on('end', () => {
        let json = JSON.parse(data);
        console.log(json);
    });

}).on("error", (err) => {
    console.log("Error: " + err.message);
});

req.end();
