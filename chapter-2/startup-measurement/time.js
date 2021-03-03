const spawn = require("child_process").spawn;
const request = require("request");

const pingIntervalMs = 5;

const args = process.argv.slice(2);
if (args.length == 0) {
  console.log('The path of java executable jar must be specified !');
  exit(1);
}

const procCmd = args[0];
const targetUrl = args[1];
const cmd = args[0].split(" ");

const startTime = new Date().getTime();
const proc = spawn(cmd[0], cmd.slice(1), { stdio: 'ignore' });

const intervalHandle = setInterval(() => {
  request(targetUrl, (error, response, body) => {
      if (!error && response && response.statusCode === 200 && body) {
          const time = new Date().getTime() - startTime;
          console.log(time + " ms");
          clearInterval(intervalHandle);
          proc.kill();
          process.exit(0);
      } else {
          // @ts-ignore
          if (!error || !error.code === "ECONNREFUSED") {
            console.log(error ? error : response.statusCode);
          }
      }
    }
  );
}, pingIntervalMs);
