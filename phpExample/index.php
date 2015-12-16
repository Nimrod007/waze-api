<?php
session_start();
header('Content-Type: text/html; charset=UTF-8'); 


echo "Calculate your drive time using waze<br>";
echo "Enter your start and end locations, use the format : Street House number City<br>";
echo "For example : 5th Avenue, NY, United States <br><br>";
?>


<form name="input" action="pickStreet.php" method="post">
FROM: <input type="text" name="start"><br>
TO: <input type="text" name="end"><br>
<input type="submit" value="Next">
</form> 