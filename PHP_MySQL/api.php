<?php

  require_once dirname(__FILE__) . "/crud.php";

  $response  = array();

  //Jika ke-set GET dengan apicall
  if (isset($_GET['apicall'])) {
    switch ($_GET['apicall']) {

      case 'create':
        $db = new crud();
        $create = $db->create(
          $_POST['username'],
          $_POST['password']
        );

        //Jika proses create berhasil
        if ($create){
          $response['error'] = false;
          $response['message'] = "Insert success!";
          $response['users'] = $db->getAll();
        } else{
          $response['error'] = true;
          $response['message'] = "Insert failed!";
        }
        break;

      case 'getAll':
        $db = new crud();
        $response['error'] = false;
        $response['message'] = "Get data success!";
        $response['users'] = $db->getAll();
        break;

        case 'update':
          $db = new crud();
          //Invoke method update dengan parameter id, username, dan password diambil dari POST
          $update = $db->update(
            $_POST['id'],
            $_POST['username'],
            $_POST['password']
          );
          //Jika berhasil eksekusi method update
          if ($update) {
            $response['error'] = false;
            $response['message'] = "Update success!";
            $response['users'] = $db->getAll();
          } else{
            $response['error'] = true;
            $response['message'] = "Update failed!";
          }
          break;

        case 'delete':
          $db = new crud();
          $delete = $db->delete(
            $_POST['id']
          );
          if ($delete) {
            $response['error'] = false;
            $response['message'] = "Delete success!";
            $response['users'] = $db->getAll();
          } else{
            $response['error'] = true;
            $response['message'] = "Delete failed!";
            $response['users'] = $db->getAll();
          }

          break;

      default:
        # code...
        break;
    }
  }
  echo json_encode($response);

 ?>
