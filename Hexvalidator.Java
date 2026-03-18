public class HexValidator {

    public static boolean isValidHex(String value) {
        if (value == null) {
            return false;
        }

        value = value.trim();

        if (value.length() < 1 || value.length() > 2) {
            return false;
        }

        for (int i = 0; i < value.length(); i++) {
            char ch = Character.toUpperCase(value.charAt(i));

            boolean isDigit = ch >= '0' && ch <= '9';
            boolean isLetter = ch >= 'A' && ch <= 'F';

            if (!isDigit && !isLetter) {
                return false;
            }
        }

        return true;
    }
}
