<?php

  $tableName = "DataDiri";
  $col_id = "id";
  $col_username = "username";
  $col_password = "password";

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    # code...
    $id = $_POST[$col_id];
    $username = $_POST[$col_username];
    $password = $_POST[$col_password];

    $sql = "UPDATE " . $tableName . " SET " . $col_username . "=" . "'" . $username . "'" . "," .
            $col_password . "=" . "SHA2(" . "'" . $password . "'" . ",224)" . " where " . $col_id . "=" . $id;
  }

  require_once('con.php');
  if (mysqli_query($con, $sql)) {
    # code...
    echo "Updated successfully";
  } else{
    echo "Cannot update";
  }

  mysqli_close($con);

 ?>
