package com.example.UserSevice;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.example.Entity.Users;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public List<Users> listUsers();
    public Users addUser(Users user);
    public Users getUser(long id);
    public void SendingEmail(Users users) throws UnsupportedEncodingException, MessagingException;
    // public boolean isEnable(Users user);

}
