import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 8080);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            while (true) {
                String input = scanner.nextLine();

                bufferedWriter.write(input);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                String response = bufferedReader.readLine();
                System.out.println(response);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}