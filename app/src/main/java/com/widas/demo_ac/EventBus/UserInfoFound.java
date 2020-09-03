package com.widas.demo_ac.EventBus;

import com.example.cidaasv2.Service.Entity.UserinfoEntity;

public class UserInfoFound {

    public static class Userinfo{
        private UserinfoEntity userinfo;
        public Userinfo(UserinfoEntity userinfo){
            this.userinfo = userinfo;
        }
        public UserinfoEntity getUserinfo(){
            return this.userinfo;
        }
    }
}
