// This class contains the manual conversion methods used in the program.
 //Built-in radix conversion methods were avoided to match the task rules.
 

public class HexConverter {

    // Converts a hexadecimal string into decimal manually
    
    public static int hexToDecimal(String hex) {
        hex = hex.toUpperCase();

        int total = 0;
        int powerValue = 1;

        // Start from the right-hand side because that is the lowest place value
        for (int i = hex.length() - 1; i >= 0; i--) {
            int digitValue = hexCharToInt(hex.charAt(i));
            total += digitValue * powerValue;
            powerValue *= 16;
        }

        return total;
    }

    // Converts a decimal number into binary manually using repeated division by 2
     
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

    // Converts a decimal number into octal manually using repeated division by 8
     
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

    //Converts a single hexadecimal character into its integer value
     
    private static int hexCharToInt(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }

        return ch - 'A' + 10;
    }
}
