import java.util.function.Function;
public class Main{
    static int process(Function<Integer, Integer> f , int k){
        return f.apply(k);
    }
    public static void main(String[] args){
        int result = process(n -> n*2 , 3);
        System.out.println(result);
    }
}
