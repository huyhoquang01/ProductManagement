<?php 
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$data = json_decode(file_get_contents("php://input"));

include_once '../controllers/product_controller.php';
include_once '../models/product.php';

$product_name = $data->product_name;
$price = $data->price;
$image_url = $data->image_url;
$category_id = $data->category_id;
// validation

if ($product_name && $price && $image_url && $category_id) {
    $product = new Product(-1, $product_name, $price, $image_url, $category_id);
    $status = (new ProductController())->insert($product);
    if ($status) {
        http_response_code(200);
        echo json_encode(
            array(
                "status" => true,
                "message" => "Insert success"
            )
        ); 
    } else {
        http_response_code(404);
        echo json_encode(
            array(
                "status" => false,
                "message" => "Insert failed"
            )
        );
    }
}
else {
    http_response_code(404);
    echo json_encode(
        array(
            "status" => false,
            "message" => "Insert failed"
        )
    );   
}

?>