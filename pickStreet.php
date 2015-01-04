<?php
session_start();
header('Content-Type: text/html; charset=UTF-8'); 

//get the route start and end from the POST
$start = $_POST['start'];
$end = $_POST['end'];


//encode it (to send in url)
$startEncoded = urlencode($start);
$endEncoded = urlencode($end);

//prepare url get street name information to cord's
$wazeGetStartCordUrl = "https://www.waze.com/SearchServer/mozi?q=$startEncoded&lang=eng&lon=-73.96888732910156%20&lat=40.799981900731964&origin=livemap";
$wazeGetEndCordUrl = "https://www.waze.com/SearchServer/mozi?q=$endEncoded&lang=eng&lon=-73.96888732910156%20&lat=40.799981900731964&origin=livemap";

//send waze the street names and get back the cordinets (might be more than 1 result for start and end)
$startResposeText = file_get_contents($wazeGetStartCordUrl);
$startResponseJson = json_decode($startResposeText,true);
$endResposeText = file_get_contents($wazeGetEndCordUrl);
$endResponseJson = json_decode($endResposeText,true);

//save the response in session for later usage
$_SESSION['start'] = $startResponseJson;
$_SESSION['end'] = $endResponseJson;

//let user pick the start and end
	?>
	<p>Select start point</p>
	<select name="startPosition" form="route">
	<?php
	for ($i = 0 ; $i<count($startResponseJson) ; $i++){
		?> <option value="<?php echo $i?>"><?php echo $startResponseJson[$i]['name']?></option> <?php
	} ?>
	</select><br><br>
	<p>Select end point</p>
	<select name="endPosition" form="route">
	<?php
	for ($i = 0 ; $i<count($endResponseJson) ; $i++){
		?> <option value="<?php echo $i?>"><?php echo $endResponseJson[$i]['name']?></option> <?php
	} ?>
	</select><br><br>
		
	<form name="input" action="route.php" method="post" id="route">
	<input type="submit" value="Calculate">
	</form> 