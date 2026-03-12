package vitalsync;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClientSimulator {

    public static void main(String[] args) {
        try {
            Random rand = new Random();

            // نقدر نرسل بيانات لأكثر من غرفة
            String[] rooms = {"ROOM_101", "ROOM_102", "ROOM_103"};

            while (true) {
                for (String room : rooms) {
                    int heartRate = rand.nextInt(80) + 40; // 40-120
                    int oxygen = rand.nextInt(20) + 80;    // 80-100

                    String data = room + "," + heartRate + "," + oxygen;

                    try (Socket socket = new Socket("localhost", 5000);
                         PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                        out.println(data);
                        System.out.println("Sent: " + data);

                    } catch (Exception e) {
                        System.out.println("Failed to send: " + data);
                    }
                }

                Thread.sleep(2000); // كل ثانيتين
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
