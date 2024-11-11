package utils;

import models.EmpresaProveedora;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendEmail {
    public static void enviarCorreo(EmpresaProveedora em) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        final String username = "persointegrado@gmail.com";
        final String password = "roexqxvkhkikvsbh";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("persointegrado@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("persointegradouser@gmail.com\n"));
            message.setSubject("Creacion de contrato");
            message.setText(em.toString());
            Transport.send(message);
            System.out.println("Correo enviado exitosamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
