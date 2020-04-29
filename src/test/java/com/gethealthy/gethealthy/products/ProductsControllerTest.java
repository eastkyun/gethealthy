package com.gethealthy.gethealthy.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gethealthy.gethealthy.WithAccount;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("상품 목록 정렬 테스트")
    @Test
    public void getProducts() throws Exception {
//        Product product = new Product();
//        product.setName("홍삼즙");
//        productRepository.save(product);

        mockMvc.perform(get("/products")
        .param("page","0")
        .param("size","10")
        .param("sort","name"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithAccount("dongkyun")
    @DisplayName("장바구니에 상품 추가")
    @Test
    public void addProductInCart() throws Exception {

        ProductForm productForm = new ProductForm();
        productForm.setName("홍삼즙");

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productForm))
                .with(csrf()))
                .andExpect(status().isOk());


        Product product = productRepository.findByName("홍삼즙");
        assertNotNull(product);
        Account dongkyun = accountRepository.findByNickname("dongkyun");
        assertNotNull(dongkyun);
        assertTrue(dongkyun.getCart().contains(product));
    }
    @WithAccount("dongkyun")
    @DisplayName("장바구니에 상품 제거")
    @Test
    public void removeProductInCart() throws Exception {

        ProductForm productForm = new ProductForm();
        productForm.setName("홍삼즙");

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productForm))
                .with(csrf()))
                .andExpect(status().isOk());


        Product product = productRepository.findByName("홍삼즙");
        Account dongkyun = accountRepository.findByNickname("dongkyun");
        assertTrue(dongkyun.getCart().contains(product));


        mockMvc.perform(post("/cart/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productForm))
                .with(csrf()))
                .andExpect(status().isOk());


        Product product1 = productRepository.findByName("홍삼즙");
        Account dongkyun1 = accountRepository.findByNickname("dongkyun");
        assertFalse(dongkyun1.getCart().contains(product1));
    }

    @WithAccount("dongkyun")
    @DisplayName("홍삼즙 : 좋아요 토글")
    @Test
    public void Liked() throws Exception {

        mockMvc.perform(get("/홍삼즙/liked")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk());
        Product product = productRepository.findByName("홍삼즙");
        assertEquals(product.getName(),"홍삼즙");
        assertEquals(1,product.getLiked());

        mockMvc.perform(get("/홍삼즙/liked")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk());
        Product product1 = productRepository.findByName("홍삼즙");
        assertEquals(product1.getName(),"홍삼즙");
        assertEquals(0,product1.getLiked());
    }

    @WithAccount("dongkyun")
    @DisplayName("홍삼즙 : 좋아요 증가")
    @Test
    public void increaseLiked() throws Exception {

        mockMvc.perform(get("/홍삼즙/liked/increase")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk());
        Product product = productRepository.findByName("홍삼즙");
        assertEquals(product.getName(),"홍삼즙");
        assertEquals(1,product.getLiked());
    }

    @WithAccount("dongkyun")
    @DisplayName("홍삼즙 : 좋아요 취소")
    @Test
    public void decreaseLiked() throws Exception {

        mockMvc.perform(get("/홍삼즙/liked/increase")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/홍삼즙/liked/decrease")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk());

        Product product = productRepository.findByName("홍삼즙");
        assertEquals(product.getName(),"홍삼즙");
        assertEquals(0,product.getLiked());
    }


}