# Giai đoạn 2: Mã hóa đối xứng (Symmetric Encryption)

## 1. Khái niệm mã hóa đối xứng
Mã hóa đối xứng sử dụng **cùng một khóa** cho cả quá trình mã hóa (Encryption) và giải mã (Decryption). Do đó, việc trao đổi khóa một cách an toàn là bài toán quan trọng nhất.

## 2. Tiêu chuẩn AES (Advanced Encryption Standard)
AES là thuật toán mã hóa đối xứng phổ biến nhất hiện nay, thay thế cho DES cũ kỹ. AES hỗ trợ các độ dài khóa: 128, 192, và 256 bits.

## 3. Các thành phần quan trọng

### Initialization Vector (IV)
IV là một chuỗi ngẫu nhiên được dùng để bắt đầu quá trình mã hóa. Nó đảm bảo rằng nếu bạn mã hóa cùng một đoạn văn bản hai lần với cùng một khóa, kết quả đầu ra sẽ khác nhau. **IV không cần giữ bí mật, nhưng phải là ngẫu nhiên.**

### Modes of Operation (Chế độ hoạt động)
- **ECB (Electronic Codebook):** Chia dữ liệu thành các khối độc lập. **Cực kỳ không an toàn** vì các khối giống nhau sẽ cho ra bản mã giống nhau (rò rỉ pattern).
- **CBC (Cipher Block Chaining):** Khối sau phụ thuộc vào khối trước. Cần IV. An toàn nhưng không hỗ trợ tính toán song song.
- **GCM (Galois/Counter Mode):** Cung cấp cả tính bảo mật và tính xác thực (Authenticated Encryption). **Được khuyến nghị sử dụng hiện nay.**

## 4. Thực hành với Java (AES/GCM)

```java
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AesGcmExample {
    private static final int GCM_IV_LENGTH = 12; // bytes
    private static final int GCM_TAG_LENGTH = 128; // bits

    public static String encrypt(String input, SecretKey key) throws Exception {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        byte[] cipherText = cipher.doFinal(input.getBytes());
        // Trả về IV + CipherText để giải mã sau này
        byte[] combinedParts = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, combinedParts, 0, iv.length);
        System.arraycopy(cipherText, 0, combinedParts, iv.length, cipherText.length);
        
        return Base64.getEncoder().encodeToString(combinedParts);
    }
}
```

## 5. KeyGenerator
Sử dụng `KeyGenerator` để tạo một `SecretKey` thực sự ngẫu nhiên và an toàn.

```java
KeyGenerator keyGen = KeyGenerator.getInstance("AES");
keyGen.init(256); // Độ dài khóa 256 bits
SecretKey secretKey = keyGen.generateKey();
```

---
[Quay lại README chính](../README.md)
