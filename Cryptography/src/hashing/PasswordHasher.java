package hashing;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Demo các phương pháp băm mật khẩu từ cơ bản đến an toàn (PBKDF2).
 * Lưu ý: Trong thực tế nên dùng các thư viện như BCrypt hoặc Argon2.
 */
public class PasswordHasher {

    // Tham số cho PBKDF2
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Tạo Salt ngẫu nhiên.
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Băm mật khẩu sử dụng PBKDF2 (An toàn).
     */
    public static String hashPassword(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Kiểm tra mật khẩu (So sánh hash).
     */
    public static boolean verifyPassword(String password, String storedHash, byte[] salt) throws Exception {
        String newHash = hashPassword(password, salt);
        return newHash.equals(storedHash);
    }

    public static void main(String[] args) throws Exception {
        String myPassword = "SuperSecretPassword123";
        byte[] salt = generateSalt();

        System.out.println("--- Password Hashing Demo ---");
        System.out.println("Password gốc: " + myPassword);
        
        String hash = hashPassword(myPassword, salt);
        System.out.println("Hash (PBKDF2): " + hash);
        System.out.println("Salt (Base64): " + Base64.getEncoder().encodeToString(salt));

        boolean isCorrect = verifyPassword(myPassword, hash, salt);
        System.out.println("Kiểm tra mật khẩu đúng: " + isCorrect);
        
        boolean isWrong = verifyPassword("wrongPass", hash, salt);
        System.out.println("Kiểm tra mật khẩu sai: " + isWrong);
    }
}
