package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    EditText name, address, email, mobile, password, reEnterPassword;

    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        name = (EditText)findViewById(R.id.input_name);
        address = (EditText)findViewById(R.id.input_address);
        email = (EditText)findViewById(R.id.input_emails);
        mobile = (EditText)findViewById(R.id.input_mobile);
        password = (EditText)findViewById(R.id.input_passwords);
        reEnterPassword = (EditText)findViewById(R.id.input_reEnterPassword);



        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String Name = name.getText().toString();
        String Address = address.getText().toString();
        String Email = email.getText().toString();
        String Mobile = mobile.getText().toString();
        String Password = password.getText().toString();
        String type = "register";

        BackGroundWorker backGroundWorker = new BackGroundWorker(this);
        backGroundWorker.execute(type, Name, Address, Email, Mobile, Password);


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name0 = name.getText().toString();
        String address0 = address.getText().toString();
        String email0 = email.getText().toString();
        String mobile0 = mobile.getText().toString();
        String password0 = password.getText().toString();
        String reEnterPassword0 = reEnterPassword.getText().toString();

        if (name0.isEmpty() || name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (address0.isEmpty()) {
            address.setError("Enter Valid Address");
            valid = false;
        } else {
            address.setError(null);
        }


        if (email0.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email0).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (mobile0.isEmpty() || mobile.length()!=10) {
            mobile.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobile.setError(null);
        }

        if (password0.isEmpty() || password.length() < 4 || password.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (reEnterPassword0.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword0.equals(password0))) {
            reEnterPassword.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPassword.setError(null);
        }

        return valid;
    }
}