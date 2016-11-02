import Connection.PGPMessage;
import HomKit.Home;
import HomeSecurityLayer.PaireClesRSA;
import Interfaces.IHMHome.IHMHome;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchProviderException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        //Run directly the whole system.
        new Home().start();
    }
}