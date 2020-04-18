package com.gethealthy.gethealthy.settings;

import com.gethealthy.gethealthy.account.AccountService;
import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.account.tag.TagRepository;
import com.gethealthy.gethealthy.domain.Account;
import com.gethealthy.gethealthy.domain.Tag;
import com.gethealthy.gethealthy.settings.form.*;
import com.gethealthy.gethealthy.settings.validator.NicknameValidator;
import com.gethealthy.gethealthy.settings.validator.PasswordFormValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class SettingsController {
    static final String SETTINGS_PROFILE_VIEW_NAME = "settings/profile";
    static final String SETTINGS_PROFILE_URL = "/settings/profile";
    static final String SETTINGS_PASSWORD_VIEW_NAME = "settings/password";
    static final String SETTINGS_PASSWORD_URL = "/settings/password";
    static final String SETTINGS_NOTIFICATIONS_VIEW_NAME = "settings/notifications";
    static final String SETTINGS_NOTIFICATIONS_URL = "/settings/notifications";
    static final String SETTINGS_ACCOUNT_VIEW_NAME="settings/account";
    static final String SETTINGS_ACCOUNT_URL="/settings/account";
    static final String SETTINGS_TAGS_VIEW_NAME="settings/tags";
    static final String SETTINGS_TAGS_URL="/settings/tags";


    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NicknameValidator nicknameValidator;

    @Autowired
    private PasswordFormValidator passwordFormValidator;

    @Autowired
    private TagRepository tagRepository;

    @InitBinder("passwordForm")
    public void passwordFormInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(passwordFormValidator);
    }
    @InitBinder("nicknameForm")
    public void nicknameFormInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(nicknameValidator);
    }

    @GetMapping(SETTINGS_PROFILE_URL)
    public String profileUpdateForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, Profile.class));
        return SETTINGS_PROFILE_VIEW_NAME;
    }

    @PostMapping(SETTINGS_PROFILE_URL)
    public String updateProfile(@CurrentUser Account account, @Valid @ModelAttribute Profile profile, Errors errors,
                                RedirectAttributes attributes, Model model){
        if(errors.hasErrors()){
            model.addAttribute(account);
            return SETTINGS_PROFILE_VIEW_NAME;
        }
        accountService.updateProfile(account, profile);
        attributes.addFlashAttribute("message","프로필을 수정했습니다.");
        return "redirect:/"+ SETTINGS_PROFILE_VIEW_NAME;
    }

    @GetMapping(SETTINGS_PASSWORD_URL)
    public String updatePasswordForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new PasswordForm());
        return SETTINGS_PASSWORD_VIEW_NAME;
    }

    @PostMapping(SETTINGS_PASSWORD_URL)
    public String updatePassword(@CurrentUser Account account, @Valid PasswordForm passwordForm, Errors errors,
                                 RedirectAttributes attributes, Model model){
        if(errors.hasErrors()){
            model.addAttribute(account);
            return SETTINGS_PASSWORD_VIEW_NAME;
        }
        accountService.updatePassword(account,passwordForm.getNewPassword());
        attributes.addFlashAttribute("message","패스워드를 변경했습니다.");
        return "redirect:/"+SETTINGS_PASSWORD_VIEW_NAME;

    }

    @GetMapping(SETTINGS_NOTIFICATIONS_URL)
    public String updateNotificationsForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, Notifications.class));
        return SETTINGS_NOTIFICATIONS_VIEW_NAME;
    }

    @PostMapping(SETTINGS_NOTIFICATIONS_URL)
    public String updateNotifications(@CurrentUser Account account, @Valid Notifications notifications, Errors errors,
                                 RedirectAttributes attributes, Model model) {
        if(errors.hasErrors()){
            model.addAttribute(account);
            return SETTINGS_NOTIFICATIONS_VIEW_NAME;
        }
        accountService.updateNotifications(account,notifications);
        attributes.addFlashAttribute("message","알림 설정을 변경했습니다.");

        return "redirect:/"+SETTINGS_NOTIFICATIONS_VIEW_NAME;
    }

    @GetMapping(SETTINGS_TAGS_URL)
    public String updateTags(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        return SETTINGS_TAGS_VIEW_NAME;
    }
    @PostMapping(SETTINGS_TAGS_URL+"/add")
    @ResponseBody
    public ResponseEntity addTags(@CurrentUser Account account, @RequestBody TagForm tagForm, Model model){
        String title = tagForm.getTagTitle();
//        Tag tag = tagRepository.findByTitle(title).orElseGet(() -> tagRepository.save(Tag.builder()
//                .title(tagForm.getTagTitle())
//                .build())); // Optional 쓸때
        Tag tag = tagRepository.findByTitle(title);

        if(tag ==null){
            tagRepository.save(Tag.builder().title(tagForm.getTagTitle()).build());
        }
        accountService.addTag(account,tag);

        return ResponseEntity.ok().build() ;
    }



    @GetMapping(SETTINGS_ACCOUNT_URL)
    public String updateAccountForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, NicknameForm.class));
        return SETTINGS_ACCOUNT_VIEW_NAME;
    }
    @PostMapping(SETTINGS_ACCOUNT_URL)
    public String updateAccountForm(@CurrentUser Account account, @Valid NicknameForm nicknameForm, Errors errors,
                                    Model model, RedirectAttributes attributes){
        if(errors.hasErrors()){
            model.addAttribute(account);
            return SETTINGS_ACCOUNT_VIEW_NAME;
        }
        accountService.updateNickname(account, nicknameForm.getNickname());
        attributes.addFlashAttribute("message","닉네임을 수정했습니다.");
        return "redirect:/"+SETTINGS_ACCOUNT_VIEW_NAME;
    }
}
