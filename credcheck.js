const args = require('yargs').argv;
const os = require('os');
const fs = require('fs');
const path = require('path');

function credcheck(dir) {
    console.log('Path: ' + dir);

    const redactDict = new Map();
    // storage account keys
    redactDict.set(/\\"keyName\\":\\"key1\\",\\"value\\":\\"(.*?)\\"/g, '\\"keyName\\":\\"key1\\",\\"value\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"keyName\\":\\"key2\\",\\"value\\":\\"(.*?)\\"/g, '\\"keyName\\":\\"key2\\",\\"value\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/;AccountKey=(.*?)(;|\\")/g, ';AccountKey=MGMT_PLACEHOLDER$2');
    redactDict.set(/\\"primaryMasterKey\\":\\"(.*?)\\"/g, '\\"primaryMasterKey\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"primaryReadonlyMasterKey\\":\\"(.*?)\\"/g, '\\"primaryReadonlyMasterKey\\":\\"MGMT_PLACEHOLDER\\"');	
    redactDict.set(/\\"secondaryMasterKey\\":\\"(.*?)\\"/g, '\\"secondaryMasterKey\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"secondaryReadonlyMasterKey\\":\\"(.*?)\\"/g, '\\"secondaryReadonlyMasterKey\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/;SharedAccessKey=(.*?)(;|\\")/g, ';SharedAccessKey=MGMT_PLACEHOLDER$2');
    redactDict.set(/\\"primaryKey\\":\\"(.*?)\\"/g, '\\"primaryKey\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"secondaryKey\\":\\"(.*?)\\"/g, '\\"secondaryKey\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"accessSAS\\": \\"(.*?)\\"/g, '\\"accessSAS\\": \\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"administratorLoginPassword\\":\\"(.*?)\\"/g, '\\"administratorLoginPassword\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"permissions\\":\\"Full\\",\\"value\\":\\"(.*?)\\"/g, '\\"keyName\\":\\"key1\\",\\"value\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"adminPassword\\":{\\"type\\":\\"String\\",\\"value\\":\\"(.*?)\\"}/g, '\\"adminPassword\\":{\\"type\\":\\"String\\",\\"value\\":\\"MGMT_PLACEHOLDER\\"}');
    redactDict.set(/\\"DOCKER_REGISTRY_SERVER_PASSWORD\\":\\"(.*?)\\"/g, '\\"DOCKER_REGISTRY_SERVER_PASSWORD\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"connectionString\\":\\"(.*?)\\"/g, '\\"connectionString\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/&sig=(.*?)(&|\\")/g, '&sig=MGMT_PLACEHOLDER&');
    redactDict.set(/\\"primary\\":\\"(.*?)\\"/g, '\\"primary\\":\\"MGMT_PLACEHOLDER\\"');
    redactDict.set(/\\"secondary\\":\\"(.*?)\\"/g, '\\"secondary\\":\\"MGMT_PLACEHOLDER\\"');

    credcheckRecursive(dir, redactDict);
}

function credcheckRecursive(dir, redactDict) {
    if (fs.existsSync(dir)) {
        fs.readdirSync(dir).forEach(function(file, index) {
            const curPath = path.join(dir, file);
            if (fs.lstatSync(curPath).isDirectory()) {
                // recurse
                credcheckRecursive(curPath, redactDict);
            } else {
                if (curPath.endsWith('.json')) {
                    const content = fs.readFileSync(curPath).toString('utf8');
                    var redactedContent = content;
                    for (const [key, value] of redactDict.entries()) {
                        redactedContent = redactedContent.replace(key, value);
                    }
                    if (redactedContent !== content) {
                        console.log('File redacted: ' + curPath);

                        fs.writeFileSync(curPath, redactedContent);
                    }
                }
            }
        });
    }
}

const dir = args['path']

credcheck(dir);
