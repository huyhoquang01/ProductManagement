<?php
    include_once '../configs/database_config.php';
    include_once '../models/user.php';
    include_once '../models/reset.php';


    class ProductService {
        private $connection;
        private $tblproducts = "tblProducts";
        private $tblCategories = "tblCategories";

        public function __construct()
        {
            $this->connection = (new DatabaseConfig())->getConnection();
        }

        public function getAllProducts()
        {
            try {
                $query = "SELECT id, product_name, price, image_url, category_id
                                 FROM " . $this->tblproducts ." order by id desc ";
                $stmt = $this->connection->prepare($query);
                
                $stmt->execute();
                if ($stmt->rowCount() > 0) {
                    $products = array();
                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                        extract($row);
                        $product = array(
                            "id" => $id,
                            "product_name" => $product_name,
                            "price" => $price,
                            "image_url" => $image_url,
                            "category_id" => $category_id,
                        );
                        array_push($products, $product);
                    }
                    return $products;
                }                
            } catch (Exception $e) {}
            return null;
        }

        public function getById($id)
        {
            try {
                $query = "SELECT id, product_name, price, image_url, category_id
                                 FROM " . $this->tblproducts ." where id=:id ";
                $stmt = $this->connection->prepare($query);
                $stmt->bindParam(":id", $id);
                $stmt->execute();
                if ($stmt->rowCount() > 0) {
                    $row = $stmt->fetch(PDO::FETCH_ASSOC);
                    extract($row);
                    $product = array(
                        "id" => $id,
                        "product_name" => $product_name,
                        "price" => $price,
                        "image_url" => $image_url,
                        "category_id" => $category_id,
                    );                    
                    return $product;
                }                
            } catch (Exception $e) {                
            }
            return null;
        }


        public function getAllCategories()
        {
            try {
                $query = "SELECT id, category_name
                                 FROM " . $this->tblCategories ."  ";
                $stmt = $this->connection->prepare($query);
                
                $stmt->execute();
                if ($stmt->rowCount() > 0) {
                    $categories = array();
                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                        extract($row);
                        $cat = array(
                            "id" => $id,
                            "category_name" => $category_name,
                        );
                        array_push($categories, $cat);
                    }
                    return $categories;
                }                
            } catch (Exception $e) {                
            }
            return null;
        }


        public function insert($product)
        {
            try {
                $query = "INSERT INTO " . $this->tblproducts ." 
                        SET product_name = :product_name, price = :price, 
                        image_url=:image_url, category_id=:category_id
                                        ";
                $stmt = $this->connection->prepare($query);
                
                $product_name = $product->getProductName();
                $price = $product->getPrice();
                $image_url = $product->getImageUrl();
                $category_id = $product->getCategoryId();

                $stmt->bindParam(":product_name", $product_name);
                $stmt->bindParam(":price", $price);
                $stmt->bindParam(":image_url", $image_url);
                $stmt->bindParam(":category_id", $category_id);

                $this->connection->beginTransaction();
                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (Exception $e) {}
            return false;
        }


        public function update($product)
        {
            try {
                $query = "UPDATE " . $this->tblproducts ." 
                        SET product_name = :product_name, price = :price, 
                        image_url=:image_url, category_id=:category_id
                        WHERE id=:id ";
                $stmt = $this->connection->prepare($query);
                
                $product_name = $product->getProductName();
                $price = $product->getPrice();
                $image_url = $product->getImageUrl();
                $category_id = $product->getCategoryId();
                $id = $product->getId();

                $stmt->bindParam(":product_name", $product_name);
                $stmt->bindParam(":price", $price);
                $stmt->bindParam(":image_url", $image_url);
                $stmt->bindParam(":category_id", $category_id);
                $stmt->bindParam(":id", $id);

                $this->connection->beginTransaction();
                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (Exception $e) {                
            }
            return false;
        }

        public function delete($id)
        {
            try {
                $query = "DELETE FROM " . $this->tblproducts ."
                        WHERE id=:id ";
                $stmt = $this->connection->prepare($query);
                $stmt->bindParam(":id", $id);

                $this->connection->beginTransaction();
                if ($stmt->execute()) {
                    $this->connection->commit();
                    return true;
                } else {
                    $this->connection->rollBack();
                    return false;
                }
            } catch (Exception $e) {                
            }
            return false;
        }

    }
?>