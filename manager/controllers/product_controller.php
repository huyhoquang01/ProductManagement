<?php

    include_once '../services/product_service.php';

    class ProductController {

        private $product_service;
        public function __construct()
        {
            $this->product_service = new ProductService();        
        }

        public function getAllProducts()
        {
            return $this->product_service->getAllProducts();            
        }

        public function getById($id)
        {
            return $this->product_service->getById($id);            
        }

        public function getAllCategories()
        {
            return $this->product_service->getAllCategories();            
        }

        public function insert($product)
        {
            return $this->product_service->insert($product);            
        }

        public function update($product)
        {
            return $this->product_service->update($product);            
        }
        public function delete($id)
        {
            return $this->product_service->delete($id);
        }
    }
?>