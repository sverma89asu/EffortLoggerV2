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

            // Check if inputted date is in the future
            Date presentDate = new Date();
            if (date.after(presentDate)) {
                return false;
            }

            return true;
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

            // Check if inputted time is in the future
            Date presentTime = new Date();
            if (time.after(presentTime)) {
                return false;
            }

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean lifeCycleValidate(String userInput) {
        if (userInput.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean effortCategoryValidate(String userInput) {
        if (userInput.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean deliverableValidate(String userInput) {
        if (userInput.isEmpty()) {
            return false;
        }
        return true;
    }

}