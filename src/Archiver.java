import java.io.IOException;
import java.util.Map;


public class Archiver {
    public void doZip(String from, String to) throws IOException {
        ZipReader reader = new ZipReader();
        String read = reader.read(from);
        String bwtEncoded = Transformer.doBwtForward(read);
        String alphabet = AlphabetFinder.findAlphabet(bwtEncoded);
        String mtfEncoded = Simplifier.doMtfForward(bwtEncoded, alphabet);
        Pair<String, Map<Character, Integer>> pair = Compressor.doHuffForward(mtfEncoded);
        reader.writeEncoded(to, new Pair<>(alphabet, pair));
    }

    public void doUnzip(String from, String to) throws IOException {
        ZipReader reader = new ZipReader();
        Pair<Map<Character, Integer>, Pair<String, String>> pair = reader.readEncoded(from);
        Map<Character, Integer> frequencies = pair.getV1();
        String content = pair.getV2().getV1();
        String alphabet = pair.getV2().getV2();
        String huffDecoded = Compressor.doHuffBackwards(frequencies, content);
        String mtfDecoded = Simplifier.doMtfBackwards(huffDecoded, alphabet);
        String bwtDecoded = Transformer.doBwtBackwards(mtfDecoded);
        reader.write(to, bwtDecoded);
    }
}
