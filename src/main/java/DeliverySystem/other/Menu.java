package DeliverySystem.other;

import DeliverySystem.DeliveryMain;
import DeliverySystem.exceptions.InvalidInputException;
import org.apache.log4j.Logger;

public enum Menu {
    SHIP_PACKAGE (1, "Ship package"),
    EDIT_PROFILE (2, "Edit your profile information"),
    CHANGE_DISCOUNT (3, "Apply for discount"),
    ADD_RECIPIENT (4, "Add recipient"),
    VIEW_PROFILES (5, "View recipient profiles"),
    OPERATING_CITIES (6, "View operating cities"),
    EXIT_PROGRAM (7, "Exit program");

    private static final Logger LOGGER = Logger.getLogger(DeliveryMain.class.getName());

    private int selectionNum;
    private String name;

    Menu(int selectionNum, String name) {
        this.selectionNum = selectionNum;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getSelectionNum() {
        return selectionNum;
    }

    public void printMenu() {
        int num = 1;
        LOGGER.info("Delivery App Menu:");
        for (Menu option : getClass().getEnumConstants()) {
            LOGGER.info(num + ". " + option.getName());
            num++;
        }
    }

    public Menu makeSelection(int selection) throws InvalidInputException {
        for (Menu option : getClass().getEnumConstants()) {
            if (selection == option.getSelectionNum())
                return option;
        }
        throw new InvalidInputException("Menu selection not in range.");
    }
}
