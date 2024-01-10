public class LengthHandler {
    public static String doRleForward(String str) {
        int n = str.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {

            if (str.charAt(i) == '0') {
                int count = 1;
                while (i < n - 1
                       && str.charAt(i) == str.charAt(i + 1)) {
                    count++;
                    i++;
                }
                builder.append('0').append(count);
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String doRleBackwards(String str) {
        int n = str.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) == '0') {
                builder.append(String.valueOf('0').repeat(Integer.parseInt(str.charAt(i + 1) + "")));
                i++;
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }
}
