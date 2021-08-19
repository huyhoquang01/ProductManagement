<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset password</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header text-center">Reset password</div>
            <div class="card-body">
                <?php
                    $key = $_GET['key'];
                    $token = $_GET['token'];
                    if ($key && $token) {
                        
                        include_once '../controllers/user_controller.php';
                        $reset_token = (new UserController())->getByToken($token, $key);
                        if ($reset_token) { ?>
                           <form action="user_reset_password.php" method="POST">
                               <input type="hidden" name="email" value="<?php echo $key;?>">
                               <input type="hidden" name="token" value="<?php echo $token;?>">
                               <div class="form-group">
                                   <label for="">Password</label>
                                   <input type="password" class="form-control" name="password">
                               </div>
                               <div class="form-group">
                                   <label for="">Confirm Password</label>
                                   <input type="password" class="form-control" name="confirm_password">
                               </div>
                               <div class="form-group">
                                   <input type="submit" class="form-control btn btn-primary" value="Submit">
                               </div>
                           </form>
                        <?php }
                    else { ?>
                        <p>This link is not available</p>
                   <?php } } ?>
            </div>
        </div>
    </div>
</body>
</html>