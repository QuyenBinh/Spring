package com.example.DTO;

import java.util.Set;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.example.Entity.Role;
import com.example.Entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.Entity.Post;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();
    @JsonIgnore
    private Collection<Post> post;
    @CreationTimestamp 
    private Date createat;
    @UpdateTimestamp
    private Date updateat;

    public UserDTO(String username, String pass, String email)  {
        this.username = username;
        this.password = pass;
        this.email = email;
    }

    public UserDTO(Users user)  {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

}
