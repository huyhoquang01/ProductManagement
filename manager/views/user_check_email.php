<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
$data = json_decode(file_get_contents("php://input"));

include_once '../controllers/user_controller.php';

if (filter_var($data->email, FILTER_VALIDATE_EMAIL)) {
    $status = (new UserController())->checkEmailExist($data->email);
    if ($status) {
        echo 1;
    } else {
        echo 0;
    }
}else {
    echo json_encode(
        array(
            "status" => false,
            "message" => "email is not valid."
        ));
}
?>