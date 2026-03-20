import java.util.ArrayList;
import java.util.List;

// This program scans a QR code containing up to 5 hexadecimal values
 //Each valid value is converted manually into octal, decimal and binary
 //These values are then used to control the SwiftBot's speed, lights and movement
 

public class MainProgram {

    public static void main(String[] args) {

        // This stores every valid hex value entered during the full session
        List<String> allValidHexValues = new ArrayList<>();

        // This object handles the real robot features
        SwiftBotController robot = new SwiftBotController();

        printHeader();

        boolean running = true;

        while (running) {

            System.out.println("\nPlease scan a QR code.");
            System.out.println("You can enter up to 5 hexadecimal values separated by &.");

            // This will Scan the QR code using the SwiftBot camera
            String qrInput = robot.scanQRCode();

            if (qrInput == null || qrInput.trim().isEmpty()) {
                System.out.println("No QR code was detected. Please try again.");
                continue;
            }

            System.out.println("QR text detected: " + qrInput);

            // This will Split the QR text in case multiple values were entered
            String[] qrValues = qrInput.split("&");

            if (qrValues.length > 5) {
                System.out.println("Only the first 5 values will be processed.");
            }

            List<String> acceptedHexValues = new ArrayList<>();
            List<String> rejectedHexValues = new ArrayList<>();

            // This will Only allow up to 5 values from one QR code
            int limit = Math.min(qrValues.length, 5);

            for (int i = 0; i < limit; i++) {
                String value = qrValues[i].trim().toUpperCase();

                // This will Check whether the value is a valid hexadecimal number
                if (HexValidator.isValidHex(value)) {
                    acceptedHexValues.add(value);
                    allValidHexValues.add(value);
                } else {
                    rejectedHexValues.add(value);
                }
            }

            // This will Print invalid values before the dance starts
            if (!rejectedHexValues.isEmpty()) {
                System.out.println("Ignored invalid value(s): " + String.join(", ", rejectedHexValues));
            }

            if (acceptedHexValues.isEmpty()) {
                System.out.println("No valid hexadecimal values were found in this QR code.");
            } else {

                // This will Run the dance routine for each valid value in sequence
                for (String hex : acceptedHexValues) {
                    runDanceForHex(hex, robot);
                }
            }

            // This will Ask whether the user wants to continue
            System.out.println("\nPress Y on the robot to scan another QR code.");
            System.out.println("Press X on the robot to exit.");

            char choice = robot.waitForButton();

            if (choice == 'X') {
                running = false;
            }
        }

        //This will Save all valid values in ascending order before ending
        LogFileManager.writeSortedHexLog(allValidHexValues);

        System.out.println("Program finished. Goodbye.");
    }

    //Thhis  Handles one full dance routine for one hexadecimal value
     
    private static void runDanceForHex(String hex, SwiftBotController robot) {

        // Convert the hex value manually
        int decimal = HexConverter.hexToDecimal(hex);
        String binary = HexConverter.decimalToBinary(decimal);
        String octal = HexConverter.decimalToOctal(decimal);

        //this part works out the speed and led values 
        int speed = DanceController.calculateSpeed(octal);
        int red = DanceController.calculateRed(decimal);
        int green = DanceController.calculateGreen(decimal);
        int blue = DanceController.calculateBlue(red, green);

        printDanceInformation(hex, decimal, binary, octal, speed, red, green, blue);

        // This is used for the underlights to set it before moving 
        robot.setUnderlights(red, green, blue);

        System.out.println("The swiftbot will now begin the dance.");

        // This part will perfrom the movement sequence for the program
        DanceController.performRobotDance(robot, binary, hex.length(), speed);

        // Turn the lights off after the routine is complete
        robot.turnOffUnderlights();

        System.out.println("The Dance is now complete. The underlights have been turned off.");
    }

    // this part will print the title of my program
private static void printHeader() {
        System.out.println("==========================================");
        System.out.println("        My swiftbot Task 9 Program        ");
        System.out.println("==========================================");
    }

    // This Displays all the values needed before the robot starts moving
    private static void printDanceInformation(String hex, int decimal, String binary, String octal,
                                              int speed, int red, int green, int blue) {

        System.out.println("\n========== DANCE SETTINGS ==========");
        System.out.println("Hexadecimal: " + hex);
        System.out.println("Octal: " + octal);
        System.out.println("Decimal: " + decimal);
        System.out.println("Binary: " + binary);
        System.out.println("Speed: " + speed);
        System.out.println("LED Colour: (red " + red + ", green " + green + ", blue " + blue + ")");
        System.out.println("Total Moves: " + binary.length()); // small extra feature
        System.out.println("====================================");
    }
}
