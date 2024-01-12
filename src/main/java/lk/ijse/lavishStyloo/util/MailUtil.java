package lk.ijse.lavishStyloo.util;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailUtil {
    public static void sendEmail(String mail,String yourSubject,String mailBody,String orderId) {
        String to = mail;
        System.out.println("Process to Mail");
        String from = "otpgenarete@gmail.com";
        final String username = "otpgenarete";//change accordingly
        final String password = "rkukvsgwijhhqvyi";//change accordingly

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.gmail.com");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(yourSubject);

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
           BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = mailBody;
            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            //multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            //messageBodyPart = new MimeBodyPart();
          //  System.out.println("1");
          //  DataSource fds = new FileDataSource("C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\PrintPDF\\" +orderId+".pdf");

           // messageBodyPart.setDataHandler(new DataHandler(fds));
          //  messageBodyPart.setHeader("Content-ID", "<pdf>");

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String filename = "C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\PrintPDF\\" +orderId+".pdf";//change accordingly
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(messageBodyPart2);

            // put everything together
            message.setContent(multipart);
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendEmail(String mail,String yourSubject,StringBuffer mailBody) {
        String to = mail;
        System.out.println("Process to Mail");
        String from = "otpgenarete@gmail.com";
        final String username = "otpgenarete";//change accordingly
        final String password = "rkukvsgwijhhqvyi";//change accordingly

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.gmail.com");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(yourSubject);

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = String.valueOf(mailBody);
            messageBodyPart.setContent(htmlText, "text/html");
            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);
            // put everything together
            message.setContent(multipart);
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
