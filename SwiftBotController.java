import swiftbot.*;

import java.awt.image.BufferedImage;

public class SwiftBotController {

    private SwiftBotAPI swiftBot;
    private volatile Boolean continueChoice;

    public SwiftBotController() {
        try {
            swiftBot = SwiftBotAPI.INSTANCE;
        } catch (Exception e) {
            System.out.println("I2C disabled or SwiftBot unavailable.");
            System.exit(5);
        }
    }

    public String scanQRCode() {
        int attempts = 0;

        while (attempts <= 10) {
            System.out.println("Scanning for QR code...");

            try {
                BufferedImage img = swiftBot.getQRImage();
                String decodedMessage = swiftBot.decodeQRImage(img);

                if (decodedMessage != null && !decodedMessage.isEmpty()) {
                    System.out.println("SUCCESS: QR code found");
                    return decodedMessage;
                }

                attempts++;
            } catch (Exception e) {
                System.out.println("Unable to find QR code... trying again...");
                attempts++;
            }
        }

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

    public void moveForward(int speed, double seconds) {
        try {
            int durationMs = (int) (seconds * 1000);
            swiftBot.move(speed, speed, durationMs);
            Thread.sleep(durationMs);
        } catch (Exception e) {
            System.out.println("Error moving forward: " + e.getMessage());
        }
    }

    public void spin(int speed, double seconds) {
        try {
            int durationMs = (int) (seconds * 1000);

            // spin on the spot
            swiftBot.move(speed, -speed, durationMs);
            Thread.sleep(durationMs);
        } catch (Exception e) {
            System.out.println("Error spinning: " + e.getMessage());
        }
    }

    public void turnOffUnderlights() {
        try {
            swiftBot.disableUnderlights();
        } catch (Exception e) {
            System.out.println("Error turning off underlights: " + e.getMessage());
        }
    }

    public boolean waitForYesOrNo() {
        continueChoice = null;

        try {
            swiftBot.enableButton(Button.Y, () -> {
                System.out.println("Y button pressed.");
                continueChoice = true;
            });

            swiftBot.enableButton(Button.X, () -> {
                System.out.println("X button pressed.");
                continueChoice = false;
            });

            while (continueChoice == null) {
                Thread.sleep(100);
            }

            swiftBot.disableButton(Button.Y);
            swiftBot.disableButton(Button.X);
            swiftBot.disableAllButtons();

            return continueChoice;

        } catch (Exception e) {
            System.out.println("Error reading button input: " + e.getMessage());
            return false;
        }
    }
}
