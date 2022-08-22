package DeliverySystem.other;

import DeliverySystem.exceptions.InvalidInputException;

public final class ValidateInput {

    private ValidateInput() {}

    public static boolean validateYesNo(String input) throws InvalidInputException {
        if (input.charAt(0) == 'y' || input.charAt(0) == 'Y') {
            return true;
        } else if (input.charAt(0) == 'n' || input.charAt(0) == 'N') {
            return false;
        }
        else throw new InvalidInputException("Please enter a valid input (y/n)");
    }

    public static int validateZip(int input) throws InvalidInputException {
        if (input <= 99999 && input >= 10000) {
            return input;
        }
        else throw new InvalidInputException("Zipcode given not in valid range");
    }
}
