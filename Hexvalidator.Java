// This class is responsible for checking whether a value is a valid hexadecimal number
//A valid value must be 1 or 2 characters long and only use 0-9 or A-F


public class HexValidator {

    public static boolean isValidHex(String value) {
        if (value == null) {
            return false;
        }

        value = value.trim();

        // This  task only allows 1-digit or 2-digit hexadecimal values
        if (value.length() < 1 || value.length() > 2) {
            return false;
        }

        for (int i = 0; i < value.length(); i++) {
            char ch = Character.toUpperCase(value.charAt(i));

            boolean isDigit = ch >= '0' && ch <= '9';
            boolean isLetter = ch >= 'A' && ch <= 'F';

            // This Reject anything outside the valid hex range
            if (!isDigit && !isLetter) {
                return false;
            }
        }

        return true;
    }
}
