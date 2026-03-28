# Các chủ đề Nâng cao trong Java Cryptography

Tài liệu này bổ trợ cho các giai đoạn cơ bản, tập trung vào các kỹ thuật thực tế quy mô lớn.

## 1. Bouncy Castle Integration
**Bouncy Castle** là một thư viện mật mã học mã nguồn mở cực kỳ mạnh mẽ, cung cấp nhiều thuật toán mà JRE mặc định không có (hoặc triển khai tốt hơn).

### Cách sử dụng:
1. Thêm dependency (Maven/Gradle).
2. Đăng ký Provider:
```java
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;

Security.addProvider(new BouncyCastleProvider());
```
3. Sau đó bạn có thể dùng các thuật toán như `"Argon2"`, `"RIPEMD160"`, v.v... bằng cách truyền thêm tham số `"BC"` vào `getInstance()`.

## 2. Mã hóa hỗn hợp (Hybrid Encryption) - RSA + AES
Đây là cách các hệ thống thực tế (như HTTPS, GPG) hoạt động.

- **Vấn đề:** RSA an toàn nhưng chậm và giới hạn kích thước dữ liệu. AES nhanh nhưng khó trao đổi khóa.
- **Giải pháp:**
    1. Tạo một khóa AES ngẫu nhiên (Session Key).
    2. Mã hóa dữ liệu bằng khóa AES này.
    3. Mã hóa chính khóa AES đó bằng **Public Key** của người nhận (RSA).
    4. Gửi cả `Dữ liệu đã mã hóa` + `Khóa AES đã mã hóa`.
    5. Người nhận dùng **Private Key** để giải mã lấy lại khóa AES, sau đó dùng khóa AES để giải mã dữ liệu.

## 3. Quản lý Security Providers
Bạn có thể kiểm tra danh sách các Provider đang chạy trong JVM của mình:
```java
for (Provider provider : Security.getProviders()) {
    System.out.println(provider.getName());
}
```
Việc thay đổi thứ tự ưu tiên của Provider có thể ảnh hưởng đến hiệu năng và độ bảo mật.

## 4. Google Tink
Nếu bạn thấy JCA/JCE quá phức tạp và dễ gây lỗi (như quên Salt, IV), hãy tìm hiểu **Google Tink**. Đây là một thư viện "multi-language" giúp việc sử dụng mật mã học trở nên cực kỳ đơn giản và khó bị sai sót.

---
[Quay lại README chính](../README.md)
