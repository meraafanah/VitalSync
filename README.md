# VitalSync - Real-Time Hospital Patient Monitoring System 🏥

VitalSync is a robust, multi-threaded Java application designed for real-time monitoring of patient vital signs within a hospital environment. This system simulates bedside monitors and provides a central dashboard for medical staff to track patient health and receive immediate alerts for critical conditions.

## 🚀 Key Features
- **Real-Time Data Streaming:** Uses Socket Programming to transmit heart rate and oxygen levels from patient rooms to a central server.
- **Centralized Dashboard:** A graphical user interface (Swing GUI) that displays live data for multiple patients simultaneously.
- **Smart Alerting System:** Automated visual alerts (color-coded in Red) when vital signs cross critical thresholds (Heart Rate < 50 or > 120, Oxygen < 90%).
- **Multi-threaded Server:** Efficiently handles multiple concurrent patient connections using Java's `ExecutorService`.
- **Data Persistence:** All vital signs and critical events are securely stored in a MySQL database for historical analysis.
- **Thread Safety:** Implements safe UI updates using `SwingUtilities.invokeLater` to prevent concurrency issues.

## 🛠️ Technologies & Tools
- **Language:** Java (JDK 17+)
- **Architecture:** Client-Server (Distributed System)
- **Networking:** TCP Sockets
- **Concurrency:** Multi-threading & Thread Pools
- **Database:** MySQL & JDBC
- **UI:** Java Swing

## 📁 Project Structure
- `Server.java`: The central hub that manages connections and routes data.
- `ClientHandler.java`: Manages individual client sessions on the server side.
- `ClientSimulator.java`: Simulates the bedside medical device generating random vital signs.
- `Dashboard.java`: The doctor's interface for real-time monitoring.
- `DBConnection.java`: Handles secure communication with the MySQL database.

## ⚙️ Setup and Installation

1. **Database Setup:**
   Create a database named `vitalsync_db` and run the following SQL to create the tables:
   ```sql
   CREATE TABLE patients (
       patient_id INT PRIMARY KEY AUTO_INCREMENT,
       full_name VARCHAR(100),
       room_number VARCHAR(10),
       status VARCHAR(20)
   );

   CREATE TABLE vital_logs (
       log_id INT PRIMARY KEY AUTO_INCREMENT,
       patient_id INT,
       heart_rate INT,
       oxygen_level INT,
       recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
   );
