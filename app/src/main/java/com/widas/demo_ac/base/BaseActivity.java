package com.widas.demo_ac.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.widas.demo_ac.Alert.CustomAlert;
import com.widas.demo_ac.Alert.SweetAlertDialog;
import com.widas.demo_ac.R;

import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;
import timber.log.Timber;

/**
 * Created by ganesh on 12/02/18.
 */

public class BaseActivity<PresenterType extends Presenter> extends NucleusAppCompatActivity<PresenterType> {

    //public Cidaas cidaas;
    // local variables
  //  public SessionManager sessionManager;
   // private NfcAdapter adapter;

    //custom alert dialog for alternative progress dialog
    AlertDialog mAlertDialog;
    AlertDialog.Builder dialogBuilder;
    CustomAlert customAlert;
    protected SweetAlertDialog sweetDialog;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  cidaas = MyApplication.getCidaasInstance();
        // initialise session
    //    adapter = NfcAdapter.getDefaultAdapter(this);
     //   sessionManager = SessionManager.getSessionInstance(getApplicationContext());
        customAlert = new CustomAlert();
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

    @Override
    public void onPause() {
        super.onPause();
        hideLoader();
      /*  if (adapter != null)
            adapter.disableForegroundDispatch(this);*/

    }

    @Override
    public void onResume() {
        super.onResume();
       /* if (adapter != null) {
            try {
                Intent intent = getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent tagIntent = PendingIntent.getActivity(this, 0, intent, 0);
                IntentFilter iso = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
                adapter.enableForegroundDispatch(this, tagIntent, new IntentFilter[]{iso},
                        new String[][]{new String[]{IsoDep.class.getName()}});
                OnChallengeReceived();
            } catch (Exception ex) {
                Logger.addRecordToLog("Exception in baseactivity " + ex.getMessage());
            }


        }*/


    }

    public void OnChallengeReceived() {
       /* if (adapter != null && adapter.isEnabled()) {
            Intent intent = getIntent();
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent tagIntent = PendingIntent.getActivity(this, 0, intent, 0);
            IntentFilter iso = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
            adapter.enableForegroundDispatch(this, tagIntent, new IntentFilter[]{iso},
                    new String[][]{new String[]{IsoDep.class.getName()}});
        }*/
    }

   /* @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag != null) {
            Logger.addRecordToLog("Tag is not null nfc is available !");
        }
    }*/

    @Override
    protected void onDestroy() {
        if (sweetDialog != null)
            sweetDialog.hide();
        hideLoader();
        super.onDestroy();

    }

    // show loader
    public void showLoader() {
        try {
            if (mAlertDialog != null && mAlertDialog.isShowing()) return;

            dialogBuilder = new AlertDialog.Builder(BaseActivity.this);
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

    @Override
    protected void onStop() {
        hideLoader();
        super.onStop();

    }

    // hide loader
    public void hideLoader() {
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
    }

    public SweetAlertDialog showAlertDialogs(Activity activity, String title, String contextTitle,
                                            Drawable drawable, int alertType, String confirmText, boolean showCancel, String cancelText) {
        return customAlert.showAlertDialog(activity, title, contextTitle, drawable, alertType, confirmText, showCancel, cancelText);
    }

    public SweetAlertDialog showBasicDialog(Activity activity, String title) {
        return customAlert.basicDialog(activity, title);
    }
}
