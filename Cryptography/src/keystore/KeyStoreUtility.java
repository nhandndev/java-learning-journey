package keystore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;

/**
 * Quản lý khóa trong Java KeyStore (PKCS12).
 */
public class KeyStoreUtility {

    private static final String KEYSTORE_FILE = "my_vault.p12";
    private static final String KEYSTORE_PASSWORD = "strong-password-123";
    private static final String KEY_ALIAS = "my-aes-key";
    private static final String KEY_PASSWORD = "key-password-456";

    public static void createAndSaveKey() throws Exception {
        // 1. Tạo khóa AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();

        // 2. Khởi tạo KeyStore (PKCS12)
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null, KEYSTORE_PASSWORD.toCharArray()); // Khởi tạo kho mới

        // 3. Cất khóa vào kho
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray());
        ks.setEntry(KEY_ALIAS, entry, protParam);

        // 4. Lưu ra file
        try (FileOutputStream fos = new FileOutputStream(KEYSTORE_FILE)) {
            ks.store(fos, KEYSTORE_PASSWORD.toCharArray());
        }
        System.out.println("Đã lưu khóa vào file: " + KEYSTORE_FILE);
    }

    public static void loadKey() throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(KEYSTORE_FILE)) {
            ks.load(fis, KEYSTORE_PASSWORD.toCharArray());
        }

        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray());
        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) ks.getEntry(KEY_ALIAS, protParam);
        
        SecretKey key = entry.getSecretKey();
        System.out.println("Đã tải lại khóa từ KeyStore. Định dạng: " + key.getFormat());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("--- KeyStore Management Demo ---");
        createAndSaveKey();
        loadKey();
        
        // Dọn dẹp file nháp
        new File(KEYSTORE_FILE).delete();
    }
}
