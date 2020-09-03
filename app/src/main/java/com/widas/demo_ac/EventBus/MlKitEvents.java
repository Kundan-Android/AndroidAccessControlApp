package com.widas.demo_ac.EventBus;

public class MlKitEvents {


    public static class QrCodeScanEvents {

        public String rawValues;
         public QrCodeScanEvents(String value){
            rawValues = value;
        }

    }

}
