package com.widas.demo_ac.pattern.presenter;

import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.widas.demo_ac.entity.AuthenticationEntity;

public interface IPatternUsagePresenter {
    void callAuthenticate(AuthenticateEntity authenticateEntity, AuthenticationEntity authenticationEntity);
}
