package Security;

import java.math.BigInteger;
import java.security.PublicKey;

import org.bouncycastle.jce.provider.X509CertificateObject;


/**
 * Created by bubble on 05/10/2016.
 */
public class Certificat {
    static private BigInteger seqnum = BigInteger.ZERO;
    public X509CertificateObject x509;

    Certificat() {
        //We have to genertate the certi
    }

    public boolean verifiCerif(PublicKey publicKey) {
        // return True, if the certificat is valid
        return false;

    }
}
