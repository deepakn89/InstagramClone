package com.dnagaraj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {

    EditText etSignupUsername,etSignupPassword,etLoginUsername,etLoginPassword;
    Button btnSignup,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        etSignupUsername=findViewById(R.id.etSignupUsername);
        etSignupPassword=findViewById(R.id.etSignupPassword);
        etLoginUsername=findViewById(R.id.etLoginUsername);
        etLoginPassword=findViewById(R.id.etLoginPassword);

        btnSignup=findViewById(R.id.btnSignup);
        btnLogin=findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseUser parseUser=new ParseUser();
                parseUser.setUsername(etSignupUsername.getText().toString());
                parseUser.setPassword(etSignupPassword.getText().toString());

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(SignUpLoginActivity.this, parseUser.get("username")+" signup successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        }else{
                            FancyToast.makeText(SignUpLoginActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(etLoginUsername.getText().toString(), etLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null){
                            FancyToast.makeText(SignUpLoginActivity.this, user.getUsername()+" login successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        }else{
                            FancyToast.makeText(SignUpLoginActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });

            }
        });
    }
}