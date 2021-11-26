package com.example.Controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.example.Entity.Users;
import com.example.Exception.ErrorResponse;
import com.example.Exception.ExceptionValid;
import com.example.UserSevice.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class RegisterController {
    @Autowired
    private UserService userService;
    private ErrorResponse errorResponse; 
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Users entity,BindingResult bindingResult) throws UnsupportedEncodingException, MessagingException{
        if(bindingResult.hasErrors()) {
            throw new ExceptionValid("Nhap lai");
        }
        Users user = userService.addUser(entity);
        userService.SendingEmail(user);
        return ResponseEntity.status(200).body(user);
    }

}
