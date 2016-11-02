package Connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Iterator;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.jcajce.JcePBEKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;


public class PGPMessage {
    public static String decryptRSA(PublicKey key, byte[] data) {
        //
        // Get an RSA Cipher instance
        //

        //Cipher rsa = null;

        try {
      /* The following commented code can be used the BouncyCastle
       * JCE provider signature is intact, which is not the
       * case when BC has been repackaged using jarjar
      rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
      rsa.init (Cipher.DECRYPT_MODE, key, CryptoHelper.sr);
      return rsa.doFinal(data);
      */

            String plainText = null;
            AsymmetricBlockCipher eng = new RSAEngine();

            eng = new PKCS1Encoding(eng);
            SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(key.getEncoded());
            eng.init(false, (AsymmetricKeyParameter) PublicKeyFactory.createKey(subPubKeyInfo));
            byte[] plainByte = eng.processBlock(data, 0, data.length);
            plainText = new String(plainByte);

            return plainText;

        } catch (InvalidCipherTextException icte) {
            System.out.println(icte.fillInStackTrace());
            return null;
        } catch (IOException icte) {
            System.out.println(icte.fillInStackTrace());
            return null;
        }
    }

    public static byte[] encryptRSA(Key key, byte[] data) {
        //
        // Get an RSA Cipher instance
        //
        //Cipher rsa = null;

        try {
      /* The following commented code can be used the BouncyCastle
       * JCE provider signature is intact, which is not the
       * case when BC has been repackaged using jarjar
      rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
      rsa.init (Cipher.ENCRYPT_MODE, key, CryptoHelper.sr);
      return rsa.doFinal(data);
      */
            AsymmetricBlockCipher c = new PKCS1Encoding(new RSABlindedEngine());
            if (key instanceof RSAPublicKey) {
                c.init(true, new RSAKeyParameters(true, ((RSAPublicKey) key).getModulus(), ((RSAPublicKey) key).getPublicExponent()));
            } else if (key instanceof RSAPrivateKey) {
                c.init(true, new RSAKeyParameters(true, ((RSAPrivateKey) key).getModulus(), ((RSAPrivateKey) key).getPrivateExponent()));
            } else {
                return null;
            }

            int insize = c.getInputBlockSize();

            int offset = 0;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (offset < data.length) {
                int len = Math.min(insize, data.length - offset);
                baos.write(c.processBlock(data, offset, len));
                offset += len;
            }

            return baos.toByteArray();

        } catch (InvalidCipherTextException icte) {
            System.out.println(icte.fillInStackTrace());
            return null;
        } catch (IOException ioe) {
            System.out.println(ioe.fillInStackTrace());

            return null;
        }
    }

}
