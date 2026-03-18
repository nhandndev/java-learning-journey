# 🔐 Java Cryptography Learning Journey

Chào mừng bạn đến với lộ trình học tập về **Cryptography (Mật mã học)** trong Java. Đây là một phần quan trọng trong lộ trình trở thành Java Developer chuyên nghiệp, giúp bạn bảo vệ dữ liệu và xây dựng các ứng dụng an toàn.

---

## 🎯 Mục tiêu
- Hiểu kiến trúc Java Cryptography Architecture (JCA) và Java Cryptography Extension (JCE).
- Thành thạo việc băm dữ liệu (Hashing), mã hóa (Encryption) và chữ ký số (Digital Signatures).
- Biết cách quản lý khóa bảo mật (Key Management) an toàn.

---

## 🛤️ Lộ trình (Roadmap)

### Giai đoạn 1: Cơ bản về JCA & Hashing
*Tìm hiểu cách tạo "dấu vân tay" cho dữ liệu.* [Xem chi tiết 📘](./docs/phase1-hashing.md)
- [x] **Khái niệm cơ bản:** Hiểu về Hash function, Salt, Pepper.
- [x] **MessageDigest API:** Sử dụng `MessageDigest` để tạo hash (SHA-256, SHA-512).
- [x] **Password Hashing:** Tại sao không nên dùng MD5/SHA đơn thuần cho mật khẩu. Tìm hiểu về `BCrypt` hoặc `Argon2`.
- [x] **MAC (Message Authentication Code):** Sử dụng `HMac` để xác thực tính toàn vẹn của dữ liệu với một secret key.

### Giai đoạn 2: Mã hóa đối xứng (Symmetric Encryption)
*Dùng một khóa duy nhất để mã hóa và giải mã.* [Xem chi tiết 📘](./docs/phase2-symmetric.md)
- [x] **AES (Advanced Encryption Standard):** Tiêu chuẩn vàng hiện nay.
- [x] **Chế độ hoạt động (Modes):** ECB vs CBC vs GCM (Tại sao GCM được ưu tiên?).
- [x] **IV (Initialization Vector):** Tầm quan trọng của IV ngẫu nhiên.
- [x] **KeyGenerator:** Cách tạo và lưu trữ khóa đối xứng an toàn.

### Giai đoạn 3: Mã hóa bất đối xứng (Asymmetric Encryption)
*Cặp khóa Public Key & Private Key.* [Xem chi tiết 📘](./docs/phase3-asymmetric.md)
- [x] **RSA Algorithm:** Hiểu cơ chế hoạt động, độ dài khóa (2048-bit+).
- [x] **KeyPairGenerator:** Tạo cặp khóa.
- [x] **Trao đổi khóa (Key Exchange):** Tìm hiểu về Diffie-Hellman.

### Giai đoạn 4: Chữ ký số & Chứng chỉ (Digital Signatures)
*Đảm bảo danh tính và tính không thể phủ nhận.* [Xem chi tiết 📘](./docs/phase4-signatures.md)
- [x] **Signature API:** Ký và xác thực dữ liệu bằng RSA/ECDSA.
- [x] **X.509 Certificates:** Chứng chỉ số là gì?
- [x] **PKI (Public Key Infrastructure):** Tổng quan về hệ thống hạ tầng khóa công khai.

### Giai đoạn 5: Quản lý khóa & Bảo mật nâng cao
*Nơi cất giữ "chìa khóa" an toàn.* [Xem chi tiết 📘](./docs/phase5-key-management.md)
- [x] **KeyStore:** Lưu trữ khóa và chứng chỉ trong Java KeyStore (`.jks`, `.p12`).
- [x] **Java Security Manager:** Các thiết lập bảo mật cấp JVM.
- [x] **HTTPS/TLS:** Cách Java xử lý kết nối bảo mật qua JSSE (Java Secure Socket Extension).

---

## 📁 Cấu trúc Thư mục Đề xuất & Code Mẫu
Dưới đây là mã nguồn đầy đủ cho từng phần để bạn có thể chạy thử trực tiếp:
- [x] **Hashing & HMAC**: [PasswordHasher.java](./src/hashing/PasswordHasher.java), [DataIntegrity.java](./src/hashing/DataIntegrity.java)
- [x] **Symmetric (AES)**: [AesGcmEncryptor.java](./src/symmetric/AesGcmEncryptor.java)
- [x] **Asymmetric (RSA)**: [RsaEncryptor.java](./src/asymmetric/RsaEncryptor.java)
- [x] **Digital Signatures**: [DigitalSigner.java](./src/signature/DigitalSigner.java)
- [x] **KeyStore Management**: [KeyStoreUtility.java](./src/keystore/KeyStoreUtility.java)

---

## 🔥 Chủ đề Nâng cao (Advanced)
Ngoài các kiến thức cơ bản, bạn nên tìm hiểu thêm các chủ đề sau để làm chủ hoàn toàn Java Cryptography:
- [Advanced Topics (Bouncy Castle, Hybrid Encryption, Providers)](./docs/advanced-topics.md) 🚀

---

## 📚 Tài liệu Tham khảo
- [Official Java Cryptography Architecture (JCA) Guide](https://docs.oracle.com/en/java/javase/21/security/java-cryptography-architecture-jca-reference-guide.html)
- [Baeldung - Java Cryptography](https://www.baeldung.com/java-cryptography-intro)
- [OWASP Cryptographic Storage Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Cryptographic_Storage_Cheat_Sheet.html)

---
🚀 *Chúc bạn có một hành trình học tập thú vị và bảo mật!*
