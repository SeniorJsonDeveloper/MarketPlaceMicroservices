package dn.mp_notifications.domain.configuration;

import dn.mp_notifications.api.dto.EmailDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class EmailConfig {

    private final JavaMailSender javaMailSender;

    public void sendEmail(EmailDto emailDto){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(emailDto.getPhoneNumber());
            mimeMessageHelper.setSubject(emailDto.getSenderId());
            mimeMessageHelper.setText((String) emailDto.getMessage(),true);
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            log.error("Exception from trying to send email: {}" ,e.getMessage());
        }
    }




}
