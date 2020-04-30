package com.gethealthy.gethealthy.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gethealthy.gethealthy.WithAccount;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.products.Product;
import com.gethealthy.gethealthy.products.ProductForm;
import com.gethealthy.gethealthy.products.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        mockMvc.perform(post("/review/list")
                .param("page","0")
                .param("size","10")
                .param("sort","created")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productForm))
                .with(csrf()))
                .andDo(print())
                .andExpect(model().attributeExists("reviews"))
                .andExpect(status().is3xxRedirection());

    }

    private void createPost() {

        Product item = productRepository.findByName("홍삼즙");
        Post review = Post.builder().title("제목" ).contents("내용" )
                .created(LocalDateTime.now())
                .liked((long) 0)
                .category((long) 3)
                .product(item)
                .created(LocalDateTime.now())
                .build();
        postRepository.save(review);
    }

}