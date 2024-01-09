import java.util.*;

public class Compressor {

    private static TreeMap<Character, Integer> countFrequency(String text) {
        TreeMap<Character, Integer> freqMap = new TreeMap<>();
        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            Integer count = freqMap.get(c);
            freqMap.put(c, count != null ? count + 1 : 1);
        }
        return freqMap;
    }

    private static CodeTreeNode huffman(PriorityQueue<CodeTreeNode> codeTreeNodes) {
        while (codeTreeNodes.size() > 1) {
            CodeTreeNode left = codeTreeNodes.poll();
            CodeTreeNode right = codeTreeNodes.poll();

            CodeTreeNode parent = new CodeTreeNode(null, right.weight + left.weight, left, right);
            codeTreeNodes.add(parent);
        }
        return  codeTreeNodes.poll();
    }

    private static String huffmanDecode(String encoded, CodeTreeNode tree) {
        StringBuilder decoded = new StringBuilder();

        CodeTreeNode node = tree;
        for (int i = 0; i < encoded.length(); i++) {
            node = encoded.charAt(i) == '0' ? node.left : node.right;
            if (node.content != null) {
                decoded.append(node.content);
                node = tree;
            }
        }
        return decoded.toString();
    }

    public static Pair<String, Map<Character, Integer>> doHuffForward(String content) {
        // вычисление таблицы частот с которыми встречаются символы в тексте
        TreeMap<Character, Integer> frequencies = countFrequency(content);

        PriorityQueue<CodeTreeNode> codeTreeNodes = new PriorityQueue<>();

        // генерация листов будущего дерева для символов текста
        for(Character c: frequencies.keySet()) {
            codeTreeNodes.add(new CodeTreeNode(c, frequencies.get(c)));
        }
        // построение кодового дерева алгоритмом Хаффмана
        CodeTreeNode tree = huffman(codeTreeNodes);

        // построение таблицы префиксных кодов для символов исходного текста
        TreeMap<Character, String> codes = new TreeMap<>();
        for (Character c: frequencies.keySet()) {
            codes.put(c, tree.getCodeForCharacter(c, ""));
        }

        // кодирование текста префиксными кодами
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            encoded.append(codes.get(content.charAt(i)));
        }
        return new Pair<>(encoded.toString(), frequencies);
    }

    public static String doHuffBackwards(Map<Character, Integer> frequencies, String content) {
        PriorityQueue<CodeTreeNode> codeTreeNodes = new PriorityQueue<>();

        for(Character c: frequencies.keySet()) {
            codeTreeNodes.add(new CodeTreeNode(c, frequencies.get(c)));
        }
        CodeTreeNode tree2 = huffman(codeTreeNodes);

        // декодирование обратно исходной информации из сжатой
        return huffmanDecode(content, tree2);
    }

    // класс для представления кодового дерева
    private static class CodeTreeNode implements Comparable<CodeTreeNode> {

        Character content;
        int weight;
        CodeTreeNode left;
        CodeTreeNode right;

        public CodeTreeNode(Character content, int weight) {
            this.content = content;
            this.weight = weight;
        }

        public CodeTreeNode(Character content, int weight, CodeTreeNode left, CodeTreeNode right) {
            this.content = content;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(CodeTreeNode o) {
            return -(o.weight - weight);
        }

        // извлечение кода для символа
        public String getCodeForCharacter(Character ch, String parentPath) {
            if (content == ch) {
                return  parentPath;
            } else {
                if (left != null) {
                    String path = left.getCodeForCharacter(ch, parentPath + 0);
                    if (path != null) {
                        return path;
                    }
                }
                if (right != null) {
                    return right.getCodeForCharacter(ch, parentPath + 1);
                }
            }
            return null;
        }
    }

    // класс реализующий битовый массив
    public static class BitArray {
        int size;
        byte[] bytes;

        private final byte[] masks = new byte[] {0b00000001, 0b00000010, 0b00000100, 0b00001000,
                0b00010000, 0b00100000, 0b01000000, (byte) 0b10000000};

        public BitArray(int size) {
            this.size = size;
            int sizeInBytes = size / 8;
            if (size % 8 > 0) {
                sizeInBytes = sizeInBytes + 1;
            }
            bytes = new byte[sizeInBytes];
        }

        public int get(int index) {
            int byteIndex = index / 8;
            int bitIndex = index % 8;
            return (bytes[byteIndex] & masks[bitIndex]) != 0 ? 1 : 0;
        }

        public void set(int index, int value) {
            int byteIndex = index / 8;
            int bitIndex = index % 8;
            if (value != 0) {
                bytes[byteIndex] = (byte) (bytes[byteIndex] | masks[bitIndex]);
            } else {
                bytes[byteIndex] = (byte) (bytes[byteIndex] & ~masks[bitIndex]);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(get(i) > 0 ? '1' : '0');
            }
            return sb.toString();
        }

        public int getSizeInBytes() {
            return bytes.length;
        }
    }
}