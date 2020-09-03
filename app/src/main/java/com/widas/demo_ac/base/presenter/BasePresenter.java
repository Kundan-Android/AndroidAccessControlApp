package com.widas.demo_ac.base.presenter;

import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nucleus.presenter.RxPresenter;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ganesh on 12/02/18.
 */

public class BasePresenter<ViewType> extends RxPresenter<ViewType> {


    // on create
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    // on destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // on save
    @Override
    protected void onSave(Bundle state) {
        super.onSave(state);
    }

    // on take view
    @Override
    protected void onTakeView(ViewType viewType) {
        super.onTakeView(viewType);
    }

    // on drop
    @Override
    protected void onDropView() {
        super.onDropView();
    }


    public void updateUserUpdatedTime(String userId, String verificationType, String tenantKey) {
        //     iUserInfo = new IUserInfoImpl();
        //   iUserInfo.updateUserUpdatedTime(userId, verificationType,tenantKey );
    }


    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    //Get user By Userid


    /* // enroll pattern
     public void enrollPattern(AuthenticationEntity authenticationEntity, final IResult<PatternEnrollServiceResultEntity> patternEnrollServiceResultEntityIResult) {
         try {
             AuthService authService = new AuthService();
             final IAuthService iAuthService = authService.createClient(authenticationEntity.getBaseURL());
             final ErrorEntity errorEntity = new ErrorEntity();

             // get device info
             AuthDeviceInfo authDeviceInfo = getDeviceInfo();

             if (authDeviceInfo != null) {

                 // assign entity as body
                 final PatternEnrollServiceEntity patternEnrollServiceEntity = new PatternEnrollServiceEntity();
                 patternEnrollServiceEntity.setDeviceInfo(authDeviceInfo);
               //  patternEnrollServiceEntity.setStatusId(authenticationEntity.getStatusId());
             //    patternEnrollServiceEntity.setVerifierPassword(authenticationEntity.getVerifierPassword());

                 // construct url
                 final String url = authenticationEntity.getVerificationServiceURL() + "/" + authenticationEntity.getVerificationType().toLowerCase() + "/enroll";
                 cidaas.getAccessToken(authenticationEntity.getUserId(), new Result<AccessTokenEntity>() {
                     @Override
                     public void success(AccessTokenEntity accessTokenEntity) {
                         // call enroll service
                         iAuthService.enrollPattern(url, Constants.CONTENT_TYPE_JSON, accessTokenEntity.getAccess_token(), patternEnrollServiceEntity)
                                 .enqueue(new Callback<PatternEnrollServiceResultEntity>() {
                                     @Override
                                     public void onResponse(Call<PatternEnrollServiceResultEntity> call,
                                                            Response<PatternEnrollServiceResultEntity> response) {
                                         if (response.isSuccessful()) {
                                             patternEnrollServiceResultEntityIResult.onSuccess(response.body());
                                         } else {
                                             setErrorBody(response, errorEntity);
                                             errorEntity.setErrorMessage(response.message());
                                             errorEntity.setErrorCode(response.code());
                                             patternEnrollServiceResultEntityIResult.onError(errorEntity);
                                             Logger.addRecordToLog("Pattern Error" + response.message());
                                             Logger.addRecordToLog("Pattern Error" + response.message());
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<PatternEnrollServiceResultEntity> call, Throwable t) {
                                         errorEntity.setErrorMessage(t.getMessage());
                                         errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                                         patternEnrollServiceResultEntityIResult.onError(errorEntity);
                                         Logger.addRecordToLog("Pattern Error" + t.getMessage());
                                         Logger.addRecordToLog("Pattern Error" + t.getMessage());
                                     }
                                 });
                     }

                     @Override
                     public void failure(WebAuthError webAuthError) {
                         // TODO: 25-Oct-18  set url
                         try {
                             Helper.setUpCidaasSDKURL(authTenantInfo);
                             errorEntity.setErrorMessage(webAuthError.getMessage());
                             errorEntity.setErrorCode(webAuthError.getStatusCode());
                             patternEnrollServiceResultEntityIResult.onError(errorEntity);
                             Logger.addRecordToLog("Pattern Error" + webAuthError.getMessage());
                             Logger.addRecordToLog("Pattern Error" + webAuthError.getMessage());
                         } catch (Exception ex) {
                             Logger.addRecordToLog("Pattern Error" + ex.getMessage());
                         }


                     }
                 });


             } else {
                 Timber.d("No device info");
                 Logger.addRecordToLog("No device info");
                 errorEntity.setErrorMessage("No device info");
                 errorEntity.setErrorCode(HttpStatusCode.EXPECTATION_FAILED);
                 patternEnrollServiceResultEntityIResult.onError(errorEntity);
             }
         } catch (Exception ex) {
             Timber.d(ex.getMessage());//Crashlytics.logException(ex);
             Logger.addRecordToLog(ex.getMessage());//Crashlytics.logException(ex);
             ErrorEntity errorEntity = new ErrorEntity();
             errorEntity.setErrorMessage(ex.getMessage());
             errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
             patternEnrollServiceResultEntityIResult.onError(errorEntity);
         }
     }

     //enroll FingerPrint
     public void enrollFingerprint(AuthenticationEntity authenticationEntity, final IResult<FingerEnrollServiceResultEntity> fingerEnrollServiceResultEntityIResult) {
         try {
             AuthService authService = new AuthService();
             final IAuthService iAuthService = authService.createClient(authenticationEntity.getBaseURL());
             final ErrorEntity errorEntity = new ErrorEntity();

             // get device info
             AuthDeviceInfo authDeviceInfo = getDeviceInfo();

             if (authDeviceInfo != null) {

                 // assign entity as body
                 final FingerEnrollServiceEntity fingerEnrollServiceEntity = new FingerEnrollServiceEntity();
                 fingerEnrollServiceEntity.setDeviceInfo(authDeviceInfo);
                 fingerEnrollServiceEntity.setStatusId(authenticationEntity.getStatusId());

                 // construct url
                 final String url = authenticationEntity.getVerificationServiceURL() + "/" + authenticationEntity.getVerificationType().toLowerCase() + "/enroll";
                 cidaas.getAccessToken(authenticationEntity.getUserId(), new Result<AccessTokenEntity>() {
                     @Override
                     public void success(AccessTokenEntity accessTokenEntity) {
                         // call enroll service
                         iAuthService.enrollFinger(url, Constants.CONTENT_TYPE_JSON, accessTokenEntity.getAccess_token(), fingerEnrollServiceEntity).enqueue(new Callback<FingerEnrollServiceResultEntity>() {
                             @Override
                             public void onResponse(Call<FingerEnrollServiceResultEntity> call, Response<FingerEnrollServiceResultEntity> response) {
                                 if (response.isSuccessful()) {
                                     fingerEnrollServiceResultEntityIResult.onSuccess(response.body());
                                 } else {
                                     setErrorBody(response, errorEntity);
                                     errorEntity.setErrorMessage(response.message());
                                     errorEntity.setErrorCode(response.code());
                                     fingerEnrollServiceResultEntityIResult.onError(errorEntity);
                                 }
                             }


                             @Override
                             public void onFailure(Call<FingerEnrollServiceResultEntity> call, Throwable t) {
                                 errorEntity.setErrorMessage(t.getMessage());
                                 errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                                 fingerEnrollServiceResultEntityIResult.onError(errorEntity);
                             }
                         });
                     }

                     @Override
                     public void failure(WebAuthError webAuthError) {
                         try { // TODO: 25-Oct-18  set url
                             Helper.setUpCidaasSDKURL(authTenantInfo);
                             errorEntity.setErrorMessage(webAuthError.getMessage());
                             errorEntity.setErrorCode(webAuthError.getStatusCode());
                             fingerEnrollServiceResultEntityIResult.onError(errorEntity);

                         } catch (Exception ex) {

                         }

                     }
                 });

             } else {
                 Logger.addRecordToLog("No device info");
                 errorEntity.setErrorMessage("No device info");
                 errorEntity.setErrorCode(HttpStatusCode.EXPECTATION_FAILED);
                 fingerEnrollServiceResultEntityIResult.onError(errorEntity);
             }
         } catch (Exception ex) {
             Timber.d(ex.getMessage());//Crashlytics.logException(ex);
             Logger.addRecordToLog(ex.getMessage());//Crashlytics.logException(ex);
             ErrorEntity errorEntity = new ErrorEntity();
             errorEntity.setErrorMessage(ex.getMessage());
             errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
             fingerEnrollServiceResultEntityIResult.onError(errorEntity);
         }
     }

     //Enroll SmartPush
     public void enrollSmartPush(AuthenticationEntity authenticationEntity, final IResult<SmartPushEnrollServiceResultEntity> smartPushEnrollServiceResultEntityIResult) {
         try {
             AuthService authService = new AuthService();
             final IAuthService iAuthService = authService.createClient(authenticationEntity.getBaseURL());
             final ErrorEntity errorEntity = new ErrorEntity();

             // get device info
             AuthDeviceInfo authDeviceInfo = getDeviceInfo();

             if (authDeviceInfo != null) {

                 // assign entity as body
                 final SmartPushEnrollServiceEntity smartPushEnrollServiceEntity = new SmartPushEnrollServiceEntity();
                 smartPushEnrollServiceEntity.setDeviceInfo(authDeviceInfo);
                 smartPushEnrollServiceEntity.setStatusId(authenticationEntity.getStatusId());
                 smartPushEnrollServiceEntity.setVerifierPassword(authenticationEntity.getVerifierPassword());

                 // construct url
                 final String url = authenticationEntity.getVerificationServiceURL() + "/" + authenticationEntity.getVerificationType().toLowerCase() + "/enroll";
                 cidaas.getAccessToken(authenticationEntity.getUserId(), new Result<AccessTokenEntity>() {
                     @Override
                     public void success(AccessTokenEntity accessTokenEntity) {
                         // call enroll service
                         iAuthService.enrollSmartPush(url, Constants.CONTENT_TYPE_JSON, accessTokenEntity.getAccess_token(), smartPushEnrollServiceEntity).enqueue(new Callback<SmartPushEnrollServiceResultEntity>() {
                             @Override
                             public void onResponse(Call<SmartPushEnrollServiceResultEntity> call, Response<SmartPushEnrollServiceResultEntity> response) {
                                 if (response.isSuccessful()) {
                                     smartPushEnrollServiceResultEntityIResult.onSuccess(response.body());
                                 } else {
                                     setErrorBody(response, errorEntity);
                                     errorEntity.setErrorMessage(response.message());
                                     errorEntity.setErrorCode(response.code());
                                     smartPushEnrollServiceResultEntityIResult.onError(errorEntity);
                                 }
                             }

                             @Override
                             public void onFailure(Call<SmartPushEnrollServiceResultEntity> call, Throwable t) {
                                 errorEntity.setErrorMessage(t.getMessage());
                                 errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                                 smartPushEnrollServiceResultEntityIResult.onError(errorEntity);
                             }

                         });
                     }

                     @Override
                     public void failure(WebAuthError webAuthError) {
                         // TODO: 25-Oct-18  set url
                         try {
                             Helper.setUpCidaasSDKURL(authTenantInfo);
                             errorEntity.setErrorMessage(webAuthError.getMessage());
                             errorEntity.setErrorCode(webAuthError.getStatusCode());
                             smartPushEnrollServiceResultEntityIResult.onError(errorEntity);
                         } catch (Exception ex) {

                         }

                     }
                 });

             } else {
                 Timber.d("No device info");
                 Logger.addRecordToLog("No device info");
                 errorEntity.setErrorMessage("No device info");
                 errorEntity.setErrorCode(HttpStatusCode.EXPECTATION_FAILED);
                 smartPushEnrollServiceResultEntityIResult.onError(errorEntity);
             }
         } catch (Exception ex) {
             Timber.d(ex.getMessage());//Crashlytics.logException(ex);
             Logger.addRecordToLog(ex.getMessage());//Crashlytics.logException(ex);
             ErrorEntity errorEntity = new ErrorEntity();
             errorEntity.setErrorMessage(ex.getMessage());
             errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
             smartPushEnrollServiceResultEntityIResult.onError(errorEntity);
         }
     }
 */
    public RequestBody StringtoRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    /*public void enrollVoice(final AuthenticationEntity authenticationEntity, final MultipartBody.Part requestBody,
                            final IResult<VoiceEnrollServiceResultEntity> voiceEnrollServiceResultEntityIResult) {
        try {
            AuthService authService = new AuthService();
            final IAuthService iAuthService = authService.createClient(authenticationEntity.getBaseURL());
            final ErrorEntity errorEntity = new ErrorEntity();
            // get device info
            AuthDeviceInfo authDeviceInfo = getDeviceInfo();

            if (authDeviceInfo != null) {

                // assign entity as body
                final VoiceEnrollServiceEntity voiceEnrollServiceEntity = new VoiceEnrollServiceEntity();
                *//*  voiceEnrollServiceEntity.setVoice(authenticationEntity.getVerifierbioMetrics());*//*
                voiceEnrollServiceEntity.setStatusId(authenticationEntity.getStatusId());

                voiceEnrollServiceEntity.setPushNotificationId(authDeviceInfo.getPushNotificationId());
                voiceEnrollServiceEntity.setDeviceMake(authDeviceInfo.getDeviceMake());
                voiceEnrollServiceEntity.setDeviceModel(authDeviceInfo.getDeviceModel());
                voiceEnrollServiceEntity.setDeviceId(authDeviceInfo.getDeviceId());


                final HashMap<String, RequestBody> map = new HashMap<>();
                map.put("statusId", StringtoRequestBody(authenticationEntity.getStatusId()));
                map.put("pushNotificationId", StringtoRequestBody(authDeviceInfo.getPushNotificationId()));
                map.put("deviceMake", StringtoRequestBody(authDeviceInfo.getDeviceMake()));
                map.put("deviceModel", StringtoRequestBody(authDeviceInfo.getDeviceModel()));
                map.put("deviceId", StringtoRequestBody(authDeviceInfo.getDeviceId()));
                final MultipartBody.Part voicefile = authenticationEntity.getVerifierbioMetrics();
// call enroll service
                // construct url
                final String url = authenticationEntity.getVerificationServiceURL() + "/" + authenticationEntity.getVerificationType().toLowerCase() + "/enroll";


                cidaas.getAccessToken(authenticationEntity.getUserId(), new Result<AccessTokenEntity>() {
                    @Override
                    public void success(AccessTokenEntity accessTokenEntity) {
                        iAuthService.enrollVoice(url, accessTokenEntity.getAccess_token(), requestBody, map).enqueue(new Callback<VoiceEnrollServiceResultEntity>() {
                            @Override
                            public void onResponse(Call<VoiceEnrollServiceResultEntity> call, Response<VoiceEnrollServiceResultEntity> response) {
                                if (response.isSuccessful()) {
                                    voiceEnrollServiceResultEntityIResult.onSuccess(response.body());
                                } else {
                                    setErrorBody(response, errorEntity);
                                    errorEntity.setErrorMessage(response.message());
                                    errorEntity.setErrorCode(response.code());
                                    voiceEnrollServiceResultEntityIResult.onError(errorEntity);
                                }
                            }


                            @Override
                            public void onFailure(Call<VoiceEnrollServiceResultEntity> call, Throwable t) {
                                errorEntity.setErrorMessage(t.getMessage());
                                errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                                voiceEnrollServiceResultEntityIResult.onError(errorEntity);
                            }

                        });
                    }

                    @Override
                    public void failure(WebAuthError webAuthError) {
                        // TODO: 25-Oct-18  set url
                        try {
                            Helper.setUpCidaasSDKURL(authTenantInfo);
                            errorEntity.setErrorMessage(webAuthError.getMessage());
                            errorEntity.setErrorCode(webAuthError.getStatusCode());
                            voiceEnrollServiceResultEntityIResult.onError(errorEntity);
                        } catch (Exception ex) {

                        }

                    }
                });
            } else {
                Logger.addRecordToLog("No device info");
                errorEntity.setErrorMessage("No device info");
                errorEntity.setErrorCode(HttpStatusCode.EXPECTATION_FAILED);
                voiceEnrollServiceResultEntityIResult.onError(errorEntity);
            }
        } catch (Exception e) {
            Logger.addRecordToLog("Service call voice Exception" + e);//Crashlytics.logException(e);
            Logger.addRecordToLog("Service call voice Exception" + e);//Crashlytics.logException(e);
        }
    }

    //Enroll Face

    public void enrollFace(final AuthenticationEntity authenticationEntity,
                           final IResult<FaceEnrollServiceResultEntity> faceEnrollServiceResultEntityIResult) {
        try {
            AuthService authService = new AuthService();
            final IAuthService iAuthService = authService.createClient(authenticationEntity.getBaseURL());
            final ErrorEntity errorEntity = new ErrorEntity();

            // get device info
            AuthDeviceInfo authDeviceInfo = getDeviceInfo();

            if (authDeviceInfo != null) {


                final HashMap<String, RequestBody> map = new HashMap<>();
                map.put("statusId", StringtoRequestBody(authenticationEntity.getStatusId()));
                map.put("pushNotificationId", StringtoRequestBody(authDeviceInfo.getPushNotificationId()));
                map.put("deviceMake", StringtoRequestBody(authDeviceInfo.getDeviceMake()));
                map.put("deviceModel", StringtoRequestBody(authDeviceInfo.getDeviceModel()));
                map.put("deviceId", StringtoRequestBody(authDeviceInfo.getDeviceId()));

                // construct url
                final String url = authenticationEntity.getVerificationServiceURL() + "/" + authenticationEntity.getVerificationType().toLowerCase() + "/enroll";
                cidaas.getAccessToken(authenticationEntity.getUserId(), new Result<AccessTokenEntity>() {
                    @Override
                    public void success(AccessTokenEntity accessTokenEntity) {
                        // call enroll service
                        iAuthService.enrollFace(url, accessTokenEntity.getAccess_token(), authenticationEntity.getVerifierbioMetrics(), map).enqueue(new Callback<FaceEnrollServiceResultEntity>() {
                            @Override
                            public void onResponse(Call<FaceEnrollServiceResultEntity> call, Response<FaceEnrollServiceResultEntity> response) {
                                if (response.isSuccessful()) {
                                    faceEnrollServiceResultEntityIResult.onSuccess(response.body());
                                } else {
                                    setErrorBody(response, errorEntity);
                                    assert response.errorBody() != null;
                                    errorEntity.setErrorMessage(response.errorBody().source().toString());
                                    errorEntity.setErrorCode(response.code());
                                    Logger.addRecordToLog("response" + response.message());
                                    try {
                                        Logger.addRecordToLog("response" + response.errorBody().source().readByteString());
                                        Logger.addRecordToLog("response" + response.errorBody().source().readByteString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    faceEnrollServiceResultEntityIResult.onError(errorEntity);
                                }
                            }

                            @Override
                            public void onFailure(Call<FaceEnrollServiceResultEntity> call, Throwable t) {
                                errorEntity.setErrorMessage(t.getMessage() + t.getCause());
                                //    Crashlytics.logException(t);
                                errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                                faceEnrollServiceResultEntityIResult.onError(errorEntity);
                            }

                        });
                    }

                    @Override
                    public void failure(WebAuthError webAuthError) {
// TODO: 25-Oct-18    set url
                        try {
                            Helper.setUpCidaasSDKURL(authTenantInfo);
                            errorEntity.setErrorMessage(webAuthError.getMessage());
                            errorEntity.setErrorCode(webAuthError.getErrorCode());
                            faceEnrollServiceResultEntityIResult.onError(errorEntity);
                        } catch (Exception ex) {

                        }

                    }
                });

            } else {
                Timber.d("No device info");
                Logger.addRecordToLog("No device info");
                errorEntity.setErrorMessage("No device info");
                errorEntity.setErrorCode(HttpStatusCode.EXPECTATION_FAILED);
                faceEnrollServiceResultEntityIResult.onError(errorEntity);
            }
        } catch (Exception e) {
            Logger.addRecordToLog("Service call Face Exception" + e);
            Logger.addRecordToLog("Service call Face Exception" + e);
            //   Crashlytics.logException(e);
        }
    }*/


