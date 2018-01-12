package security;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.operator.OperatorCreationException;

/**
 * Implement the Certificat interface
 */

public class Certificat {

  static private BigInteger seqnum = BigInteger.ZERO;
  public X509CertificateHolder x509;


  public Certificat(X509CertificateObject certificat) {
    x509 = certificat;
  }

  /**
   * @param issuer CA for the certificat
   * @param subject the owner
   * @param publicKey public key
   * @param privateKey private key
   * @param validityDays how much days the certificat will be valid
   */
  public Certificat(String issuer, String subject, PublicKey publicKey, PrivateKey privateKey, int validityDays) {
    //TODO: Generate the certificat from the given data
  }

  public boolean verifiCerif(PublicKey publicKey){
    //TODO: Check the certificat is valid
  }

  public PublicKey getPublicKey() {
    //TODO: Get the public key
  }


  public static String serialize(Certificat certificat) {
    // TODO: Generate a serializer
  }

  public static Certificat deserialize(String string) {
    //TODO: Generate the deserializer
  }

  public String display() throws IOException {
    //TODO: Implement the displayer
  }

  public String getIssuer() {
    // Get the issuer of the certificat
  }
}
