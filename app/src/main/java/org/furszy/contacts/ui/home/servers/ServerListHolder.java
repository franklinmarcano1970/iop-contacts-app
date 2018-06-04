package org.furszy.contacts.ui.home.servers;

import android.view.View;
import android.widget.TextView;

import org.furszy.contacts.R;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.furszy.ui.lib.base.adapter.BaseViewHolder;

/**
 * Created by mati on 03/03/17.
 */
public class ServerListHolder extends BaseViewHolder {

    TextView txt_name;
    CircleImageView img;

    public ServerListHolder(View itemView, int holderType) {
        super(itemView,holderType);

        txt_name = (TextView) itemView.findViewById(R.id.txt_name);
        img = (CircleImageView) itemView.findViewById(R.id.img_profile);

    }
}