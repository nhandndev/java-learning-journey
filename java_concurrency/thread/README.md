# java-learning-journey
Java Concurrency: Threads - Full Kiến Thức và Mở Rộng
1. Threads là gì? (Kiến thức cơ bản)
   Định nghĩa: Một Thread (luồng) là đơn vị nhỏ nhất có thể được lên lịch và thực thi bởi hệ điều hành. Trong Java, các thread cho phép chương trình thực hiện nhiều tác vụ cùng một lúc (hoạt động đồng thời).
   Tiến trình (Process) vs. Luồng (Thread):
   Process: Một chương trình đang chạy. Mỗi process có không gian bộ nhớ riêng biệt, tài nguyên riêng và thường độc lập với các process khác. Ví dụ: Trình duyệt Chrome là một process.
   Thread: Là một phần của process. Nhiều thread có thể tồn tại trong một process và chia sẻ cùng một không gian bộ nhớ, tài nguyên của process đó. Các thread trong cùng một process có thể giao tiếp dễ dàng hơn nhưng cũng dễ gây ra vấn đề đồng bộ.
   Tại sao cần Threads?
   Phản hồi tốt hơn: Ứng dụng có thể vẫn phản hồi (ví dụ: giao diện người dùng không bị "đơ") trong khi một tác vụ nặng đang chạy ở nền.
   Sử dụng hiệu quả tài nguyên: Đặc biệt trên các CPU đa lõi, threads cho phép tận dụng khả năng xử lý song song, giúp tăng tốc độ thực thi tổng thể.
   Lập trình module: Chia một tác vụ lớn thành nhiều tác vụ nhỏ hơn, dễ quản lý hơn, mỗi tác vụ chạy trên một thread riêng.
2. Cách tạo Thread trong Java
   Có hai cách chính để tạo một thread trong Java:

Cách 1: Kế thừa lớp Thread
Bạn tạo một lớp mới kế thừa từ java.lang.Thread và ghi đè (override) phương thức run(). Phương thức run() chứa logic mà thread này sẽ thực thi.
Để bắt đầu thread, bạn tạo một đối tượng của lớp đó và gọi phương thức start().
Ví dụ Code 1.1:

java

// Bước 1: Tạo một lớp kế thừa từ Thread
class MyThread extends Thread {
@Override
public void run() {
// Logic mà thread này sẽ thực hiện
for (int i = 0; i < 5; i++) {
System.out.println(Thread.currentThread().getName() + " - Count: " + i);
try {
Thread.sleep(100); // Tạm dừng 100ms để thấy rõ sự xen kẽ
} catch (InterruptedException e) {
System.out.println(Thread.currentThread().getName() + " bị ngắt quãng.");
Thread.currentThread().interrupt(); // Đặt lại trạng thái ngắt
}
}
System.out.println(Thread.currentThread().getName() + " đã kết thúc.");
}
}
public class ThreadExample1 {
public static void main(String[] args) {
System.out.println("Main thread bắt đầu.");
// Bước 2: Tạo đối tượng của lớp MyThread
MyThread thread1 = new MyThread();
thread1.setName("Thread-1"); // Đặt tên cho thread
MyThread thread2 = new MyThread();
thread2.setName("Thread-2");
// Bước 3: Gọi phương thức start() để bắt đầu thread
thread1.start(); // Gọi run() một cách bất đồng bộ
thread2.start(); // Gọi run() một cách bất đồng bộ
// Logic của main thread vẫn tiếp tục chạy song song
for (int i = 0; i < 5; i++) {
System.out.println(Thread.currentThread().getName() + " - Count: " + i);
try {
Thread.sleep(100);
} catch (InterruptedException e) {
System.out.println(Thread.currentThread().getName() + " bị ngắt quãng.");
Thread.currentThread().interrupt();
}
}
System.out.println("Main thread kết thúc.");
}
}
Cách 2: Triển khai giao diện Runnable
Đây là cách được khuyến nghị hơn vì Java không hỗ trợ đa kế thừa. Khi triển khai Runnable, lớp của bạn vẫn có thể kế thừa từ một lớp khác nếu cần.
Bạn tạo một lớp mới triển khai giao diện java.lang.Runnable và ghi đè phương thức run().
Để bắt đầu thread, bạn tạo một đối tượng của lớp đó, sau đó tạo một đối tượng Thread mới và truyền đối tượng Runnable vào constructor của Thread, rồi gọi start() trên đối tượng Thread.
Ví dụ Code 1.2:

