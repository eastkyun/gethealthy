package com.gethealthy.gethealthy.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.products.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommunityController {
    static final String Community_MAIN_URL = "/community";
    static final String Community_MAIN_VIEW_NAME = "community/index";
    static final String Community_NOTICE_URL = "/community/notice";
    static final String Community_NOTICE_VIEW_NAME = "community/notice";
    static final String Community_REVIEW_URL = "/community/review";
    static final String Community_REVIEW_VIEW_NAME = "community/review";
    static final String Community_QNA_URL = "/community/qna";
    static final String Community_QNA_VIEW_NAME = "community/qna";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;
    @GetMapping(Community_MAIN_URL)
    public String communityMain(@CurrentUser Account account, Model model,
                                @PageableDefault(size = 5, sort = "created", direction = Sort.Direction.DESC)
                                        Pageable pageable){
        if(account!=null) model.addAttribute(account);
        Page<Post> postPage = postRepository.findAll(pageable);
        model.addAttribute(postPage);

        return Community_MAIN_VIEW_NAME;
    }


    @GetMapping(Community_NOTICE_URL)
    public String notice(@CurrentUser Account account, Model model,
                         @PageableDefault(size = 5, sort = "created", direction = Sort.Direction.DESC)
                                 Pageable pageable){
        if(account!=null) model.addAttribute(account);
        Page<Post> noticePage = postRepository.findAllByCategory(1L, pageable);
        model.addAttribute(noticePage);
        return Community_NOTICE_VIEW_NAME;
    }

    @GetMapping(Community_REVIEW_URL)
    public String review(@CurrentUser Account account, Model model,
                         @PageableDefault(size = 5, sort = "created", direction = Sort.Direction.DESC)
                                 Pageable pageable){
        if(account!=null) model.addAttribute(account);
        Page<Post> noticePage = postRepository.findAllByCategory(3L, pageable);
        model.addAttribute(noticePage);
        return Community_REVIEW_VIEW_NAME;
    }

    @GetMapping(Community_QNA_URL)
    public String qna(@CurrentUser Account account, Model model,
                      @PageableDefault(size = 5, sort = "created", direction = Sort.Direction.DESC)
                              Pageable pageable){
        if(account!=null) model.addAttribute(account);
        Page<Post> noticePage = postRepository.findAllByCategory(2L, pageable);
        model.addAttribute(noticePage);
        return Community_QNA_VIEW_NAME;
    }

}
