public class Main {

    private static int counter = 0;

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        };
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   
        System.out.println("Giá trị cuối cùng của counter là: " + counter); // Mong đợi: 2000
    }
}