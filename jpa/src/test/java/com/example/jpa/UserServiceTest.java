package com.example.jpa;

import com.example.Entity.Post;
import com.example.Rebository.PostRebository;
import com.example.Rebository.UserRebository;
import com.example.UserSevice.PostService;
import com.example.UserSevice.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserServiceTest {
    @MockBean
    PostRebository postRebository;

    @Autowired
    private PostService postService;

    @Before
    public void setUp() {

        Mockito.when(postRebository.findAll()).thenReturn(IntStream.range(0  ,10).mapToObj(i ->  new Post("title -" + i, "detail- " + i)).collect(Collectors.toList()));

    }
    @Test
    public void testCount() {

        Assert.assertEquals(10, postService.list().size());

    }
}
