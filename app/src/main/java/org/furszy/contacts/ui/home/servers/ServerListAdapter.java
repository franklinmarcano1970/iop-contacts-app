package org.furszy.contacts.ui.home.servers;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;

import org.furszy.contacts.R;
import org.furszy.contacts.ui.home.contacts.ProfileHolder;
import org.libertaria.world.locnet.NodeInfo;
import org.libertaria.world.profile_server.ModuleRedtooth;
import org.libertaria.world.profile_server.ProfileInformation;

import tech.furszy.ui.lib.base.adapter.BaseAdapter;
import tech.furszy.ui.lib.base.adapter.RecyclerListItemListeners;

/**
 * Created by mati on 03/03/17.
 */
public class ServerListAdapter extends BaseAdapter<NodeInfo, ServerListHolder> {

   // ModuleRedtooth module;

    public ServerListAdapter(final Activity context, /*final ModuleRedtooth module, */RecyclerListItemListeners<NodeInfo> recyclerListItemListeners) {
        super(context);
       // this.module = module;
        setListEventListener(recyclerListItemListeners);

    }

    @Override
    protected ServerListHolder createHolder(View itemView, int type) {
        return new ServerListHolder(itemView, type);
    }

    @Override
    protected int getCardViewResource(int type) {
        return R.layout.my_contacts_row;
    }

    @Override
    protected void bindHolder(final ServerListHolder holder, final NodeInfo data, int position) {
        holder.txt_name.setText(data.getContact().getAddress().getHostAddress());
        /*if (data.getImg()!=null && data.getImg().length>1)
            holder.img.setImageBitmap(BitmapFactory.decodeByteArray(data.getImg(),0,data.getImg().length));*/
    }
}
