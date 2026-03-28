# 🔐 Cryptography trong Java

> **📌 Mục tiêu:** Hiểu khái niệm cốt lõi để không bị "lạ lẫm" khi gặp Cryptography trong Spring Security, JWT, hay HTTPS. **Không cần thuộc code.**

---

## 1. Tổng quan — Cryptography là gì?

**Mật mã học** là khoa học bảo vệ thông tin, đảm bảo:
- **Bí mật** — chỉ người được phép mới đọc được
- **Toàn vẹn** — dữ liệu không bị sửa đổi trên đường truyền
- **Xác thực** — biết chắc ai là người gửi

### Các khái niệm cần nhớ

| Khái niệm | Ý nghĩa |
|---|---|
| **Plaintext** | Dữ liệu gốc chưa mã hóa |
| **Ciphertext** | Dữ liệu sau khi mã hóa |
| **Key** | "Chìa khóa" dùng để mã hóa / giải mã |
| **Encryption** | Plaintext → Ciphertext |
| **Decryption** | Ciphertext → Plaintext |
| **Hashing** | Biến dữ liệu thành chuỗi cố định, **không thể đảo ngược** |

> **Trong Java thực tế:** Spring Security, JWT, HTTPS đã xử lý Cryptography thay bạn. Nhiệm vụ của bạn là **hiểu đủ để dùng đúng**.

---

## 2. Hashing (Băm) ⭐ Quan trọng nhất cần hiểu

### Hashing là gì?

Biến dữ liệu bất kỳ → chuỗi có độ dài **cố định**.

```
"password123"  →  sha256()  →  "ef92b778bafe771..."  (64 ký tự, luôn vậy)
"password124"  →  sha256()  →  "9b9f2a29e5f8a1..."  (hoàn toàn khác!)
```

**3 đặc điểm cần nhớ:**
- **Một chiều** → Không thể lấy lại dữ liệu gốc từ hash
- **Cùng input → cùng output** → Dùng để so sánh, kiểm tra
- **Thay đổi nhỏ → output khác hoàn toàn** → Phát hiện giả mạo dễ dàng

### Thuật toán

| Thuật toán | Dùng không? |
|---|---|
| MD5 | ❌ Không dùng cho bảo mật |
| SHA-1 | ⚠️ Cũ, tránh dùng |
| **SHA-256** | ✅ Dùng cái này |

### Ứng dụng thực tế

- **Lưu mật khẩu** — Không bao giờ lưu mật khẩu gốc, chỉ lưu hash
- **Checksum file** — Kiểm tra file tải về có bị hỏng/giả mạo không
- **JWT** — Phần signature của token dùng hash

---

## 3. Salt — Tại sao chỉ hash mật khẩu là chưa đủ?

### Vấn đề: Rainbow Table Attack

Nếu nhiều user dùng cùng mật khẩu → cùng hash → hacker biết ngay.

```
"password123" → sha256() → "ef92b778..."
"password123" → sha256() → "ef92b778..."  ← User khác, hash y hệt!
```

### Giải pháp: Salt

**Salt** = chuỗi ngẫu nhiên, thêm vào mỗi mật khẩu **trước khi** hash.

```
"password123" + salt_UserA  → hash → "a1b2c3..."
"password123" + salt_UserB  → hash → "x9y8z7..."  ← Khác hoàn toàn!
```

> **Thực tế:** `BCryptPasswordEncoder` của Spring Security tự động thêm salt. Bạn không cần tự code, chỉ cần hiểu tại sao nó làm vậy.

---

## 4. Symmetric vs Asymmetric Encryption

### Symmetric (Mã hóa đối xứng)

> **1 khóa** dùng cho cả mã hóa lẫn giải mã.

```
Gửi:   Plaintext  → [KEY] → Ciphertext
Nhận:  Ciphertext → [KEY] → Plaintext
```

- **Thuật toán phổ biến:** AES
- **Ưu điểm:** Rất nhanh
- **Nhược điểm:** Làm sao chia sẻ KEY an toàn?

