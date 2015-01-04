<?php
session_start();
header('Content-Type: text/html; charset=UTF-8'); 

//get the route start and end from the POST
$start = $_POST['startPosition'];
$end = $_POST['endPosition'];

//get the cord's from user selected start & end adress
$startResponseJson = $_SESSION['start'];
$startLat = $startResponseJson[$start]['location']['lat'];
$startLon = $startResponseJson[$start]['location']['lon'];

$endResponseJson = $_SESSION['end'];
$endLat = $endResponseJson[$end]['location']['lat'];
$endLon = $endResponseJson[$end]['location']['lon'];

//prepare URL for route time query
$wazeRouteurl = "https://www.waze.com/RoutingManager/routingRequest?from=x%3A$startLon+y%3A$startLat&to=x%3A$endLon+y%3A$endLat&at=0&returnJSON=true&returnGeometries=true&returnInstructions=true&timeout=60000&nPaths=3&options=AVOID_TRAILS%3At";
//https://www.waze.com/RoutingManager/routingRequest?from=x%3A-73.9922319+y%3A40.7379049&to=x%3A-73.9451895+y%3A40.8134374&at=0&returnJSON=true&returnGeometries=true&returnInstructions=true&timeout=60000&nPaths=3&options=AVOID_TRAILS%3At
//send GET request to WAZE web site 
$routeResponseText = file_get_contents($wazeRouteurl);

//minor tweaks so this would be a valid json (NaN is a javascript valid json not php)
$routeResponseText = str_replace("NaN",'"NaN"',$routeResponseText);

$routeResponseJson = json_decode($routeResponseText,true);

//ge route names from response
$route1Name = $routeResponseJson['alternatives'][0]['response']['routeName'];  
$route2Name = $routeResponseJson['alternatives'][1]['response']['routeName'];

//take the 2 routes and calculate the total time
$route1 = $routeResponseJson['alternatives'][0]['response']['results'];
$route2 = $routeResponseJson['alternatives'][1]['response']['results'];

//this calculcation is in seconds
$route1TotalTimeSec = 0;
foreach ($route1 as $street){
	$route1TotalTimeSec += $street['crossTime'];
}

$route2TotalTimeSec = 0;
foreach ($route2 as $street){
	$route2TotalTimeSec += $street['crossTime'];
}

//move to minutes :
$route1TotalTimeMin = round($route1TotalTimeSec/60);
$route2TotalTimeMin = round($route2TotalTimeSec/60);

$startPoint = $startResponseJson[$start]['name'];
$endPoint = $endResponseJson[$end]['name'];

echo "Route Start:   $startPoint<br><br>";
echo "Route End:     $endPoint<br><br>";
echo "Route #1:      $route1Name, Duration (minutes): $route1TotalTimeMin<br><br>";
echo "Route #2:      $route2Name, Duration (minutes): $route2TotalTimeMin<br>";
?>