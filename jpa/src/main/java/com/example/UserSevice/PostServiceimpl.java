package com.example.UserSevice;

import java.util.ArrayList;
import java.util.List;

import com.example.Entity.Post;
import com.example.Rebository.PostRebository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PostServiceimpl implements PostService{
    
    public ArrayList<Post> list = new ArrayList<>();
    @Autowired
    private PostRebository postRebository;
    @Override
    public Post create(Post post) {
        return postRebository.save(post);   
    }
    @Override
    public List<Post> list() {
        List<Post> list = postRebository.findAll();
        return list;
    }
    @Override
    @Scheduled(cron = "0 0 0 * * *") // second, minute, hour, day of month, month, day(s) of week
    public void deletePostNotActive() {
        List<Post> list = postRebository.findAll();
        for(Post post : list)   {
            if(!post.isIsenabled())   {
                postRebository.deleteById(post.getId());
            }
        }
    }
    
}
