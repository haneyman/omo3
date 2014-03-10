package com.omo.service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailViaSES {

    //static final String FROM = "omo@markhaney.net";   // Replace with your "From" address. This address must be verified.
    //static final String TO = "haneyman@yahoo.com";  // Replace with a "To" address. If you have not yet requested
    // production access, this address must be verified.

    //static final String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
    //static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

    // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
    static final String SMTP_USERNAME = "AKIAIT2EIN3IPS4ZXCDA";  // Replace with your SMTP username.
    static final String SMTP_PASSWORD = "ApNERvEUDq79h7R5AOA9OXGJ+6FYgG0bkzV6YuHggswA";  // Replace with your SMTP password.

    // Amazon SES SMTP host name. This example uses the us-east-1 region.
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";

    // Port we will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use
    // STARTTLS to encrypt the connection.
    static final int PORT = 25;

    public static void sendEmail(String subject, String body, String from, String to) throws Exception {

        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);

        // Set properties indicating that we want to use STARTTLS to encrypt the connection.
        // The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setContent(body, "text/html; charset=utf-8");

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try
        {
            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }




}