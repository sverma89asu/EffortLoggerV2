import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class LogOfLogs {

	public static void main(String[] args) throws ParseException {
		// Date date1 = null, date2 = null;
 
		String time1 = "00:00:00";
		String time2 = "00:00:00";
		//SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//		date1 = format.parse(time1);
//		date2 = format.parse(time2); Dont think i need these yet
		// https://stackoverflow.com/questions/4927856/how-can-i-calculate-a-time-difference-in-java

		/* Create a new log using variables:
			int logNumber, String date, String startTime, String endTime, 
			String lifeCycleStep, String effortCategory, String deliverable)
		*/ 
		// oldLog is our original log we will edit
		LogType oldLog = new LogType(1, "2023/10/28", "08:30:00", "08:45:00", "Producable Cycle", "100%", "Sunday");
		LogType newLog = new LogType(oldLog); // newLog to show the edited version 
				
		// Output options they can use
		System.out.println("Which change would you like to make?\nEnter the number:");
		System.out.print("0. Quit\n"
				+ "1. Date\n"
				+ "2. Start Time\n"
				+ "3. End Time\n"
				+ "4. Life Cycle Step\n"
				+ "5. Effort Category\n"
				+ "6. Deliverable\n"
				+ "7. Print Options again\n");
		
		
		boolean isValid = true; // check if the option given is valid (0-7)
		int line = -1; // begin line at -1 so it doesn't go to any switch statement
		Scanner scan = new Scanner(System.in);
		String answer = "";
		do {
			try {
				isValid = true;
				line = scan.nextInt(); 
				scan.nextLine(); // Get option from user
			}
			// Catch any options that aren't 0-7 
			catch(Exception e) { 
				scan.nextLine(); // Flush out scanner
				line = -1;
				isValid = false;
			}
				switch (line) {
					
				case 1: // https://stackoverflow.com/questions/18873014/parse-string-date-in-yyyy-mm-dd-format
					System.out.println("What do you want to change date to?\nPlease Use yyyy-MM-dd format: \n");
					answer = scan.nextLine();
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-DD");
					Date convertedCurrentDate = date.parse(answer);
					String newDate = date.format(convertedCurrentDate);
					newLog.setDate(newDate);
					break;
				case 2:
					System.out.println("What do you want to change Start Time to?\nPlease use hh:mm:ss format: \n");
					answer = scan.nextLine();
					SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
					Date convertedCurrentTime = time.parse(answer);
					String newTime = time.format(convertedCurrentTime);
					newLog.setStartTime(newTime);
					time1 = newTime;
					newLog.setDeltaTime(time1, time2);
					break;
				case 3:
					System.out.println("What do you want to change Stop Time to?\nPlease use hh:mm:ss format: \n");
					answer = scan.nextLine();
					time = new SimpleDateFormat("HH:mm:ss");
					convertedCurrentTime = time.parse(answer);
					newTime = time.format(convertedCurrentTime);
					newLog.setEndTime(newTime);
					time2 = newTime;
					newLog.setDeltaTime(time1, time2);
					break;
				case 4:
					System.out.println("What do you want to change Life Cycle Step to?\n");
					answer = scan.nextLine();
					newLog.setLifeCycleStep(answer);
					break;
				case 5:
					System.out.println("What do you want to change Effort Category to?\n");
					answer = scan.nextLine();
					newLog.setEffortCategory(answer);
					break;
				case 6:
					System.out.println("What do you want to change Deliverable to?\n");
					answer = scan.nextLine();
					newLog.setDeliverable(answer);
					break;
					
				case 7: 
					System.out.println("Which change would you like to make?\nEnter the number:");
					System.out.print("0. Quit\n"
							+ "1. Date\n"
							+ "2. Start Time\n"
							+ "3. End Time\n"
							+ "4. Life Cycle Step\n"
							+ "5. Effort Category\n"
							+ "6. Deliverable\n"
							+ "?. Print Options again\n");
					break;
				default:
					if (line != 0) {
						System.out.println("Unknown input");
					}
					break;
				}
				if (isValid == true) {
					System.out.println("Change made! Next change?");
				}
		} while (line != 0);
		scan.close();
		
		System.out.println("\nOLD: \n" + oldLog.toString());
		System.out.println("NEW: \n" + newLog.toString());
		
	}
}
