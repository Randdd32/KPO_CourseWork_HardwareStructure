package com.hardware.hardware_structure.service.email;

import com.hardware.hardware_structure.service.AbstractIntegrationTest;
import com.hardware.hardware_structure.web.dto.email.EmailRequestDto;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultEmailServiceTests extends AbstractIntegrationTest {

    @Autowired
    private DefaultEmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void sendSimpleEmailTest() {
        EmailRequestDto request = new EmailRequestDto();
        request.setTo("recipient@example.com");
        request.setSubject("Test Subject");
        request.setMessage("Hello World");
        request.setFrom("sender@example.com");

        emailService.sendSimpleEmail(request);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        Assertions.assertNotNull(sentMessage.getTo());
        Assertions.assertEquals("recipient@example.com", sentMessage.getTo()[0]);
        Assertions.assertEquals("Test Subject", sentMessage.getSubject());
        Assertions.assertEquals("Hello World", sentMessage.getText());
        Assertions.assertEquals("sender@example.com", sentMessage.getReplyTo());
    }

    @Test
    void sendEmailWithAttachmentsTest() throws Exception {
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        EmailRequestDto request = new EmailRequestDto();
        request.setTo("recipient@example.com");
        request.setSubject("With Attachment");
        request.setMessage("Check this file");

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "content".getBytes()
        );

        emailService.sendEmailWithAttachments(request, List.of(file));

        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    void validateRecipientTest() {
        EmailRequestDto request = new EmailRequestDto();
        request.setTo("");

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                emailService.sendSimpleEmail(request)
        );
    }
}
