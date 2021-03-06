package iop.org.iop_sdk_android.core.wrappers;

import org.libertaria.world.global.IntentMessage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mati on 26/12/16.
 */

public class IntentWrapperAndroid implements IntentMessage {

    private String action;
    private String packageName;
    private Map<String, Serializable> bundle;

    public IntentWrapperAndroid(String action) {
        this.action = action;
        bundle = new HashMap<>();
    }

    @Override
    public void setPackage(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public void put(String key, Object o) {
        if (!(o instanceof Serializable)) throw new IllegalArgumentException("Object is not serializable, "+o.getClass().getName());
        bundle.put(key, (Serializable) o);
    }

    @Override
    public Map<String, Serializable> getBundle() {
        return bundle;
    }
}
