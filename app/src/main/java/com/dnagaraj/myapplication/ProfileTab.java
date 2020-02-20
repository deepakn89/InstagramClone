package com.dnagaraj.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ProfileTab extends Fragment {

    private EditText etProfileName,etBio,etProfession,etHobbies,etFavSports;
    Button btnUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);

        etProfileName=view.findViewById(R.id.etProfileName);
        etBio=view.findViewById(R.id.etBio);
        etProfession=view.findViewById(R.id.etProfession);
        etHobbies=view.findViewById(R.id.etHobbies);
        etFavSports=view.findViewById(R.id.etFavSport);

        btnUpdateInfo=view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser=ParseUser.getCurrentUser();

        if(parseUser.get("ProfileName")==null){
            etProfileName.setText("");
        }else {
            etProfileName.setText(parseUser.get("ProfileName").toString());
        }

        if(parseUser.get("Bio")==null){
            etBio.setText("");
        }else {
            etBio.setText(parseUser.get("Bio").toString());
        }

        if(parseUser.get("Profession")==null){
            etProfession.setText("");
        }else {
            etProfession.setText(parseUser.get("Profession").toString());
        }

        if(parseUser.get("Hobbies")==null){
            etHobbies.setText("");
        }else {
            etHobbies.setText(parseUser.get("Hobbies").toString());
        }

        if(parseUser.get("FavSports")==null){
            etFavSports.setText("");
        }else {
            etFavSports.setText(parseUser.get("FavSports").toString());
        }

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("ProfileName",etProfileName.getText().toString());
                parseUser.put("Bio",etBio.getText().toString());
                parseUser.put("Profession",etProfession.getText().toString());
                parseUser.put("Hobbies",etHobbies.getText().toString());
                parseUser.put("FavSports",etFavSports.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getActivity(), "Info saved successfully",
                                    FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        }else{
                            FancyToast.makeText(getActivity(), e.getMessage(),
                                    FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        return  view;
    }
}