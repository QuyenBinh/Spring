package com.example.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Entity.Post;
import com.example.Entity.Users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Component
public class UsersDetailsimpl implements UserDetails{
    private static final long serialVersionUID = 1L; //trước và sau khi chuyển đổi, lớp UsersDetailsimpl là một

	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;
    private boolean isEnabled;
    private Collection<Post> post;

	private Collection<? extends GrantedAuthority> authorities;
    public UsersDetailsimpl(Long id,String name, String email, String pass,boolean isEnabled,Collection<? extends GrantedAuthority> author )    {

            this.id = id;
            this.username = name;
            this.password = pass;
            this.email = email;
            this.isEnabled = isEnabled;
            this.authorities = author;

    }
    public static UsersDetailsimpl build(Users user) {

        List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
        return new UsersDetailsimpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),user.isEnabled(), authorities);

    }
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // @Override
    // public boolean isEnabled() {
    //     return false;
    // }
    
}
