import java.util.Scanner;

public class Simplifier {
    public static String encode(String msg, String symTable){
        StringBuilder builder = new StringBuilder();
        StringBuilder s = new StringBuilder(symTable);
        for(char c : msg.toCharArray()){
            int idx = s.indexOf("" + c);
            builder.append(idx).append(" ");
            s.deleteCharAt(idx).insert(0, c);
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static String decode(String toDecode, String symTable){
        StringBuilder output = new StringBuilder();
        StringBuilder s = new StringBuilder(symTable);
        Scanner scanner = new Scanner(toDecode);
        while (scanner.hasNextInt()) {
            int idx = scanner.nextInt();
            char c = s.charAt(idx);
            output.append(c);
            s.deleteCharAt(idx).insert(0, c);
        }
        return output.toString();
    }

    public static String doMtfForward(String toEncode, String alphabet) {
        return encode(toEncode, alphabet);
    }

    public static String doMtfBackwards(String toEncode, String alphabet) {
        return decode(toEncode, alphabet);
    }
}