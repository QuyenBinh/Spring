package com.example.jpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.DTO.PostDTO;
import com.example.DtoMapper.PostDtoMapper;
import com.example.Entity.Post;
import com.example.Rebository.PostRebository;
import com.example.UserSevice.PostService;
import com.example.security.jwt.JwtUtils;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.web.http.SecurityHeaders.bearerToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    PostService postService;
    @Mock
    PostRebository postRebository;

    @MockBean
    PostDtoMapper mapper;
    @Autowired
    ApplicationContext context;
    @Autowired
    private AuthenticationManager authenticator;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private WebApplicationContext webapp;

    public String token(String username, String pass)   {
        Authentication authentication = authenticator.authenticate(new UsernamePasswordAuthenticationToken("mod", "123123123"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    @Test
    public void testFindAll() throws Exception {

        String jwt = token("user","123123123");
        List<Post> postlist = IntStream.range(0,10).mapToObj(i -> new Post(i,"title -" + i, "content-" + i)).collect(Collectors.toList());
        given(postService.list()).willReturn(postlist);
        assertThat(jwt).isNotNull();
        mvc.perform(get("/user/listpost")
                .header(HttpHeaders.AUTHORIZATION,jwt).accept(MediaType.APPLICATION_JSON) //Authorization
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(10))).andExpect(jsonPath("$[0].id",is(0)))
                .andDo(print());
    }
    @Test
    //post
    public void testPost() throws Exception {

        String jwt = token("user","123123123");
        Post post = new Post("Title","content");
        given(postService.create(post)).willReturn(post);
        mvc.perform(post("/user/post")
                .header(HttpHeaders.AUTHORIZATION,jwt)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               // .andExpect(jsonPath("$"),notNullValue())
                .andExpect(jsonPath("$.title",is("title")))
                .andDo(print());

    }
    @Test
    public void testFindById() throws Exception {

        String jwt = token("user","123123123");
        Post post =  new Post(1, "t","c");
//        testEntityManager.persist(post);
//        testEntityManager.flush();
        System.out.print("titel");
        long id = post.getId();
        given(postRebository.findById(id)).willReturn(post);
        mvc.perform(get("/user/post/" + post.getId()).header(HttpHeaders.AUTHORIZATION,jwt).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$",notNullValue()));

    }
    @Before
    public void before() {

        mvc = MockMvcBuilders.webAppContextSetup(webapp)
                .apply(springSecurity())
                .build();

    }
}
