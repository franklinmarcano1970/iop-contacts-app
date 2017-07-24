package org.fermat.redtooth.services;


import org.fermat.redtooth.global.Module;
import org.fermat.redtooth.services.chat.ChatModule;
import org.fermat.redtooth.services.interfaces.PairingModule;
import org.fermat.redtooth.services.interfaces.ProfilesModule;

/**
 * Created by furszy on 6/4/17.
 *
 * Improvements:
 * Add versioning to modules.
 */

public enum EnabledServices {

    PROFILE_DATA("prof_data", ProfilesModule.class),
    PROFILE_PAIRING("prof_pair", PairingModule.class),
    CHAT("chat", ChatModule.class)
    ;

    private String name;
    private Class<Module> moduleClass;

    EnabledServices(String name,Class moduleClass) {
        this.name = name;
        this.moduleClass = moduleClass;
    }

    public String getName() {
        return name;
    }

    public Class<Module> getModuleClass() {
        return moduleClass;
    }

    public static EnabledServices getServiceByName(String name){
        switch (name){
            case "prof_data":
                return PROFILE_DATA;
            case "prof_pair":
                return PROFILE_PAIRING;
            case "chat":
                return CHAT;
            default:
                throw new IllegalArgumentException("service with name: "+name+" not found");
        }
    }
}
