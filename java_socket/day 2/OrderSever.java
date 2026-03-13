import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class OrderSever {
    public static void main(String[] args) {



        // Goi y:
        // com -> 20000
        // pho -> 30000
        // tra_sua -> 25000
        // banh_mi -> 15000

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server dang chay...");

            Socket socket = serverSocket.accept();
            System.out.println("Client da ket noi!");

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String request = bufferedReader.readLine();
                if(request == null){
                    break;
                }
                if("bye".equals(request)){
                    bufferedWriter.write("bye");
                    bufferedWriter.flush();
                    bufferedWriter.newLine();
                    break;
                }
                // TODO 5: goi ham processOrder(...)
                String result = processOrder(request);

                // TODO 6: gui result ve client
                bufferedWriter.write(result + " Tong Gia Tien la " +totalprice);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
static int totalprice = 0;
    public static String processOrder(String product) {
        int price = 0;
        if (product.equals("com")) {
            price = 20000;
            totalprice += price;

        } else if (product.equals("pho")) {
            price = 30000;
            totalprice += price;
        } else if (product.equals("tra_sua")) {
            price = 25000;
            totalprice += price;
        } else if (product.equals("banh_mi")) {
            price = 15000;
            totalprice += price;
        } else {
            return "Product not found";
        }

        return "tien cua vat pham nay la "+price;
    }
}