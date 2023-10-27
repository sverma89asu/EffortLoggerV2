import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class LogOfLogs {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat SDFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Date date1 = null, date2 = null;
		boolean isValid = true;
//		System.out.println("The original Date: " + cal.getTime());
//		String curr_date = SDFormat.format(cal.getTime());
//    	System.out.println("Formatted Date: " + curr_date);
 
		String time1 = "00:00:00";
		String time2 = "00:00:00";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		date1 = format.parse(time1);
		date2 = format.parse(time2);
		// https://stackoverflow.com/questions/4927856/how-can-i-calculate-a-time-difference-in-java
//		long difference = date2.getTime() - date1.getTime(); 
//		long diffMinutes = difference / 60000;
		//System.out.println(diffMinutes);
		
		LogType newLog = new LogType();
		newLog.setLogNumber(1);
		Scanner scan = new Scanner(System.in);
		String answer = "";
		LogType oldLog = new LogType(newLog);
		oldLog.setLogNumber(1);
		int line = -1;
		System.out.println("Which change would you like to make?\nEnter the number:");
		System.out.print("0. Quit\n"
				+ "1. Date\n"
				+ "2. Start Time\n"
				+ "3. End Time\n"
				+ "4. Life Cycle Step\n"
				+ "5. Effort Category\n"
				+ "6. Deliverable\n"
				+ "7. Print Options again\n");
		
		do {
			try {
				//System.out.println(newLog.toString());
				isValid = true;
				line = scan.nextInt();
				scan.nextLine();
			}
			catch(Exception e) {
				System.out.println("Invalid");
				scan.nextLine();
				line = -1;
				isValid = false;
			}
				switch (line) {
					
				case 1:
					System.out.println("What do you want to change date to?\nPlease Use yyyy-MM-dd format: \n");
					answer = scan.nextLine();
					// https://stackoverflow.com/questions/18873014/parse-string-date-in-yyyy-mm-dd-format
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
					//date1 = format.parse(time1);
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
					//date2 = format.parse(time2);
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


/*
this.logNumber = logNumber;
this.date = date;
this.startTime = startTime;
this.endTime = endTime;
this.lifeCycleStep = lifeCycleStep;
this.effortCategory = effortCategory;
this.deliverable = deliverable;*/