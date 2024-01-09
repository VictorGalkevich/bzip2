import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ZipReader {
    public String read(String path) throws IOException {
        return new String(Files.readAllBytes(Path.of(path)));
    }

    public void write(String path, String content) throws IOException {
        Files.write(Path.of(path), content.getBytes());
    }

    public void writeEncoded(String path, Pair<String, Pair<String, Map<Character, Integer>>> pair) {
        File file = new File("%s.vzip".formatted(path));
        String bits = pair.getV2().getV1();
        String alphabet = pair.getV1();
        Map<Character, Integer> frequencies = pair.getV2().getV2();
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream(file));
            os.writeInt(frequencies.size());
            for (Character character: frequencies.keySet()) {
                os.writeChar(character);
                os.writeInt(frequencies.get(character));
            }
            int compressedSizeBits = bits.length();
            Compressor.BitArray bitArray = new Compressor.BitArray(compressedSizeBits);
            for (int i = 0; i < bits.length(); i++) {
                bitArray.set(i, bits.charAt(i) != '0' ? 1 : 0);
            }
            os.writeInt(compressedSizeBits);
            os.write(bitArray.bytes, 0, bitArray.getSizeInBytes());
            os.writeUTF(alphabet);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pair<Map<Character, Integer>, Pair<String, String>> readEncoded(String path) {
        Map<Character, Integer> frequencies = new TreeMap<>();
        StringBuilder bits = new StringBuilder();
            try {
                DataInputStream os = new DataInputStream(new FileInputStream(path));
                int frequencyTableSize = os.readInt();
                for (int i = 0; i < frequencyTableSize; i++) {
                    frequencies.put(os.readChar(), os.readInt());
                }
                int dataSizeBits = os.readInt();
                Compressor.BitArray bitArray = new Compressor.BitArray(dataSizeBits);
                int read = os.read(bitArray.bytes, 0, bitArray.getSizeInBytes());
                String alphabet = os.readUTF();
                os.close();
                for (int i = 0; i < bitArray.size; i++) {
                    bits.append(bitArray.get(i) != 0 ? "1" : 0);
                }
                return new Pair<>(frequencies, new Pair<>(bits.toString(), alphabet));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
    }
}
