package com.gethealthy.gethealthy.settings.form;

import com.gethealthy.gethealthy.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
//@NoArgsConstructor // null point exception 방지
public class Profile {
    @Length(max = 35)
    private String bio;
    @Length(max = 50)
    private String url;
    @Length(max = 50)
    private String occupation;
    @Length(max = 50)
    private String location;

    private String profileImage;

    // null point exception 방지
}
