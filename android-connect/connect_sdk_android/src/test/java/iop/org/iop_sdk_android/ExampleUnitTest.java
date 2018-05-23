package iop.org.iop_sdk_android;

import org.junit.Test;
import org.libertaria.world.connection.DeviceNetworkConnection;
import org.libertaria.world.core.IoPConnect;
import org.libertaria.world.core.IoPConnectContext;
import org.libertaria.world.crypto.CryptoWrapper;
import org.libertaria.world.global.DeviceLocation;
import org.libertaria.world.locnet.NodeInfo;
import org.libertaria.world.profile_server.SslContextFactory;
import org.libertaria.world.profile_server.engine.MessageQueueManager;
import org.libertaria.world.profiles_manager.LocalProfilesDao;
import org.libertaria.world.profiles_manager.PairingRequestsManager;
import org.libertaria.world.profiles_manager.ProfilesManager;

import java.util.List;

import iop.org.iop_sdk_android.core.modules.profile.ProfilesModuleImp;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void shouldBeGetListProfileServers() throws Exception {
        IoPConnectContext contextWrapper = null;
        CryptoWrapper cryptoWrapper = null;
        SslContextFactory sslContextFactory = null;
        LocalProfilesDao localProfilesDao = null;
        ProfilesManager profilesManager = null;
        PairingRequestsManager pairingRequestsManager = null;
        DeviceLocation deviceLocation = null;
        DeviceNetworkConnection deviceNetworkConnection = null;
        MessageQueueManager messageQueueManager = null;

        IoPConnect ioPConnect = new IoPConnect(contextWrapper,
                cryptoWrapper,
                sslContextFactory,
                localProfilesDao,
                profilesManager,
                pairingRequestsManager,
                deviceLocation,
                deviceNetworkConnection,
                messageQueueManager);
        ProfilesModuleImp profilesModuleImp = new ProfilesModuleImp(null, ioPConnect, null, null);
        List<NodeInfo> resultNodes =  profilesModuleImp.getProfileServers();

        System.out.println("Found " + resultNodes.size() + " matching nodes");

        for (NodeInfo node : resultNodes)
            System.out.println("  " + node);

        assert resultNodes.size() > 0;
    }
}