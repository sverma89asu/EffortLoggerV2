package tu14.logs;

import tu14.input.ValidateInput;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class LogOfLogs {

	public static void main(String[] args) 	{
		// Date date1 = null, date2 = null;
		Scanner scan;
		boolean isValid;
		ArrayList<LogType> LogsArray = new ArrayList<>();
		ArrayList<LogType> LogsArrayEdited = new ArrayList<>();
		String time1;
		String time2;
		
		/* Create a new log using variables:
			int logNumber, String date, String startTime, String endTime, 
			String lifeCycleStep, String effortCategory, String deliverable)
		*/ 
		// oldLog is our original log we will edit
		LogType oldLog = new LogType(1, "2023/10/28", "18:15:56", "19:59:00", "Producable Cycle", "100%", "Sunday");
		LogType oldLog2 = new LogType(2, "2023/10/28", "18:15:56", "19:59:00", "Producable Cycle", "100%", "Sunday");
		LogsArray.add(oldLog); LogsArray.add(oldLog2);
		LogType newLog = new LogType(oldLog); // newLog to show the edited version 
		LogType newLog2 = new LogType(oldLog2);


		scan = new Scanner(System.in);
		int logNumber = -1;
		do {
			System.out.println("Which Log Number do you want to change? Choose 1-" + LogsArray.size());
			try {
				logNumber = scan.nextInt() - 1;
				scan.nextLine();
			}
			catch(Exception e) {
				scan.nextLine();
			}
		} while (logNumber < 0 || logNumber > LogsArray.size());

		int line; // begin line at -1 so it doesn't go to any switch statement
		String answer;
		do {
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
					while (!ValidateInput.dateValidate(answer)) {
						System.out.println("Invalid format for date, try again!");
						answer = scan.nextLine();
					}
					String newDate = answer;
					LogsArray.get(logNumber).setDate(newDate);
					break;
				case 2:
					System.out.println("What do you want to change Start Time to?\nPlease use hh:mm:ss format: \n");
					answer = scan.nextLine();
					while (!ValidateInput.timeValidate(answer)) {
						System.out.println("Invalid format for time, try again!");
						answer = scan.nextLine();
					}
					String newStartTime = answer;
					LogsArray.get(logNumber).setStartTime(newStartTime);
					time1 = newStartTime;
					LogsArray.get(logNumber).setDeltaTime(time1, newLog.getEndTime());
					break;
				case 3:
					System.out.println("What do you want to change Stop Time to?\nPlease use hh:mm:ss format: \n");
					answer = scan.nextLine();
					while (!ValidateInput.timeValidate(answer)) {
						System.out.println("Invalid format for time, try again!");
						answer = scan.nextLine();
					}
					String newEndTime = answer;
					LogsArray.get(logNumber).setEndTime(newEndTime);
					time2 = newEndTime;
					LogsArray.get(logNumber).setDeltaTime(newLog.getStartTime(), time2);
					break;
				case 4:
					System.out.println("What do you want to change Life Cycle Step to?\n");
					answer = scan.nextLine();
					while (!ValidateInput.lifeCycleValidate(answer)) {
						System.out.println("Invalid Life Cycle Step, try again!");
						answer = scan.nextLine();
					}
					LogsArray.get(logNumber).setLifeCycleStep(answer);
					break;
				case 5:
					System.out.println("What do you want to change Effort Category to?\n");
					answer = scan.nextLine();
					while (!ValidateInput.effortCategoryValidate(answer)) {
						System.out.println("Invalid Effort Category, try again!");
						answer = scan.nextLine();
					}
					LogsArray.get(logNumber).setEffortCategory(answer);
					break;
				case 6:
					System.out.println("What do you want to change Deliverable to?\n");
					answer = scan.nextLine();
					while (!ValidateInput.deliverableValidate(answer)) {
						System.out.println("Invalid Deliverable, try again!");
						answer = scan.nextLine();
					}
					LogsArray.get(logNumber).setDeliverable(answer);
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
				if (isValid) {
					System.out.println("Change made! Next change?");
				}
		} while (line != 0);
		LogsArrayEdited.add(LogsArray.get(logNumber));
		scan.close();
		
		System.out.println("\nOLD 1: \n" + oldLog);
		System.out.println("NEW 1: \n" + newLog);
		
		System.out.println("\nOLD 2: \n" + oldLog2);
		System.out.println("NEW 2: \n" + newLog2);

		for (LogType logType : LogsArrayEdited) {
			System.out.println("List of edited logs:");
			System.out.println(logType.toString());
		}
		
	}
}
