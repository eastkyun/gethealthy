package com.gethealthy.gethealthy.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


    @GetMapping(Community_QNA_URL)
    public String qna(){
        return Community_QNA_VIEW_NAME;
    }
}
