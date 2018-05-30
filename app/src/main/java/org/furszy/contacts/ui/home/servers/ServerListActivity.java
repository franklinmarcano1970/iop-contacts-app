package org.furszy.contacts.ui.home.servers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.furszy.contacts.BaseActivity;
import org.furszy.contacts.R;
import org.furszy.contacts.ui.chat.MessagesFragment;
import org.libertaria.world.profile_server.ProfileInformation;
import org.libertaria.world.profile_server.engine.listeners.ProfSerMsgListener;
import org.libertaria.world.services.chat.ChatCallClosedException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.furszy.contacts.App.INTENT_CHAT_REFUSED_BROADCAST;
import static org.furszy.contacts.ui.chat.WaitingChatActivity.REMOTE_PROFILE_PUB_KEY;
import static world.libertaria.shared.library.services.chat.ChatIntentsConstants.ACTION_ON_CHAT_DISCONNECTED;
import static world.libertaria.shared.library.services.chat.ChatIntentsConstants.EXTRA_INTENT_DETAIL;

/**
 * Created by furszy on 7/3/17.
 */

public class ServerListActivity extends BaseActivity implements View.OnClickListener {

    private View root;

    private String remotePk;
    private ProfileInformation remoteProfile;
    private MessagesFragment messagesFragment;
    private ExecutorService executor;

    private BroadcastReceiver chatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(INTENT_CHAT_REFUSED_BROADCAST)){
                Toast.makeText(ServerListActivity.this,"Chat closed",Toast.LENGTH_LONG).show();
                onBackPressed();
            }else if (action.equals(ACTION_ON_CHAT_DISCONNECTED)){
                String remotePubKey = intent.getStringExtra(REMOTE_PROFILE_PUB_KEY);
                String reason = intent.getStringExtra(EXTRA_INTENT_DETAIL);
                if (remotePk.equals(remotePubKey)){
                    Toast.makeText(ServerListActivity.this,"Chat disconnected",Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        }
    };

    @Override
    protected void onCreateView(Bundle savedInstanceState, ViewGroup container) {
        super.onCreateView(savedInstanceState, container);
        root = getLayoutInflater().inflate(R.layout.servers_fragment,container);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#21619C")));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#21619C"));
        }
        remotePk = getIntent().getStringExtra(REMOTE_PROFILE_PUB_KEY);
        remoteProfile = profilesModule.getKnownProfile(selectedProfPubKey,remotePk);
        messagesFragment = (MessagesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_servers);

        // cancel chat notifications if there is any..
        app.cancelChatNotifications();

        localBroadcastManager.registerReceiver(chatReceiver,new IntentFilter(INTENT_CHAT_REFUSED_BROADCAST));
        localBroadcastManager.registerReceiver(chatReceiver,new IntentFilter(ACTION_ON_CHAT_DISCONNECTED));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (executor==null){
            executor = Executors.newSingleThreadExecutor();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // close chat
                    chatModule.refuseChatRequest(selectedProfPubKey,remoteProfile.getHexPublicKey());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        if (executor!=null){
            if (!executor.isShutdown())
                executor.shutdown();
            executor = null;
        }

        localBroadcastManager.unregisterReceiver(chatReceiver);

        finish();
    }

    @Override
    public void onBackPressed() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // close chat
                    chatModule.refuseChatRequest(selectedProfPubKey,remoteProfile.getHexPublicKey());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        super.onBackPressed();
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();

    }

}
