import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


public class Archiver {
    public void doZip(String from, String to) throws IOException {
        ZipReader reader = new ZipReader();
        String read = reader.read(from);
        String bwtEncoded = Transformer.doBwtForward(read);
        String alphabet = AlphabetFinder.findAlphabet(bwtEncoded);
        String mtfEncoded = Simplifier.doMtfForward(bwtEncoded, alphabet);
        String rleEncoded = LengthHandler.doRleForward(mtfEncoded);
        Pair<String, Map<Character, Integer>> pair = Compressor.doHuffForward(rleEncoded);
        reader.writeEncoded(to, new Pair<>(alphabet, pair));
    }

    public void doUnzip(String from, String to) throws IOException {
        ZipReader reader = new ZipReader();
        Pair<Map<Character, Integer>, Pair<String, String>> pair = reader.readEncoded(from);
        Map<Character, Integer> frequencies = pair.getV1();
        String content = pair.getV2().getV1();
        String alphabet = pair.getV2().getV2();
        String huffDecoded = Compressor.doHuffBackwards(frequencies, content);
        String rleDecoded = LengthHandler.doRleBackwards(huffDecoded);
        String mtfDecoded = Simplifier.doMtfBackwards(rleDecoded, alphabet);
        String bwtDecoded = Transformer.doBwtBackwards(mtfDecoded);
        reader.write(to, bwtDecoded);
    }
}
