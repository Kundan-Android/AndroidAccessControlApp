package com.widas.demo_ac;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseApp;
import com.widas.demo_ac.AccessControl.Model.ScannedQRdata;
import com.widas.demo_ac.AccessControl.View.AccessControlActivity;
import com.widas.demo_ac.Alert.CustomAlert;
import com.widas.demo_ac.Alert.SweetAlertDialog;
import com.widas.demo_ac.EventBus.MlKitEvents;
import com.widas.demo_ac.entity.AuthenticationEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class ScannerActivity extends AppCompatActivity {

    @BindView(R.id.cameraSourcePreview)
    CameraSourcePreview cameraSourcePreview;

    @BindView(R.id.animation_view)
    LottieAnimationView animation_view;

    private CameraSource cameraSource = null;

    BarcodeScanningProcessor barcodeScanningProcessor = null;

    boolean isProcessing = false;
    CustomAlert customAlert;
    protected SweetAlertDialog sweetDialog;
    //custom alert dialog for alternative progress dialog
    AlertDialog mAlertDialog;
    AlertDialog.Builder dialogBuilder;
    String accessToken = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        animation_view = findViewById(R.id.animation_view);
        cameraSourcePreview = findViewById(R.id.cameraSourcePreview);
        customAlert = new CustomAlert();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        accessToken = bundle.getString("accessToken");
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        // ...create the camera resource
        startScanner();
    }

    //On pause
    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        if (cameraSourcePreview != null)
            cameraSourcePreview.stop();

        super.onPause();
    }


    @Override
    protected void onStop() {
        try {
            //  stopCamera();

            super.onStop();
        } catch (Exception e) {//Crashlytics.logException(e);
            super.onStop();
        }
    }

    //On Destroy
    @Override
    protected void onDestroy() {
        //hideLoader();
        if (cameraSource != null) {
            cameraSource.release();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    private void startScanner() {

        animation_view.setVisibility(View.VISIBLE);
        if (cameraSource != null) {
            try {
                if (cameraSourcePreview == null) {
                    Timber.d("resume: cameraSourcePreview is null");
                    return;
                }

                cameraSourcePreview.start(cameraSource);

            } catch (IOException e) {
                //Logger.addRecordToLog("Unable to start Scanning camera source " + e.getLocalizedMessage());
                Log.d("ScannerActivity","Unable to start Scanning camera source " + e.getLocalizedMessage());
                cameraSource.release();
                cameraSource = null;
            }
        } else {
            createCameraSource();
        }
    }
    private void createCameraSource() {

        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, new ICameraSource() {
                @Override
                public void blinkedEvent() {

                }

                @Override
                public void pictureTaken(File file) {

                }
            });

            // set front facing
            cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
        }
        barcodeScanningProcessor = new BarcodeScanningProcessor();

        cameraSource.setMachineLearningFrameProcessor(barcodeScanningProcessor);

        startScanner();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onQRcodeResult(MlKitEvents.QrCodeScanEvents qrCodeScanEvents) {

        if (!qrCodeScanEvents.rawValues.isEmpty() && !isProcessing) {
            try {

                Timber.d("onQRcodeResult %s ", qrCodeScanEvents.rawValues);
                isProcessing = true;
                animation_view.setVisibility(View.GONE);
                cameraSource.stop();

               /* // valid url check
                if (!qrCodeScanEvents.rawValues.startsWith("otpauth")) {
                   Timber.d("Inside scanning starts with otpauth false " + qrCodeScanEvents.rawValues);
                    showAlertDialog(getString(R.string.invalid_qr_code), false);

                    //  resumeCamera();
                } else {*/
                    // stop camera
                    //stopCamera();
                 //   if (CheckConnection()) {
                        // show loader
                        showLoader();

                        ObjectMapper obj = new ObjectMapper();

                        ScannedQRdata scannedQRdataEntity = obj.readValue(qrCodeScanEvents.rawValues,ScannedQRdata.class);
                        Timber.d(""+scannedQRdataEntity.getDi());
                        Timber.d(""+scannedQRdataEntity.getDn());
                        Timber.d(""+scannedQRdataEntity.getIss());

                        Intent accessControl = new Intent(this, AccessControlActivity.class);
                        accessControl.putExtra("device_id",scannedQRdataEntity.getDi());
                        accessControl.putExtra("device_name",scannedQRdataEntity.getDn());
                        accessControl.putExtra("issuer_url",scannedQRdataEntity.getIss());
                        accessControl.putExtra("ph_id",scannedQRdataEntity.getPhId());

                        hideLoader();
                        startActivity(accessControl);

                       /* // construct an url and call construct an entity method
                        Uri uri = Uri.parse(qrCodeScanEvents.rawValues);
                        Timber.d("Scanned result Uri " + uri);
                        constructQRCodeEntity(uri);*/
                       // hideLoader();
                      //  showAlertDialog(getString(R.string.ok),true);


                  /*  } else {
                     //   Logger.addRecordToLog("No internet ");
                        showAlertDialog(getApplicationContext().getString(R.string.no_internet_conn), false);
                    }*/
               // }
            } catch (Exception ex) {
              //  hideLoader();
                showAlertDialog(getString(R.string.invalid_code), false);//Crashlytics.logException(ex);
               // Logger.addRecordToLog("Scanner Exception" + ex.getLocalizedMessage());
               // Logger.addRecordToLog("Scanner Exception" + ex.getLocalizedMessage());
                //stopCamera();
                // startCamera();
            }
        }
    }

    String baseURL = "";

    public static String decodeUri(String uri) {

        if (TextUtils.isEmpty(uri))
            return null;
        if (uri.startsWith("file://") && uri.length() > 7)
            return Uri.decode(uri.substring(7));
        return Uri.decode(uri);
    }
    // hide loader
    public void hideLoader() {
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
    }

    // construct entity
    public void constructQRCodeEntity(Uri uri) {
        try {
            String decodedUri = decodeUri(uri.toString());
            uri = Uri.parse(decodedUri);

            baseURL = uri.getQueryParameter("burl");  // base url not redirect
            if (baseURL != null) {

            /* d = display name
              issuer = tenant name
              tk = tenant key
              l = logo url
              sub = sub
              cid = authenticator client id
              rurl = redirect url
              eid = exchange id
              secret = totp_secret (only for totp)*/

                AuthenticationEntity incomingData = new AuthenticationEntity();


                //  showLoader();

                // Secret will be present only for (TOTP) PUSH otherwise it'll be null
                incomingData.setSecret(uri.getQueryParameter("secret"));

                incomingData.setExchange_id(uri.getQueryParameter("eid"));
                incomingData.setDisplayName(uri.getQueryParameter("d"));
                incomingData.setLogoURL(uri.getQueryParameter("l"));
                incomingData.setVerificationType(uri.getQueryParameter("t"));
                incomingData.setIssuer(uri.getQueryParameter("issuer"));  // tenant name
                incomingData.setTenantKey(uri.getQueryParameter("tk"));  // tenant key
                incomingData.setUserId(uri.getQueryParameter("sub"));
                incomingData.setClientId(uri.getQueryParameter("cid")); // authenticator client id
                incomingData.setBaseURL(baseURL);
                //incomingData.setStatusId(uri.getQueryParameter("st"));
                // incomingData.setRandomNumbers(uri.getQueryParameter("rns"));  // Random numbers for PUSH verification

                String redirectUrl = baseURL.replace("https", getApplicationContext().getPackageName());

                incomingData.setRedirectURL(redirectUrl + "/apps-srv/ping");
                // incomingData.setViewType("login");
                // incomingData.setSocialURL(baseURL + "/oauth2-social-service/token");
                //  incomingData.setVerificationServiceURL(baseURL + "/verification-srv");

              //  fieldValidation(incomingData);

            } else {
                showAlertDialog(getString(R.string.invalid_qr_code), false);
            }

        } catch (Exception ex) {////hlytics.logException(ex);
            // hideLoader();
            showAlertDialog(getString(R.string.invalid_code), false);
            // Logger.addRecordToLog("Scanner Construct URL" + ex.getMessage());
            //  Toast.makeText(this, "Scanner Construct URL"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    // field validation
   /* public void fieldValidation(AuthenticationEntity incomingData) {
        try {

            if (incomingData.getIssuer() == null || incomingData.getBaseURL() == null ||
                    incomingData.getClientId() == null || incomingData.getUserId() == null ||
                    incomingData.getVerificationType() == null ||
                    incomingData.getTenantKey() == null) {

              //  Logger.addRecordToLog("fieldValidation failed " + new ObjectMapper().writeValueAsString(incomingData));
                // hide loader
             //   hideLoader();
                // show dialog
                showAlertDialog(getString(R.string.invalid_qr_code), false);

            } else {
                switch (incomingData.getVerificationType()) {
                    case AuthenticationType.touch:
                        handleTouchId(incomingData);
                        break;
                    case AuthenticationType.totp:
                        if (incomingData.getSecret() == null) {

                            hideLoader();
                            // show dialog
                            showAlertDialog(getString(R.string.invalid_totp), false);
                        } else {

                            proceedtoNextStep(incomingData);
                        }
                        break;

                    case AuthenticationType.fido:
                        if (nfcAdapter == null) {
                            hideLoader();
                            showAlertDialog(getString(R.string.device_doesnt_support_nfc), false);
                        } else if (!nfcAdapter.isEnabled()) {
                            hideLoader();
                            showAlertDialog(getString(R.string.nfc_disabbled), false);
                        } else {
                            proceedtoNextStep(incomingData);
                        }
                        break;
                    default:
                        proceedtoNextStep(incomingData);
                        break;
                }
            }


        } catch (Exception ex) {
         //   hideLoader();//Crashlytics.logException(ex);
            showAlertDialog(getString(R.string.invalid_code), false);
            // Toast.makeText(this, "Scanner Field validation"+ex.getMessage(), Toast.LENGTH_SHORT).show();
            Timber.d("Scanner Field validation" + ex.getMessage());
           // Logger.addRecordToLog("Scanner Field validation" + ex.getMessage());
        }
    }
*/
    // show loader
    public void showLoader() {
        try {
            if (mAlertDialog != null && mAlertDialog.isShowing()) return;

            dialogBuilder = new AlertDialog.Builder(ScannerActivity.this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
            TextView tv_title = dialogView.findViewById(R.id.tv_title);
            TextView tv_message = dialogView.findViewById(R.id.tv_message);
            tv_title.setText(R.string.please_wait);
            tv_message.setText(getString(R.string.loading));
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(false);
            mAlertDialog = dialogBuilder.create();
            mAlertDialog.show();
        } catch (Exception ex) {
            Timber.d("Scanner activity dialog exception " + ex.getMessage());
        }


    }

    private void startScanning() {
        isProcessing = false;
        animation_view.setVisibility(View.VISIBLE);

        startScanner();
    }

    // show alert dialog
    public void showAlertDialog(CharSequence message, boolean b) {
        int type = SweetAlertDialog.ERROR_TYPE;
        if (b)
            type = SweetAlertDialog.SUCCESS_TYPE;
        hideLoader();
        sweetDialog = showAlertDialogs(ScannerActivity.this, getString(R.string.information),
                message.toString(), null, type, getString(R.string.ok), false, "");
        sweetDialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismiss();
            hideLoader();
            if (b)
                gotoNextScreen();

        }).setOnDismissListener(dialog -> {
            if (!b)
                startScanning();
        });
        sweetDialog.show();
    }

    private void gotoNextScreen() {
            Intent accessControl = new Intent(this, AccessControlActivity.class);
            startActivity(accessControl);
    }

    public SweetAlertDialog showAlertDialogs(Activity activity, String title, String contextTitle,
                                             Drawable drawable, int alertType, String confirmText, boolean showCancel, String cancelText) {
        return customAlert.showAlertDialog(activity, title, contextTitle, drawable, alertType, confirmText, showCancel, cancelText);
    }

    public SweetAlertDialog showBasicDialog(Activity activity, String title) {
        return customAlert.basicDialog(activity, title);
    }
    //Check internetConnection
    public boolean CheckConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    // hide loader

}
