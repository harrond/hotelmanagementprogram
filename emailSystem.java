import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

public class emailSystem {
	/*
	 * This class to send email stores the information required to send e-mail to send an e-mail
	 * @author SugilAhn(안수길)
	 */
	private static final String mailServer = "smtp.gmail.com"; //For email server (user gmail smtp email service)
	private static final String Id = "ask5742@gmail.com";      //The sender's email address
	private static final String passwd = "gnpvnp34";           //         "         password
	class EmailAuthenticator extends Authenticator {
		// this define id and pw to check proof of E-mail address
		private String id;
		private String pw;

		public EmailAuthenticator(String id, String passwd) {
			this.id = id;
			this.pw = passwd;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id, pw);
		}
	}

	public void sendEmail(String from, String to, String subject, String content) {
		//The method for send email
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", mailServer);
		props.put("mail.smtp.auth", "true");

		EmailAuthenticator authenticator = new EmailAuthenticator(Id, passwd);

		Session session = Session.getInstance(props, authenticator);

		try {
			Message msg = new MimeMessage(session); 
			
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); 
			//to check recipient's email address 
			msg.setSubject(subject); // email's title
			msg.setContent(content, "text/html; charset=EUC-KR"); // email's content 
			//To set content's charset
			msg.setSentDate(new Date());// From date

			Transport.send(msg); // send email
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
