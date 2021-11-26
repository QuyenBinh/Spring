package com.example.UserSevice;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.example.Entity.Role;
import com.example.Entity.Users;
import com.example.Rebository.UserRebository;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserSeviceimpl implements UserService{

    public static ArrayList<Users> users = new ArrayList();
    private UserRebository userRebository;
    @Autowired
    public void setUserRebository(UserRebository userRebository)    {
        this.userRebository = userRebository;
    }
    @Autowired
    private JavaMailSender javamailSender;

    // public PasswordEncoder passwordEncoder;
    // static {
    //     users.add(new Users((long) 1,"Tran van a","a@gmail.com","12345678"));
    //     users.add(new Users((long) 2,"Tran van b","b@gmail.com","12345678"));
    //     users.add(new Users((long) 3,"Tran van c","c@gmail.com","12345678"));
    //     users.add(new Users((long) 4,"Tran van d","d@gmail.com","12345678"));
    //     users.add(new Users((long) 5,"Tran van e","e@gmail.com","12345678"));
    //     users.add(new Users((long) 6,"Tran van f","f@gmail.com","12345678"));
    // }

    @Override
    public Users addUser(Users user)  {
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRebository.save(user);
    } 
    @Override
    public List<Users> listUsers() {
        return userRebository.findAll();
    }
    @Override
    public Users getUser(long id) {
        Users user = userRebository.findById(id);
        return user;
    }

    @Override
    public void SendingEmail(Users user) throws UnsupportedEncodingException, MessagingException {
        String toAddress = user.getEmail();
        String fromAddress = "vanquyen4789@gmail.com";
        String senderName = "qqqqq";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "Your company name.";
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("binhquyen47@gmail.com");
        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");
        javamailSender.send(msg);
    }

}






















    // public Users build(Users user)  {
    //         List<Role> roles = user.getRole().stream().map(role->new SimpleGrantedAuthority(role.getName_role())).collect(Collectors.toList());
    //         return new Users((long)user.getId(),user.getUserName(),user.getEmail(),user.getPassword(),roles);
    // }

    // @Override
    // public boolean isEnable(Users user) {
    //     return user.isEnable();
    // }
    

