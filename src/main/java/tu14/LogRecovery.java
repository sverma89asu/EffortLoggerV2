package tu14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject; //Include org.json into Lib

public class LogRecovery {
    public LogType retrieveLogFromServer() {
        try {
            URL url = new URL("http://yourserver.com/tables/backuplog"); // Replace with your server URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();

            // Convert the retrieved JSON data to a tu14.LogType object
            LogType retrievedLog = parseLogType(response.toString());
            return retrievedLog;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private LogType parseLogType(String json) {
        LogType log = new LogType();
        try {
            JSONObject jsonObject = new JSONObject(json);
            log.setLogNumber(jsonObject.getInt("id"));
            log.setDate(jsonObject.getString("start"));
            log.setStartTime(jsonObject.getString("start"));
            log.setEndTime(jsonObject.getString("end"));
            log.setLifeCycleStep(jsonObject.getString("lifeCycle"));
            log.setEffortCategory(jsonObject.getString("effortCategory"));
            log.setDeliverable(jsonObject.getString("deliverable"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }
}
