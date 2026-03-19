import swiftbot.*;

import java.awt.image.BufferedImage;

// This class contains all the SwiftBot API code Keeping it separate which  makes the rest of the program easier to read and explain.
 

public class SwiftBotController {

    private SwiftBotAPI swiftBot;
    private volatile Character buttonChoice;

    public SwiftBotController() {
        try {
            swiftBot = SwiftBotAPI.INSTANCE;
        } catch (Exception e) {
            System.out.println("SwiftBot could not be started.");
            System.out.println("Please check that the robot is connected properly.");
            System.exit(5);
        }
    }

    // This will  Scan the QR code using the robot camera. It retries a few times before giving up.
     
    public String scanQRCode() {
        int attempts = 0;

        while (attempts <= 10) {
            System.out.println(" Now Scanning for QR code...");

            try {
                BufferedImage img = swiftBot.getQRImage();
                String decodedMessage = swiftBot.decodeQRImage(img);

                if (decodedMessage != null && !decodedMessage.isEmpty()) {
                    System.out.println("The QR code found successfully.");
                    return decodedMessage;
                }

                attempts++;

            } catch (Exception e) {
                System.out.println("Unable to read QR code... trying again.");
                attempts++;
            }
        }

        System.out.println("No QR code could be read after several attempts.");
        return "";
    }

    public void setUnderlights(int red, int green, int blue) {
        try {
            int[] rgb = { red, green, blue };
            swiftBot.fillUnderlights(rgb);
        } catch (Exception e) {
            System.out.println("Error setting underlights: " + e.getMessage());
        }
    }

    // This Moves the robot forward by making both wheels move at the same speed
     
    public void moveForward(int speed, double seconds) {
        try {
            int durationMs = (int) (seconds * 1000);
            swiftBot.move(speed, speed, durationMs);
        } catch (Exception e) {
            System.out.println("Error moving forward: " + e.getMessage());
        }
    }

    //this will spin the swiftbot in place with the wheels moving in opposite directions
    public void spin(int speed, double seconds) {
        try {
            int durationMs = (int) (seconds * 1000);
            swiftBot.move(speed, -speed, durationMs);
        } catch (Exception e) {
            System.out.println("Error spinning: " + e.getMessage());
        }
    }

    /*
     * Turns off all underlights
     */
    public void turnOffUnderlights() {
        try {
            swiftBot.disableUnderlights();
        } catch (Exception e) {
            System.out.println("Error turning off underlights: " + e.getMessage());
        }
    }
// This waits fior either x or y input from the Swfitbot
    public char waitForButton() {
        buttonChoice = null;

        try {
            swiftBot.enableButton(Button.Y, () -> {
                System.out.println("Y button pressed.");
                buttonChoice = 'Y';
            });

            swiftBot.enableButton(Button.X, () -> {
                System.out.println("X button pressed.");
                buttonChoice = 'X';
            });

            while (buttonChoice == null) {
                Thread.sleep(100);
            }

            swiftBot.disableButton(Button.Y);
            swiftBot.disableButton(Button.X);
            swiftBot.disableAllButtons();

            return buttonChoice;

        } catch (Exception e) {
            System.out.println("Error reading button input: " + e.getMessage());
            return 'X';
        }
    }
}
