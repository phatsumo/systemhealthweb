/**
 *
 */
package systemhealth.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to send email using Javamail.
 *
 * @author 1062992
 *
 */
public class EmailNotifier {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EmailNotifier.class);

    String username = "xxxx";
    String password = "xxxx";

    Session session = null;
    Message message = null;

    /**
     * Default constructor
     */
    public EmailNotifier() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    }

    /**
     *
     * @param msg
     * @param from
     * @param recipients
     * @param subject
     */
    public void sendMail(String msg, String from, String recipients,
            String subject) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipients));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

            LOGGER.debug("Mail sent.");

        } catch (MessagingException e) {
            LOGGER.error("Failed to send message", e);
            throw new RuntimeException(e);
        }
    }

}
