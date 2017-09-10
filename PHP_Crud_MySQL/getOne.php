<?php

  $tableName = "DataDiri";
  $col_id = "id";
  $col_username = "username";
  $col_password = "password";

  $id = $_GET['id'];
  require_once('con.php');

  $sql = "SELECT * FROM " . $tableName . " where " . $col_id . "=" . $id;

  $result = mysqli_query($con, $sql);
  $resultArray = array();

  while($row = mysqli_fetch_array($result)){
    array_push($resultArray, array(
      "id"=>$row[$col_id],
      "username"=>$row[$col_username],
      "password"=>$row[$col_password]
    ));
  }

  echo json_encode(array('result'=>$resultArray));

  mysqli_close($con);

 ?>
