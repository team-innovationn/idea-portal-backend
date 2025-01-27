//package com.ecobank.idea.service.impl;
//
//import com.ecobank.idea.service.EmailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailServiceImpl implements EmailService {
//    // Bean to handle email services
////    @Autowired
////    private final JavaMailSender mailSender;
//
//    @Override
//    public void sendVerificationEmail(String emailAddress, Object payload) {
//        String subject = "Email Verification";
//        String confirmationUrl = "http://localhost:8080/verify?token=" + String.valueOf(payload);
//        String message = "Please click the link below to verify your email address:\n" + confirmationUrl;
//
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(emailAddress);
//        email.setSubject(subject);
//        email.setText(message);
//
////        mailSender.send(email);
//    }
//}
