package com.example.Rebository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.example.Entity.ConfirmationToken;
import com.example.Entity.Users;

@Repository
public interface ConfirmationTokenRebository extends JpaRepository<ConfirmationToken,Long> {

    ConfirmationToken findByConfirmationtoken(String confirmationtoken); 
    
}
