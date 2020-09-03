package com.widas.demo_ac.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.google.android.material.textfield.TextInputLayout;
import com.widas.demo_ac.EventBus.LoginEvent;
import com.widas.demo_ac.EventBus.UserInfoFound;
import com.widas.demo_ac.MainActivity;
import com.widas.demo_ac.R;
import com.widas.demo_ac.common.SessionManager;
import com.widas.demo_ac.common.Util;
import com.widas.demo_ac.pattern.view.PatternRecognitionUsageActivity;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class LoginActivity extends AppCompatActivity {

   /* @BindView(R.id.input_name)
    EditText input_name;
    @BindView(R.id.input_pwd)
    EditText input_pwd;
    @BindView(R.id.input_layout_name)
    TextInputLayout input_layout_name;
    @BindView(R.id.input_layout_pwd)
    TextInputLayout input_layout_pwd;*/
    //custom alert dialog for alternative progress dialog
    AlertDialog mAlertDialog;
    AlertDialog.Builder dialogBuilder;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoader();
        EventBus.getDefault().unregister(this);
    }

    public void login(View view) {
        switch(view.getId()){
            case R.id.login:
                if (Util.isInternetAvailable(this)) {
                    showLoader();
               /* input_layout_name.setError(null);
                input_layout_pwd.setError(null);
                if (!isValidEmail(input_name.getText().toString())){
                    input_layout_name.setError("Please enter correct email.");
                    return;
                }
                if (input_pwd.getText().toString().isEmpty()){
                    input_layout_pwd.setError("Please enter password");
                    return;
                }
                showLoader();
                LoginEntity loginEntity = new LoginEntity();
                loginEntity.setUsername(input_name.getText().toString());
                loginEntity.setPassword(input_pwd.getText().toString());
                loginEntity.setUsername_type("email");*/
               /* Cidaas.getInstance(this).loginWithCredentials(loginEntity, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity result) {
                        hideLoader();
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentMain);
                        finish();
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        hideLoader();
                        Toast.makeText(LoginActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

                    }
                });*/
                    Cidaas.getInstance(this).getRequestId(new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            // Toast.makeText(LoginActivity.this, ""+result.getData().getRequestId(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(WebAuthError error) {

                        }
                    });//
                    Cidaas.getInstance(this).loginWithBrowser(LoginActivity.this, "#3574f6", new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accessTokenresult) {
                            //Your Success Code
                            new SessionManager(context).setAccessToken(accessTokenresult.getAccess_token());
                            //  EventBus.getDefault().post(new LoginEvent.ReceivedAccessToken(accessTokenresult.getAccess_token()));

                            Cidaas.getInstance(LoginActivity.this).getUserInfo(accessTokenresult.getSub(), new Result<UserinfoEntity>() {
                                @Override
                                public void success(UserinfoEntity userinfoEntity) {
                                    new SessionManager(context).setUserDetails(userinfoEntity);
                                    EventBus.getDefault().post(new UserInfoFound.Userinfo(userinfoEntity));
                                }

                                @Override
                                public void failure(WebAuthError error) {
                                    Timber.d("failure");
                                }
                            });
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            //Your Failure Code
                            Timber.d("failure");

                        }
                    });
                } else Toast.makeText(context, "Please check internet connection!", Toast.LENGTH_SHORT).show();
                break;
           /* case R.id.btn_passwordless:
                goToMFAListScreen();
                break;*/

        }
    }


    private void goToMFAListScreen() {
        Intent intent = new Intent(this, /*MFAListActivity*/PatternRecognitionUsageActivity.class);
        startActivity(intent);
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    // show loader
    public void showLoader() {
        try {
            if (mAlertDialog != null && mAlertDialog.isShowing()) return;

            dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
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
