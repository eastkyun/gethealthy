package com.gethealthy.gethealthy.settings.validator;

import com.gethealthy.gethealthy.account.AccountRepository;
import com.gethealthy.gethealthy.domain.Account;
import com.gethealthy.gethealthy.settings.form.NicknameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NicknameValidator implements Validator {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(NicknameForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NicknameForm nicknameForm = (NicknameForm) target;
        Account byNickname = accountRepository.findByNickname(nicknameForm.getNickname());
        if(byNickname != null){
                errors.rejectValue("nickname","wrong.value","입력하신 닉네임을 사용할 수 없습니다.");
        }
    }
}
