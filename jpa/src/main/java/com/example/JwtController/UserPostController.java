package com.example.JwtController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.DTO.PostDTO;
import com.example.DtoMapper.PostDtoMapper;
import com.example.Entity.Post;
import com.example.Entity.Users;
import com.example.Rebository.PostRebository;
import com.example.Rebository.UserRebository;
import com.example.Response.MessageResponse;
import com.example.Service.UserDetailServiceimpls;
import com.example.Service.UsersDetailsimpl;
import com.example.UserSevice.PostService;
import com.example.UserSevice.PostServiceimpl;
import com.example.UserSevice.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
public class UserPostController {
    @Autowired
    private PostService postServiceimpl;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRebository userRebository;
    @Autowired
    private UserDetailServiceimpls userDetailServiceimpls;
    @Autowired
    private PostRebository postRebository;
    @Autowired
    private PostDtoMapper postmapper;
    @PostMapping("/post")
    public ResponseEntity<?> Post(@RequestBody Post post)    {

            UsersDetailsimpl userdetail = (UsersDetailsimpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // lasy ra user hien tai
            Users user = userRebository.findByUsername(userdetail.getUsername());
            PostDTO postdto = new PostDTO();
            postdto.setTitle(post.getTitle());
            postdto.setContent(post.getContent());
            postdto.setUser(user);
//            Post p =  new Post();
//            p.setTitle(post.getTitle());
//            p.setContent(post.getContent());
//            p.setUser(user);
            Post p = postmapper.coverDtoToEntity(postdto);
            postServiceimpl.create(p);
            user.setPost(Collections.singleton(p));
            userRebository.save(user);
            return ResponseEntity.ok(p);

    }
    @GetMapping("/listpost")
    public ResponseEntity<?> List()  {

        List<Post> list = new ArrayList<>();
        list = postServiceimpl.list();
        return ResponseEntity.ok().body(list);

    }
    @GetMapping("/post/{id}")
    public ResponseEntity<?> PostId(@PathVariable("id") long id)   {
        UsersDetailsimpl userdetail = (UsersDetailsimpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // lasy ra user hien tai
        Users user = userRebository.findByUsername(userdetail.getUsername());
        List<Post> list = postRebository.findByIdUser(user);
        Post p = null;
        for(Post post : list)   {
            if(post.getId() == id) {
                p = post;
                break;
            }
        }
        PostDTO postdto = postmapper.covertEntityToDto(p);
        return ResponseEntity.ok(p);

    }
}
