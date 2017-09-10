<?php

$tableName = "DataDiri";
$col_username = "username";
$col_password = "password";

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
  //MySQL Connection
  require_once('con.php');

  //Mengambil nilai
  $username = $_POST['username'];
  // $password_hashed = password_hash('password', PASSWORD_DEFAULT);
  // $password_hashed = md5('password');
  $password = $_POST['password'];

  //Query create and password encrypted using SHA2 with 224 bit length
  $sql = "INSERT INTO " . $tableName . " (" . $col_username . "," . $col_password . ")" . " VALUES ('$username', SHA2('$password', 224))";

  //Execute Query
  if(mysqli_query($con, $sql)){
    echo "Added successfully!";
  } else{
    echo "Cannot add";
  }

  //Close DB Connection
  mysqli_close($con);

}

 ?>
