const { spawnSync } = require('child_process');

function exec(serviceName, command){

  console.log(`Installing dependencies for [${serviceName}]`);
  console.log(`Folder: ${serviceName} Command: ${command}`);

  spawnSync(command, [], { 
    cwd: serviceName,
    shell: true,
    stdio: 'inherit'
  });
}

//verify Consumer contract tests
exec('planner', 'mvn verify');
//verify Provider contract tests given the Pact
exec('toposervice', 'mvn verify');
