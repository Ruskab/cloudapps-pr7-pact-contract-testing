const { spawnSync } = require('child_process');

function exec(serviceName, command){

  console.log(`Installing dependencies for [${serviceName}]`);
  console.log(`Folder: ${serviceName} Command: ${command}`);

  spawnSync(command, [], { 
    shell: true,
    stdio: 'inherit'
  });
}

//verify Consumer contract tests
exec('unit tests', 'mvn -D test=ikab.dev.javalibros.rest.BookRestControllerTest test');
