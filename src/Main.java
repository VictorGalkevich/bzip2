import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Archiver archiver = new Archiver();
        try {
            archiver.doZip("voyna-i-mir-tom-1.txt", "zipped");
            archiver.doUnzip("zipped.vzip", "unzipped.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
