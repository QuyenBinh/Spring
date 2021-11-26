package com.example.UserSevice;

import org.springframework.stereotype.Service;

import java.util.List;

import com.example.Entity.Post;

@Service
public interface PostService {

    public Post create(Post post);  
    public List<Post> list();
    public void deletePostNotActive();
    
}
