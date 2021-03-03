time.js
=======

Node.js script to measure the start time of an application without having to modicfy application code.

The application is spawned in a background process and a URL is tested until a HTTP 200 response is returned. The time taken to spawn the process and a HTTP 200 reponse is measured and reported to the terminal.


Build
=====

Before the script is run for the first time, the required node.js modules require installing.

```

$ npm install request

```


Running
=======

To measure the start time for an application invoke the script;

```
$ node time.js "java -jar /path/to/appication-runner.jar"  "http://localhost:8080/hello"
```

where;

"java -jar /path/to/application-runner.jar" - The command to start the application being tested

"http://localhost:8080/hello" - the URL to test.
