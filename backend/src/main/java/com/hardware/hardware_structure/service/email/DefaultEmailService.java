package com.hardware.hardware_structure.service.email;

import com.hardware.hardware_structure.core.configuration.EmailConfigurationProperties;
import com.hardware.hardware_structure.web.dto.email.EmailRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultEmailService implements EmailService {
    private final JavaMailSender emailSender;
    private final EmailConfigurationProperties mailProps;

    @Override
    public void sendSimpleEmail(EmailRequestDto emailRequestDto) {
        SimpleMailMessage message = createSimpleMailMessage(emailRequestDto);
        emailSender.send(message);
    }

    @Override
    @Async
    public void sendSimpleEmailAsync(EmailRequestDto emailRequestDto) {
        SimpleMailMessage message = createSimpleMailMessage(emailRequestDto);
        emailSender.send(message);
    }

    @Override
    public void sendEmailWithAttachments(EmailRequestDto emailRequestDto, List<MultipartFile> attachments)
            throws MessagingException {
        if (attachments == null) {
            attachments = Collections.emptyList();
        }
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(mailProps.getUsername());
        messageHelper.setTo(validateRecipient(emailRequestDto.getTo()));
        messageHelper.setSubject(emailRequestDto.getSubject());
        messageHelper.setText(emailRequestDto.getMessage());
        mimeMessage.setHeader("Content-Language", "ru");
        if (emailRequestDto.getFrom() != null && !emailRequestDto.getFrom().isBlank()) {
            messageHelper.setReplyTo(emailRequestDto.getFrom());
        }
        int counter = 1;
        for (MultipartFile file : attachments) {
            String filename = (file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
                    ? file.getOriginalFilename()
                    : "attachment_" + counter;
            messageHelper.addAttachment(filename, file);
            counter++;
        }
        emailSender.send(mimeMessage);
    }

    private SimpleMailMessage createSimpleMailMessage(EmailRequestDto emailRequestDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailProps.getUsername());
        simpleMailMessage.setTo(validateRecipient(emailRequestDto.getTo()));
        simpleMailMessage.setSubject(emailRequestDto.getSubject());
        simpleMailMessage.setText(emailRequestDto.getMessage());
        if (emailRequestDto.getFrom() != null && !emailRequestDto.getFrom().isBlank()) {
            simpleMailMessage.setReplyTo(emailRequestDto.getFrom());
        }
        return simpleMailMessage;
    }

    private String validateRecipient(String to) {
        if (to == null || to.isBlank()) {
            throw new IllegalArgumentException("Recipient email (to) must be specified");
        }
        return to;
    }
}
