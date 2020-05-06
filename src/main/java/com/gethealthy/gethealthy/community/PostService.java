package com.gethealthy.gethealthy.community;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountRepository;
import com.gethealthy.gethealthy.community.form.PostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void createPost(Account account, Post post) {
        Optional<Account> byId = accountRepository.findById(account.getId());
        byId.ifPresent(a->a.getPosts().add(post));
        postRepository.save(post);
    }
    public void deletePost(Account account,Post post){
        Optional<Account> byId = accountRepository.findById(account.getId());
        byId.ifPresent(a->a.getPosts().remove(post));
        postRepository.delete(post);
    }

    public void updatePost(Account account, Post post) {
//        Todo update post

    }
}
