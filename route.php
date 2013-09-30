<?php
header('Content-Type: text/html; charset=UTF-8'); 

//get the route start and end from the POST
$start = $_POST['start'];
$end = $_POST['end'];

//encode it (to send in url)
$startEncoded = urlencode($start);
$endEncoded = urlencode($end);

//prepare url get street name information to cord's
$wazeGetStartCordUrl = "http://www.waze.co.il/WAS/mozi?q=$startEncoded";
$wazeGetEndCordUrl = "http://www.waze.co.il/WAS/mozi?q=$endEncoded";

//send waze the street names and get back the cordinets
$startResposeText = file_get_contents($wazeGetStartCordUrl);
$startResponseJson = json_decode($startResposeText,true);
$startLat = $startResponseJson[0]['location']['lat'];
$startLon = $startResponseJson[0]['location']['lon'];

$endResposeText = file_get_contents($wazeGetEndCordUrl);
$endResponseJson = json_decode($endResposeText,true);
$endLat = $endResponseJson[0]['location']['lat'];
$endLon = $endResponseJson[0]['location']['lon'];


//prepare URL for route time query
$wazeRouteurl = "http://www.waze.co.il/RoutingManager/routingRequest?from=x%3A$startLon+y%3A$startLat+bd%3Atrue+s%3A51335+st_id%3A60799&to=x%3A$endLon+y%3A$endLat+bd%3Atrue+s%3A30045+st_id%3A4480&returnJSON=true&returnGeometries=true&returnInstructions=true&timeout=60000&nPaths=2";

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

echo "Route start : $start<br>";
echo "x-$startLon, y-$startLat<br>";
echo "Route end : $end<br>";
echo "x-$endLon, y-$endLat<br>";
echo "Route - $route1Name , Total time (minutes) : $route1TotalTimeMin<br>";
echo "Route - $route2Name , Total time (minutes) : $route2TotalTimeMin<br>";
?>