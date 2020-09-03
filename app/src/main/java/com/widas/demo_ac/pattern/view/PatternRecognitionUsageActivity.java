package com.widas.demo_ac.pattern.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAllow.PushAllowResponse;
import com.widas.demo_ac.Alert.SweetAlertDialog;
import com.widas.demo_ac.MainActivity;
import com.widas.demo_ac.R;
import com.widas.demo_ac.base.BaseActivity;
import com.widas.demo_ac.entity.AuthenticationEntity;
import com.widas.demo_ac.login.LoginActivity;
import com.widas.demo_ac.pattern.model.IPatternVerificationView;
import com.widas.demo_ac.pattern.presenter.PatternRecognitionUsagePresenter;

import java.util.List;

public class PatternRecognitionUsageActivity extends BaseActivity<PatternRecognitionUsagePresenter> implements View.OnClickListener, IPatternVerificationView {

    //local Variables
    AuthenticationEntity authenticationEntity;
    UserinfoEntity userInfoEntity;
    boolean isVerification;

    // Progress Dialogue
    private ProgressDialog progressDialog;

    //Pattern Lock View Variable
    PatternLockView mPatternLockView;
    TextView patternTitle;

    int selectedColor;
    String color, oldPattern;
    //AlertDialog
    AlertDialog alertDialog = null;
    //Enroll Flag
    boolean isEnroll = true;
    boolean isDraw = true;
    PatternRecognitionUsagePresenter patternRecognitionUsagePresenter;

