package vitalsync;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    private JTextArea displayArea;

    public Dashboard() {
        setTitle("Doctor Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addPatientData(String data) {
        try {
            // تقسيم البيانات: ROOM,heartRate,oxygen
            String[] parts = data.split(",");
            int heartRate = Integer.parseInt(parts[1]);
            int oxygen = Integer.parseInt(parts[2]);

            // تحديد اللون للحالة الحرجة
            if (heartRate < 50 || heartRate > 120 || oxygen < 90) {
                displayArea.setForeground(Color.RED);
            } else {
                displayArea.setForeground(Color.BLACK);
            }

            displayArea.append(data + "\n");
        } catch (Exception e) {
            displayArea.append("Invalid data received: " + data + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });
    }
}
