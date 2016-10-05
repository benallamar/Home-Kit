package Security;

import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.util.BigIntegers;

/**
 * Created by bubble on 05/10/2016.
 */
public class Certificat {
    static private BigIntegers seqnum = BigIntegers.Zero;
    public X509CertificateObject x509;
    Certificat(){

    }
    public boolean verifiCerif(PublicKey publicKey){
        // return True, if the certificat is valid
    }
}