java

// Bước 1: Tạo một lớp triển khai giao diện Runnable
class MyRunnable implements Runnable {
private String threadName;
public MyRunnable(String name) {
this.threadName = name;
}
@Override
public void run() {
// Logic mà thread này sẽ thực hiện
for (int i = 0; i < 5; i++) {
System.out.println(threadName + " - Count: " + i);
try {
Thread.sleep(150); // Tạm dừng 150ms
} catch (InterruptedException e) {
System.out.println(threadName + " bị ngắt quãng.");
Thread.currentThread().interrupt();
}
}
System.out.println(threadName + " đã kết thúc.");
}
}
public class ThreadExample2 {
public static void main(String[] args) {
System.out.println("Main thread bắt đầu.");
// Bước 2: Tạo đối tượng của lớp MyRunnable
MyRunnable runnable1 = new MyRunnable("Runnable-1");
MyRunnable runnable2 = new MyRunnable("Runnable-2");
// Bước 3: Tạo đối tượng Thread và truyền MyRunnable vào
Thread thread1 = new Thread(runnable1);
Thread thread2 = new Thread(runnable2);
// Bước 4: Gọi phương thức start() để bắt đầu thread
thread1.start();
thread2.start();
System.out.println("Main thread vẫn đang chạy...");
try {
// Đợi thread1 và thread2 hoàn thành trước khi main thread kết thúc
thread1.join(); // main thread chờ thread1 kết thúc
thread2.join(); // main thread chờ thread2 kết thúc
} catch (InterruptedException e) {
System.out.println("Main thread bị ngắt quãng khi chờ.");
Thread.currentThread().interrupt();
}
System.out.println("Main thread kết thúc.");
}
}
Lưu ý quan trọng:

Không bao giờ gọi trực tiếp run(): Nếu bạn gọi run() thay vì start(), mã sẽ được thực thi trên thread hiện tại (thường là main thread), không phải trên một thread mới.
start() chỉ được gọi một lần: Một thread chỉ có thể được bắt đầu một lần. Gọi start() lần thứ hai trên cùng một đối tượng Thread sẽ ném ra IllegalThreadStateException.
3. Vòng đời của Thread (Thread Life Cycle)
   Một thread đi qua nhiều trạng thái khác nhau từ khi được tạo cho đến khi kết thúc:

