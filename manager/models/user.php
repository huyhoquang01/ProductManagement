<?php
    class User{
        private $id;
        private $email;
        private $hash_password;

        function __construct($id, $email, $hash_password)
        {
            $this->id = $id;
            $this->email = $email;
            $this->hash_password = $hash_password;
        }

        public function getId()
        {
            return $this->id;
        }
        public function getEmail()
        {
            return $this->email;
        }
        public function getHashPassword()
        {
            return $this->hash_password;
        }
    }
?>