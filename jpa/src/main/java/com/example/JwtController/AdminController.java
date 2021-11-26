package com.example.JwtController;

import java.util.List;
import java.util.Set;

import com.example.UserSevice.PostService;
import com.example.UserSevice.UserService;
import com.example.DTO.PostDTO;
import com.example.DTO.UserDTO;
import com.example.DtoMapper.PostDtoMapper;
import com.example.DtoMapper.userDtoMapper;
import com.example.Entity.Post;
import com.example.Entity.Role;
import com.example.Entity.Users;
import com.example.Rebository.PostRebository;
import com.example.Rebository.UserRebository;
import com.example.Response.MessageResponse;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Exception.ExceptionValid;

@RestController
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private PostService postService;
    @Autowired
    private PostRebository postRebository;

    @Autowired
    private UserRebository userRebository;

    @Autowired
    private UserService userService;

    @Autowired
    private userDtoMapper userDtoMapper;
    @Autowired
    private PostDtoMapper postMapper;


    @GetMapping("/listpost")
    public ResponseEntity<?> listUser() {
        List<Post> listpost = postRebository.findAll();
        List<PostDTO> listDto = new ArrayList<>();
        for(Post post : listpost)   {
            PostDTO postDto = postMapper.covertEntityToDto(post);
            listDto.add(postDto);
        }
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/listapprove")
    public ResponseEntity<?> list_approve()  {
        List<Post> list = postRebository.findByIsnabled();
        List<PostDTO> listDto = new ArrayList<>();
        for(Post post : list)   {
            PostDTO postDto = postMapper.covertEntityToDto(post);
            listDto.add(postDto);
        }
        return ResponseEntity.ok().body(listDto);
    }
    @GetMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable("id") long id)  {
        Post post = postRebository.findById(id);
        if(post == null)    {
            throw new ExceptionValid("Khong tim thay");
         }
        post.setIsenabled(true); 
        postRebository.save(post);
        return ResponseEntity.ok().body(new MessageResponse("Duyệt bài thành công!!!"));
    }
    @GetMapping("/listUsers")
    public ResponseEntity<?> listUsers() {
        List<Users> users = userService.listUsers();
        List<UserDTO> listuserDto = new ArrayList<>(); 
        for(Users user : users) {
           UserDTO userDto =  userDtoMapper.covertEntityToUserDTO(user) ;
           listuserDto.add(userDto); 
        }
        return ResponseEntity.ok().body(listuserDto);
    }
    @GetMapping("/{name}/{id}") 
    public ResponseEntity<?> user(@PathVariable("id") Long id, @PathVariable("name") String name ) {

        Users user = userRebository.findByIdAndUsername(id, name);
        if(user == null)    {
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Người dùng không tồn tại!!!"));
        }
        else{
            return ResponseEntity.ok().body(user);
        }
    }
    @GetMapping("/user/{id}/post") 
    public ResponseEntity<?> findPostUser(@PathVariable("id") long id)   {
        Users user = userRebository.findById(id);
        if(user == null)    {
            throw new ExceptionValid("Người dùng không tồn tại");
        }
        List<Post> list_post = postRebository.findByIdUser(user);

        if(!list_post.isEmpty())    {

            return ResponseEntity.ok().body(list_post);
            
        }

        if(list_post.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Người dùng chua đăng bài viết nào"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Người dùng không tồn tại!!!"));
    }

}
