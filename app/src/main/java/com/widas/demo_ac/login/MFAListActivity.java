package com.widas.demo_ac.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseDataEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList.MFAListResponseData;
import com.widas.demo_ac.R;
import com.widas.demo_ac.adapter.MFAAdapter;

import java.util.ArrayList;
import java.util.List;

public class MFAListActivity extends AppCompatActivity implements MFAAdapter.RecyclerViewClickListener {

    @BindView(R.id.mfalistactivityrecyclerView)
    RecyclerView mfaRecyclerView;
    private List<MFAListResponseDataEntity> list = new ArrayList<>();
    private MFAAdapter mfaAdapter;
    List<MFAListResponseDataEntity> listResponseDataEntities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfalist);
        ButterKnife.bind(this);
//
//         Cidaas.getInstance(this).getMFAList("", new Result<MFAListResponseEntity>() {
//                    @Override
//                    public void success(MFAListResponseEntity result) {
//
//                    }
//
//                    @Override
//                    public void failure(WebAuthError error) {
//
//                    }
//                });
        PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
        passwordlessEntity.setUsageType(UsageType.PASSWORDLESS);
        passwordlessEntity.setEmail("kundan.kishore@widas.in");
       // passwordlessEntity.setTrackId(trackid);
       // passwordlessEntity.setRequestId(result.getData().getRequestId());
       // passwordlessEntity.setSub(sub);
        Cidaas.getInstance(this).loginWithEmail(passwordlessEntity, new Result<InitiateEmailMFAResponseEntity>() {
            @Override
            public void success(InitiateEmailMFAResponseEntity result) {
                Timber.d(""+result.getData().getStatusId());
                //8bc370b0-971a-41ba-b7b1-437d4cf6b376
                Cidaas.getInstance(MFAListActivity.this).getMFAList(/*result.getData().getStatusId()*/"8bc370b0-971a-41ba-b7b1-437d4cf6b376", new Result<MFAListResponseEntity>() {
                    @Override
                    public void success(MFAListResponseEntity result) {
                        Timber.d(""+result.getData());
                        if(result.getData().length > 0){
                            MFAListResponseDataEntity[] data=result.getData();
                            if (data != null) {
                                for (MFAListResponseDataEntity dataEntity : data) {
                                    Timber.d("" + dataEntity.getVerificationType());
                                    listResponseDataEntities.add(dataEntity);   
                                }
                                if (listResponseDataEntities.size() != 0){
                                    mfaAdapter = new MFAAdapter(listResponseDataEntities, MFAListActivity.this);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    mfaRecyclerView.setLayoutManager(mLayoutManager);
                                    mfaRecyclerView.setAdapter(mfaAdapter);
                                }
                            }



                        } else {
                            Toast.makeText(MFAListActivity.this, "No other configuration done yet.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Timber.d(""+error);

                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                Timber.d(""+error);

            }
        });

       /* Cidaas.getInstance(this).loginWithEmail(passwordlessEntity, new Result <LoginCredentialsResponseEntity> () {
            @Override
            public void success(LoginCredentialsResponseEntity result) {
                //Your success code here
            }

            @Override
            public void failure(WebAuthError error) {
                //Your failure code here
            }
        });*/

    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(this, ""+listResponseDataEntities.get(position).getVerificationType(), Toast.LENGTH_SHORT).show();
    }
}