### Asymmetric (Mã hóa bất đối xứng)

> **2 khóa:** Public Key (chia sẻ cho mọi người) + Private Key (giữ bí mật).

```
Bob mã hóa bằng Public Key của Alice  →  Ciphertext
Alice giải mã bằng Private Key của mình  →  Plaintext
```

- **Thuật toán phổ biến:** RSA
- **Ưu điểm:** Không cần chia sẻ khóa bí mật
- **Nhược điểm:** Chậm hơn Symmetric ~1000 lần

### Thực tế dùng thế nào?

```
HTTPS = RSA (trao đổi khóa an toàn) + AES (mã hóa dữ liệu)
JWT   = HMAC-SHA256 (dùng shared key) hoặc RS256 (dùng RSA keypair)
```

---

## 5. HMAC — Bạn sẽ gặp trong JWT

**HMAC** = Hash + Secret Key → Xác thực rằng message **chưa bị sửa đổi** và đến từ **đúng nguồn**.

```
HMAC = Hash(message + secret_key)
```

**Gặp ở đâu?**
- JWT token dùng `HS256` = HMAC với SHA-256
- API authentication (AWS, Stripe đều dùng kiểu này)

---

## 6. Tóm tắt — Khi nào dùng gì?

| Tình huống | Giải pháp | Thực tế trong Java |
|---|---|---|
| Lưu mật khẩu user | Hash + Salt | `BCryptPasswordEncoder` (Spring Security) |
| Xác thực JWT | HMAC-SHA256 | JWT library tự lo |
| HTTPS / TLS | RSA + AES | JDK / Server tự xử lý |
| Checksum file | SHA-256 | `MessageDigest` (nếu cần tự code) |

---

## 7. Những điều LUÔN nhớ (dù không code)

| ❌ Sai | ✅ Đúng |
|---|---|
| Lưu mật khẩu dạng plaintext | Luôn hash + salt |
| Dùng MD5 cho bảo mật | Dùng SHA-256 trở lên |
| Tự viết thuật toán mã hóa | Dùng thư viện đã kiểm chứng |
| Hardcode secret key trong code | Dùng biến môi trường |

---

## 8. Code tham khảo (chạy thử cho biết, không cần thuộc)

<details>
<summary>👉 SHA-256 Hashing</summary>

```java
import java.security.MessageDigest;
import java.util.HexFormat;

public class HashingExample {
    public static String sha256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
        return HexFormat.of().formatHex(hashBytes);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(sha256("hello")); // luôn ra cùng 1 chuỗi
        System.out.println(sha256("hellO")); // hoàn toàn khác!
    }
}
```
</details>

<details>
<summary>👉 PBKDF2 — Lưu mật khẩu đúng cách</summary>

```java
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHashing {
    public static void main(String[] args) throws Exception {
        String password = "myPassword";
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt); // Salt ngẫu nhiên cho mỗi user

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 310_000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        System.out.println("Hash: " + Base64.getEncoder().encodeToString(hash));
        // Thực tế: Spring Security dùng BCrypt, không cần tự viết cái này
    }
}
```
</details>

<details>
<summary>👉 AES-GCM — Mã hóa dữ liệu</summary>

```java
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.Base64;

public class AesExample {
    public static void main(String[] args) throws Exception {
        // Tạo key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey key = keyGen.generateKey();

        // Tạo IV ngẫu nhiên
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        // Mã hóa
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] encrypted = cipher.doFinal("Hello Secret!".getBytes());

        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
    }
}
```
</details>

---

> **🎯 Checklist học xong phần này:**
> - [ ] Hiểu Hashing là gì và tại sao không thể đảo ngược
> - [ ] Hiểu tại sao cần Salt khi lưu mật khẩu
> - [ ] Biết phân biệt Symmetric vs Asymmetric
> - [ ] Biết HMAC liên quan đến JWT
> - [ ] Chạy thử ít nhất 1 đoạn code tham khảo ở trên
