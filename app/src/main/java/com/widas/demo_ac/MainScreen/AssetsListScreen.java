package com.widas.demo_ac.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.widas.demo_ac.AccessControl.View.AccessControlActivity;
import com.widas.demo_ac.AccessControl.service.AuthService;
import com.widas.demo_ac.MainActivity;
import com.widas.demo_ac.MainScreen.model.AssetsListData;
import com.widas.demo_ac.MainScreen.model.AssetsListModel;
import com.widas.demo_ac.R;
import com.widas.demo_ac.common.Util;
import com.widas.demo_ac.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class AssetsListScreen extends AppCompatActivity implements AssetsListAdapter.RecyclerViewClickListener {
    AuthService service;
    @BindView(R.id.rv_assets)
    RecyclerView rv_assetsList;
    @BindView(R.id.noAssetsFound)
    TextView noAssetsFound;
    ArrayList<AssetsListData> assetsListData;
    AssetsListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Context context;
    AlertDialog mAlertDialog;
    AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_list_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setTitle("Choose asset");
        ButterKnife.bind(this);
        context = this;
        service = new AuthService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoader();
          if (Util.isInternetAvailable(this)) {
        Call<AssetsListModel> getAssetsList = service.getInstance("https://nightlybuild.cidaas.de").assetsList();
        getAssetsList.enqueue(new Callback<AssetsListModel>() {
            @Override
            public void onResponse(Call<AssetsListModel> call, Response<AssetsListModel> response) {
                if (response.isSuccessful()) {
                    Timber.d("" + response.body().getData());
                    assetsListData = new ArrayList<>();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        assetsListData.add(response.body().getData().get(i));
                    }
                    Timber.d("" + assetsListData);
                    rv_assetsList.setHasFixedSize(true);
                    noAssetsFound.setVisibility(View.GONE);
                    // use a linear layout manager
                    layoutManager = new LinearLayoutManager(AssetsListScreen.this);
                    rv_assetsList.setLayoutManager(layoutManager);

                    // specify an adapter (see also next example)
                    adapter = new AssetsListAdapter(assetsListData, context);
                    rv_assetsList.setAdapter(adapter);
                    hideLoader();
                } else {
                    noAssetsFound.setVisibility(View.VISIBLE);
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<AssetsListModel> call, Throwable t) {
                Timber.d("" + call);
                hideLoader();
            }
        });
    }  else {
              hideLoader();
              Toast.makeText(context, "Please check internet connection!", Toast.LENGTH_SHORT).show();
          }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToHomeActivity();

    }
    private void goToHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View View, int position) {
        if (Util.isInternetAvailable(this)) {
            Toast.makeText(context, "" + assetsListData.get(position).getResource_name(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AccessControlActivity.class);
            intent.putExtra("device_name", assetsListData.get(position).getResource_name());
            intent.putExtra("device_id", assetsListData.get(position).getControllers().get(0).getDevice_id());
            intent.putExtra("ph_id", assetsListData.get(position).get_id());
            intent.putExtra("issuer_url", "https://nightlybuild.cidaas.de");
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(context, "Please check internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    // show loader
    public void showLoader() {
        try {
            if (mAlertDialog != null && mAlertDialog.isShowing()) return;

            dialogBuilder = new AlertDialog.Builder(AssetsListScreen.this);
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
