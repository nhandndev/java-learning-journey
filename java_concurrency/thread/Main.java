import java.lang.Thread;
class Myrunable implements Runnable{
    private String name;
    public Myrunable(String name){
        this.name=name;
    }
    @Override
    public void run(){
        for (int i = 0; i<10; i++){
            System.out.println(Thread.currentThread().getName()+":"+i);
            try{
                Thread.sleep(300);
            }catch(InterruptedException e){
                System.out.println(Thread.currentThread().getName()+":"+i +" Got interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}
public class Main{
    public static void main(String[] args){
        Myrunable Myrunable1 = new Myrunable("Myrunable1");
        Myrunable Myrunable2 = new Myrunable("Myrunable2");
        Thread t1 = new Thread(Myrunable1);
        Thread t2 = new Thread(Myrunable2);
        t1.start();
        t2.start();
    }
}