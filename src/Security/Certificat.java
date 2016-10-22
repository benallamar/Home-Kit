package Security;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.util.Date;

/**
 * Created by bubble on 05/10/2016.
 */
public class Certificat {
    static private BigInteger seqnum = BigInteger.ZERO;
    public X509CertificateHolder x509;

    public Certificat(String name, PaireClesRSA key, int validityDays) {
        //Define the SubjectPubicKeyInfo
        SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(key.pubKey().getEncoded());
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + validityDays * 24 * 60 * 60 * 1000);
        X509v3CertificateBuilder CertGen = new X509v3CertificateBuilder(
                new X500Name("CN=" + name),
                BigInteger.ONE,
                startDate,
                endDate,
                new X500Name("CN=" + name),//We set the subject of the certfifcat
                subPubKeyInfo
        );
        try {
            ContentSigner sigGen = new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(key.privKey());
            x509 = CertGen.build(sigGen);
        } catch (OperatorCreationException e) {

        }
    }

    public Certificat(X509CertificateHolder certificat) {
        x509 = certificat;
    }

    public Certificat(StringBuffer Name, PublicKey publicKey, PrivateKey privateKey, int validityDays) {
        //Define the SubjectPubicKeyInfo
        SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(key.pubKey().getEncoded());
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(System.currentTimeMillis() + validityDays * 24 * 60 * 60 * 1000);
        X509v3CertificateBuilder CertGen = new X509v3CertificateBuilder(
                new X500Name("CN=" + name),
                BigInteger.ONE,
                startDate,
                endDate,
                new X500Name("CN=" + name),//We set the subject of the certfifcat
                subPubKeyInfo
        );
        try {
            ContentSigner sigGen = new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(key.privKey());
            x509 = CertGen.build(sigGen);
        } catch (OperatorCreationException e) {

        }
    }

    public boolean verifiCerif(PublicKey publicKey) {
        // return True, if the certificat is valid
        return x509.isValidOn(new Date());

    }

    public PublicKey getPublicKey() {
        return (PublicKey) x509.getSubjectPublicKeyInfo().getPublicKeyData();
    }


    public static String serialize(Certificat certificat) {
        StringWriter sw = new StringWriter();
        PemWriter pw = new PemWriter(sw);
        pw.writeObject((Object) certificat.x509);
        pw.flush();
        pw.close();
        return pw.toString();
    }

    public static Certificat deserialize(String string) {
        StringReader sr = new StringReader();
        PemReader pr = new PemReader(sr);
        return new Certificat((X509CertificateHolder) pr.readPemObject());
    }
}
