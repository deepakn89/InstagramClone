package com.dnagaraj.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class UsersTab extends Fragment {
    private ListView listView;
    private ArrayList arrayList;
    private ArrayAdapter arrayAdapter;

    public UsersTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_users_tab, container, false);

        listView=view.findViewById(R.id.usersListView);
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);

        final TextView txtLoadingUsers=view.findViewById(R.id.txtLoadingUsers);

        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();

        //Users display in listview should not be equal to current user
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e==null){
//                    FancyToast.makeText(getActivity(), "Info saved successfully",
//                            FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    if(users.size()>0){
                        for(ParseUser user:users){
                            arrayList.add(user.getUsername());
                        }

                        listView.setAdapter(arrayAdapter);
                        txtLoadingUsers.animate().alpha(0).setDuration(2000);
                        listView.setVisibility(View.VISIBLE);
                    }

                }else{
                    FancyToast.makeText(getActivity(), e.getMessage(),
                            FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();

                }
            }
        });

        return view;
    }
}