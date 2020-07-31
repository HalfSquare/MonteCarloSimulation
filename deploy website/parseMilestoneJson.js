var fs = require('fs'), filename = process.argv[2];

fs.readFile(filename, 'utf8', function(err, data) {
    if (err) throw err;
    const id = JSON.parse(data)[0]["id"]
    fs.writeFileSync(filename, String(id));
});