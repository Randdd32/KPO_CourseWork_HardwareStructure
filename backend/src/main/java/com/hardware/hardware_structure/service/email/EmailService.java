package com.hardware.hardware_structure.service.email;

import com.hardware.hardware_structure.web.dto.email.EmailRequestDto;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface EmailService {
    void sendSimpleEmail(EmailRequestDto emailRequestDto);

    void sendSimpleEmailAsync(EmailRequestDto emailRequestDto);

    void sendEmailWithAttachments(EmailRequestDto emailRequestDto, List<MultipartFile> attachments) throws MessagingException;
}
