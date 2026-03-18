public class HexConverter {

    public static int hexToDecimal(String hex) {
        hex = hex.toUpperCase();
        int total = 0;
        int powerValue = 1;

        for (int i = hex.length() - 1; i >= 0; i--) {
            int digitValue = hexCharToInt(hex.charAt(i));
            total += digitValue * powerValue;
            powerValue *= 16;
        }

        return total;
    }

    public static String decimalToBinary(int decimal) {
        if (decimal == 0) {
            return "0";
        }

        String result = "";

        while (decimal > 0) {
            int remainder = decimal % 2;
            result = remainder + result;
            decimal = decimal / 2;
        }

        return result;
    }

    public static String decimalToOctal(int decimal) {
        if (decimal == 0) {
            return "0";
        }

        String result = "";

        while (decimal > 0) {
            int remainder = decimal % 8;
            result = remainder + result;
            decimal = decimal / 8;
        }

        return result;
    }

    private static int hexCharToInt(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }

        return ch - 'A' + 10;
    }
}
