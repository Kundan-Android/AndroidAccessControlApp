package com.widas.demo_ac.AccessControl.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.widas.demo_ac.AccessControl.Model.ActionPerformStatus;
import com.widas.demo_ac.AccessControl.Model.ActionResponse;
import com.widas.demo_ac.AccessControl.Model.DeviceStatus;
import com.widas.demo_ac.AccessControl.service.AuthService;
import com.widas.demo_ac.MainActivity;
import com.widas.demo_ac.R;
import com.widas.demo_ac.common.Util;
import com.widas.demo_ac.helper.MyBounceInterpolator;
import com.zcw.togglebutton.ToggleButton;

public class AccessControlActivity extends AppCompatActivity {
   /* @BindView(R.id.toggleSwitch)
    ToggleButton toggleSwitch;*/
//    @BindView(R.id.switchStatus)
//    TextView switchStatus;
    /*@BindView(R.id.deviceName)
     TextView deviceName;*/
    @BindView(R.id.control_Btn)
    TextView control_Btn;
    @BindView(R.id.constraint_layout)
    ConstraintLayout constraintLayout;
    Animation myAnim;
    double animationDuration =  3000;

    String device_id, device_name, issuer_url = "", ph_id;
   // APIInterface apiInterface;
    DeviceStatus deviceResponseStatus;
    AlertDialog mAlertDialog;
    AlertDialog.Builder dialogBuilder;
    AuthService service;
    boolean  switchStatusdynamic = false;
    Window window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_control);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        Bundle bundle = getIntent().getExtras();
        device_id = bundle.getString("device_id");
        device_name = bundle.getString("device_name");
        issuer_url = bundle.getString("issuer_url");
        ph_id = bundle.getString("ph_id");
        getSupportActionBar().setTitle("Device: "+device_name);

        myAnim = AnimationUtils.loadAnimation(AccessControlActivity.this, R.anim.bounce);

        //https://nightlybuild.cidaas.de/access-control-srv/"

     //   deviceName.setText(device_name);

       // apiInterface = APIClient.getClient(issuer_url).create(APIInterface.class);
