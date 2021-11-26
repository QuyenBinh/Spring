package com.example.DtoMapper;

import com.example.DTO.PostDTO;
import com.example.Entity.Post;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Component
public class PostDtoMapper {
    @Autowired
    private ModelMapper modelMapper;

    public PostDTO covertEntityToDto(Post post)  {
        
        PostDTO postDto  = modelMapper.map(post, PostDTO.class);
        postDto.setIduser(post.getUser().getId());
        return postDto;

    }
    public Post coverDtoToEntity(PostDTO postDto)   {

        Post post = modelMapper.map(postDto,Post.class);
        return post;
        
    }
}
