package com.dnagaraj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SignUp";

    EditText etName,etPunchSpeed,etPunchPower,etKickSpeed,etKickPower;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save=findViewById(R.id.btnSave);
        etName=findViewById(R.id.etName);
        etPunchSpeed=findViewById(R.id.etPunchSpeed);
        etPunchPower=findViewById(R.id.etPunchPower);
        etKickSpeed=findViewById(R.id.etKickSpeed);
        etKickPower=findViewById(R.id.etKickPower);

        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        /*
            Inorder to save data to parse-dashboard/server, you need to create parseObject
            in the below fashion.
        */

//        ParseObject boxer=new ParseObject("Boxer");
//        boxer.put("punch_speed",300);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                //If there is no error/exception, then show toast
//                if(e==null){
//                    Toast.makeText(SignUp.this,"Parser object saved successfully for boxer",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        try {
            final ParseObject kickBoxer=new ParseObject("KickBoxer");

            kickBoxer.put("name", etName.getText().toString());
            kickBoxer.put("punch_speed", Integer.parseInt(etPunchSpeed.getText().toString()));
            kickBoxer.put("punch_power", Integer.parseInt(etPunchPower.getText().toString()));
            kickBoxer.put("kick_speed", Integer.parseInt(etKickSpeed.getText().toString()));
            kickBoxer.put("kick_power", Integer.parseInt(etKickPower.getText().toString()));

            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Log.d(TAG, "done: e=" + e);
                    if (e == null) {
                        //Toast.makeText(SignUp.this,kickBoxer.get("name")+" Parser object saved successfully",Toast.LENGTH_LONG).show();
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    }
                }
            });
        }catch(Exception e){
            FancyToast.makeText(this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }

    }
}