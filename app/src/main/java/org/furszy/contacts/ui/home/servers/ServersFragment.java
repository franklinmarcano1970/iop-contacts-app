package org.furszy.contacts.ui.home.servers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.furszy.contacts.CreateProfileActivity;
import org.furszy.contacts.ProfileInformationActivity;
import org.furszy.contacts.R;
import org.furszy.contacts.app_base.BaseAppRecyclerFragment;
import org.furszy.contacts.contacts.ProfileAdapter;
import org.libertaria.world.locnet.NodeInfo;
import org.libertaria.world.profile_server.ProfileInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import tech.furszy.ui.lib.base.adapter.BaseAdapter;
import tech.furszy.ui.lib.base.adapter.RecyclerListItemListeners;

import static world.libertaria.shared.library.global.client.IntentBroadcastConstants.INTENT_EXTRA_PROF_KEY;
import static world.libertaria.shared.library.global.client.IntentBroadcastConstants.INTENT_EXTRA_NODE_INFO;

/**
 * Created by jcardozo on 6/4/17.
 */

public class ServersFragment extends BaseAppRecyclerFragment<NodeInfo> {

    private static final Logger log = LoggerFactory.getLogger(ServersFragment.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setEmptyText(getResources().getString(R.string.empty_servers));
        setEmptyTextColor(Color.parseColor("#4d4d4d"));
        setEmptyView(R.drawable.img_contact_empty);
        return view;
    }

    @Override
    protected BaseAdapter initAdapter() {
        return new ServerListAdapter(getActivity(),new RecyclerListItemListeners<NodeInfo>() {
            @Override
            public void onItemClickListener(NodeInfo data, int position) {
               Intent intent1 = new Intent(getActivity(), CreateProfileActivity.class);
                intent1.putExtra(INTENT_EXTRA_PROF_KEY, selectedProfilePubKey);
                Bundle b = new Bundle();
                b.putSerializable(INTENT_EXTRA_NODE_INFO, data);
                intent1.putExtras(b);
                //i.setClass(this, SearchDetailsActivity.class);
                getActivity().startActivity(intent1);
            }

            @Override
            public void onLongItemClickListener(NodeInfo data, int position) {

            }
        });
    }

    @Override
    protected List onLoading() {
        List<NodeInfo> ni = new ArrayList<>();
        ni = null;
        try {
            if (profilesModule!=null){
                return profilesModule.getProfileServersAll();
            }
        }catch (Exception e){
            log.info("onLoading",e);
        }
        return null;
    }


}