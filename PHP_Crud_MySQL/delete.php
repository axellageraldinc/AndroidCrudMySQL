<?php

  $tableName = "DataDiri";
  $col_id = "id";

  $id = $_GET['id'];

  require_once('con.php');

  $sql = "DELETE FROM " . $tableName . " WHERE " . $col_id . "=" . $id;

  if (mysqli_query($con, $sql)) {
    # code...
    echo "Deleted successfully";
  } else{
    echo "Delete failed!";
  }

 ?>
