package com.widas.demo_ac;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.VerificationV2.presentation.View.CidaasVerification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.widas.demo_ac.EventBus.LoginEvent;
import com.widas.demo_ac.EventBus.UserInfoFound;
import com.widas.demo_ac.MainScreen.AssetsListScreen;
import com.widas.demo_ac.common.SessionManager;
import com.widas.demo_ac.common.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    /*@BindView(R.id.card_view_home)
    CardView mCardView;*/
    @BindView(R.id.user)
    TextView userName;
    @BindView(R.id.openBtn)
    TextView openBtn;
    @BindView(R.id.listBtn)
    TextView listBtn;
    @BindView(R.id.lastLogin)
    TextView lastLogin;
   // Button login;

    AlertDialog mAlertDialog;
    AlertDialog.Builder dialogBuilder;
    private String accessToken = "";
    SessionManager sessionManager = null;
    private UserinfoEntity userinfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getFCMTocken();
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setElevation(1);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        //  login = findViewById(R.id.login);
        showLoader();
        sessionManager = new SessionManager(AppApplication.getInstance().getApplicationContext());
        userinfoEntity = new UserinfoEntity();

        String token = getIntent().getDataString();
        if (token != null) {
            Cidaas.getInstance(this).handleToken(token);
        }
        openBtn.setOnClickListener(view -> {
            if (CameraUtils.isCameraPermissionGranted(MainActivity.this)) {
                // ...go to scanner activity
                Intent intent = new Intent(MainActivity.this, ScannerActivity/*AccessControlActivity*/.class);
                intent.putExtra("accessToken",accessToken);
                startActivity(intent);
            } else {
                // ...else request the camera permission
                CameraUtils.requestCameraPermission(MainActivity.this);
            }
        });
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.isInternetAvailable(view.getContext())) {
                    Intent intent = new Intent(MainActivity.this, AssetsListScreen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "Please check internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /* login.setOnClickListener(view -> {
         *//* LoginEntity entity = new LoginEntity();
            entity.setUsername("raja.narayanan@widas.in");
            entity.setPassword("2716");
            entity.setUsername_type("email");
            Cidaas.getInstance(this).loginWithCredentials(entity, new Result<LoginCredentialsResponseEntity>() {
                @Override
                public void success(LoginCredentialsResponseEntity result) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                }
            });*//*
         *//* LoginRequest.getPasswordlessPatternLoginRequestEntity("RED[0,1,2]",)
                    loginRequest
           CidaasVerification.getInstance(this).loginWithPattern();*//*

            PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
            passwordlessEntity.setEmail("raja.narayanan@widas.in");
            passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);

            Cidaas.getInstance(this).loginWithPatternRecognition("RED[0,1,2]", passwordlessEntity, new Result<LoginCredentialsResponseEntity>() {
                @Override
                public void success(LoginCredentialsResponseEntity result) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(WebAuthError error) {
                    Toast.makeText(MainActivity.this, "UnSuccess"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });*/


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }


    public void getFCMTocken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Timber.e( "getInstanceId failed"+ task.getException().getMessage());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Cidaas.getInstance(getApplicationContext()).setFCMToken(token);
                        CidaasVerification.getInstance(getApplicationContext()).updateFCMToken(token);
                        Timber.i("FCM TOKEN" + token);
       //  Toast.makeText(MainActivity.this, "Token "+token, Toast.LENGTH_SHORT).show();
                    }
                });
    }





    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CameraUtils.REQUEST_CAMERA_PERM : {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // ...go to scanner activity
                    Intent intent = new Intent(MainActivity.this,ScannerActivity/*AccessControlActivity*/.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        userinfoEntity = sessionManager.getUserDetails();
        if (userinfoEntity != null) {
            userName.setText(userinfoEntity.getName());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            long timeStamplong = userinfoEntity.getLast_accessed_at();
            Timestamp timestamp = new Timestamp(timeStamplong);
            Date date = new Date(timestamp.getTime());
            lastLogin.setText("Last Login: " + formatter.format(date));
            hideLoader();
        }




       /* AuthService service = new AuthService();
        Call<DeviceStatus> deviceStatus = service.getStatus().getDeviceStatus("47771079-4e77-4744-b892-f46cc62410c8");

        deviceStatus.enqueue(new Callback<DeviceStatus>() {
            @Override
            public void onResponse(Call<DeviceStatus> call, Response<DeviceStatus> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeviceStatus> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error throwable", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoader();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoReceived(UserInfoFound.Userinfo userinfo){
        Log.d("asb",userinfo+"");
       // Timestamp timestamp = new Timestamp(userinfo.getUserinfo().getLast_accessed_at());
       // Date date = new Date(userinfo.getUserinfo().getLast_accessed_at());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        long timeStamplong = userinfo.getUserinfo().getLast_accessed_at();
        Timestamp timestamp = new Timestamp(timeStamplong);
        Date date = new Date(timestamp.getTime());

        userName.setText(userinfo.getUserinfo().getName());
        lastLogin.setText("Last Login: "+formatter.format(date));
        hideLoader();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedAccessToken(LoginEvent.ReceivedAccessToken receivedAccessToken){
        Log.d("asb",receivedAccessToken+"");
        accessToken = receivedAccessToken.getAccessToken();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home)
             onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    // show loader
    public void showLoader() {
        try {
            if (mAlertDialog != null && mAlertDialog.isShowing()) return;

            dialogBuilder = new AlertDialog.Builder(MainActivity.this);
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
            Timber.d("Login activity dialog exception " + ex.getMessage());
        }


    }
    // hide loader
    public void hideLoader() {
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
    }
}
