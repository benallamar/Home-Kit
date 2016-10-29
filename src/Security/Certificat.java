package Security;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.util.Date;

/**
 * Created by bubble on 05/10/2016.
 */
public class Certificat {
    static private BigInteger seqnum = BigInteger.ZERO;
    public X509CertificateHolder x509;

    //For autoSigned Ceritifcate
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

    //For Signer Certificate
    public Certificat(String name, PublicKey publicKey, PrivateKey privateKey, int validityDays) {
        //Define the SubjectPubicKeyInfo
        SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
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
            ContentSigner sigGen = new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(privateKey);
            x509 = CertGen.build(sigGen);
        } catch (OperatorCreationException e) {

        }
    }

    public boolean verifiCerif(PublicKey publicKey) throws OperatorCreationException, CertException {
        //TODO: Verfifying the Certficat using the private cle.
        ContentVerifierProvider contentVerifierProvider = new JcaContentVerifierProviderBuilder()
                .setProvider("BC").build(publicKey);
        return x509.isSignatureValid(contentVerifierProvider);

    }

    public PublicKey getPublicKey() {
        return (PublicKey) x509.getSubjectPublicKeyInfo().getPublicKeyData();
    }


    public static String serialize(Certificat certificat) throws IOException {
        StringWriter sw = new StringWriter();
        PemWriter pw = new PemWriter(sw);
        pw.writeObject(new JcaMiscPEMGenerator(certificat.x509));
        pw.flush();
        pw.close();
        return pw.toString();
    }

    public static Certificat deserialize(String string) throws IOException {
        StringReader sr = new StringReader(string);
        PEMParser pr = new PEMParser(sr);
        return new Certificat((X509CertificateHolder) pr.readObject());
    }
}
