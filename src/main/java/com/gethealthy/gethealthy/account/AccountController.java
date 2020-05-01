package com.gethealthy.gethealthy.account;

import com.gethealthy.gethealthy.account.form.SignUpForm;
import com.gethealthy.gethealthy.account.validator.SignUpFormValidator;
import com.gethealthy.gethealthy.products.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AccountController {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private AccountRepository accountRepository;
    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }


//    GET “/sign-up” 요청을 받아서 account/sign-up.html 페이지 보여준다.
    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("signUpForm",new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors){
        if (errors.hasErrors())
            return "account/sign-up";

        //TODO 회원 가입 처리
        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);

        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model){
        String view = "account/checked-email";
        Account account = accountRepository.findByEmail(email);
        if(account==null){
            model.addAttribute("error","wrong.email");
            return view;
        }

        if(!account.isValidToken(token)){
            model.addAttribute("error","wrong.token");
            return view;
        }
        accountService.completeSignUp(account);

        model.addAttribute("numberOfUser",accountRepository.count());
        model.addAttribute("nickname",account.getNickname());
        return view;
    }
    @GetMapping("/check-email")
    public String checkEmail(@CurrentUser Account account, Model model){
        model.addAttribute("email", account.getEmail());
        return "account/check-email";
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentUser Account account, Model model){
        if(!account.canSendConfirmEmail()){
            model.addAttribute("error","인증 이메일은 1시간에 한 번만 전송할 수 있습니다.");
            model.addAttribute("email", account.getEmail());
            return "account/check-email";
        }
        accountService.sendSignUpConfirmEmail(account);
        return "redirect:/";
    }

    @GetMapping("/mypage/{nickname}")
    public String viewProfile(@PathVariable String nickname, Model model, @CurrentUser Account account){
        Account byNickname = accountRepository.findByNickname(nickname);
        if(byNickname==null) {
            throw new IllegalArgumentException(nickname+"에 해당하는 사용자가 없습니다.");
        }
        model.addAttribute("likedList",byNickname.getLikedList());
        model.addAttribute(byNickname); // byNickname의 타입의 낙타 표기법으로 view에 전달
        model.addAttribute("isOwner", byNickname.equals(account));

        return "account/profile";
    }
    @GetMapping("/email-login")
    public String emailLoginForm() {
        return "account/email-login";
    }
    @PostMapping("/email-login")
    public String sendEmailLoginLink(String email, Model model, RedirectAttributes attributes) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            model.addAttribute("error", "유효한 이메일 주소가 아닙니다.");
            return "account/email-login";
        }

        if (!account.canSendConfirmEmail()) {
            model.addAttribute("error", "이메일 로그인은 1시간 뒤에 사용할 수 있습니다.");
            return "account/email-login";
        }

        accountService.sendLoginLink(account);
        attributes.addFlashAttribute("message", "이메일 인증 메일을 발송했습니다.");
        return "redirect:/email-login";
    }

    @GetMapping("/login-by-email")
    public String loginByEmail(String token, String email, Model model) {
        Account account = accountRepository.findByEmail(email);
        if (account == null || !account.isValidToken(token)) {
            model.addAttribute("error", "로그인할 수 없습니다.");
            return "account/logged-in-by-email";
        }

        accountService.login(account);
        return "account/logged-in-by-email";
    }
}

