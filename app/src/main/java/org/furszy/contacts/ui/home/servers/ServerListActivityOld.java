package org.furszy.contacts.ui.home.servers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import org.furszy.contacts.App;
import org.furszy.contacts.BaseDrawerActivity;
import org.furszy.contacts.R;
import org.furszy.contacts.app_base.BaseAppRecyclerFragment;
import org.furszy.contacts.ui.send_request.SendRequestActivity;

import java.util.HashMap;
import java.util.Map;

import static org.furszy.contacts.App.INTENT_ACTION_ON_SERVICE_CONNECTED;
import static world.libertaria.shared.library.util.OpenApplicationsUtil.CONTACTS_POSITION;
import static world.libertaria.shared.library.util.OpenApplicationsUtil.INITIAL_FRAGMENT_EXTRA;

/**
 * Created by josejcb  on 5/30/18.
 */

public class ServerListActivityOld extends BaseDrawerActivity {

    public static final String INIT_REQUESTS = "in_req";
    public static final String FRAGMENT_SERVERS = "servers";


    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab_add;
    private ViewPagerAdapter adapter;

    private boolean initInRequest;

    private BroadcastReceiver initReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(INTENT_ACTION_ON_SERVICE_CONNECTED)) {
                loadBasics();
                refreshFragments();
            }
        }
    };

    private void refreshFragments() {
        if (adapter != null) {
            refreshContacts();
        }
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState, ViewGroup container) {
        if (!App.getInstance().createProfSerConfig().isIdentityCreated()) {
            Intent intent = new Intent(this, ServerListActivityOld.class);
            startActivity(intent);
            finish();
        } else {
            root = getLayoutInflater().inflate(R.layout.servers_main, container);
            setTitle("Available Servers");

            if (getIntent() != null) {
                if (getIntent().hasExtra(INIT_REQUESTS)) {
                    initInRequest = true;
                }
            }

            viewPager = (ViewPager) root.findViewById(R.id.viewpager);
            setupViewPager(viewPager);
            tabLayout = (TabLayout) root.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            fab_add = (FloatingActionButton) root.findViewById(R.id.fab_add);
            fab_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), SendRequestActivity.class));
                }
            });
        }

        localBroadcastManager.registerReceiver(initReceiver, new IntentFilter(INTENT_ACTION_ON_SERVICE_CONNECTED));

        if (viewPager != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int initialFragment = extras.getInt(INITIAL_FRAGMENT_EXTRA);
                viewPager.setCurrentItem(initialFragment);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        setNavigationMenuItemChecked(0);
        if (initInRequest) {
            viewPager.setCurrentItem(1);
            initInRequest = false;
        }
        localBroadcastManager.registerReceiver(initReceiver, new IntentFilter(INTENT_ACTION_ON_SERVICE_CONNECTED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        localBroadcastManager.unregisterReceiver(initReceiver);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CONTACTS_POSITION, new ServersFragment(), FRAGMENT_SERVERS);
        viewPager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final Map<Integer, Pair<String, Fragment>> fragments = new HashMap<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position).second;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Integer position, Fragment fragment, String title) {
            fragments.put(position, new Pair<>(title, fragment));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).first;
        }
    }

    public void refreshContacts() {
        final Fragment fragment = adapter.getItem(CONTACTS_POSITION);
        ((BaseAppRecyclerFragment) fragment).loadBasics();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ServersFragment) fragment).refresh();
            }
        });
    }

}
