<?php
    class DatabaseConfig {
        private $host = "localhost";
        private $username = "root";
        private $password = "";
        private $database_name = "SHOPPING";

        public $connection;

        public function getConnection(){
            $this->connection = null;
            try {
                $this->connection = new PDO("mysql:host=" . $this->host .
                                            "; dbname=" . $this->database_name,
                                            $this->username, $this->password);
                $this->connection->exec("set names utf8");
            } catch (Exception $e) {   
                echo "Ket noi database khong thanh cong: " .$e->getMessage();     
            }
            return $this->connection;
        }

    }
?>