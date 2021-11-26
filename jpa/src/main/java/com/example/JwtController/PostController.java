package com.example.JwtController;

import java.util.ArrayList;
import java.util.List;

import com.example.DTO.PostDTO;
import com.example.DtoMapper.PostDtoMapper;
import com.example.Entity.Post;
import com.example.Rebository.PostRebository;
import com.example.UserSevice.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
@RequestMapping("/post")
public class PostController {
    
    @Autowired
    private PostService postService;
    @Autowired
    private PostRebository postRebository;
    @Autowired
    private PostDtoMapper postmapper; 
    @GetMapping(value="/list")
    public ResponseEntity<?> listPost() {
        List<PostDTO> listdto = new ArrayList<>();
        List<Post> list = postService.list();
        for(Post post : list)   {

            PostDTO postDto = postmapper.covertEntityToDto(post);
            listdto.add(postDto);

        }
        return ResponseEntity.ok().body(listdto);
    }
    


}
