package org.libertaria.world.profile_server.engine.listeners;

/**
 * Created by mati on 30/03/17.
 */

public interface ProfSerPartSearchListener<O> extends ProfSerMsgListener {

    void onMessageReceive(int messageId, O message,int recordIndex,int recordCount);

}
