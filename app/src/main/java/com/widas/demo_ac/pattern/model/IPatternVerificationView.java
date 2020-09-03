package com.widas.demo_ac.pattern.model;

import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;

public interface IPatternVerificationView {
    void patternAuthenticateSuccess(AuthenticateResponse authenticateResponse);
    void patternAuthenticateFailed(WebAuthError webAuthError);
}
