<?php

require_once('vendor/autoload.php');

/**
 * Created by PhpStorm.
 * User: richardlam
 * Date: 4/25/17
 * Time: 12:34 PM
 */
class EmailController {

    public static function sendEmail($api, $recipient, $message) {
        $token = $api->authentication();
        $username = $token->data->name;

        if ($recipient && $message) {
            $emailQuery = "SELECT email FROM account WHERE accountName=?";
            $email = '';
            $headers = "From: admin@securechat4.me";

            if ($stmt = $api->getConnection()->prepare($emailQuery)) {
                $stmt->bind_param("s", $recipient);
                $stmt->execute();
                $stmt->store_result();
                $numRows = $stmt->num_rows;
                $stmt->bind_result($email);
                $stmt->fetch();
                $stmt->close();

                if ($numRows == 1) {
                    //PHPMailer Object
                    $mail = new PHPMailer();

                    // set mailer to use SMTP
                    $mail->isSMTP();

                    // As this email.php script lives on the same server as our email server
                    // we are setting the HOST to localhost
                    $mail->Host = "mail.privateemail.com";  // specify main and backup server

                    $mail->SMTPAuth = true;     // turn on SMTP authentication

                    // When sending email using PHPMailer, you need to send from a valid email address
                    // In this case, we setup a test email account with the following credentials:
                    // email: send_from_PHPMailer@bradm.inmotiontesting.com
                    // pass: password
                    $mail->Username = "admin@securechat4.me";  // SMTP username
                    $mail->Password = "cia478"; // SMTP password

                    $mail->SMTPSecure = 'tls';                            // Enable TLS encryption, `ssl` also accepted
                    $mail->Port = 587;

                    //From email address and name
                    $mail->From = "admin@securechat4.me";
                    $mail->FromName = "SecureChat Admin";

                    //To address and name
                    $mail->addAddress($email); //Recipient name is optional

                    //Address to which recipient will reply
                    $mail->addReplyTo("admin@securechat4.com", "Reply");

                    //Send HTML or Plain Text email
                    $mail->isHTML(false);

                    $mail->Subject = "SecureChat - " . $username . "'s Public Key";
                    $mail->Body = $message;
                    //$mail->AltBody = "This is the plain text version of the email content";

                    if($mail->send()) {
                        $responseArray = array(
                            'response' => '0',
                        );
                    } else {
                        echo "Mailer Error: " . $mail->ErrorInfo;
                        $responseArray = array(
                            'response' => '2',
                        );
                    }
                } else {
                    $responseArray = array(
                        'response' => '1',
                    );
                }
            } else {
                $responseArray = array(
                    'response' => '-1',
                );
            }
        } else {
            $responseArray = array(
                'response' => '1',
            );
        }

        echo(json_encode($responseArray));
    }
}