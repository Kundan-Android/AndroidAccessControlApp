package com.widas.demo_ac.common.EventBus;

import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAllow.PushAllowResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject.PushRejectResponse;
import com.widas.demo_ac.entity.AuthenticationEntity;
import com.widas.demo_ac.entity.NewPushNotificationEntity;

public class MFAVerificationEvents {


    public static class NotifyMainActvityEvents {

        public NewPushNotificationEntity newPushNotificationEntity;
        public NotifyMainActvityEvents(NewPushNotificationEntity newPushNotificationEntityData){
            newPushNotificationEntity = newPushNotificationEntityData;
        }
    }



    public static class MFAVerificationAckSuccessEvents {

        public PushAcknowledgeResponse acknowledgeResponse;
        public AuthenticationEntity authenticationEntity;
        public MFAVerificationAckSuccessEvents(PushAcknowledgeResponse response, AuthenticationEntity authEntity){
            acknowledgeResponse = response;
            authenticationEntity= authEntity;
        }
    }

    public static class MFAVerificationAckFailedEvents {

        public WebAuthError webAuthError;
        public MFAVerificationAckFailedEvents(WebAuthError error){
            webAuthError = error;
        }
    }




    public static class MFAVerificationPushAllowSuccessEvents {

        public PushAllowResponse pushAllowResponse;
        public MFAVerificationPushAllowSuccessEvents(PushAllowResponse response){
            pushAllowResponse = response;
        }
    }

    public static class MFAVerificationPushAllowFailedEvents {

        public WebAuthError webAuthError;
        public MFAVerificationPushAllowFailedEvents(WebAuthError error){
            webAuthError = error;
        }
    }


    public static class MFAVerificationPushRejectSuccessEvents {

        public PushRejectResponse pushRejectResponse;
        public MFAVerificationPushRejectSuccessEvents(PushRejectResponse response){
            pushRejectResponse = response;
        }
    }

    public static class MFAVerificationPushRejectFailedEvents {

        public WebAuthError webAuthError;
        public MFAVerificationPushRejectFailedEvents(WebAuthError error){
            webAuthError = error;
        }
    }

    public static class MFAVerificationAuthenticateSuccessEvents {

        public AuthenticateResponse authenticateResponse;
        public MFAVerificationAuthenticateSuccessEvents(AuthenticateResponse response){
           authenticateResponse = response;
        }
    }

    public static class MFAVerificationAuthenticateFailedEvents {

        public WebAuthError webAuthError;
        public MFAVerificationAuthenticateFailedEvents(WebAuthError error){
            webAuthError = error;
        }
    }


    public static class  MFAVerificationAckExpiredEvents{

    }
}
