package Security;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

/**
 * Created by bubble on 04/10/2016.
 */
public class PaireClesRSA {
    private KeyPair key;

    public PaireClesRSA(int length) {
        try {
            // Genertate the RSA Key
            SecureRandom rand = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(length, rand);
            key = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.out.print("No recognized algorithm");
        }
        // We have to use the bouncy castle to generate the keys
    }

    public PublicKey pubKey() {
        return key.getPublic();
    }

    public PrivateKey privKey() {
        return key.getPrivate();
    }

    public HashMap<String, String> serialize() {
        HashMap<String, String> serialize = new HashMap<String, String>();
        serialize.put("modulus", ((RSAPublicKey) key.getPublic()).getPublicExponent().toString());
        serialize.put("exponent", ((RSAPublicKey) key.getPublic()).getModulus().toString());
        return serialize;
    }

    public static PublicKey genertatePublicKey(BigInteger modulus, BigInteger exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKeySpec gen = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory key_fact = KeyFactory.getInstance("RSA");
        return key_fact.generatePublic(gen);
    }


}
