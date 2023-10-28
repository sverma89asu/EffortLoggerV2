import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

public class LogType {
	private int logNumber;
	private String date;
	private String startTime;
	private String endTime;
	private int deltaTime = 0;
	private String lifeCycleStep;
	private String effortCategory;
	private String deliverable;
	
	public LogType(int logNumber, String date, String startTime, String endTime, String lifeCycleStep, String effortCategory, String deliverable) {
		this.logNumber = logNumber;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lifeCycleStep = lifeCycleStep;
		this.effortCategory = effortCategory;
		this.deliverable = deliverable;
	}
	
	public LogType() {
		this.logNumber = 0;
		this.date = null;
		this.startTime = null;
		this.endTime = null;
		this.deltaTime = 0;
		this.lifeCycleStep = null;
		this.effortCategory = null;
		this.deliverable = null;
	}
	
	public LogType(LogType c) {
		this.logNumber = c.logNumber;
		this.date = c.date;
		this.startTime = c.startTime;
		this.endTime = c.endTime;
		this.lifeCycleStep = c.lifeCycleStep;
		this.effortCategory = c.effortCategory;
		this.deliverable = c.deliverable;
	}
	
	public int getLogNumbeR() {
		return logNumber;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public int getDeltaTime(Date date1, Date date2) {
		return deltaTime;
	}
	
	public String getLifeCycleStep() {
		return lifeCycleStep;
	}
	
	public String getEffortCategory() {
		return effortCategory;
	}
	
	public String getDeliverable() {
		return deliverable;
	}
	
	
	// Setters
	public void setLogNumber(int number) {
		this.logNumber = number;
	}
	

	public void setDate(String date) {
		this.date = date;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public void setEndTime(String newTime) {
		this.endTime = newTime;
	}
	
	public void setDeltaTime(String time1, String time2) {
		System.out.println(time1 + " " + time2);
		long minutes = Duration.between(LocalTime.parse(time1), LocalTime.parse(time2)).toMinutes();
		System.out.println(minutes);
		this.deltaTime = (int)(-minutes);
	}
	
	public void setLifeCycleStep(String lifeCycleStep) {
		this.lifeCycleStep = lifeCycleStep;
	}
	
	public void setEffortCategory(String effortCategory) {
		this.effortCategory = effortCategory;
	}
	
	public void setDeliverable(String deliverable) {
		this.deliverable = deliverable;
	}

	public String toString() {
		return "Log Number: "
				+ logNumber + "\nDate: "
				+ date + "\nStart Time: "
				+ startTime + "\nStop Time: "
				+ endTime + "\nDelta Time: "
				+ deltaTime + "\nLife Cycle Step: "
				+ lifeCycleStep + "\nEffort Category: "
				+ effortCategory + "\nDeliverable: "
				+ deliverable + "\n";
	}
	
	
	
}
