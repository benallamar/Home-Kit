package Security;

import java.security.SecureRandom;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;

/**
 * Created by bubble on 04/10/2016.
 */
public class PaireClesRSA {
    private KeyPair key;

    PaireClesRSA(int length) {
        // We have to use the bouncy castle to generate the keys
        SecureRandom rand = new SecureRandom();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512, rand);
        key = kpg.generateKeyPair();
    }

    public PublicKey pubKey() {
        return key.getPublic();
    }

    public PrivateKey privKey() {
        return key.getPrivate();
    }
}
