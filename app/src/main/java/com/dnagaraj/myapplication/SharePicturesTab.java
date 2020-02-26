package com.dnagaraj.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.security.Permission;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class SharePicturesTab extends Fragment implements View.OnClickListener{
    private static final String TAG = "SharePicturesTab";

    private EditText etDescription;
    private Button btnShareImage;
    private ImageView ivImage;

    Bitmap receivedBitmap;

    public SharePicturesTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_pictures_tab, container, false);

        ivImage=view.findViewById(R.id.ivImage);
        etDescription=view.findViewById(R.id.etDescription);
        btnShareImage=view.findViewById(R.id.btnShareImage);

        ivImage.setOnClickListener(SharePicturesTab.this);
        btnShareImage.setOnClickListener(SharePicturesTab.this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ivImage:
                if(Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                }else{
                    getChosenImage();
                }

                break;

            case R.id.btnShareImage:
                if(receivedBitmap!=null){
                    if(etDescription.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),"You must enter description",Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                    }else{
                        //To upload image to server , we need to convert image from bitmap to array of bytes.
                        //Bcz image will be very large and its a huge task. If we convert to array of bytes, upload will be faster

                        //Convert bitmap image to byte array
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        receivedBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                        byte[] bytes=byteArrayOutputStream.toByteArray();

                        //upload to parse server
                        ParseFile parseFile=new ParseFile("img.png",bytes);
                        ParseObject parseObject=new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("description",etDescription.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                        //Show dialog
                        final ProgressDialog progressDialog=new ProgressDialog(getContext());
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    FancyToast.makeText(getContext(),"Picture details saved successfully",Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                }else{
                                    FancyToast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                }else{
                    FancyToast.makeText(getContext(),"You must select image from storage",Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                }
                break;
        }
    }

    private void getChosenImage() {
        //FancyToast.makeText(getContext(),"Now we can access the image", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000){
            if(resultCode == Activity.RESULT_OK){
                //This is for getting image from external storage in fragments
                try {
                    //Get the Uri from MediaStore content provider pointing to the media
                    assert data != null;
                    Uri selectedImage = data.getData();

                    //Now retrieving a cursor object containing the data column for the image media is required to be performed.
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    assert selectedImage != null;
                    Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(selectedImage, filePath, null, null, null);

                    //Want to access first object
                    assert cursor != null;
                    cursor.moveToFirst();

                    //To get index of data present in array and picture path
                    int columnIndex = cursor.getColumnIndex(filePath[0]);
                    String picturePath = cursor.getString(columnIndex);

                    Log.d(TAG, "onActivityResult: picturePath= "+picturePath);

                    //Once we get picture path , we can close cursor
                    cursor.close();

                    //Decode image file
                    receivedBitmap = BitmapFactory.decodeFile(picturePath);
                    ivImage.setImageBitmap(receivedBitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}