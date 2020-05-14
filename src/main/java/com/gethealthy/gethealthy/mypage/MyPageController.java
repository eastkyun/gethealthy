package com.gethealthy.gethealthy.mypage;

import com.gethealthy.gethealthy.account.AccountService;
import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.mypage.form.NicknameForm;
import com.gethealthy.gethealthy.mypage.form.Notifications;
import com.gethealthy.gethealthy.mypage.form.PasswordForm;
import com.gethealthy.gethealthy.mypage.form.Profile;
import com.gethealthy.gethealthy.mypage.validator.NicknameValidator;
import com.gethealthy.gethealthy.mypage.validator.PasswordFormValidator;
import com.gethealthy.gethealthy.products.Product;
import com.gethealthy.gethealthy.products.ProductService;
import com.gethealthy.gethealthy.products.form.ProductForm;
import com.gethealthy.gethealthy.products.validator.ProductFormValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

@Controller
public class MyPageController {
    static final String MYPAGE_PROFILE_VIEW_NAME = "account/mypage/profile";
    static final String MYPAGE_PROFILE_URL = "/mypage/profile";
    static final String MYPAGE_PASSWORD_VIEW_NAME = "account/mypage/password";
    static final String MYPAGE_PASSWORD_URL = "/mypage/password";
    static final String MYPAGE_NOTIFICATIONS_VIEW_NAME = "account/mypage/notifications";
    static final String MYPAGE_NOTIFICATIONS_URL = "/mypage/notifications";
    static final String MYPAGE_ACCOUNT_VIEW_NAME="account/mypage/account";
    static final String MYPAGE_ACCOUNT_URL ="/mypage/account";
    static final String MYPAGE_CART_VIEW_NAME="account/mypage/cart";
    static final String MYPAGE_CART_URL ="/mypage/cart";
    static final String MYPAGE_ORDER_VIEW_NAME="account/mypage/order";
    static final String MYPAGE_ORDER_URL ="/mypage/order";
    static final String MYPAGE_PRODUCT_VIEW_NAME="/products/form";
    static final String MYPAGE_PRODUCT_URL ="/mypage/product";


    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NicknameValidator nicknameValidator;

    @Autowired
    private PasswordFormValidator passwordFormValidator;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductFormValidator productFormValidator;
    @InitBinder("productForm")
    public void productFormInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(productFormValidator);
    }
    @InitBinder("passwordForm")
    public void passwordFormInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(passwordFormValidator);
    }
    @InitBinder("nicknameForm")
    public void nicknameFormInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(nicknameValidator);
    }

    @GetMapping(MYPAGE_PROFILE_URL)
    public String profileUpdateForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, Profile.class));
        return MYPAGE_PROFILE_VIEW_NAME;
    }

    @PostMapping(MYPAGE_PROFILE_URL)
    public String updateProfile(@CurrentUser Account account, @Valid @ModelAttribute Profile profile, Errors errors,
                                RedirectAttributes attributes, Model model){
        if(errors.hasErrors()){
            model.addAttribute(account);
            return MYPAGE_PROFILE_VIEW_NAME;
        }
        accountService.updateProfile(account, profile);
        attributes.addFlashAttribute("message","프로필을 수정했습니다.");
        return "redirect:"+ MYPAGE_PROFILE_URL;
    }

    @GetMapping(MYPAGE_PASSWORD_URL)
    public String updatePasswordForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new PasswordForm());
        return MYPAGE_PASSWORD_VIEW_NAME;
    }

    @PostMapping(MYPAGE_PASSWORD_URL)
    public String updatePassword(@CurrentUser Account account, @Valid PasswordForm passwordForm, Errors errors,
                                 RedirectAttributes attributes, Model model){
        if(errors.hasErrors()){
            model.addAttribute(account);
            return MYPAGE_PASSWORD_VIEW_NAME;
        }
        accountService.updatePassword(account,passwordForm.getNewPassword());
        attributes.addFlashAttribute("message","패스워드를 변경했습니다.");
        return "redirect:"+ MYPAGE_PASSWORD_URL;

    }

    @GetMapping(MYPAGE_NOTIFICATIONS_URL)
    public String updateNotificationsForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, Notifications.class));
        return MYPAGE_NOTIFICATIONS_VIEW_NAME;
    }

    @PostMapping(MYPAGE_NOTIFICATIONS_URL)
    public String updateNotifications(@CurrentUser Account account, @Valid Notifications notifications, Errors errors,
                                      RedirectAttributes attributes, Model model) {
        if(errors.hasErrors()){
            model.addAttribute(account);
            return MYPAGE_NOTIFICATIONS_VIEW_NAME;
        }
        accountService.updateNotifications(account,notifications);
        attributes.addFlashAttribute("message","알림 설정을 변경했습니다.");

        return "redirect:"+ MYPAGE_NOTIFICATIONS_URL;
    }

    @GetMapping(MYPAGE_ACCOUNT_URL)
    public String updateAccountForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, NicknameForm.class));
        return MYPAGE_ACCOUNT_VIEW_NAME;
    }
    @PostMapping(MYPAGE_ACCOUNT_URL)
    public String updateAccountForm(@CurrentUser Account account, @Valid NicknameForm nicknameForm, Errors errors,
                                    Model model, RedirectAttributes attributes){
        if(errors.hasErrors()){
            model.addAttribute(account);
            return MYPAGE_ACCOUNT_VIEW_NAME;
        }
        accountService.updateNickname(account, nicknameForm.getNickname());
        attributes.addFlashAttribute("message","닉네임을 수정했습니다.");
        return "redirect:"+MYPAGE_ACCOUNT_URL;
    }

    // 장바구니
    @GetMapping(MYPAGE_CART_URL)
    public String cartList(@CurrentUser Account account, Model model){

        Set<Product> cart = accountService.getCart(account);
        model.addAttribute(account);
        model.addAttribute("cartList",cart);
        return MYPAGE_CART_VIEW_NAME;
    }

    @GetMapping(MYPAGE_ORDER_URL)
    public String orderList(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        return MYPAGE_ORDER_VIEW_NAME;
    }

    @GetMapping(MYPAGE_PRODUCT_URL)
    public String productForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new ProductForm());
        return MYPAGE_PRODUCT_VIEW_NAME;
    }
    @PostMapping(MYPAGE_PRODUCT_URL+"/add")
    public String createProduct(@CurrentUser Account account, @Valid ProductForm productForm,
                                Errors errors, Model model) throws UnsupportedEncodingException {
        if(errors.hasErrors()){
            model.addAttribute(account);
            model.addAttribute(new ProductForm());
            return MYPAGE_PRODUCT_VIEW_NAME;
        }
        Product product = modelMapper.map(productForm, Product.class);
        productService.createProduct(account,product);
        model.addAttribute(account);

        return "redirect:/products/details/"+ URLEncoder.encode(product.getName(), "UTF-8");
    }
}
