## waze api - wrapping waze live map 

#### 3 steps and your up & running!

##### 1) download [waze-server](https://github.com/Nimrod007/waze-api/releases/download/1.1/waze-server.jar)
##### 2) run the server (port 8080) :
 ```bash
 java -jar waze-server.jar server
 ```
 
##### 3) send a request:
```bash
curl -v "http://localhost:8080/waze/routesWithDirections?end=156+5th+Avenue%2C+New+York%2C+NY+10010&start=6+East+57th+Street%2C+New+York%2C+NY+10022" -H "Accept: application/json"
```

##### documentation (full list of supported requests) - [link](http://htmlpreview.github.io/?https://github.com/Nimrod007/waze-api/blob/master/docs.html)

##### list of all end-points with curl examples - [link](https://github.com/Nimrod007/waze-api/blob/master/scripts/testWazeAppServer.sh)

#### advance usage:

##### using the the waze service (no server) - [link](https://github.com/Nimrod007/waze-api/blob/master/src/test/java/com/waze/WazeRouteServiceTest.java)
##### run the server with custom port:
 ```bash
 java -Ddw.server.applicationConnectors[0].port=9090 -Ddw.server.adminConnectors[0].port=9091 -jar waze-server.jar server
 ```
##### run the server with custom config (see [conf.yml](/src/main/resources/conf.yml) for example):
 ```bash
 java -jar waze-server.jar.jar server conf.yml
 ```

##### http://www.nimrodstech.com
