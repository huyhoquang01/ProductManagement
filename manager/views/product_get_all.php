<?php 
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
$data = json_decode(file_get_contents("php://input"));

include_once '../controllers/product_controller.php';

$products = (new ProductController())->getAllProducts();
    if ($products) {
        http_response_code(200);
        echo json_encode($products); 
    } else {
        http_response_code(200);
        echo json_encode(array());
    }
?>