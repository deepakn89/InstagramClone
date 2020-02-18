package com.dnagaraj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etLoginEmail,etLoginPassword;
    Button btnLoginAct,btnSignupAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.sign_up);

        etLoginEmail=findViewById(R.id.etLoginEmail);
        etLoginPassword=findViewById(R.id.etLoginPassword);
        btnLoginAct=findViewById(R.id.btnLoginAct);
        btnSignupAct=findViewById(R.id.btnSignupAct);

        btnLoginAct.setOnClickListener(this);
        btnSignupAct.setOnClickListener(this);

        if(ParseUser.getCurrentUser()!=null){
//            ParseUser.getCurrentUser();
//            ParseUser.logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginAct:
                if(etLoginEmail.getText().toString().equals("")||etLoginPassword.getText().toString().equals("")){
                    FancyToast.makeText(LoginActivity.this, "None of the fields should be blank",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                }else {
                    ParseUser.logInInBackground(etLoginEmail.getText().toString(), etLoginPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                FancyToast.makeText(LoginActivity.this, user.getUsername() + " logged in successfully",
                                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(LoginActivity.this, String.valueOf(e.getCode()), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                }
                break;

            case R.id.btnSignupAct:
                break;
        }
    }

    public void rootLoginLayoutTapped(View view) {
        try{
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity(){
        Intent intent=new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);
    }
}