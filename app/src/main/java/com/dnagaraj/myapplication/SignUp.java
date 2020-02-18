package com.dnagaraj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SignUp";

    EditText etEmail,etUsername,etPassword;
    Button btnSignup,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Set tool bar name
        setTitle(R.string.sign_up);

        //Ui components
        etEmail=findViewById(R.id.etEmail);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        etPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Check condition for Enter key press and taken action
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnSignup);
                }
                return false;
            }
        });

        btnSignup=findViewById(R.id.btnSignUp);
        btnLogin=findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if(ParseUser.getCurrentUser()!=null){   //This means there exist a user and token session got created once we signed up
//            ParseUser.getCurrentUser();
//            ParseUser.logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                if(etEmail.getText().toString().equals("")||etUsername.getText().toString().equals("")||etPassword.getText().toString().equals("")){
                    FancyToast.makeText(SignUp.this, "None of the fields can be blank",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                }else {
                    final ParseUser parseUser = new ParseUser();
                    parseUser.setEmail(etEmail.getText().toString());
                    parseUser.setUsername(etUsername.getText().toString());
                    parseUser.setPassword(etPassword.getText().toString());
                    Log.d(TAG, "onClick: before signup: parseUser=" + parseUser);

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + etUsername.getText().toString());
                    progressDialog.show();

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.d(TAG, "done: inside");
                            if (e == null) {
                                Log.d(TAG, "done: e is null");
                                FancyToast.makeText(SignUp.this, parseUser.getUsername() + " signup successfully",
                                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            } else {
                                Log.d(TAG, "done: e is not null");
                                FancyToast.makeText(SignUp.this, String.valueOf(e.getCode()) + "=" + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;

            case R.id.btnLogin:
                Intent intent=new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    //If you tap on root layout, keyboard should get hide
    public void rootLayoutTapped(View view) {
        try{
            //System service
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            //To hide keyboard
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity(){
        Intent intent=new Intent(SignUp.this,SocialMediaActivity.class);
        startActivity(intent);
    }
}