    PushAllowResponse pushAllowResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_recognition_usage);

        getSupportActionBar().setTitle(R.string.pattern_settings);

        patternRecognitionUsagePresenter = new PatternRecognitionUsagePresenter(this);

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/
        try {

            // bind butterknife
            ButterKnife.bind(this);

            //

            showColorDialog();
            // get intent
            if ((getIntent() != null && getIntent().getSerializableExtra("authenticationEntity") != null) && (getIntent().getSerializableExtra("userInfoEntity") != null)) {

                authenticationEntity = (AuthenticationEntity) getIntent().getSerializableExtra("authenticationEntity");
                userInfoEntity = (UserinfoEntity) getIntent().getSerializableExtra("userInfoEntity");
            } else if ((getIntent().getSerializableExtra("authenticationEntity") != null) && (getIntent().getExtras().get("isVerification") != null)) {
                authenticationEntity = (AuthenticationEntity) getIntent().getSerializableExtra("authenticationEntity");
                isVerification = getIntent().getExtras().getBoolean("isVerification", false);
            }
            if(getIntent()!=null && getIntent().getSerializableExtra("pushAllowResponse")!=null){
                pushAllowResponse = (PushAllowResponse) getIntent().getSerializableExtra("pushAllowResponse");
            }

            //patternView
        } catch (Exception e) {
            Timber.d(e.getMessage());
        }
    }

    @Nullable
    @BindView(R.id.RedButton)
    Button RedButton;

    @Nullable
    @BindView(R.id.BlueButton)
    Button BlueButton;

    @Nullable
    @BindView(R.id.GreenButton)
    Button GreenButton;

    @Nullable
    @BindView(R.id.OrangeButton)
    Button OrangeButton;

    @Nullable
    @BindView(R.id.PinkButton)
    Button PinkButton;

    @Nullable
    @BindView(R.id.YellowButton)
    Button YellowButton;

    AlertDialog mAlertDialog;
    AlertDialog.Builder dialogBuilder;

    // show color dialog
    public void showColorDialog() {
        try {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();

            final View dialog = inflater.inflate(R.layout.dialog_color, null);
            ButterKnife.bind(this, dialog);
            builder.setView(dialog);
            //Set On Click listeners
            RedButton.setOnClickListener(this);
            GreenButton.setOnClickListener(this);
            YellowButton.setOnClickListener(this);
            BlueButton.setOnClickListener(this);
            PinkButton.setOnClickListener(this);
            OrangeButton.setOnClickListener(this);


            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);

            alertDialog.show();


        } catch (Exception ex) {
            Timber.d(ex.getMessage());
        }
    }

    //Pattern Lock View Properties
    public void addPatternViewProperties(int selectedColor) {
        try {
            mPatternLockView = findViewById(R.id.pattern_setup_pattern);
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            mPatternLockView.setDotCount(3);
            mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
            mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
            mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
            mPatternLockView.setAspectRatioEnabled(true);
            mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
            mPatternLockView.setDotAnimationDuration(150);
            mPatternLockView.setPathEndAnimationDuration(100);
            mPatternLockView.setNormalStateColor(ResourceUtils.getColor(this, selectedColor));
            mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, selectedColor));
            mPatternLockView.setInStealthMode(false);
            mPatternLockView.setTactileFeedbackEnabled(true);
            mPatternLockView.setInputEnabled(true);
            mPatternLockView.addPatternLockListener(mPatternLockViewListener);
        } catch (Exception e) {

        }
    }
    //Pattern Listener
    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            showLoader();

            String patterntoVerify = PatternLockUtils.patternToString(mPatternLockView, pattern);

            AuthenticateEntity authenticateEntity = new AuthenticateEntity();
            authenticateEntity.setPass_code(constructPassCode(patterntoVerify));
           /* authenticateEntity.setExchange_id(pushAllowResponse.getData().getExchange_id().getExchange_id());
            authenticateEntity.setClient_id(pushAllowResponse.getData().getClient_id());
            authenticateEntity.setDevice_id(pushAllowResponse.getData().getDevice_id());
            authenticateEntity.setPush_id(pushAllowResponse.getData().getPush_id());
            authenticateEntity.setVerificationType(authenticationEntity.getVerificationType());
*/
           Timber.d("");
          //  patternRecognitionUsagePresenter.callAuthenticate(authenticateEntity,authenticationEntity);


        }

        @Override
        public void onCleared() {

        }
    };

    // show loader
    public void showLoader() {
        try {
            if (mAlertDialog != null && mAlertDialog.isShowing()) return;

            dialogBuilder = new AlertDialog.Builder(PatternRecognitionUsageActivity.this);
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RedButton: {
                // Toast.makeText(getApplicationContext(),"Red",Toast.LENGTH_LONG).show();
                selectedColor = R.color.colorRed;
                color = "RED";
                addPatternViewProperties(selectedColor);
                break;
            }
            case R.id.YellowButton: {
                selectedColor = R.color.colorYellow;
                color = "YELLOW";

                addPatternViewProperties(selectedColor);
                break;
            }
            case R.id.BlueButton: {
                selectedColor = R.color.colorBlue;
                color = "BLUE";
                addPatternViewProperties(selectedColor);
                break;
            }
            case R.id.GreenButton: {
                selectedColor = R.color.colorGreen;
                color = "GREEN";
                addPatternViewProperties(selectedColor);
                break;
            }
            case R.id.OrangeButton: {
                selectedColor = R.color.colorOrange;
                color = "ORANGE";
                addPatternViewProperties(selectedColor);
                break;
            }
            case R.id.PinkButton: {
                selectedColor = R.color.colorMagenta;
                color = "PINK";
                addPatternViewProperties(selectedColor);
                break;
            }

            case R.id.pattern_usage_reset_button: {
                ResetPattern();
                break;
            }


        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private String constructPassCode(String pattern){
        char stringarray[] = pattern.toCharArray();

        //String builder
        StringBuilder patternString = new StringBuilder();
        // Encrypt the Pattern
        for (char charvalue : stringarray
        ) {
            int value = Integer.parseInt(String.valueOf(charvalue));
            switch (value) {
                case 0:
                    patternString.append("000-000&");
                    break;
                case 1:
                    patternString.append("000-001&");
                    break;
                case 2:
                    patternString.append("000-002&");
                    break;
                case 3:
                    patternString.append("001-000&");
                    break;
                case 4:
                    patternString.append("001-001&");
                    break;
                case 5:
                    patternString.append("001-002&");
                    break;
                case 6:
                    patternString.append("002-000&");
                    break;
                case 7:
                    patternString.append("002-001&");
                    break;
                case 8:
                    patternString.append("002-002&");
                    break;
                default:
                    break;
            }
        }
        String finalpattern = "";
        if (patternString != null && patternString.length() >= 0) {
            finalpattern = patternString.substring(0, patternString.length() - 1);
        }

        return color + "-" + finalpattern;
    }

    //This Method is called From Onclick Method in XML
    public void ResetPattern() {
        recreate();
        /*try {
            oldPattern = null;
            mPatternLockView = findViewById(R.id.pattern_setup_pattern);
            isDraw = true;
            showColorDialog();
        } catch (Exception ex) {
            Timber.d(ex.getMessage());
        }
*/
    }

    // hide loader
    public void hideLoader() {
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
    }

    public void ShowDialog(String message, boolean isError) {
        hideLoader();
        if (isError) {
            sweetDialog = showAlertDialogs(this, getString(R.string.information), message, null, SweetAlertDialog.ERROR_TYPE, getString(R.string.ok)
                    , false, "");
            sweetDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    recreate();
                }
            });
            sweetDialog.show();

        } else {
            sweetDialog = showAlertDialogs(this, getString(R.string.information), message, null, SweetAlertDialog.SUCCESS_TYPE, getString(R.string.ok)
                    , false, "");
            sweetDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    sweetAlertDialog.dismiss();
                    Intent intent = new Intent(PatternRecognitionUsageActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            sweetDialog.show();
        }
    }


    @Override
    protected void onDestroy() {
        hideLoader();
        super.onDestroy();
    }


    //on BackPressed
    @Override
    public void onBackPressed() {

        hideLoader();
        //Redirect to home
        Intent intent = new Intent(PatternRecognitionUsageActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //On Click Navigation Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }




    @Override
    public void patternAuthenticateSuccess(AuthenticateResponse authenticateResponse) {
        hideLoader();

        if (authenticateResponse.isSuccess()) {
            String message = String.format((getString(R.string.success_login)));
            getPresenter().updateUserUpdatedTime(authenticationEntity.getUserId(), authenticationEntity.getVerificationType(),authenticationEntity.getTenantKey() );

            CharSequence styledText = Html.fromHtml(message);
            ShowDialog(styledText.toString(), false);
        } else {
            String message = getString(R.string.invalid_code);
            ShowDialog(message, true);
        }
    }

    @Override
    public void patternAuthenticateFailed(WebAuthError webAuthError) {
        hideLoader();
        int code = 400;
        try {
            if (webAuthError != null && webAuthError.getErrorEntity() != null)
                code = webAuthError.getErrorEntity().getCode();

        } catch (Exception Ex) {
           Timber.d(Ex.getMessage());
        }
        String message="";
        if (code==3063) {
            message = getString(R.string.pattern_not_correct_try_again);
        }else {
            message = "";//Helper.getErrorMessageFromCode(code, PatternRecognitionUsageActivity.this);
        }
        Timber.d("Pattern usage code " +code +"Message "+ message);
        ShowDialog(message, true);
    }
}

