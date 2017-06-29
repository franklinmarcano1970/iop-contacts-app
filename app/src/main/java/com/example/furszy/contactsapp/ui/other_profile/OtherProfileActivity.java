package com.example.furszy.contactsapp.ui.other_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.furszy.contactsapp.BaseActivity;
import com.example.furszy.contactsapp.DialogBuilder;
import com.example.furszy.contactsapp.R;

/**
 * Created by Neoperol on 6/28/17.
 */

public class OtherProfileActivity extends BaseActivity {

    Button btnConnect;
    @Override
    protected void onCreateView(Bundle savedInstanceState, ViewGroup container) {
        getLayoutInflater().inflate(R.layout.other_profile_activity, container);
        setTitle("Name of the user");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Open File Folder

        btnConnect = (Button) findViewById(R.id.btnConnectStatus);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LayoutInflater content = LayoutInflater.from(OtherProfileActivity.this);
                View dialogView = content.inflate(R.layout.dialog_other_profile, null);
                DialogBuilder nodeDialog = new DialogBuilder(OtherProfileActivity.this);
                nodeDialog.setView(dialogView);
                nodeDialog.setPositiveButton("Disconnect", null);
                nodeDialog.setNegativeButton("Cancel", null);
                nodeDialog.show();
            }

        });

    }
}
