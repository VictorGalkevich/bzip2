import java.util.HashSet;
import java.util.Set;

public class AlphabetFinder {
    public static String findAlphabet(String content) {
        StringBuilder builder = new StringBuilder();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < content.length(); i++) {
            set.add(content.charAt(i));
        }
        for (Character c : set) {
            builder.append(c);
        }
        return builder.toString();
    }
}
