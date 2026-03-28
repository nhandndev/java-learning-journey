package asymmetric;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * Triển khai mã hóa RSA với Padding OAEP (An toàn).
 */
public class RsaEncryptor {

    private static final String ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("--- RSA Asymmetric Demo ---");
        
        // 1. Tạo cặp khóa
        KeyPair pair = generateKeyPair();
        PublicKey pubKey = pair.getPublic();
        PrivateKey privKey = pair.getPrivate();

        String secretMessage = "Chào mừng bạn đến với thế giới mã hóa bất đối xứng!";
        System.out.println("Tin nhắn gốc: " + secretMessage);

        // 2. Mã hóa bằng Public Key
        String encrypted = encrypt(secretMessage, pubKey);
        System.out.println("Bản mã RSA: " + encrypted);

        // 3. Giải mã bằng Private Key
        String decrypted = decrypt(encrypted, privKey);
        System.out.println("Tin nhắn giải mã: " + decrypted);
    }
}
