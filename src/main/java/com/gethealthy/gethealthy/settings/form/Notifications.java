package com.gethealthy.gethealthy.settings.form;

import com.gethealthy.gethealthy.domain.Account;
import lombok.Data;

@Data
public class Notifications {
    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdatedByEmail;

    private boolean studyUpdatedByWeb;

}
