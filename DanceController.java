//This class works out the dance settings such as speed, colours and movement

public class DanceController {

    // Speed is based on the octal value shown as digits.
   
    public static int calculateSpeed(String octal) {
        int octalValue = stringToInt(octal);

        if (octalValue < 50) {
            octalValue = octalValue + 50;
        }

        if (octalValue > 100) {
            octalValue = 100;
        }

        return octalValue;
    }

    // Red is simply the decimal value
     
    public static int calculateRed(int decimal) {
        return decimal;
    }

   
    public static int calculateGreen(int decimal) {
        return (decimal % 80) * 3;
    }

    //Blue is whichever is greater: red or green
     
    public static int calculateBlue(int red, int green) {
        return Math.max(red, green);
    }

    // This performms the robots movement based on the binary digits as 1 = move foward whereas 0 = spin
    
    public static void performRobotDance(SwiftBotController robot, String binary, int hexLength,
                                         int speed) {

        double forwardDuration = (hexLength == 1) ? 1.0 : 0.5;
        double spinDuration = 1.0;

        // Read the binary number from right to left
        for (int i = binary.length() - 1; i >= 0; i--) {
            char bit = binary.charAt(i);

            if (bit == '1') {
                System.out.println("FORWARD");
                robot.moveForward(speed, forwardDuration);
            } else {
                System.out.println("SPIN");
                robot.spin(speed, spinDuration);
            }
        }
    }

    // A Simple method to convert a string of digits into an integer without parseInt
     
    private static int stringToInt(String text) {
        int value = 0;

        for (int i = 0; i < text.length(); i++) {
            value = value * 10 + (text.charAt(i) - '0');
        }

        return value;
    }
}
