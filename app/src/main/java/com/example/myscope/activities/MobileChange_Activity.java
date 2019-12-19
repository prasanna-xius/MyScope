package com.example.myscope.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myscope.R;

public class MobileChange_Activity  extends BaseActivity implements View.OnClickListener {

    Button btn_send_link;
    EditText edtRegisterEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_change_main);
        btn_send_link=(Button)findViewById(R.id.btn_send_link);
        edtRegisterEmail=(EditText)findViewById(R.id.edt_register_email);


        btn_send_link.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_send_link:
                if (validate() == false) {

                    onSignupFailed();
                    return;
                }
                Intent btn_otp=new Intent(getApplicationContext(), Login_Page.class);
                startActivity(btn_otp);
                Toast.makeText(getApplicationContext(), "Please check your Email and change Mobile Number", Toast.LENGTH_SHORT).show();

                break;

        }

    }

    private void onSignupFailed() {

        Toast.makeText(getBaseContext(), "Please Enter Valid EmailId", Toast.LENGTH_LONG).show();

        btn_send_link.setEnabled(true);

    }
    private boolean validate() {
        boolean valid = true;

        String emailId = edtRegisterEmail.getText().toString();

        if (emailId.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            edtRegisterEmail.setError("enter a valid email address");
            valid = false;
        } else {
            edtRegisterEmail.setError(null);
        }


        return valid;
    }

}
