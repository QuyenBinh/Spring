package com.example.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.Entity.Users;
import com.example.Rebository.UserRebository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceimpls implements UserDetailsService{
    @Autowired
    private UserRebository userRebository;
    @Autowired
    private UsersDetailsimpl userDetailimpl;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user  = userRebository.findByUsername(username); 
        if(user == null)    {
            throw new UsernameNotFoundException("Khong tim thay user");
        }
        return userDetailimpl.build(user);

    }

}
