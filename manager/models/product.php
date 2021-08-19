<?php
    class Product{
        private $id;
        private $product_name;
        private $price;
        private $image_url;
        private $category_id;

        function __construct($id, $product_name, $price, $image_url, $category_id)
        {
            $this->id = $id;
            $this->product_name = $product_name;
            $this->price = $price;
            $this->image_url = $image_url;
            $this->category_id = $category_id;
        }

        public function getId()
        {
            return $this->id;
        }
        public function getProductName()
        {
            return $this->product_name;
        }
        public function getPrice()
        {
            return $this->price;
        }
        public function getImageUrl()
        {
            return $this->image_url;
        }
        public function getCategoryId()
        {
            return $this->category_id;
        }
    }
?>