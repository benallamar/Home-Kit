import Security.PaireClesRSA;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        PaireClesRSA doubl_key = new PaireClesRSA(512);
        System.out.print(doubl_key.privKey().getEncoded());
        System.out.print(BigInteger.ZERO);
    }
}