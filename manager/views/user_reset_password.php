<?php 
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../controllers/user_controller.php';

$email = $_POST['email'];
$token = $_POST['token'];
$password = $_POST['password'];
$confirm_password = $_POST['confirm_password'];

if ($email && $token && $password == $confirm_password) {
    $status = (new UserController())->updatePasswordAndToken($token, $email, $password);
    if ($status) {
        http_response_code(200);
        echo json_encode(array(
            "status" => true,
            "message" => "Success"
        ));
    } else {
        http_response_code(404);
        echo json_encode(array(
            "status" => false,
            "message" => "Failed"
        ));
    }
}
else {
    http_response_code(404);
    echo json_encode(array(
        "status" => false,
        "message" => "Failed"
    ));
}

?>