NEW (Mới tạo): Khi một đối tượng Thread được tạo ra (ví dụ: new Thread(runnable)), nhưng phương thức start() chưa được gọi. Thread đang tồn tại nhưng chưa sẵn sàng để chạy.
RUNNABLE (Sẵn sàng chạy): Khi phương thức start() được gọi. Thread được xếp vào hàng chờ của bộ lập lịch (scheduler) của hệ điều hành. Nó có thể đang chạy hoặc chờ đợi để được chạy.
BLOCKED (Bị chặn): Thread ở trạng thái này khi nó cố gắng truy cập một đoạn mã được bảo vệ bởi một khóa (monitor lock), và khóa đó hiện đang được giữ bởi một thread khác. Nó sẽ chờ cho đến khi khóa được giải phóng.
WAITING (Chờ đợi): Thread ở trạng thái này khi nó gọi một trong các phương thức Object.wait(), Thread.join(), hoặc LockSupport.park(). Nó đang chờ một thread khác thực hiện một hành động cụ thể (ví dụ: gọi notify()/notifyAll(), hoặc thread mà nó join() đã kết thúc).
TIMED_WAITING (Chờ đợi có thời gian): Giống như WAITING, nhưng với một thời gian chờ tối đa. Các phương thức gây ra trạng thái này là Thread.sleep(), Object.wait(long timeout), Thread.join(long timeout), LockSupport.parkNanos(), LockSupport.parkUntil().
TERMINATED (Kết thúc): Thread ở trạng thái này khi phương thức run() của nó đã hoàn thành việc thực thi, hoặc nếu nó kết thúc do một exception chưa được xử lý. Thread không thể được khởi động lại từ trạng thái này.
4. Các phương thức quan trọng của lớp Thread
   start(): Bắt đầu thực thi thread. Nó gọi phương thức run() trong một thread mới.
   run(): Chứa logic thực thi của thread.
   sleep(long milliseconds): Dừng thực thi của thread hiện tại trong một khoảng thời gian nhất định. Nó chuyển thread từ RUNNABLE sang TIMED_WAITING.
   join() / join(long milliseconds): Cho phép một thread chờ cho đến khi một thread khác hoàn thành công việc của nó. Ví dụ: threadA.join() sẽ khiến thread hiện tại (thường là main thread) đợi threadA kết thúc.
   yield(): Một gợi ý cho bộ lập lịch rằng thread hiện tại sẵn sàng nhường lại CPU cho các thread khác có cùng độ ưu tiên. Bộ lập lịch có thể bỏ qua gợi ý này.
   setName(String name) / getName(): Thiết lập và lấy tên của thread. Hữu ích cho việc debug.
   setPriority(int newPriority) / getPriority(): Thiết lập và lấy độ ưu tiên của thread (từ Thread.MIN_PRIORITY (1) đến Thread.MAX_PRIORITY (10), mặc định là Thread.NORM_PRIORITY (5)). Độ ưu tiên chỉ là gợi ý cho bộ lập lịch, không đảm bảo hoàn toàn.
   currentThread(): Là một phương thức static trả về tham chiếu đến đối tượng thread đang thực thi đoạn mã này.
   interrupt(): Gửi tín hiệu ngắt (interruption signal) đến một thread. Nó không dừng thread ngay lập tức mà chỉ đặt cờ interrupted của thread đó là true. Thread cần kiểm tra cờ này và tự xử lý việc ngắt (thường là dừng công việc).
   isInterrupted(): Trả về trạng thái của cờ interrupted của thread (không xóa cờ).
   interrupted(): Là phương thức static, kiểm tra cờ interrupted của thread hiện tại và xóa cờ đó về false.
   isAlive(): Kiểm tra xem thread có đang ở trạng thái RUNNABLE, BLOCKED, WAITING, hoặc TIMED_WAITING hay không (nghĩa là nó đã được start và chưa bị TERMINATED).
5. Xử lý Interruption (Ngắt quãng)
   Ngắt quãng là cơ chế an toàn và hợp lý để yêu cầu một thread dừng công việc của nó. Thay vì ép buộc dừng (như stop() - đã bị deprecated), bạn gửi một tín hiệu, và thread tự quyết định khi nào và làm thế nào để dừng.

Khi một thread đang ở trạng thái WAITING, TIMED_WAITING, hoặc BLOCKED (ví dụ: do sleep(), wait(), join()), việc gọi interrupt() trên nó sẽ khiến nó ném ra InterruptedException.
Nếu thread đang chạy bình thường, interrupt() chỉ đặt cờ interrupted là true. Thread cần tự kiểm tra Thread.currentThread().isInterrupted() để biết khi nào cần dừng.
Ví dụ Code 2: Xử lý Ngắt quãng

java

