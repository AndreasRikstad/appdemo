package andrzej.appdemo.emailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("emailSender")
public class EmailSenderImpl implements EmailSender {
	
	@Autowired
    private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String to, String subject, String content) {
		MimeMessage mail = javaMailSender.createMimeMessage();
		try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setFrom("noreply@appdemo.net");
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
	
	}
}
