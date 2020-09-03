package com.widas.demo_ac.EventBus;

public  class LoginEvent {

    public static class ReceivedAccessToken{

        private String accessToken;

        public ReceivedAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessToken() {
            return accessToken;
        }
    }

}
