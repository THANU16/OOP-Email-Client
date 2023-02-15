package EmailClient.com;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Mail implements Serializable {


    private LocalDateTime todayDate = LocalDateTime.now();
    private String toMailID;
    private String subject;
    private String body;
    private LocalDate sendMailDate;
    public Mail( String toMailID, String subject, String body) {
        this.toMailID = toMailID; // set Recipient's email ID
        this.subject = subject;  // set email subject
        this.body = body;  // set the email body
        sendMailDate = LocalDate.from(todayDate);  //set the today date
    }

    //mail is send when the method is call
    public void sendMail(){
        final String username = "thanushanthk16@gmail.com";
        final String password = "lnskjdvohvvkjojp";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kanagarajahthanushanth@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toMailID)
            );
            message.setSubject(subject);  // set the subject of sending mail
            message.setText(body);  // set the sbody of sending mail

            Transport.send(message);

            System.out.println("mail sending is finished");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //get the date of sending mail
    public LocalDate getSendMailDate (){
        return sendMailDate;
    }

    //get the subject of mail
    public String getSubject(){
        return subject;
    }

    //get the body of mail
    public String getBody(){
        return body;
    }

    //get the to mail address
    public String getToMailID(){
        return toMailID;
    }


}
