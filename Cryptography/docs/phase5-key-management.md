# Giai đoạn 5: Quản lý khóa & Bảo mật nâng cao

## 1. Vấn đề lưu trữ khóa
Mã hóa mạnh đến đâu cũng vô dụng nếu bạn để lộ khóa. Khóa **không bao giờ** nên được hard-code trong mã nguồn.

## 2. Java KeyStore (JKS)
`KeyStore` là một tệp lưu trữ (container) bảo mật dùng để quản lý các khóa riêng tư, chuỗi chứng chỉ và các khóa bí mật.

### Các loại KeyStore:
- **JKS:** Định dạng cũ của Java.
- **PKCS12 (.p12, .pfx):** Định dạng chuẩn công nghiệp, được khuyến nghị dùng hiện nay.

### Làm việc với KeyStore trong Java:
```java
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

public class KeyStoreManager {
    public static void loadKey() throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        char[] password = "keystore-password".toCharArray();
        
        try (FileInputStream fis = new FileInputStream("mykeystore.p12")) {
            ks.load(fis, password);
        }

        // Lấy private key từ keystore
        KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) 
            ks.getEntry("myAlias", new KeyStore.PasswordProtection(password));
        PrivateKey privateKey = entry.getPrivateKey();
    }
}
```

## 3. TrustStore vs KeyStore
- **KeyStore:** Chứa khóa riêng tư của BẠN để chứng minh danh tính của bạn.
- **TrustStore:** Chứa các chứng chỉ công khai của CÁC BÊN KHÁC mà bạn tin tưởng (thường là CA gốc).

## 4. JSSE (Java Secure Socket Extension)
JSSE cung cấp các API để xử lý các kết nối bảo mật qua mạng bằng giao thức SSL/TLS. Khi bạn gọi một API HTTPS trong Java, JSSE sẽ tự động xử lý quá trình bắt tay (handshake) và mã hóa dữ liệu.

## 5. Best Practices về bảo mật trong Java
- Luôn sử dụng `SecureRandom` thay vì `Random` cho các mục đích mật mã.
- Xóa mảng `char[]` hoặc `byte[]` chứa thông tin nhạy cảm (mật khẩu) khỏi bộ nhớ ngay khi dùng xong (Java String không thể xóa vì nó là immutable).
- Thường xuyên cập nhật khóa và chứng chỉ.
- Sử dụng các thư viện đã được kiểm chứng (như Google Tink) nếu bài toán quá phức tạp.

---
[Quay lại README chính](../README.md)
