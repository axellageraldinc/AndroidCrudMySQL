<?php

  class dbConnect{

    private $con;

    function __construct(){

    }

    function connection(){
      //include file constant .php untuk mengambil constant host, db name, username, dan password
      include_once dirname(__FILE__) . "/constant.php";
      //Connecting ke database menggunakan parameter2 yang diambil dari constant.php
      $this->con = new mysqli(HOST, username, password, DB);

      //Check apakah ada error saat connect database
      if (mysqli_connect_errno()) {
        echo "Failed to connect to database : " . mysqli_connect_error();
      }

      return $this->con;
    }

  }

 ?>
