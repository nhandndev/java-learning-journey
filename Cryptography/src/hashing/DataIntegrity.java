package hashing;

import java.security.MessageDigest;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Demo SHA-256 (Tính toàn vẹn) và HMAC (Tính toàn vẹn + Xác thực).
 */
public class DataIntegrity {

    /**
     * Tạo Hash SHA-256 cho dữ liệu.
     */
    public static String calculateSHA256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        return HexFormat.of().formatHex(hash);
    }

    /**
     * Tạo HMAC-SHA256 với một Secret Key.
     */
    public static String calculateHMAC(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] hmacBytes = mac.doFinal(data.getBytes());
        return HexFormat.of().formatHex(hmacBytes);
    }

    public static void main(String[] args) throws Exception {
        String message = "Hello, this is a secret message!";
        String key = "my-secret-key-123";

        System.out.println("--- Data Integrity Demo ---");
        System.out.println("Thông điệp: " + message);
        
        System.out.println("SHA-256 Hash: " + calculateSHA256(message));
        System.out.println("HMAC-SHA256: " + calculateHMAC(message, key));
        
        // Giả sử hacker sửa tin nhắn
        String tamperedMessage = "Hello, this is a secret message?";
        System.out.println("\n--- Sau khi bị sửa đổi ---");
        System.out.println("SHA-256 mới: " + calculateSHA256(tamperedMessage));
        System.out.println("Hacks xác định được sự thay đổi: " + !calculateSHA256(message).equals(calculateSHA256(tamperedMessage)));
    }
}
