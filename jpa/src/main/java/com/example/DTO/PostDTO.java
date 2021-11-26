package com.example.DTO;

import com.example.Entity.Post;
import com.example.Entity.Users;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private long id; 
    private String title;
    private String content;
    private long iduser;
    private Users user;
    public PostDTO(Post post)    {

        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.iduser = post.getUser().getId();

    }
    public PostDTO(long id, String title, String content)   {

        this.id = id;
        this.content = content;
        this.title = title;

    }
}
