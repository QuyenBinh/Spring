package com.example.Rebository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Entity.Post;
import com.example.Entity.Users;

@Repository
public interface PostRebository extends JpaRepository<Post, Long>{

    Post findById(long id);
    @Query("SELECT e from Post e where e.user = :user ")
    List<Post> findByIdUser(Users user);
    @Query("select e from Post e where e.isenabled = 0")
    List<Post> findByIsnabled();
    @Query("select e from Post e where e.user = :user")
    List<Post> findByUser(Users user);

}
