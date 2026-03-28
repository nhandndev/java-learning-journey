# Giai đoạn 3: Mã hóa bất đối xứng (Asymmetric Encryption)

## 1. Khái niệm mã hóa bất đối xứng
Sử dụng một cặp khóa đi kèm với nhau:
- **Public Key (Khóa công khai):** Có thể chia sẻ cho bất kỳ ai. Dùng để **mã hóa** dữ liệu hoặc **xác thực** chữ ký.
- **Private Key (Khóa riêng tư):** Phải giữ bí mật tuyệt đối. Dùng để **giải mã** dữ liệu hoặc **tạo** chữ ký.

## 2. Thuật toán RSA
RSA là thuật toán mã hóa bất đối xứng lâu đời và phổ biến nhất. Nó dựa trên độ khó của việc phân tích một số nguyên cực lớn thành các thừa số nguyên tố.

### Độ dài khóa khuyến nghị:
Tối thiểu là **2048-bit**. Các hệ thống yêu cầu bảo mật cao thường dùng 3072-bit hoặc 4096-bit.

## 3. KeyPairGenerator trong Java

```java
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RsaKeyGen {
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        return keyPairGen.generateKeyPair();
    }
}
```

## 4. Mã hóa và Giải mã RSA

```java
import javax.crypto.Cipher;

public class RsaExample {
    public static byte[] encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }
}
```
> **Lưu ý:** Việc sử dụng Padding như `OAEP` là bắt buộc để đảm bảo an toàn cho mã hóa RSA.

## 5. Mã hóa bất đối xứng vs Đối xứng
- **Bất đối xứng:** Chậm, chỉ mã hóa được lượng dữ liệu nhỏ (thường dùng để mã hóa "khóa đối xứng").
- **Đối xứng:** Nhanh, mã hóa được lượng dữ liệu lớn.
- **Thực tế:** Người ta dùng RSA để trao đổi một khóa AES, sau đó dùng khóa AES đó để mã hóa toàn bộ dữ liệu (gọi là **Hybrid Encryption**).

## 6. Trao đổi khóa Diffie-Hellman
Cho phép hai bên tự thiết lập một khóa bí mật chung qua một kênh truyền thông không bảo mật mà không cần gửi trực tiếp khóa đó cho nhau.

---
[Quay lại README chính](../README.md)
