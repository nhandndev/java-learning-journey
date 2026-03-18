# Giai đoạn 4: Chữ ký số & Chứng chỉ (Digital Signatures)

## 1. Chữ ký số (Digital Signature) là gì?
Chữ ký số cung cấp 3 yếu tố quan trọng:
1. **Tính xác thực (Authentication):** Biết chính xác ai là người gửi.
2. **Tính toàn vẹn (Integrity):** Đảm bảo dữ liệu không bị thay đổi trên đường đi.
3. **Tính không thể phủ nhận (Non-repudiation):** Người gửi không thể chối bỏ việc họ đã ký dữ liệu đó.

### Cơ chế hoạt động:
- **Ký:** `Dữ liệu -> Hash -> Mã hóa bằng Private Key = Chữ ký`.
- **Xác thực:** `Chữ ký -> Giải mã bằng Public Key = Hash cũ`. Sau đó so sánh với `Hash mới` của dữ liệu nhận được.

## 2. Signature API trong Java

```java
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class DigitalSignatureExample {
    public static byte[] signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public static boolean verifySignature(byte[] data, byte[] signatureBytes, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(signatureBytes);
    }
}
```

## 3. Khái niệm ECDSA
**Elliptic Curve Digital Signature Algorithm (ECDSA)** là thuật toán chữ ký số dựa trên đường cong Elliptic. Nó cung cấp mức độ bảo mật tương đương RSA nhưng với **độ dài khóa ngắn hơn nhiều**, giúp xử lý nhanh hơn và tiết kiệm băng thông.

## 4. Chứng chỉ số X.509
Một Public Key đơn thuần không thể chứng minh nó thuộc về ai. **Chứng chỉ số** là một tài liệu được ký bởi một bên thứ ba tin cậy (Certificate Authority - CA) để xác nhận: "Public Key này thực sự thuộc về công ty/người này".

## 5. PKI (Public Key Infrastructure)
Là một tập hợp các quy tắc, vai trò, phần mềm để quản lý, phân phối và thu hồi các chứng chỉ số. Đây là nền tảng của bảo mật trên Internet (HTTPS).

---
[Quay lại README chính](../README.md)
