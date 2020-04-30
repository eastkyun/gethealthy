package com.gethealthy.gethealthy.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommunityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;
//    @Test
//    void reviewList() throws Exception {
//        createPost("제목","내용");
//        mockMvc.perform(post("/review/list")
//                .param("page","0")
//                .param("size","5")
//                .param("sort","name")
//                .with(csrf()))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
//
//    private void createPost(String title, String contents) {
//        for(int i=0;i<100;i++){
//            Post review = Post.builder().title(title+i).contents(contents+i)
//                    .created(LocalDateTime.now())
//                    .liked((long) 0)
//                    .category((long) 3)
//                    .build();
//            postRepository.save(review);
//        }
//    }
}