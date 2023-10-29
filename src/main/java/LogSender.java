package main.java;
	import java.io.OutputStream;
	import java.net.HttpURLConnection;
	import java.net.URL;

	public class LogSender {
	    public void sendLogsToServer(LogType log) {
	        try {
	            URL url = new URL("http://yourserver.com/tables/backuplog"); // Replace with your server URL
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestProperty("Authorization", "Bearer dGVtcG9yYXJ5IGFsc29fdGVtcG9yYXJ5");
	            
	            String logData = "{"
	                    + "\"id\": " + log.getLogNumber() + ","
	                    + "\"start\": \"" + log.getStartTime() + "\","
	                    + "\"end\": \"" + log.getEndTime() + "\","
	                    + "\"lifeCycle\": \"" + log.getLifeCycleStep() + "\","
	                    + "\"effortCategory\": \"" + log.getEffortCategory() + "\","
	                    + "\"deliverable\": \"" + log.getDeliverable() + "\""
	                    + "}";

	            OutputStream os = conn.getOutputStream();
	            os.write(logData.getBytes());
	            os.flush();

	            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
	                throw new RuntimeException("Failed : HTTP error code : "
	                        + conn.getResponseCode());
	            }

	            conn.disconnect();
	            System.out.println("Logs successfully sent to the server.");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
