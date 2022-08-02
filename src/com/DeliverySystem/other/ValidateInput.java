package com.DeliverySystem.other;

import com.DeliverySystem.exceptions.InvalidInputException;

public final class ValidateInput {

    private ValidateInput() {}

    public static boolean validateYesNo(String input) throws InvalidInputException {
        if (input.charAt(0) == 'y' || input.charAt(0) == 'Y') {
            return true;
        } else if (input.charAt(0) == 'n' || input.charAt(0) == 'N') {
            return false;
        }
        else throw new InvalidInputException("Y or N not given");
    }
}
