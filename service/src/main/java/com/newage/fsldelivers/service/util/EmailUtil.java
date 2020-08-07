package com.newage.fsldelivers.service.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newage.fsldelivers.model.User;
import com.newage.fsldelivers.service.exception.ServiceErrorCode;
import com.newage.fsldelivers.service.exception.ServiceErrorMessage;
import com.newage.fsldelivers.service.exception.ServiceException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EmailUtil {
	
	@Value("${fsldelivey.smtp.port}")
	String port;

	@Value("${fsldelivey.smtp.host}")
	String serverHost;

	@Value("${fsldelivey.smtp.fromEmailId}")
	String fromMailId;

	@Value("${fsldelivey.smtp.fromEmailPassword}")
	String fromMailPassword;
	
	public void sendEmail(String email) {
		sendEmail(email,null);
	}

	public void sendEmail(String email,User user) {

		try {
			Session session = setPropertiesAndGetSession();

			// creates a new e-mail message
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(fromMailId));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			msg.setSubject("Reset your password");
			msg.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			String resetLink=resetpasswordTemplate(user);
			messageBodyPart.setContent(resetLink, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			log.info("Mail is sent to : " + email);
			Transport.send(msg);
		} catch (MessagingException e) {
			log.error("Error in sending Email : " + email + ", " + fromMailId, e);
		} catch (Exception e) {
			log.error("Exception in sending Email : " + email + ", " + fromMailPassword, e);
			throw new ServiceException(ServiceErrorCode.EMAIL_TRIGGER_FAILED, ServiceErrorMessage.EMAIL_TRIGGER_FAILED);
		}
	}

	private Session setPropertiesAndGetSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", serverHost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);

		return Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromMailId, fromMailPassword);
			}
		});
	}
	
	private String resetpasswordTemplate(User user) {
		String html="<td style=\"border-collapse:collapse\">Hi "+user.getFirstName() +",<br><br>\n" + 
				"You requested to reset the password for your FSL Deliver account with the e-mail address (<a href=\"mailto:"+user.getEmail()+"\" target=\"_blank\">"+user.getEmail()+"</a>).\n" + 
				" Please click this link to reset your password.<br><br><a href=\"https://www.fsldelivery.com/settings/reset_password?code=\""+user.getResetCode()+"\" \n" + 
				" style=\"color:#2b6dad;text-decoration:none\" target=\"_blank\">\n" + 
				" https://www.quora.com/<wbr>settings/reset_password?code=<wbr>onldY3DGScz9Yenl442dFouaQdoDII<wbr>tr</a><br><br>\n" + 
				"<br><br>Thanks,<br>\n" + 
				" The FSL Deliver Team<br></td>";
		return html;
	}
}
