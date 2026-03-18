package signature;

import java.security.*;
import java.util.Base64;

/**
 * Demo Chữ ký số sử dụng RSA và ECDSA.
 */
public class DigitalSigner {

    /**
     * Ký dữ liệu sử dụng RSA.
     */
    public static String signRSA(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * Xác thực chữ ký RSA.
     */
    public static boolean verifyRSA(String data, String signatureStr, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
        return signature.verify(signatureBytes);
    }

    /**
     * Tạo cặp khóa ECDSA (Đường cong Elliptic).
     */
    public static KeyPair generateECKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        keyGen.initialize(256); // Thường dùng 256-bit cho EC
        return keyGen.generateKeyPair();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("--- Digital Signature Demo (RSA) ---");
        KeyPairGenerator rsaGen = KeyPairGenerator.getInstance("RSA");
        rsaGen.initialize(2048);
        KeyPair rsaPair = rsaGen.generateKeyPair();

        String document = "Tôi cam kết học Java Cryptography mỗi ngày!";
        String signature = signRSA(document, rsaPair.getPrivate());
        
        System.out.println("Tài liệu: " + document);
        System.out.println("Chữ ký RSA: " + signature);
        System.out.println("Xác thực đúng: " + verifyRSA(document, signature, rsaPair.getPublic()));

        System.out.println("\n--- Digital Signature Demo (ECDSA) ---");
        KeyPair ecPair = generateECKeyPair();
        // Tương tự RSA, chỉ thay đổi thuật toán thành "SHA256withECDSA"
        Signature ecSign = Signature.getInstance("SHA256withECDSA");
        ecSign.initSign(ecPair.getPrivate());
        ecSign.update(document.getBytes());
        byte[] ecSignature = ecSign.sign();
        
        System.out.println("Chữ ký ECDSA (Base64): " + Base64.getEncoder().encodeToString(ecSignature));
    }
}
