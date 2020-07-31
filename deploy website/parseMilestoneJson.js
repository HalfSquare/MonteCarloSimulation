function getJsonString () {
    let args = null;
    process.argv
        .slice(2, process.argv.length)
        .forEach( arg => {
            if (arg.slice(0,2) === '--') {
                const longArg = arg.split('=');
                args = longArg.length > 1 ? longArg[1] : "";
            }
        });
    return args;
}

const jsonString = getJsonString();

let json = JSON.parse(jsonString)
console.log(json);