class InterruptibleTask implements Runnable {
@Override
public void run() {
System.out.println(Thread.currentThread().getName() + " bắt đầu làm việc.");
try {
// Mô phỏng công việc dài
for (int i = 0; i < 1000000; i++) {
if (Thread.currentThread().isInterrupted()) {
System.out.println(Thread.currentThread().getName() + " phát hiện bị ngắt và dừng lại.");
throw new InterruptedException("Công việc bị ngắt!"); // Thoát khỏi vòng lặp và phương thức
}
// Giả định công việc nặng
// System.out.println(Thread.currentThread().getName() + " - Processing " + i);
}
// Nếu có sleep() hoặc wait()
System.out.println(Thread.currentThread().getName() + " chuẩn bị ngủ 5 giây.");
Thread.sleep(5000); // Nếu bị ngắt khi ngủ, InterruptedException sẽ được ném ra
System.out.println(Thread.currentThread().getName() + " thức dậy sau giấc ngủ.");
} catch (InterruptedException e) {
System.out.println(Thread.currentThread().getName() + " bị gián đoạn: " + e.getMessage());
// Xử lý việc dọn dẹp tài nguyên tại đây
// Đặt lại cờ ngắt nếu bạn muốn thread bị ngắt lần nữa bởi upstream code
Thread.currentThread().interrupt();
} finally {
System.out.println(Thread.currentThread().getName() + " kết thúc.");
}
}
}
public class InterruptExample {
public static void main(String[] args) {
Thread taskThread = new Thread(new InterruptibleTask(), "Task-Thread");
taskThread.start();
try {
Thread.sleep(2000); // Main thread chờ 2 giây
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
System.out.println("Main thread yêu cầu Task-Thread dừng lại.");
taskThread.interrupt(); // Gửi tín hiệu ngắt
try {
taskThread.join(); // Chờ Task-Thread kết thúc hoàn toàn
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
System.out.println("Main thread kết thúc.");
}
}
6. Các vấn đề mở rộng và cân nhắc
   6.1. Đồng bộ hóa (Synchronization)
   Khi nhiều thread truy cập và sửa đổi cùng một tài nguyên chia sẻ (shared resource), có thể xảy ra các vấn đề như:

Race condition (Điều kiện chạy đua): Kết quả của chương trình phụ thuộc vào thứ tự thực thi không xác định của các thread.
Data inconsistency (Dữ liệu không nhất quán): Dữ liệu bị hỏng hoặc không chính xác do các thread ghi đè lên nhau.
Để giải quyết, Java cung cấp các cơ chế đồng bộ hóa:

synchronized keyword:
Synchronized method: Toàn bộ phương thức được bảo vệ. Khi một thread gọi một phương thức synchronized trên một đối tượng, nó sẽ có được khóa (monitor lock) của đối tượng đó. Các thread khác sẽ phải chờ nếu muốn gọi bất kỳ phương thức synchronized nào khác trên cùng đối tượng đó.
Synchronized block: Chỉ một phần của mã được bảo vệ. Cú pháp: synchronized (this) { ... } hoặc synchronized (objectReference) { ... }. Nó linh hoạt hơn và hiệu quả hơn vì chỉ khóa một phần nhỏ cần thiết.
volatile keyword: Đảm bảo rằng các thay đổi đối với một biến là ngay lập tức hiển thị cho các thread khác. Ngăn chặn việc các thread đọc các giá trị cũ từ bộ nhớ cache của CPU của chúng. Không thay thế synchronized cho các hoạt động nguyên tử (atomic operations) trên nhiều bước.
Java.util.concurrent package (JUC): Cung cấp các công cụ đồng bộ hóa mạnh mẽ hơn như:
Lock interface (ví dụ ReentrantLock): Cung cấp khả năng khóa linh hoạt hơn so với synchronized (có thể khóa/mở khóa rõ ràng, thử khóa không chặn, khóa đọc/ghi).
Semaphore: Giới hạn số lượng thread có thể truy cập một tài nguyên cụ thể cùng một lúc.
CountDownLatch, CyclicBarrier: Các công cụ phối hợp thread.
Atomic classes (ví dụ AtomicInteger): Cung cấp các hoạt động nguyên tử trên các kiểu dữ liệu cơ bản mà không cần khóa rõ ràng.
Ví dụ Code 3: Race Condition và synchronized

java

class Counter {
private int count = 0;
// Phương thức không đồng bộ (gây ra race condition)
public void increment() {
count++;
}
// Phương thức đồng bộ hóa (giải quyết race condition)
public synchronized void synchronizedIncrement() {
count++;
}
public int getCount() {
return count;
}
}
public class SynchronizationExample {
public static void main(String[] args) throws InterruptedException {
Counter counter = new Counter();
// --- Ví dụ Race Condition ---
System.out.println("--- Race Condition Example ---");
Thread[] threads1 = new Thread[100];
for (int i = 0; i < threads1.length; i++) {
threads1[i] = new Thread(() -> {
for (int j = 0; j < 1000; j++) {
counter.increment(); // Gọi phương thức không đồng bộ
}
});
threads1[i].start();
}
for (Thread t : threads1) {
t.join(); // Đợi tất cả các thread kết thúc
}
System.out.println("Final count (without sync): " + counter.getCount()); // Sẽ nhỏ hơn 100*1000 = 100000
// Reset counter cho ví dụ đồng bộ
counter = new Counter();
// --- Ví dụ Synchronized ---
System.out.println("\n--- Synchronized Example ---");
Thread[] threads2 = new Thread[100];
for (int i = 0; i < threads2.length; i++) {
threads2[i] = new Thread(() -> {
for (int j = 0; j < 1000; j++) {
counter.synchronizedIncrement(); // Gọi phương thức đồng bộ
}
});
threads2[i].start();
}
for (Thread t : threads2) {
t.join(); // Đợi tất cả các thread kết thúc
}
System.out.println("Final count (with sync): " + counter.getCount()); // Sẽ là 100000
}
}
6.2. Deadlock (Tắc nghẽn)
Xảy ra khi hai hoặc nhiều thread bị chặn vĩnh viễn, mỗi thread chờ tài nguyên mà thread khác đang giữ.

Điều kiện cho Deadlock (Bốn điều kiện của Coffman):
Mutual Exclusion (Loại trừ lẫn nhau): Tài nguyên không thể được chia sẻ.
Hold and Wait (Giữ và Chờ): Một thread giữ ít nhất một tài nguyên và đang chờ một tài nguyên khác.
No Preemption (Không chiếm đoạt): Tài nguyên không thể bị chiếm đoạt. Chỉ có thread giữ tài nguyên mới có thể giải phóng nó.
Circular Wait (Chờ vòng tròn): Một chuỗi các thread mà mỗi thread chờ tài nguyên do thread tiếp theo trong chuỗi giữ.
Ví dụ Code 4: Deadlock

java

public class DeadlockExample {
public static Object lock1 = new Object();
public static Object lock2 = new Object();
public static void main(String[] args) {
Thread thread1 = new Thread(() -> {
synchronized (lock1) {
System.out.println("Thread 1: Đã giữ lock 1");
try {
Thread.sleep(100); // Chờ một chút để Thread 2 có cơ hội giữ lock 2
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
System.out.println("Thread 1: Đang chờ lock 2...");
synchronized (lock2) { // Thread 1 cố gắng giữ lock 2
System.out.println("Thread 1: Đã giữ lock 1 và lock 2");
}
}
}, "Thread-A");
Thread thread2 = new Thread(() -> {
synchronized (lock2) { // Thread 2 cố gắng giữ lock 2 trước
System.out.println("Thread 2: Đã giữ lock 2");
try {
Thread.sleep(100); // Chờ một chút để Thread 1 có cơ hội giữ lock 1
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
System.out.println("Thread 2: Đang chờ lock 1...");
synchronized (lock1) { // Thread 2 cố gắng giữ lock 1
System.out.println("Thread 2: Đã giữ lock 2 và lock 1");
}
}
}, "Thread-B");
thread1.start();
thread2.start();
// Các thread sẽ bị treo ở đây vì không ai có thể lấy được lock mà mình cần
// Chương trình sẽ không bao giờ in ra các dòng "Đã giữ lock 1 và lock 2"
System.out.println("Main thread kết thúc (nhưng có thể có deadlock).");
}
}
Cách phòng tránh Deadlock:

Phân cấp thứ tự khóa: Luôn lấy các khóa theo một thứ tự cố định.
Thời gian chờ khóa (Lock Timeout): Sử dụng tryLock(long timeout, TimeUnit unit) của giao diện Lock để thử khóa trong một khoảng thời gian nhất định.
Phát hiện và Phục hồi: Không phải lúc nào cũng khả thi trong Java tiêu chuẩn.
6.3. Starvation (Đói tài nguyên)
Một thread không bao giờ có thể giành được quyền truy cập vào một tài nguyên hoặc CPU mà nó cần vì các thread khác luôn được ưu tiên hơn hoặc liên tục giữ tài nguyên.

6.4. Livelock (Khóa sống)
Các thread liên tục cố gắng phản hồi các hành động của nhau mà không thực sự tiến triển, dẫn đến việc chúng bị bận rộn nhưng không làm được gì. (Ví dụ: hai người cố gắng nhường đường cho nhau nhưng lại liên tục di chuyển về cùng một phía).

6.5. Thread Pools (Hồ bơi Thread)
Việc tạo và hủy thread là tốn kém. Thread Pools giúp quản lý các thread hiệu quả hơn bằng cách tái sử dụng chúng.

java.util.concurrent.Executors cung cấp các factory methods để tạo các loại ExecutorService khác nhau:
newFixedThreadPool(int nThreads): Tạo một pool với số lượng thread cố định.
newCachedThreadPool(): Tạo pool tự động tăng/giảm số lượng thread tùy theo nhu cầu.
newSingleThreadExecutor(): Đảm bảo các tác vụ được thực thi tuần tự bởi một thread duy nhất.
newScheduledThreadPool(int corePoolSize): Cho phép lên lịch các tác vụ chạy định kỳ hoặc sau một khoảng thời gian nhất định.
Ví dụ Code 5: Thread Pool

java

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
class WorkerTask implements Runnable {
private String taskName;
public WorkerTask(String name) {
this.taskName = name;
}
@Override
public void run() {
System.out.println(Thread.currentThread().getName() + " bắt đầu thực hiện " + taskName);
try {
Thread.sleep((long) (Math.random() * 2000)); // Mô phỏng công việc
} catch (InterruptedException e) {
System.out.println(Thread.currentThread().getName() + " bị ngắt khi làm " + taskName);
Thread.currentThread().interrupt();
}
System.out.println(Thread.currentThread().getName() + " hoàn thành " + taskName);
}
}
public class ThreadPoolExample {
public static void main(String[] args) throws InterruptedException {
// Tạo một Fixed Thread Pool với 3 thread
ExecutorService executor = Executors.newFixedThreadPool(3);
// Gửi 10 tác vụ vào pool
for (int i = 1; i <= 10; i++) {
executor.submit(new WorkerTask("Task-" + i));
}
// Tắt pool sau khi tất cả các tác vụ đã được gửi đi
executor.shutdown(); // Ngừng chấp nhận các tác vụ mới, nhưng hoàn thành các tác vụ đang chờ
// Chờ tất cả các tác vụ trong pool kết thúc, tối đa 5 giây
if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
System.err.println("Một số tác vụ không kết thúc trong thời gian quy định. Đang cố gắng dừng ép buộc...");
executor.shutdownNow(); // Cố gắng dừng tất cả các tác vụ đang chạy
}
System.out.println("Tất cả các tác vụ đã hoàn thành hoặc bị dừng.");
}
}
6.6. Future và Callable
Runnable: Không trả về giá trị và không thể ném checked exception.
Callable<V>: Tương tự Runnable, nhưng call() trả về một giá trị (V) và có thể ném Exception.
Future<V>: Đại diện cho kết quả của một tác vụ bất đồng bộ. Bạn có thể kiểm tra xem tác vụ đã hoàn thành chưa, lấy kết quả (get()) hoặc hủy tác vụ.
Ví dụ Code 6: Callable và Future

java

import java.util.concurrent.*;
class SumTask implements Callable<Long> {
private int start;
private int end;
public SumTask(int start, int end) {
this.start = start;
this.end = end;
}
@Override
public Long call() throws Exception {
long sum = 0;
for (int i = start; i <= end; i++) {
sum += i;
// Mô phỏng công việc nặng
Thread.sleep(10);
}
System.out.println(Thread.currentThread().getName() + " đã tính tổng từ " + start + " đến " + end);
return sum;
}
}
public class CallableFutureExample {
public static void main(String[] args) throws InterruptedException, ExecutionException {
ExecutorService executor = Executors.newFixedThreadPool(2);
// Tạo các tác vụ Callable
Callable<Long> task1 = new SumTask(1, 100);
Callable<Long> task2 = new SumTask(101, 200);
// Gửi tác vụ và nhận về Future
Future<Long> future1 = executor.submit(task1);
Future<Long> future2 = executor.submit(task2);
System.out.println("Main thread đang làm việc khác...");
// Mô phỏng công việc khác của main thread
Thread.sleep(500);
// Lấy kết quả từ Future (get() sẽ chặn cho đến khi kết quả có sẵn)
long result1 = future1.get();
long result2 = future2.get();
System.out.println("Kết quả Task 1: " + result1);
System.out.println("Kết quả Task 2: " + result2);
System.out.println("Tổng cộng: " + (result1 + result2));
executor.shutdown();
if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
executor.shutdownNow();
}
}
}
7. Các khuyến nghị và Thực tiễn tốt nhất
   Sử dụng Thread Pools: Hầu hết các ứng dụng nên sử dụng ExecutorService (Thread Pools) thay vì tự tạo Thread trực tiếp.
   Ưu tiên Runnable hơn Thread: Để duy trì tính linh hoạt trong thiết kế lớp của bạn.
   Sử dụng công cụ java.util.concurrent: Đối với các tác vụ đồng bộ hóa phức tạp, các lớp trong JUC mạnh mẽ và an toàn hơn so với synchronized thông thường.
   Xử lý ngắt quãng một cách lịch sự: Triển khai cơ chế kiểm tra isInterrupted() và xử lý InterruptedException để thread có thể dừng một cách gọn gàng.
   Tránh Deadlock: Cẩn thận với thứ tự lấy khóa.
   Minimize Scope of Synchronization: Đồng bộ hóa càng ít mã càng tốt để giảm tranh chấp và tăng hiệu suất.
   volatile cho visibility, synchronized cho atomicity: Hiểu rõ sự khác biệt giữa hai keyword này.
   Luôn đóng ExecutorService: Gọi shutdown() sau khi tất cả tác vụ được gửi để đảm bảo các thread được giải phóng.
   Hy vọng phần kiến thức chi tiết này giúp bạn hiểu rõ về Threads trong Java, cách sử dụng chúng, các vấn đề tiềm ẩn và cách giải quyết. Concurrency là một chủ đề phức tạp nhưng rất quan trọng cho các ứng dụng hiện đại! Nếu bạn có bất kỳ câu hỏi nào khác hoặc muốn đi sâu hơn vào một khía cạnh cụ thể, đừng ngần ngại hỏi nhé!
   giải thích full kiến thức và hco tôi bài tapajk thực hành nhiều