public class DanceController {

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

    public static int calculateRed(int decimal) {
        return decimal;
    }

    public static int calculateGreen(int decimal) {
        return (decimal % 80) * 3;
    }

    public static int calculateBlue(int red, int green) {
        return Math.max(red, green);
    }

    public static void performRobotDance(SwiftBotController robot, String binary, int hexLength,
                                         int speed, int red, int green, int blue) {

        double forwardDuration = (hexLength == 1) ? 1.0 : 0.5;
        double spinDuration = 1.0;

        robot.setUnderlights(red, green, blue);

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

    private static int stringToInt(String text) {
        int value = 0;

        for (int i = 0; i < text.length(); i++) {
            value = value * 10 + (text.charAt(i) - '0');
        }

        return value;
    }
}
