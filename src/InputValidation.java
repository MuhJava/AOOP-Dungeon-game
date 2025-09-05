import java.util.Scanner;

public class InputValidation {

    public static void clearBuffer(Scanner scanner) {
        if (scanner.hasNext()) {
            scanner.nextLine();
        }
    }

    public static boolean checkForAlphabetInput(String input) {
        if (input.matches("[a-zA-Z ]+")) {
            return true;
        } else {
            UI.displayMessage("Please provide alphabet input only");
            return false;
        }
    }

    public static boolean checkForInputLength(String input) {
        if (input.length() < 4 || input.length() > 30) {
            UI.displayMessage("Please use length between 4 and 30 characters");
            return false;
        } else {
            return true;
        }
    }
    public static boolean checkForNumericalInput(String input) {
        if (input.matches("[0-9]+")) {
            return true;
        } else {
            UI.displayMessage("Please provide numerical input only");
            return false;
        }
    }

    /**
     * Takes String array of menu options and validates an input against menu options
     * @param menuOptions String array of menu options
     * @param input Users input
     * @return True for valid
     */
    public static boolean checkForMenuOptions(String[] menuOptions, String input) {
        for (String option : menuOptions) {
            if (option.equals(input)) {
                return true;
            }
        }
        UI.displayMessage("Please select from the provided menu options");
        return false;
    }

    /**
     * Capitalises first letter of string
     * @param stringToCapitalise Capitalise first letter of
     * @return Capitalised String
     */
    public static String capitalise(String stringToCapitalise) {
        if (checkForAlphabetInput(stringToCapitalise) && !stringToCapitalise.isEmpty()) {
            return stringToCapitalise.substring(0, 1).toUpperCase() + stringToCapitalise.substring(1).toLowerCase();
        }
        return stringToCapitalise;
    }
}