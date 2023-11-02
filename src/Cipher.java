import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Files;
import java.util.Map;

public class Cipher {

    public final String ALPHABET = "АБВГДEЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя \',.!?";

    public void encrypt(String path, int key) {
        Path filePath = Path.of(path);
        List<String> list = null;
        try {
            list = Files.readAllLines(filePath);
            for (String str : list) {
                StringBuilder encrypt = new StringBuilder();
                for (char ch : str.toCharArray()) {
                    if (ALPHABET.indexOf(ch) != -1) {
                        int oldIndex = ALPHABET.indexOf(ch);
                        ch = ALPHABET.charAt(offset(oldIndex, key));
                    }
                    encrypt.append(ch);
                }
                Files.writeString(filePath, "\n", StandardOpenOption.APPEND);
                Files.writeString(filePath, encrypt, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.out.println("Invalid file path... Are you a little drunk, love?");
            e.printStackTrace();
        }
        System.out.println("I wrote everything down, love.\n" +
                "(text added to the end of the file if there was something there)");
    }

    public int offset(int oldIndex, int key) {
        int offset;
        if (key >= 0) {
            offset = (oldIndex + key) % ALPHABET.length();
        } else {
            offset = (ALPHABET.length() - (Math.abs(key) % ALPHABET.length()) + oldIndex) % ALPHABET.length();
        }
        return offset;
    }

    public void decipher(String path, int key) {
        this.encrypt(path, key);
    }

    public void bruteForce(String path) {
        HashMap<Integer, Integer> map = new HashMap<>();
        Path filePath = Path.of(path);
        List<String> list = null;
        int guess = 1;
        int value = 0;
        int key = 0;
        while (guess < ALPHABET.length()) {
            int winSequence = 0;
            try {
                list = Files.readAllLines(filePath);
                for (String str : list) {
                    char[] st = str.toCharArray();
                    char[] nw = new char[str.length()];
                    int m = 0;
                    for (char ch : st) {
                        if (ALPHABET.indexOf(ch) != -1) {
                            int oldIndex = ALPHABET.indexOf(ch);
                            ch = ALPHABET.charAt(offset(oldIndex, guess));
                            nw[m] = ch;
                            m++;
                        }
                    }
                    for (int i = 0; i < nw.length - 3; i++) {
                        if (ALPHABET.indexOf(nw[i]) >= 33 && ALPHABET.indexOf(nw[i]) <= 65) {
                            if (ALPHABET.indexOf(nw[i + 1]) == 68) {
                                if (ALPHABET.indexOf(nw[i + 2]) == 66) {
                                    if (ALPHABET.indexOf(nw[i+3]) >= 33 && ALPHABET.indexOf(nw[i+3]) <= 65) {
                                        winSequence++;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Invalid file path... Are you a little drunk, love?");
                e.printStackTrace();
            }
            map.put(guess, winSequence);
            guess++;
        }
        for (Map.Entry<Integer, Integer> in : map.entrySet()){
            if (value < in.getValue()){
                value = in.getValue();
                key = in.getKey();
            }
        }
        this.encrypt(path, key);
    }
}
