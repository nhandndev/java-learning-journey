package symmetric;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.nio.ByteBuffer;

/**
 * Triển khai mã hóa đối xứng AES/GCM/NoPadding (Best Practice).
 */
public class AesGcmEncryptor {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;

    /**
     * Mã hóa dữ liệu. IV sẽ được gắn vào đầu bản mã.
     */
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        byte[] iv = new byte[IV_LENGTH_BYTE];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] cipherText = cipher.doFinal(plainText.getBytes());

        // Gộp IV và CipherText vào một mảng duy nhất để dễ quản lý
        byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + cipherText.length)
                .put(iv)
                .put(cipherText)
                .array();

        return Base64.getEncoder().encodeToString(cipherTextWithIv);
    }

    /**
     * Giải mã dữ liệu.
     */
    public static String decrypt(String cipherTextWithIvBase64, SecretKey key) throws Exception {
        byte[] decode = Base64.getDecoder().decode(cipherTextWithIvBase64);

        // Tách IV và CipherText
        ByteBuffer bb = ByteBuffer.wrap(decode);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);
        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText);
    }

    public static void main(String[] args) throws Exception {
        // 1. Tạo khóa AES 256-bit
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();

        String originalText = "Đây là dữ liệu siêu mật cần bảo vệ bởi AES-GCM!";
        System.out.println("--- AES-GCM Demo ---");
        System.out.println("Dữ liệu gốc: " + originalText);

        // 2. Mã hóa
        String encrypted = encrypt(originalText, secretKey);
        System.out.println("Bản mã (Base64): " + encrypted);

        // 3. Giải mã
        String decrypted = decrypt(encrypted, secretKey);
        System.out.println("Dữ liệu giải mã: " + decrypted);
    }
}
