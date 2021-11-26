package com.example.UserSevice;

import com.example.Entity.ConfirmationToken;
import com.example.Entity.Users;
import com.example.Rebository.ConfirmationTokenRebository;
import com.example.Rebository.UserRebository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

   @Autowired
   private ConfirmationTokenRebository confirmationTokenRebository;
   @Autowired 
   private JavaMailSender javaMailsender;

   public SimpleMailMessage SetEmail(Users user)    {
       
       //Users users =  userRebository.findByEmail(user.getEmail());
       ConfirmationToken confirmationToken = new ConfirmationToken(user);
       confirmationTokenRebository.save(confirmationToken);
       SimpleMailMessage mail = new SimpleMailMessage();
       mail.setTo(user.getEmail());
       mail.setSubject("Mail kích hoạt tài khoản");
       mail.setText(" Vui lòng click vào link: " + "http://localhost:8082/confirm-account?token="+ confirmationToken.getConfirmationtoken());
       return mail; 
        
   }

   @Async
   public void sendEmail(SimpleMailMessage email)  {
        javaMailsender.send(email);
   }

}
