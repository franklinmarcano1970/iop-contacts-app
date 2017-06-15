package org.fermat.redtooth.profile_server.engine.app_services;

import org.fermat.redtooth.core.services.*;
import org.fermat.redtooth.global.utils.SerializationUtils;

/**
 * Created by furszy on 6/15/17.
 */

public class CryptoMsg extends BaseMsg<CryptoMsg> {

    private String algo;

    public CryptoMsg(String algo) {
        this.algo = algo;
    }

    @Override
    public CryptoMsg decode(byte[] msg) throws Exception {
        return SerializationUtils.deserialize(msg,CryptoMsg.class);
    }

    @Override
    public byte[] encode() throws Exception {
        // lazy encode to test the entire flow first..
        return SerializationUtils.serialize(this);
    }

    public String getAlgo() {
        return algo;
    }

    @Override
    public String getType() {
        return BasicCallMessages.CRYPTO.getType();
    }
}
