var path = require('path');
var gulp = require('gulp');
var args = require('yargs').argv;
var colors = require('colors');
var execa = require('execa');
var pAll = require('p-all');
var os = require('os');
var fs = require('fs');
var shell = require('gulp-shell');
var ghPages = require('gulp-gh-pages');
var argv = require('yargs').argv;
var gulpif = require('gulp-if');
var exec = require('child_process').exec;

const mappings = require('./api-specs.json');
const defaultSpecRoot = "https://raw.githubusercontent.com/Azure/azure-rest-api-specs/master";

gulp.task('default', function() {
    console.log("Usage: gulp codegen " +
        "[--spec-root <swagger specs root>] " +
        "[--projects <project names>] " +
        "[--autorest <autorest info>] " +
        "[--autorest-java <autorest.java info>] " +
        "[--debug] " +
        "[--parallel <number>] " +
        "[--autorest-args <AutoRest arguments>]\n");

    console.log("--spec-root");
    console.log(`\tRoot location of Swagger API specs, default value is "${defaultSpecRoot}"`);

    console.log("--projects");
    console.log("\tComma separated projects to regenerate, default is all. List of available project names:");
    Object.keys(mappings).forEach(function(i) {
        console.log('\t' + i.magenta);
    });

    console.log("--autorest");
    console.log("\tThe version of AutoRest. E.g. 2.0.9, or the location of AutoRest repo, e.g. E:\\repo\\autorest");

    console.log("--autorest-java");
    console.log("\tPath to an autorest.java generator to pass as a --use argument to AutoRest.");
    console.log("\tUsually you'll only need to provide this and not a --autorest argument in order to work on Java code generation.");
    console.log("\tSee https://github.com/Azure/autorest/blob/master/docs/developer/autorest-extension.md");

    console.log("--debug");
    console.log("\tFlag that allows you to attach a debugger to the autorest.java generator.");

    console.log("--parallel");
    console.log("\tSpecifies the maximum number of projects to generate in parallel.");
    console.log("\tDefaults to the number of logical CPUs on the system. (On this system, " + os.cpus().length + ")");

    console.log("--autorest-args");
    console.log("\tPasses additional argument to AutoRest generator");
});

const maxParallelism = parseInt(args['parallel'], 10) || os.cpus().length;
var specRoot = args['spec-root'] || defaultSpecRoot;
var projects = args['projects'];
var autoRestVersion = 'latest'; // default
if (args['autorest'] !== undefined) {
    autoRestVersion = args['autorest'];
}
var debug = args['debug'];
var autoRestArgs = args['autorest-args'] || '';
var autoRestExe;

gulp.task('codegen', function(cb) {
    if (autoRestVersion.match(/[0-9]+\.[0-9]+\.[0-9]+.*/) ||
        autoRestVersion == 'latest') {
            autoRestExe = 'autorest ---version=' + autoRestVersion;
            handleInput(projects, cb);
    } else {
        autoRestExe = "node " + path.join(autoRestVersion, "src/autorest-core/dist/app.js");
        handleInput(projects, cb);
    }
});

var handleInput = function(projects, cb) {
    console.info(`Generating up to ${maxParallelism} projects in parallel..`);
    if (projects === undefined) {
        const actions = Object.keys(mappings).map(proj => {
            return () => codegen(proj, cb);
        });
        pAll(actions, { concurrency: maxParallelism });
    } else {
        const actions = projects.split(",").map(proj => {
            return () => {
                proj = proj.replace(/\ /g, '');
                if (mappings[proj] === undefined) {
                    console.error('Invalid project name "' + proj + '"!');
                    process.exit(1);
                }
                return codegen(proj, cb);
            }
        });
        pAll(actions, { maxParallelism });
    }
}

var codegen = function(project, cb) {
    if (!args['preserve']) {
        const sourcesToDelete = path.join(
            mappings[project].dir,
            '/src/main/java/',
            mappings[project].package.replace(/\./g, '/'));

        deleteFolderRecursive(sourcesToDelete);
    }

    console.log('Generating "' + project + '" from spec file ' + specRoot + '/' + mappings[project].source);
    var generator = '--fluent=true';
    if (mappings[project].fluent !== null && mappings[project].fluent === false) {
        generator = '';
    }

    const generatorPath = args['autorest-java']
        ? `--use=${path.resolve(args['autorest-java'])} `
        : '--use=https://github.com/Azure/autorest.java/releases/download/v2.0.8/microsoft.azure-autorest.java-2.0.8.tgz';

    const regenManager = args['regenerate-manager'] ? ' --regenerate-manager=true ' : '';

    const outDir = process.cwd();
    // path.join won't work if specRoot is a URL
    cmd = autoRestExe + ' ' + specRoot + "/" + mappings[project].source +
                        ' --java ' +
                        ' --azure-arm=true ' +
                        generator +
                        ` --namespace=${mappings[project].package} ` +
                        ` --azure-libraries-for-java-folder=${outDir} ` +
                        ` --license-header=MICROSOFT_MIT_NO_CODEGEN ` +
                        generatorPath +
                        regenManager +
                        autoRestArgs;

    if (mappings[project].args !== undefined) {
        cmd += ' ' + mappings[project].args;
    }

    if (debug) {
        cmd += ' --java.debugger';
    }

    console.log('Command: ' + cmd);
    return execa(cmd, [], { shell: true, stdio: "inherit" });
};

var deleteFolderRecursive = function(path) {
    var header = "Code generated by Microsoft (R) AutoRest Code Generator";
    if(fs.existsSync(path)) {
        fs.readdirSync(path).forEach(function(file, index) {
            var curPath = path + "/" + file;
            if(fs.lstatSync(curPath).isDirectory()) { // recurse
                deleteFolderRecursive(curPath);
            } else { // delete file
                var content = fs.readFileSync(curPath).toString('utf8');
                if (content.indexOf(header) > -1) {
                    fs.unlinkSync(curPath);
                }
            }
        });
    }
};

gulp.task('java:build', shell.task('mvn package javadoc:aggregate -DskipTests=true -q'));
gulp.task('java:stage', ['java:build'], function(){
    return gulp.src('./target/site/apidocs/**/*').pipe(gulp.dest('./dist')); 
});

/// Top level build entry point
gulp.task('stage', ['java:stage']);
gulp.task('publish', ['stage'], function(){
    var options = {};
    if(process.env.GH_TOKEN){
        options.remoteUrl = 'https://' + process.env.GH_TOKEN + '@github.com/azure/azure-libraries-for-java.git'  
    }
    return gulp.src('./dist/**/*').pipe(gulpif(!argv.dryrun, ghPages(options)));
});
