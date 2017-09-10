<?php

  $tableName = "DataDiri";

  require_once('con.php');

  $sql = "SELECT * FROM " . $tableName;

  //Getting result
  $result = mysqli_query($con, $sql);
  $resultArray = array();

  //loop through result
  while ($row = mysqli_fetch_array($result)) {
    # code...
    //Memasukkan hasil loop ke resultArray
    array_push($resultArray, array(
      "id"=>$row['id'],
      "username"=>$row['username'],
      'password'=>$row['password']
    ));
  }

  echo json_encode(array('result'=>$resultArray));

  mysqli_close($con);

 ?>