/*
        toggleSwitch.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    showLoader();
                    Call<ActionPerformStatus> actionPerformStatusCall = service.getInstance(issuer_url).performAction(device_id,ph_id,new ActionResponse("on")); *//*apiInterface.performAction(device_id,"on");*//*
                    actionPerformStatusCall.enqueue(new Callback<ActionPerformStatus>() {
                        @Override
                        public void onResponse(Call<ActionPerformStatus> call, Response<ActionPerformStatus> response) {
                            hideLoader();
                            switchStatus.setTextColor(Color.RED);
                            switchStatus.setText("On");
                            Toast.makeText(AccessControlActivity.this, "Your door is open", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ActionPerformStatus> call, Throwable t) {
                            hideLoader();
                            switchStatus.setTextColor(Color.GREEN);
                            switchStatus.setText("Off");
                            Toast.makeText(AccessControlActivity.this, "Unable to turn on", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else{
                    showLoader();
                    Call<ActionPerformStatus> actionPerformStatusCall = service.getInstance(issuer_url).performAction(device_id,ph_id,new ActionResponse("off"));
                    actionPerformStatusCall.enqueue(new Callback<ActionPerformStatus>() {
                        @Override
                        public void onResponse(Call<ActionPerformStatus> call, Response<ActionPerformStatus> response) {
                            hideLoader();
                            switchStatus.setTextColor(Color.GREEN);
                            switchStatus.setText("Off");
                            Toast.makeText(AccessControlActivity.this, "Your door is closed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ActionPerformStatus> call, Throwable t) {
                            hideLoader();
                            switchStatus.setTextColor(Color.RED);
                            switchStatus.setText("On");
                            Toast.makeText(AccessControlActivity.this, "Unable to turn off", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });// {
         
       // });*/

    }


    @Override
    protected void onResume() {
        super.onResume();
        service = new AuthService();
        if (Util.isInternetAvailable(this)) {
            showLoader();
            handleStatus();
        } else {
            Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
         }

            control_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Util.isInternetAvailable(view.getContext())) {

                        if (switchStatusdynamic) {
                            showLoader();
                            Call<ActionPerformStatus> actionPerformStatusCall = service.getInstance(issuer_url)
                                    .performAction(device_id, ph_id, new ActionResponse("off")); /*apiInterface.performAction(device_id,"on");*/
                            actionPerformStatusCall.enqueue(new Callback<ActionPerformStatus>() {
                                @Override
                                public void onResponse(Call<ActionPerformStatus> call, Response<ActionPerformStatus> response) {
                                    hideLoader();
                                    myAnim.setDuration((long) animationDuration);
                                    // Use bounce interpolator with amplitude 0.2 and frequency 20
                                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                                    myAnim.setInterpolator(interpolator);
                                    control_Btn.startAnimation(myAnim);


                                    constraintLayout.setBackgroundColor(Color.parseColor("#3574f6"));
                                    control_Btn.setBackground(getResources().getDrawable(R.drawable.bluecircle));
                                    control_Btn.setText("open");
                                    switchStatusdynamic = false;
                                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                            .getColor(R.color.colorBlue)));
                                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                    window.setStatusBarColor(AccessControlActivity.this.getResources().getColor(R.color.colorBlue));
                                    Toast.makeText(AccessControlActivity.this, "Your door is closed", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ActionPerformStatus> call, Throwable t) {
                                    hideLoader();
                                    myAnim.setDuration((long) animationDuration);
                                    // Use bounce interpolator with amplitude 0.2 and frequency 20
                                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                                    myAnim.setInterpolator(interpolator);
                                    control_Btn.startAnimation(myAnim);
                                    constraintLayout.setBackgroundColor(Color.parseColor("#73bf43"));
                                    control_Btn.setBackground(getResources().getDrawable(R.drawable.greencircle));
                                    control_Btn.setText("closed");
                                    switchStatusdynamic = true;
                                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                            .getColor(R.color.greenoutercircle)));
                                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                    window.setStatusBarColor(AccessControlActivity.this.getResources().getColor(R.color.greenoutercircle));
                                    //  Toast.makeText(AccessControlActivity.this, "Unable to turn on", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            showLoader();
                            Call<ActionPerformStatus> actionPerformStatusCall = service.getInstance(issuer_url)
                                    .performAction(device_id, ph_id, new ActionResponse("on")); /*apiInterface.performAction(device_id,"on");*/
                            actionPerformStatusCall.enqueue(new Callback<ActionPerformStatus>() {
                                @Override
                                public void onResponse(Call<ActionPerformStatus> call, Response<ActionPerformStatus> response) {
                                    hideLoader();
                                    final Animation myAnim = AnimationUtils.loadAnimation(AccessControlActivity.this, R.anim.bounce);

                                    myAnim.setDuration((long) animationDuration);
                                    // Use bounce interpolator with amplitude 0.2 and frequency 20
                                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                                    myAnim.setInterpolator(interpolator);
                                    control_Btn.startAnimation(myAnim);
                                    constraintLayout.setBackgroundColor(Color.parseColor("#73bf43"));
                                    control_Btn.setBackground(getResources().getDrawable(R.drawable.greencircle));
                                    control_Btn.setText("close");
                                    switchStatusdynamic = true;
                                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                            .getColor(R.color.greenoutercircle)));
                                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                    window.setStatusBarColor(AccessControlActivity.this.getResources().getColor(R.color.greenoutercircle));
                                    Toast.makeText(AccessControlActivity.this, "Your door is open", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ActionPerformStatus> call, Throwable t) {
                                    hideLoader();
                                    final Animation myAnim = AnimationUtils.loadAnimation(AccessControlActivity.this, R.anim.bounce);

                                    myAnim.setDuration((long) animationDuration);
                                    // Use bounce interpolator with amplitude 0.2 and frequency 20
                                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                                    myAnim.setInterpolator(interpolator);
                                    control_Btn.startAnimation(myAnim);
                                    constraintLayout.setBackgroundColor(Color.parseColor("#3574f6"));
                                    control_Btn.setBackground(getResources().getDrawable(R.drawable.bluecircle));
                                    control_Btn.setText("open");
                                    switchStatusdynamic = true;
                                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                            .getColor(R.color.colorBlue)));
                                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                    window.setStatusBarColor(AccessControlActivity.this.getResources().getColor(R.color.colorBlue));
                                    //  Toast.makeText(AccessControlActivity.this, "Unable to turn on", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else {
                        Toast.makeText(view.getContext(), "Please check internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }

            });

            /* Call<DeviceStatus> deviceStatus = service.getInstance(issuer_url).getDeviceStatus(*//*"f4f63ee9-2d6c-4750-af29-d656f5d01f92"*//*device_id*//*"47771079-4e77-4744-b892-f46cc62410c8"*//*);

        deviceStatus.enqueue(new Callback<DeviceStatus>() {
            @Override
            public void onResponse(Call<DeviceStatus> call, Response<DeviceStatus> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(AccessControlActivity.this, "success", Toast.LENGTH_SHORT).show();
                    deviceResponseStatus = response.body();
                    if (deviceResponseStatus != null && deviceResponseStatus.getStatus() == 200) {
                        if (deviceResponseStatus.getData().getCurrentStatus().equalsIgnoreCase("on")) {
                            switchStatus.setTextColor(Color.RED);
                            switchStatus.setText("On");
                            toggleSwitch.setToggleOn();
                        } else if (deviceResponseStatus.getData().getCurrentStatus().equalsIgnoreCase("off")){
                            switchStatus.setTextColor(Color.GREEN);
                            switchStatus.setText("Off");
                            toggleSwitch.setToggleOff();
                        } else {
                            switchStatus.setTextColor(Color.GREEN);
                            switchStatus.setText("Toggle");
                           // toggleSwitch.setToggleOn(false);
                            toggleSwitch.setToggleOff();
                        }
                        hideLoader();
                    } else {
                        hideLoader();
                        Toast.makeText(AccessControlActivity.this, "Unable to get status!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    hideLoader();
                    Toast.makeText(AccessControlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeviceStatus> call, Throwable t) {
                hideLoader();
                Toast.makeText(AccessControlActivity.this, "error throwable", Toast.LENGTH_SHORT).show();
            }
        });
*/
      /*  AuthService service = new AuthService();
        Call<DeviceStatus> deviceStatus = service.getStatus().getDeviceStatus(device_id);

        deviceStatus.enqueue(new Callback<DeviceStatus>() {
            @Override
            public void onResponse(Call<DeviceStatus> call, Response<DeviceStatus> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AccessControlActivity.this, "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccessControlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeviceStatus> call, Throwable t) {
                Toast.makeText(AccessControlActivity.this, "error throwable", Toast.LENGTH_SHORT).show();
            }
        });*/

        /*Call<DeviceStatus> statuscall = apiInterface.getDeviceStatus(device_id);
        statuscall.enqueue(new Callback<DeviceStatus>() {
            @Override
            public void onResponse(Call<DeviceStatus> call, Response<DeviceStatus> response) {
                deviceStatus = response.body();
                if (deviceStatus != null && deviceStatus.getStatus() == 200) {
                    if (deviceStatus.getData().getCurrentStatus().equalsIgnoreCase("on")) {
                        switchStatus.setTextColor(Color.RED);
                        switchStatus.setText("On");
                        toggleSwitch.setToggleOn(true);
                    } else {
                        switchStatus.setTextColor(Color.GREEN);
                        switchStatus.setText("Off");
                        toggleSwitch.setToggleOn(false);
                    }
                    hideLoader();
                } else {
                    hideLoader();
                    Toast.makeText(AccessControlActivity.this, "Unable to get status!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeviceStatus> call, Throwable t) {
                statuscall.cancel();
                hideLoader();
            }
        });*/
    }

    private void handleStatus() {
        Call<DeviceStatus> deviceStatus = service.getInstance(issuer_url).getDeviceStatus(device_id,ph_id);

        deviceStatus.enqueue(new Callback<DeviceStatus>() {
            @Override
            public void onResponse(Call<DeviceStatus> call, Response<DeviceStatus> response) {
                if (response.isSuccessful()) {
                    deviceResponseStatus = response.body();
                    //Toast.makeText(AccessControlActivity.this, "success", Toast.LENGTH_SHORT).show();
                    if (deviceResponseStatus != null && deviceResponseStatus.getStatus() == 200) {
                        if (deviceResponseStatus.getData().getCurrentStatus().equalsIgnoreCase("ON")) {
                            deviceResponseStatus = response.body();
                            constraintLayout.setBackgroundColor(Color.parseColor("#73bf43"));
                            control_Btn.setBackground(getResources().getDrawable(R.drawable.greencircle));
                            control_Btn.setText("closed");
                            switchStatusdynamic = true; // door is open
                           /* getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                    .getColor(R.color.greenoutercircle)));*/
                            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                    .getColor(R.color.greenoutercircle)));
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            window.setStatusBarColor(AccessControlActivity.this.getResources().getColor(R.color.greenoutercircle));

                           /* switchStatus.setTextColor(Color.RED);
                            switchStatus.setText("On");
                            toggleSwitch.setToggleOn();*/
                        } else if (deviceResponseStatus.getData().getCurrentStatus().equalsIgnoreCase("OFF")){
                            constraintLayout.setBackgroundColor(Color.parseColor("#3574f6"));
                            control_Btn.setBackground(getResources().getDrawable(R.drawable.bluecircle));
                            control_Btn.setText("open");
                            switchStatusdynamic = false; // door is closed
                            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                    .getColor(R.color.colorBlue)));
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            window.setStatusBarColor(AccessControlActivity.this.getResources().getColor(R.color.colorBlue));

                           /* getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                    .getColor(R.color.colorBlue)));*/
                           /* switchStatus.setTextColor(Color.GREEN);
                            switchStatus.setText("Off");
                            toggleSwitch.setToggleOff();*/
                        } else {
                          /*  switchStatus.setTextColor(Color.GREEN);
                            switchStatus.setText("Toggle");
                            // toggleSwitch.setToggleOn(false);
                            toggleSwitch.setToggleOff();*/
                        }
                        hideLoader();
                    } else {
                        hideLoader();
                        Toast.makeText(AccessControlActivity.this, "Unable to get status!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    hideLoader();
                    Toast.makeText(AccessControlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeviceStatus> call, Throwable t) {
                hideLoader();
                Toast.makeText(AccessControlActivity.this, "error throwable", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToHomeActivity();

    }
    private void goToHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // show loader
    public void showLoader() {
        try {
            if (mAlertDialog != null && mAlertDialog.isShowing()) return;

            dialogBuilder = new AlertDialog.Builder(AccessControlActivity.this);
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
            Timber.d("AccessControl Activity dialog exception " + ex.getMessage());
        }
    }
    // hide loader
    public void hideLoader() {
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
    }
}
