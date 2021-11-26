package com.example.Rebository;

import java.util.List;

import com.example.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRebository extends JpaRepository<Users,Long>{

   Users findByUsername(String name);  
   Boolean existsByUsername(String username); // existsByUsername
   Boolean existsByEmail(String email);
   Users findByEmail(String email);
   Users findById(long id);

   @Query("SELECT e FROM Users e WHERE e.id = :id and e.username = :name")
   Users findByIdAndUsername(@Param("id") Long id,@Param("name") String name);
   
}

