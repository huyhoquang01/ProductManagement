<?php
class FCM
{
    public function sendNotification($_title= "", $_body = "")
    {
        $key = "AAAAifyqVPI:APA91bGnPqNPhImqliG_6_huH0W1YAdEbSm60F9DIn0M5F1ccUwmWU7gZgmL2QodegKtzSu77Z0Rw2ClViRRu3G3GkYzE1vhY1wbU4PFdDERlbmWokURLCskX2ujRjQePW5Il7SkwY7r";
        ini_set("allow_url_open", "On");
        $data = [
            "to" => "fSI6S1yCQkmnvundjyQivO:APA91bFMO4Rlws5GPHmvv0-LoyPgnEJqBsYR7sDa2BU1unIZcllqOhKwNFumtHecz7UEIkHZcy_glJCLZ8aURRYc1YJFoAuy3QZ7wfPzlEKqdZF7ROSngPuACxodvzBu1VLUPHfPTofZ",
            "notification" => [
                "body" => $_body,
                "title" => $_title
            ]
        ];

        $option = array(
            'http' => array(
                'method' => 'POST',
                'content' => json_encode($data),
                'header' => "Content-Type: application/json\r\n" . 
                            "Accept: application/json\r\n" . 
                            "Authorization:key=".$key
            )
        );
        $context = stream_context_create($option);
        $result = file_get_contents("https://fcm.googleapis.com/fcm/send", false, $context);
        return json_encode($result);
    }
}



?>