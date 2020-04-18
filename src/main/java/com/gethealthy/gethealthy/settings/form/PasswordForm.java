package com.gethealthy.gethealthy.settings.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordForm {
    @Length(min =10, max = 50)
    private String newPassword;
    @Length(min =10, max = 50)
    private String newPasswordConfirm;

}
