package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailServiceImp implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final Context context;
    private final MimeMessage message;

    @Override
    public void sendEmail(String email, String otp) throws MessagingException {
        context.setVariable("code", otp);
        String processedString = templateEngine.process("email.html", context);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("coocon.dev@gmail.com");
        helper.setTo(email);
        helper.setSubject("Code Verify");
        helper.setText(processedString, true);
        javaMailSender.send(message);
    }
}
