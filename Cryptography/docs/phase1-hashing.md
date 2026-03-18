# Giai đoạn 1: JCA/JCE & Hashing (Băm dữ liệu)

## 1. Tổng quan về JCA & JCE
- **JCA (Java Cryptography Architecture):** Là nền tảng khung (framework) cơ bản cung cấp các API cho mật mã học trong Java (như `MessageDigest`, `Signature`). Nó hoạt động theo cơ chế "Provider-based", nghĩa là bạn dùng API chung, còn thuật toán thực tế do các "Provider" (như 'SUN', 'SunJCE', hoặc bên thứ ba như 'BouncyCastle') cung cấp.
- **JCE (Java Cryptography Extension):** Mở rộng của JCA, cung cấp các API cho mã hóa, trao đổi khóa và mã xác thực thông báo (MAC). Trước đây JCE bị hạn chế xuất khẩu nhưng từ Java 9 trở đi, các hạn chế này đã được gỡ bỏ mặc định.

## 2. Hashing là gì?
Hashing (Băm) là quá trình chuyển đổi một lượng dữ liệu bất kỳ thành một chuỗi ký tự có độ dài cố định.
- **Tính chất:**
    - **Một chiều (One-way):** Không thể đảo ngược từ hash về dữ liệu gốc.
    - **Duy nhất (Deterministic):** Cùng một đầu vào luôn cho cùng một đầu ra.
    - **Hiệu ứng tuyết lở (Avalanche effect):** Một thay đổi nhỏ ở đầu vào sẽ làm thay đổi hoàn toàn đầu ra.

## 3. Sử dụng MessageDigest API
Trong Java, chúng ta sử dụng lớp `java.security.MessageDigest`.

### Ví dụ tạo Hash SHA-256:
```java
import java.security.MessageDigest;
import java.util.HexFormat;

public class HashExample {
    public static String getSHA256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(input.getBytes());
        return HexFormat.of().encodeToString(encodedHash);
    }
}
```

## 4. Salt & Pepper - Bảo mật mật khẩu
- **Salt (Muối):** Một chuỗi ngẫu nhiên được thêm vào mật khẩu trước khi băm. Nó ngăn chặn các cuộc tấn công dùng **Rainbow Tables** (bảng băm tính sẵn).
- **Pepper:** Tương tự Salt nhưng thường được lưu ở một nơi khác (ví dụ: biến môi trường) thay vì trong Database.

### Tại sao không dùng SHA-256 cho mật khẩu?
SHA-256 rất nhanh. Hacker có thể thử hàng tỷ mật khẩu mỗi giây. Đối với mật khẩu, chúng ta cần các thuật toán **chậm** và có thể cấu chỉnh độ khó (work factor) như **BCrypt**, **PBKDF2**, hoặc **Argon2**.

## 5. HMAC (Hash-based Message Authentication Code)
HMAC kết hợp Hashing với một **Secret Key**. Nó giúp xác thực không chỉ **tính toàn vẹn** mà còn **nguồn gốc** của dữ liệu.

```java
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacExample {
    public static byte[] calculateHmac(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        return mac.doFinal(data.getBytes());
    }
}
```

---
[Quay lại README chính](../README.md)
