class Counter {
   private int count;
   public void increment(){
       count++;
   }
   public synchronized void synchronizedIncrement(){
       count++;
    }
   public int getCount(){
       return count;
   }
}
public class Main
{
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread[] t1 = new Thread[100];
        for(int i = 0 ; i < t1.length ; i++){
            Counter counter1 = counter;
            t1[i] = new Thread(() ->{
                for(int j = 0 ; j < 1000 ; j++){
                    counter1.increment();
                }
            });
            t1[i].start();
        }
        for (Thread t : t1){
            t.join();
        }
        System.out.println(counter.getCount());
        counter = new Counter();
        Thread[] t2 = new Thread[100];
        for(int i = 0 ; i < t2.length ; i++){
            Counter counter1 = counter;
            t2[i] = new Thread(() ->{
                for(int j = 0 ; j < 1000 ; j++){
                    counter1.synchronizedIncrement();
                }
            });
            t2[i].start();
            }
        for(Thread t : t2) {
            t.join();
        }
            System.out.println(counter.getCount());
        }
    }
