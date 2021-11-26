package com.example.Rebository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Entity.ERole;
import com.example.Entity.Role;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleRebository extends JpaRepository<Role,Integer>{

    Optional<Role> findByName(ERole role);
    
}
