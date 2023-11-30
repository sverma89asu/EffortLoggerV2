package tu14.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateInput {
    public static boolean dateValidate(String userInput) {
        if (userInput.isEmpty()) {
            return false;
        }

        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            formatDate.setLenient(false);
            Date date = formatDate.parse(userInput);

            // Check if the inputted date is in the future
            Date presentDate = new Date();
            return !date.after(presentDate);
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean timeValidate(String userInput) {
        if (userInput.isEmpty()) {
            return false;
        }

        try {
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            formatTime.setLenient(false);
            Date time = formatTime.parse(userInput);

            // Check if the inputted time is in the future
            Date presentTime = new Date();
            return !time.after(presentTime);
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean lifeCycleValidate(String userInput) {
        return !userInput.isEmpty();
    }

    public static boolean effortCategoryValidate(String userInput) {
        return !userInput.isEmpty();
    }

    public static boolean deliverableValidate(String userInput) {
        return !userInput.isEmpty();
    }

}
