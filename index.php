<?php
header('Content-Type: text/html; charset=UTF-8'); 


echo "Calculate you drive time using waze<br>";
?>


<form name="input" action="route.php" method="post">
FROM: <input type="text" name="start"><br>
TO: <input type="text" name="end"><br>
<input type="submit" value="Calculate">
</form> 