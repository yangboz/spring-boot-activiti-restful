package info.smartkit.eip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.smartkit.eip.dto.JsonObject;

//@see: http://www.javacodegeeks.com/2014/09/testing-mail-code-in-spring-boot-application.html
@RestController
public class MailSubmissionController {
	
	private final JavaMailSender javaMailSender;

    @Autowired
    MailSubmissionController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RequestMapping(value="/mail",method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.CREATED)
	public JsonObject send(
			@RequestParam(value = "to",defaultValue="someone@localhost") String to,
			@RequestParam(value = "replyTo",defaultValue="someone@localhost") String replyTo,
			@RequestParam(value = "from",defaultValue="someone@localhost") String from,
			@RequestParam(value = "subject",defaultValue="Lorem ipsum") String subject,
			@RequestParam(value = "body",defaultValue="Lorem ipsum dolor sit amet [...]") String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setReplyTo(replyTo);
        mailMessage.setFrom(from);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);
        return new JsonObject(mailMessage);
    }

}
