package vitalsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientHandler implements Runnable {

    private Socket client;
    private Dashboard dashboard;

    public ClientHandler(Socket client, Dashboard dashboard) {
        this.client = client;
        this.dashboard = dashboard;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()))) {

            String data;
            while ((data = in.readLine()) != null) {
                System.out.println("Received: " + data);

                // عرض البيانات على الـ Dashboard
                String finalData = data;
                javax.swing.SwingUtilities.invokeLater(() -> {
                    dashboard.addPatientData(finalData);
                });

                // تقسيم البيانات: ROOM_101,85,98
                String[] parts = data.split(",");
                String room = parts[0];
                int heartRate = Integer.parseInt(parts[1]);
                int oxygen = Integer.parseInt(parts[2]);

                // حفظ البيانات بالـ MySQL
                try (Connection conn = DBConnection.getConnection()) {
                    // الحصول على patient_id حسب room_number
                    PreparedStatement ps1 = conn.prepareStatement(
                            "SELECT id FROM patients WHERE room_number = ?");
                    ps1.setString(1, room);
                    ResultSet rs = ps1.executeQuery();
                    int patientId = -1;
                    if (rs.next()) {
                        patientId = rs.getInt("id");
                    }

                    if (patientId != -1) {
                        PreparedStatement ps2 = conn.prepareStatement(
                                "INSERT INTO vital_logs (patient_id, heart_rate, oxygen_level, event_type, note) VALUES (?, ?, ?, ?, ?)"
                        );
                        ps2.setInt(1, patientId);
                        ps2.setInt(2, heartRate);
                        ps2.setInt(3, oxygen);
                        ps2.setString(4, "UPDATE"); // مثال نوع الحدث
                        ps2.setString(5, "Vital updated");
                        ps2.executeUpdate();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }
}
