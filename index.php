<?php
header('Content-Type: text/html; charset=UTF-8'); 


echo "Calculate your drive time using waze<br>";
echo "Enter your start and end locations, use the format : Street House number City<br>";
echo "For example : אבן גבירול 7 תל אביב<br><br>";
?>


<form name="input" action="route.php" method="post">
FROM: <input type="text" name="start"><br>
TO: <input type="text" name="end"><br>
<input type="submit" value="Calculate">
</form> 