    /* //Enroll Document

     public void enrollDocument(final String baseUrl, final MultipartBody.Part document,
                                final IResult<DocumentEnrollServiceResultEntity> documentEnrollServiceResultEntityIResult) {
         try {
             AuthService authService = new AuthService();
             final IAuthService iAuthService = authService.createClient(baseUrl);
             final ErrorEntity errorEntity = new ErrorEntity();

             // get device info
             AuthDeviceInfo authDeviceInfo = getDeviceInfo();

             if (authDeviceInfo != null) {
                 // construct url
                 final String url = baseUrl + "/access-control-srv/ocr/validate";
                 // call enroll service
                 iAuthService.enrollDocument(url, document).enqueue(new Callback<DocumentEnrollServiceResultEntity>() {
                     @Override
                     public void onResponse(Call<DocumentEnrollServiceResultEntity> call, Response<DocumentEnrollServiceResultEntity> response) {
                         if (response.isSuccessful()) {
                             documentEnrollServiceResultEntityIResult.onSuccess(response.body());
                         } else {
                             setErrorBody(response, errorEntity);
                             assert response.errorBody() != null;
                             errorEntity.setErrorMessage(response.errorBody().source().toString());
                             errorEntity.setErrorCode(response.code());
                             Logger.addRecordToLog("response" + response.message());
                             try {
                                 Logger.addRecordToLog("response" + response.errorBody().source().readByteString());
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                             documentEnrollServiceResultEntityIResult.onError(errorEntity);
                         }
                     }

                     @Override
                     public void onFailure(Call<DocumentEnrollServiceResultEntity> call, Throwable t) {
                         errorEntity.setErrorMessage(t.getMessage() + t.getCause());
                         Crashlytics.logException(t);
                         errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                         documentEnrollServiceResultEntityIResult.onError(errorEntity);
                     }

                 });

             } else {
                 Timber.d("No device info");
                 errorEntity.setErrorMessage("No device info");
                 errorEntity.setErrorCode(HttpStatusCode.EXPECTATION_FAILED);
                 documentEnrollServiceResultEntityIResult.onError(errorEntity);
             }
         } catch (Exception e) {
             Timber.e("Service call Face Exception" + e);
             //   Crashlytics.logException(e);
         }
     }
 */
   /* public void enrollFido(final AuthenticationEntity authenticationEntity, FidoEnrollServiceEntity fidoRequest,
                           final IResult<FidoEnrollServiceResultEntity> fidoEnrollServiceResultEntityIResult) {
        try {
            AuthService authService = new AuthService();
            final IAuthService iAuthService = authService.createClient(authenticationEntity.getBaseURL());
            final ErrorEntity errorEntity = new ErrorEntity();

            // get device info
            AuthDeviceInfo authDeviceInfo = getDeviceInfo();

            if (authDeviceInfo != null) {

                fidoRequest.setDeviceInfo(authDeviceInfo);
                // construct url
                final String url = authenticationEntity.getVerificationServiceURL() + "/" + authenticationEntity.getVerificationType().toLowerCase() + "/mobile/enroll";
                cidaas.getAccessToken(authenticationEntity.getUserId(), new Result<AccessTokenEntity>() {
                    @Override
                    public void success(AccessTokenEntity accessTokenEntity) {
                        iAuthService.enrollFido(url, Constants.CONTENT_TYPE_JSON, accessTokenEntity.getAccess_token(),
                                fidoRequest)
                                .enqueue(new Callback<FidoEnrollServiceResultEntity>() {
                                    @Override
                                    public void onResponse(Call<FidoEnrollServiceResultEntity> call,
                                                           Response<FidoEnrollServiceResultEntity> response) {
                                        if (response.isSuccessful()) {
                                            fidoEnrollServiceResultEntityIResult.onSuccess(response.body());
                                        } else {
                                            setErrorBody(response, errorEntity);
                                            errorEntity.setErrorMessage(response.message());
                                            errorEntity.setErrorCode(response.code());
                                            fidoEnrollServiceResultEntityIResult.onError(errorEntity);
                                            Logger.addRecordToLog("Fido Error" + response.message());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<FidoEnrollServiceResultEntity> call, Throwable t) {
                                        errorEntity.setErrorMessage(t.getMessage());
                                        errorEntity.setErrorCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                                        fidoEnrollServiceResultEntityIResult.onError(errorEntity);
                                        Logger.addRecordToLog("Fido Error" + t.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void failure(WebAuthError webAuthError) {
                        // TODO: 25-Oct-18  set url
                        try {
                            Helper.setUpCidaasSDKURL(authTenantInfo);
                            errorEntity.setErrorMessage(webAuthError.getMessage());
                            errorEntity.setErrorCode(webAuthError.getStatusCode());
                            fidoEnrollServiceResultEntityIResult.onError(errorEntity);
                            Logger.addRecordToLog("Fido Error" + webAuthError.getMessage());
                        } catch (Exception ex) {

                        }

                    }
                });


            }
        } catch (Exception e) {
            Logger.addRecordToLog("Service call Fido Exception" + e);
            //   Crashlytics.logException(e);
        }
    }
*/









