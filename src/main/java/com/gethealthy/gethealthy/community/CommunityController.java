package com.gethealthy.gethealthy.community;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.products.Product;
import com.gethealthy.gethealthy.products.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private PostRepository proPostRepository;

    @GetMapping(Community_MAIN_URL)
    public String communityMain(){
        return Community_MAIN_VIEW_NAME;
    }

    @GetMapping(Community_NOTICE_URL)
    public String notice(){
        return Community_NOTICE_VIEW_NAME;
    }

    @GetMapping(Community_REVIEW_URL)
    public String review(){
        return Community_REVIEW_VIEW_NAME;
    }
    @PostMapping("/review/list")
    @ResponseBody
    public ResponseEntity reviewList(@CurrentUser Account account, @RequestBody ProductForm productForm
            , Pageable pageable, Model model){
        Page<Post> reviewList = proPostRepository.findAllByCategory((long) 3, pageable);
        model.addAttribute(reviewList);
        return ResponseEntity.ok().build();
    }


    @GetMapping(Community_QNA_URL)
    public String qna(){
        return Community_QNA_VIEW_NAME;
    }
}
