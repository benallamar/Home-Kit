package Connection;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Date;
import java.util.Iterator;

import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignature;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.operator.PGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;
import org.bouncycastle.util.io.Streams;


public class PGPMessage {
	public static void signEncryptMessage(InputStream in, OutputStream out, PGPPublicKey publicKey, PGPPrivateKey secretKey, SecureRandom rand) throws Exception {
        out = new ArmoredOutputStream(out);

        PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(new BcPGPDataEncryptorBuilder(PGPEncryptedData.AES_256).setWithIntegrityPacket(true).setSecureRandom(rand));
        encryptedDataGenerator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(publicKey));

        OutputStream compressedOut = new PGPCompressedDataGenerator(PGPCompressedData.ZIP).open(encryptedDataGenerator.open(out, 4096), new byte[4096]);

        PGPSignatureGenerator signatureGenerator = new PGPSignatureGenerator(((PGPContentSignerBuilder) new BcPGPContentSignerBuilder(publicKey.getAlgorithm(), HashAlgorithmTags.SHA512)));
        signatureGenerator.init(PGPSignature.BINARY_DOCUMENT, secretKey);
        signatureGenerator.generateOnePassVersion(true).encode(compressedOut);

        OutputStream finalOut = new PGPLiteralDataGenerator().open(compressedOut, PGPLiteralData.BINARY, "", new Date(), new byte[4096]);

        byte[] buf = new byte[4096];
        int len;
        while ((len = in.read(buf)) > 0) {
            finalOut.write(buf, 0, len);
            signatureGenerator.update(buf, 0, len);
        }

        finalOut.close();
        in.close();
        signatureGenerator.generate().encode(compressedOut);
        compressedOut.close();
        encryptedDataGenerator.close();
        out.close();
    }

    public static void decryptVerifyMessage(InputStream in, OutputStream out, PGPPrivateKey secretKey, PGPPublicKey publicKey) throws Exception {
        in = new ArmoredInputStream(in);

        PGPObjectFactory pgpF = new PGPObjectFactory(in, null);
        PGPEncryptedDataList enc = (PGPEncryptedDataList) pgpF.nextObject();

        PGPObjectFactory plainFact = new PGPObjectFactory(((PGPPublicKeyEncryptedData) enc.getEncryptedDataObjects().next()).getDataStream(new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC").build(secretKey)), null);

        Object message = null;

        PGPOnePassSignatureList onePassSignatureList = null;
        PGPSignatureList signatureList = null;
        PGPCompressedData compressedData = null;

        message = plainFact.nextObject();
        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();

        while (message != null) {
            System.out.println(message.toString());
            if (message instanceof PGPCompressedData) {
                compressedData = (PGPCompressedData) message;
                plainFact = new PGPObjectFactory(compressedData.getDataStream(), null);
                message = plainFact.nextObject();
                System.out.println(message.toString());
            }

            if (message instanceof PGPLiteralData) {
                Streams.pipeAll(((PGPLiteralData) message).getInputStream(), actualOutput);
            } else if (message instanceof PGPOnePassSignatureList) {
                onePassSignatureList = (PGPOnePassSignatureList) message;
            } else if (message instanceof PGPSignatureList) {
                signatureList = (PGPSignatureList) message;
            } else {
                throw new PGPException("message unknown message type.");
            }
            message = plainFact.nextObject();
        }
        actualOutput.close();
        byte[] output = actualOutput.toByteArray();
        if (onePassSignatureList == null || signatureList == null) {
            throw new PGPException("Poor PGP. Signatures not found.");
        } else {

            for (int i = 0; i < onePassSignatureList.size(); i++) {
                PGPOnePassSignature ops = onePassSignatureList.get(0);
                System.out.println("verifier : " + ops.getKeyID());
                if (publicKey != null) {
                    ops.init(new JcaPGPContentVerifierBuilderProvider().setProvider("BC"), publicKey);
                    ops.update(output);
                    PGPSignature signature = signatureList.get(i);
                    if (ops.verify(signature)) {
                        Iterator<?> userIds = publicKey.getUserIDs();
                        while (userIds.hasNext()) {
                            String userId = (String) userIds.next();
                            System.out.println("Signed by " + userId);
                        }
                        System.out.println("Signature verified");
                    } else {
                        throw new SignatureException("Signature verification failed");
                    }
                }
            }

        }

        out.write(output);
        out.flush();
        out.close();
    }
}
