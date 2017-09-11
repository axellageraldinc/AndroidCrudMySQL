<?php

  class crud {

    private $con;

    function __construct(){
      require_once dirname(__FILE__) . "/connect.php";
      $db = new dbConnect();
      $this->con = $db->connection();
    }

    function create($username, $password){
      //Prepare statement untuk query insert
      $stmt = $this->con->prepare("INSERT INTO DataDiri (username, password)
      VALUES (?,?)");
      //bind parameter ? dengan username dan password
      $stmt->bind_param("ss", $username, base64_encode($password));
      //Jika tidak ada error saat execute
      if ($stmt->execute()) {
        return true;
      }
      return false;
    }

    function getAll(){
      //Prepare statement untuk select * from table
      $stmt = $this->con->prepare("SELECT * FROM DataDiri");
      //Execute query
      $stmt->execute();
      //bind hasil dari eksekusi query ke variabel-variabel
      $stmt->bind_result($id, $username, $password);

      $userList = array();

      while ($stmt->fetch()) {
        $user = array();
        //Associate variabel2 bind result ke array
        $user['id'] = $id;
        $user['username'] = $username;
        $user['password'] = $password;

        array_push($userList, $user);
      }
      return $userList;
    }

    function update($id, $username, $password){
      //Prepare statement untuk query update
      $stmt = $this->con->prepare("UPDATE DataDiri SET username =?, password =? WHERE id =?");
      //Bind ? yang ada di query dengan variabel2 yang dikehendaki
      $stmt->bind_param("ssi", $username, base64_encode($password), $id);
      //Jika eksekusi query berhasil tanpa error
      if ($stmt->execute()) {
        return true;
      }
      return false;
    }

    function delete($id){
      //Prepare statement untuk query delete
      $stmt = $this->con->prepare("DELETE FROM DataDiri WHERE id=?");
      $stmt->bind_param("i", $id);
      if($stmt->execute()){
        return true;
      }
      return false;
    }

  }

 ?>
