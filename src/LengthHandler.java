public class LengthHandler {
    public static String doRleForward(String str) {
        int n = str.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {

            // Count occurrences of current character
            int count = 1;
            while (i < n - 1
                   && str.charAt(i) == str.charAt(i + 1)) {
                count++;
                i++;
            }
            builder.append(str.charAt(i)).append(count);
        }
        return builder.toString();
    }

    public static String doRleBackwards(String str) {
        int n = str.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append(String.valueOf(str.charAt(i)).repeat(Integer.parseInt(str.charAt(i + 1) + "")));
            i++;
        }
        return builder.toString();
    }
}
