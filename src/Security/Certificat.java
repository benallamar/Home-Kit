package Security;

import java.io.OutputStream;
import java.math.BigInteger;
import java.security.PublicKey;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.operator.ContentSigner;

import java.security.cert.CertificateParsingException;
import java.util.Date;

/**
 * Created by bubble on 05/10/2016.
 */
public class Certificat {
    static private BigInteger seqnum = BigInteger.ZERO;
    public X509CertificateObject x509;

    public Certificat(String name, PaireClesRSA key, int validityDays) {
        //Define the SubjectPubicKeyInfo
        SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(key.pubKey().getEncoded());
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + validityDays * 24 * 60 * 60 * 1000);
        X509v3CertificateBuilder v1CertGen = new X509v3CertificateBuilder(
                new X500Name("CN=" + name),
                BigInteger.ONE,
                startDate,
                endDate,
                new X500Name("CN=" + name),
                subPubKeyInfo
        );
        ContentSigner signer = new BcECDSAContentSignerBuilder(sigAlg, digAlg).build(key.privKey());
        X509CertificateHolder certHolder = v1CertGen.build(signer);
        try {
            x509 = new X509CertificateObject(v1CertGen);

        } catch (CertificateParsingException e) {
            System.out.print("Error, we have some error in parsing your certificat, could please try letter");
        }
    }

    public boolean verifiCerif(PublicKey publicKey) {
        // return True, if the certificat is valid
        try {
            //Check if the certificat is valid
            x509.checkValidity();
            return true;
        } catch (Exception e) {
            // Raise an Exception in all cases...
            return false;
        }
    }
}
