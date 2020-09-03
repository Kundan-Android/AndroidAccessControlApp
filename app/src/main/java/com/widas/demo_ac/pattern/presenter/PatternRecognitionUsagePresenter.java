package com.widas.demo_ac.pattern.presenter;

import android.os.Bundle;


import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.widas.demo_ac.base.presenter.BasePresenter;
import com.widas.demo_ac.common.EventBus.MFAVerificationEvents;
import com.widas.demo_ac.entity.AuthenticationEntity;
import com.widas.demo_ac.pattern.model.IPatternVerificationView;
import com.widas.demo_ac.pattern.view.PatternRecognitionUsageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import timber.log.Timber;

/**
 * Created by ganesh on 15/02/18.
 */

public class PatternRecognitionUsagePresenter extends BasePresenter<PatternRecognitionUsageActivity> implements IPatternUsagePresenter {

    static IPatternVerificationView patternVerificationView;

    public PatternRecognitionUsagePresenter() {
    }

    public PatternRecognitionUsagePresenter(IPatternVerificationView iPatternVerificationView) {
        patternVerificationView = iPatternVerificationView;
    }

    // on create
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        EventBus.getDefault().register(this);
    }

    // on save
    @Override
    protected void onSave(Bundle state) {
        super.onSave(state);
    }

    // on destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // authenticate pattern
/*
    public void authenticatePattern(AuthenticationEntity authenticationEntity, final IResult<PatternAuthenticateServiceResultEntity> patternAuthenticateServiceResultEntityIResult) {
        try {
            AuthService authService = new AuthService();
            final IAuthService iAuthService = authService.createClient(authenticationEntity.getBaseURL());
            final ErrorEntity errorEntity = new ErrorEntity();

            // get device info
            AuthDeviceInfo authDeviceInfo = getDeviceInfo();

            if (authDeviceInfo != null) {

                // assign entity as body
                final PatternAuthenticateServiceEntity patternAuthenticateServiceEntity = new PatternAuthenticateServiceEntity();
                patternAuthenticateServiceEntity.setDeviceInfo(authDeviceInfo);
               // patternAuthenticateServiceEntity.setStatusId(authenticationEntity.getStatusId());
               // patternAuthenticateServiceEntity.setVerifierPassword(authenticationEntity.getVerifierPassword());


                // construct url
                final String url = authenticationEntity.getVerificationServiceURL() + "/" + authenticationEntity.getVerificationType().toLowerCase() + "/authenticate";

                        // call authenticate service
                        iAuthService.authenticatePattern(url, Constants.CONTENT_TYPE_JSON, "", patternAuthenticateServiceEntity).enqueue(new Callback<PatternAuthenticateServiceResultEntity>() {
                            @Override
                            public void onResponse(Call<PatternAuthenticateServiceResultEntity> call, Response<PatternAuthenticateServiceResultEntity> response) {
                                if (response.isSuccessful()) {
                                    patternAuthenticateServiceResultEntityIResult.onSuccess(response.body());
                                }
                                else {
                                    setErrorBody(response,errorEntity);
                                    errorEntity.setErrorCode(response.code());
                                    errorEntity.setErrorMessage(response.message());
                                    patternAuthenticateServiceResultEntityIResult.onError(errorEntity);
                                }
                            }

                            @Override
                            public void onFailure(Call<PatternAuthenticateServiceResultEntity> call, Throwable t) {
                                errorEntity.setErrorMessage(t.getMessage());
                                errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                                patternAuthenticateServiceResultEntityIResult.onError(errorEntity);
                            }
                        });
            }
            else {
                Timber.d("No device info");
                errorEntity.setErrorMessage("No device info");
                errorEntity.setErrorCode(HttpStatusCode.EXPECTATION_FAILED);
                patternAuthenticateServiceResultEntityIResult.onError(errorEntity);
            }
        }
        catch (Exception ex) {
            Timber.d(ex.getMessage());
            Crashlytics.logException(ex);
            ErrorEntity errorEntity = new ErrorEntity();
            errorEntity.setErrorMessage(ex.getMessage());
            errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
            patternAuthenticateServiceResultEntityIResult.onError(errorEntity);
        }
    }
*/


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mfaVerificationAuthenticateSuccessEvents(MFAVerificationEvents.MFAVerificationAuthenticateSuccessEvents authenticateSuccessEvents) {
        patternVerificationView.patternAuthenticateSuccess(authenticateSuccessEvents.authenticateResponse);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mfaVerificationAuthenticateFailedEvents(MFAVerificationEvents.MFAVerificationAuthenticateFailedEvents authenticateFailedEvents) {
        patternVerificationView.patternAuthenticateFailed(authenticateFailedEvents.webAuthError);
    }


    @Override
    public void callAuthenticate(AuthenticateEntity authenticateEntity, AuthenticationEntity authenticationEntity) {
        Timber.d("callAuthenticate");
    }
}

