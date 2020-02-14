package com.dnagaraj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SignUp";

    EditText etName,etPunchSpeed,etPunchPower,etKickSpeed,etKickPower;
    Button save, btnSwitchActivity;
    TextView tvGetData, tvGetAlldata;

    String data;

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
        tvGetData=findViewById(R.id.tvGetData);
        tvGetAlldata=findViewById(R.id.tvGetAllData);
        btnSwitchActivity=findViewById(R.id.btnSwitchActivity);

        save.setOnClickListener(this);

        btnSwitchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        //To get data for individual object id
        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To get data from parse server
                ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("gUDVJ7cKI0", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null){
                            tvGetData.setText(object.getString("name")+"\n"+"Punch_Speed: "+object.getInt("punch_speed")+"\n"
                                    +"Punch_power: "+object.getInt("punch_power")+"\n"+
                                    "Kick_Speed: "+object.getInt("kick_speed")+"\n"+"Kick_power: "+
                                    object.getInt("kick_power"));
                        }
                    }
                });
            }
        });

        //To get all data at once
        data="";
        tvGetAlldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> queryAll=ParseQuery.getQuery("KickBoxer");
                //Getting data on some condition
                queryAll.whereGreaterThan("punch_speed",200);
                queryAll.setLimit(2);  //No of entries, eg: here 2 entries displays

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null){
                            if(objects.size()>0){
                                for(ParseObject parseObject:objects){
                                    data=data+parseObject.get("name")+" "+ parseObject.get("punch_speed")+" "+parseObject.get("punch_power")+" "+ parseObject.get("kick_speed")+" "+parseObject.get("kick_power")+"\n";
                                }
                                tvGetAlldata.setText(data);
                                FancyToast.makeText(SignUp.this, data, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            }
                        }else{
                            FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                        }
                    }
                });
            }
        });

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