        /*
        DenyRequest denyRequest = new DenyRequest();
        try {
            AuthService authService = new AuthService();
            AuthTenantInfo authTenantInfo = pushNotificationEntity.getTenantInfo();
            final IAuthService iAuthService = authService.createClient(authTenantInfo.getBaseURL() + "/");
            String url = "verification-srv/notification/reject";
            Helper.setUpCidaasSDKURL(authTenantInfo);
            if (authTenantInfo.getUserInfoList() != null && authTenantInfo.getUserInfoList().size() > 0) {
                cidaas.getAccessToken(authTenantInfo.getUserInfoList().get(0).getUserId(), new Result<com.example.cidaasv2.Service.Entity.AccessTokenEntity>() {
                    @Override
                    public void success(com.example.cidaasv2.Service.Entity.AccessTokenEntity accessTokenEntity) {
                        denyRequest.setDenyReasonEnum(DenyReasonEnum.valueOf(reason));
                        denyRequest.setStatusId(pushNotificationEntity.getStatusId());
                        iAuthService.getDenyService(url, Constants.CONTENT_TYPE_JSON, accessTokenEntity.getAccess_token(), denyRequest)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Timber.d("deny REsponse" + response.body());
                                        if (response.isSuccessful() && response.code() == 200) {
                                            Timber.d("deny REsponse" + response.isSuccessful());
                                            EventBus.getDefault().post(new DenyEvent(true));
                                            if (response.body() != null)
                                                result.onSuccess(response.body());
                                        } else {
                                            EventBus.getDefault().post(new DenyEvent(false));
                                            ErrorEntity errorEntity = new ErrorEntity();
                                            errorEntity.setErrorMessage(response.message());
                                            setErrorBody(response, errorEntity);
                                            errorEntity.setErrorCode(response.code());
                                            result.onError(errorEntity);

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Timber.d("deny onFailure" + t.getMessage());
                                        EventBus.getDefault().post(new DenyEvent(false));
                                    }
                                });
                    }

                    @Override
                    public void failure(WebAuthError webAuthError) {
                        Timber.d("deny onError" + webAuthError.getMessage());
                        EventBus.getDefault().post(new DenyEvent(false));
                    }
                }); */
}


