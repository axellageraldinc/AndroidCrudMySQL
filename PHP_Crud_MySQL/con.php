<?php

//Definisi konstanta untuk koneksi ke DB
define('HOST', 'localhost');
define('USER', 'root');
define('PASS', '');
define('DB', 'Test1');

//connect mysql dengan konstanta yang sudah dibuat, dimasukkan ke variable $con
$con = mysqli_connect(HOST, USER, PASS, DB) or die('Unable to connect');

 ?>
