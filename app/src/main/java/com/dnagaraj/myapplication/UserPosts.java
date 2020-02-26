package com.dnagaraj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UserPosts extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        linearLayout=findViewById(R.id.linearLayout);

        final String recievedUsername=getIntent().getStringExtra("username");

        FancyToast.makeText(this,recievedUsername, Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

        setTitle(recievedUsername+"'s"+" posts");

        ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",recievedUsername);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size()>0 && e==null){
                    for(ParseObject post:objects){
                        final TextView postDesc=new TextView(UserPosts.this);
                        postDesc.setText(post.get("description")+"");

                        ParseFile postPicture= (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data.length>0 && e==null){
                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView picture=new ImageView(UserPosts.this);

                                    //Set attributes for imageview
                                    LinearLayout.LayoutParams imageview_params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageview_params.setMargins(5,5,5,5);
                                    picture.setLayoutParams(imageview_params);
                                    picture.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    picture.setImageBitmap(bitmap);

                                    //Set attributes for textview
                                    LinearLayout.LayoutParams desc_param=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    postDesc.setLayoutParams(desc_param);
                                    postDesc.setGravity(Gravity.CENTER);
                                    postDesc.setBackgroundColor(Color.BLUE);
                                    postDesc.setTextColor(Color.WHITE);
                                    postDesc.setTextSize(30f);

                                    linearLayout.addView(picture);
                                    linearLayout.addView(postDesc);
                                }
                                progressDialog.dismiss();
                            }
                        });

                    }
                }else{
                    FancyToast.makeText(UserPosts.this,recievedUsername+" doesn't have any posts..!",Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                    finish();
                }
            }
        });
    }
}