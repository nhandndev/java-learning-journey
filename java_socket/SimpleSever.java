import java.io.*;
import java.net.*;

public class SimpleSever {
    public static void main(String[] args) {
        try (ServerSocket severSocket = new ServerSocket(8080)){
            System.out.println("Waiting for connection...");
            Socket socket = severSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("System Connected"+ socket.getInetAddress());
            while (true){
                String inputLine = bufferedReader.readLine();
                bufferedWriter.write(inputLine);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                if("bye".equalsIgnoreCase(inputLine)){

                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}