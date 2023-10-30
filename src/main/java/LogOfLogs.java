import java.util.ArrayList;
import java.util.Scanner;

public class LogOfLogs {
	public static void EditLog(ArrayList<LogType> CurrentLogs, ArrayList<LogType> OldLogs) {
		Scanner scan;
		boolean isValid;
		String time1 = "00:00:00";
		String time2 = "00:00:00";
		
		isValid = true;
		scan = new Scanner(System.in);
		int logNumber = -1;
		do {
			System.out.println("Which Log Number do you want to change? Choose 1-" + CurrentLogs.size());
			try {
				isValid = true;
				logNumber = scan.nextInt() - 1;
				scan.nextLine();
			}
			catch(Exception e) {
				scan.nextLine();
				isValid = false;
			}
		} while (logNumber < 0 || logNumber > CurrentLogs.size());
		
		isValid = true; // check if the option given is valid (0-7)
		int line = -1; // begin line at -1 so it doesn't go to any switch statement
		String answer = "";
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
					CurrentLogs.get(logNumber).setDate(newDate);
					break;
				case 2:
					System.out.println("What do you want to change Start Time to?\nPlease use hh:mm:ss format: \n");
					answer = scan.nextLine();
					while (!ValidateInput.timeValidate(answer)) {
						System.out.println("Invalid format for time, try again!");
						answer = scan.nextLine();
					}
					String newStartTime = answer;
					CurrentLogs.get(logNumber).setStartTime(newStartTime);
					time1 = newStartTime;
					CurrentLogs.get(logNumber).setDeltaTime(time1, CurrentLogs.get(logNumber).getEndTime());
					break;
				case 3:
					System.out.println("What do you want to change Stop Time to?\nPlease use hh:mm:ss format: \n");
					answer = scan.nextLine();
					while (!ValidateInput.timeValidate(answer)) {
						System.out.println("Invalid format for time, try again!");
						answer = scan.nextLine();
					}
					String newEndTime = answer;
					CurrentLogs.get(logNumber).setEndTime(newEndTime);
					time2 = newEndTime;
					CurrentLogs.get(logNumber).setDeltaTime(CurrentLogs.get(logNumber).getStartTime(), time2);
					break;
				case 4:
					System.out.println("What do you want to change Life Cycle Step to?\n");
					answer = scan.nextLine();
					while (!ValidateInput.lifeCycleValidate(answer)) {
						System.out.println("Invalid Life Cycle Step, try again!");
						answer = scan.nextLine();
					}
					CurrentLogs.get(logNumber).setLifeCycleStep(answer);
					break;
				case 5:
					System.out.println("What do you want to change Effort Category to?\n");
					answer = scan.nextLine();
					while (!ValidateInput.effortCategoryValidate(answer)) {
						System.out.println("Invalid Effort Category, try again!");
						answer = scan.nextLine();
					}
					CurrentLogs.get(logNumber).setEffortCategory(answer);
					break;
				case 6:
					System.out.println("What do you want to change Deliverable to?\n");
					answer = scan.nextLine();
					while (!ValidateInput.deliverableValidate(answer)) {
						System.out.println("Invalid Deliverable, try again!");
						answer = scan.nextLine();
					}
					CurrentLogs.get(logNumber).setDeliverable(answer);
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
				if (isValid == true && line != 0) {
					System.out.println("Change made! Next change?");
				}
		} while (line != 0);
		OldLogs.add(CurrentLogs.get(logNumber));
		scan.close();
	}
}
