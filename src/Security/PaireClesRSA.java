package Security;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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

    public static String serialize(PaireClesRSA key) {
        try {
            StringWriter sw = new StringWriter();
            PemWriter pw = new PemWriter(sw);
            pw.writeObject(new JcaMiscPEMGenerator(key.pubKey()));
            pw.flush();
            pw.close();
            return pw.toString();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    public static PublicKey deserialize(String pem_format) {
        try {
            StringReader sr = new StringReader(pem_format);
            PemReader pr = new PemReader(sr);
            return new Certificat((PublicKey) pr.readPemObject());
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }


}
