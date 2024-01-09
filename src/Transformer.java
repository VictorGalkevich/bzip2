import java.util.*;
public class Transformer
{
    public static class Suffix implements Comparable<Suffix>
    {
        int index;
        int rank;
        int next;

        public Suffix(int ind, int r, int nr)
        {
            index = ind;
            rank = r;
            next = nr;
        }
        public int compareTo(Suffix s)
        {
            if (rank != s.rank) return Integer.compare(rank, s.rank);
            return Integer.compare(next, s.next);
        }
    }
    public static String doBwtForward(String s)
    {
        s += "$";
        int n = s.length();
        Suffix[] su = new Suffix[n];
        for (int i = 0; i < n; i++)
        {
            su[i] = new Suffix(i, s.charAt(i) - '$', 0);
        }
        for (int i = 0; i < n; i++)
            su[i].next = (i + 1 < n ? su[i + 1].rank : -1);
        Arrays.sort(su);
        int[] ind = new int[n];
        for (int length = 4; length < 2 * n; length <<= 1)
        {
            int rank = 0, prev = su[0].rank;
            su[0].rank = rank;
            ind[su[0].index] = 0;
            for (int i = 1; i < n; i++)
            {
                if (su[i].rank == prev &&
                    su[i].next == su[i - 1].next)
                {
                    prev = su[i].rank;
                    su[i].rank = rank;
                }
                else
                {
                    prev = su[i].rank;
                    su[i].rank = ++rank;
                }
                ind[su[i].index] = i;
            }

            for (int i = 0; i < n; i++)
            {
                int nextP = su[i].index + length / 2;
                su[i].next = nextP < n ?
                        su[ind[nextP]].rank : -1;
            }
            Arrays.sort(su);
        }
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < n; i++)
            builder.append(s.charAt(su[i].index == 0 ? s.length() - 1 : su[i].index - 1));
        return builder.toString();
    }

    public static String doBwtBackwards(String str) {
        int[] count = new int[10_000];
        List<List<Integer>> indices = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            indices.add(new ArrayList<>());
        }
        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i)]++;
            indices.get(str.charAt(i)).add(i);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10_000; i++) {
            builder.append(String.valueOf((char) i).repeat(Math.max(0, count[i])));
        }
        int[] timesMet = new int[10_000];
        int[] moveIndex = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            moveIndex[i] = indices.get(builder.charAt(i)).get(timesMet[builder.charAt(i)]++);
        }
        StringBuilder res = new StringBuilder();
        int current = moveIndex[0];
        while (res.length() != str.length() - 1) {
            res.append(builder.charAt(current));
            current = moveIndex[current];
        }
        return res.toString();
    }

}
