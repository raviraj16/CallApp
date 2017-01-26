package com.call.app.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.call.app.R;
import com.call.app.ui.activity.BaseActivity;
import com.call.app.utils.Prefs;
import com.greysonparrelli.permiso.Permiso;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private EditText home_phone_number_et;
    private TextView home_call_txt, home_sim_one_txt, home_sim_two_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Permiso.getInstance().setActivity(this);
    }

    @Override
    protected void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initView() {
        super.initView();
        home_phone_number_et = (EditText) findViewById(R.id.home_phone_number_et);
        home_call_txt = (TextView) findViewById(R.id.home_call_txt);
        home_sim_one_txt = (TextView) findViewById(R.id.home_sim_one_txt);
        home_sim_two_txt = (TextView) findViewById(R.id.home_sim_two_txt);

    }

    @Override
    protected void initValue() {
        super.initValue();
        setSimOneSelected(Prefs.getInstance().getSimOneSelection());
        home_phone_number_et.setText(Prefs.getInstance().getPhoneNumber().trim());
        home_phone_number_et.setSelection(home_phone_number_et.length());
        home_call_txt.requestFocus();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (home_phone_number_et.getText().toString().trim().length() != 10) {
            Prefs.getInstance().setPhoneNumber("");
        }
    }

    private void setSimOneSelected(boolean isSelected) {
        if (isSelected) {
            home_sim_two_txt.setSelected(false);
            home_sim_one_txt.setSelected(true);
            Prefs.getInstance().setSimOneSelection(true);
        } else {
            home_sim_one_txt.setSelected(false);
            home_sim_two_txt.setSelected(true);
            Prefs.getInstance().setSimOneSelection(false);

        }
    }

    @Override
    protected void initClickListener() {
        super.initClickListener();
        home_call_txt.setOnClickListener(HomeActivity.this);
        home_sim_two_txt.setOnClickListener(HomeActivity.this);
        home_sim_one_txt.setOnClickListener(HomeActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //
        // Third, forward the results of this method to Permiso
        //
        Permiso.getInstance().onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_call_txt:
                makeCall();
                break;
            case R.id.home_sim_one_txt:
                setSimOneSelected(true);
                break;

            case R.id.home_sim_two_txt:
                setSimOneSelected(false);
                break;
        }
    }

    private void makeCall() {
        if (home_phone_number_et.getText().toString().trim().length() != 10 || !android.util.Patterns.PHONE.matcher(home_phone_number_et.getText().toString().trim()).matches()) {
            Toast.makeText(HomeActivity.this, R.string.validation_invalid_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }

        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (resultSet.isPermissionGranted(Manifest.permission.CALL_PHONE)) {
                    Prefs.getInstance().setPhoneNumber(home_phone_number_et.getText().toString().trim());
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + home_phone_number_et.getText().toString().trim()));
                    intent.putExtra("com.android.phone.extra.slot", home_sim_one_txt.isSelected() ? 0 : 1); // 0 For sim 1
                    intent.putExtra("simSlot", home_sim_one_txt.isSelected() ? 0 : 1); //0 For sim 1
                    startActivity(intent);
                } else if (resultSet.isPermissionPermanentlyDenied(Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(HomeActivity.this, R.string.permission_permanently_denied, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                Permiso.getInstance().showRationaleInDialog(getString(R.string.permission_rationale), getString(R.string.needed_permission) + " CALL_PHONE", null, callback);
            }
        }, Manifest.permission.CALL_PHONE);

    }
}
