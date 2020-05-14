package com.gethealthy.gethealthy.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gethealthy.gethealthy.WithAccount;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountRepository;
import com.gethealthy.gethealthy.community.form.PostForm;
import com.gethealthy.gethealthy.products.Product;
import com.gethealthy.gethealthy.products.form.ProductForm;
import com.gethealthy.gethealthy.products.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommunityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void reviewList() throws Exception {
        ProductForm productForm = new ProductForm();
        productForm.setName("홍삼즙");
        createPost();
        createPost();
        createPost();
        createPost();
        createPost();
        createPost();
        createPost();
        createPost();
        createPost();

        mockMvc.perform(get("/community/review")
                .param("page","0")
                .param("size","10")
                .param("sort","created")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productForm)))
                .andDo(print())
                .andExpect(model().attributeExists("reviews"))
                .andExpect(status().isOk());

    }

    @WithAccount("dongkyun")
    @DisplayName("글 작성")
    @Test
    public void createPostTest() throws Exception {
        Account account = accountRepository.findByNickname("dongkyun");
        PostForm postForm = new PostForm();
        postForm.setTitle("제목");
        postForm.setContents("내용");
        postForm.setAuthor(account);
        postForm.setCategory(1L);
        postForm.setLiked(0L);

        mockMvc.perform(post("/post/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postForm))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        Post post = postRepository.findByTitle("제목");
        assertEquals("제목",post.getTitle());

    }

    @WithAccount("dongkyun")
    @DisplayName("글 삭제")
    @Test
    public void removePostTest() throws Exception {
        Account account = accountRepository.findByNickname("dongkyun");
        PostForm postForm = new PostForm();
        postForm.setTitle("제목");
        postForm.setContents("내용");
        postForm.setAuthor(account);
        postForm.setCategory(1L);
        postForm.setLiked(0L);

        mockMvc.perform(post("/post/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postForm))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        Post post = postRepository.findByTitle("제목");
        assertEquals("제목",post.getTitle());

        mockMvc.perform(post("/post/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postForm))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Optional<Post> post1 = postRepository.findById(post.getId());
        assertTrue(post1.isEmpty());

    }
    private void createPost() {

        Product item = productRepository.findByName("홍삼즙");
        Post review = Post.builder().title("제목" ).contents("내용" )
                .created(LocalDateTime.now())
                .liked((long) 0)
//                .category((long) 3)
                .product(item)
                .created(LocalDateTime.now())
                .build();
        postRepository.save(review);
    }

}