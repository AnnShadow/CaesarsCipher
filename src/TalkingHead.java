import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class TalkingHead {
    public Cipher cipher = new Cipher();
    public void start() throws IOException {
        System.out.println("""
                What will we do today, love?
                Only the requested numbers, remember?
                1 - encrypt text using key
                2 - decrypt text using key
                3 - I forgot the key again
                0 - I'm drunk, I'll go to bed...""");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String read = reader.readLine();
        int choice = Integer.parseInt(read);
        if (choice == 1){
            System.out.println("Provide me the file path");
            String path = reader.readLine();
            System.out.println("Good job! Now the key (you can use a negative key too)");
            String key = reader.readLine();
            cipher.encrypt(path, Integer.parseInt(key));
        } else if (choice == 2){
            System.out.println("Provide me the file path");
            String path = reader.readLine();
            System.out.println("Good job! Now the key (use the same key with\n " +
                    "the opposite sign, for example, if the key was 3, use -3,\n " +
                    "otherwise it will turn out like last time!)");
            String key = reader.readLine();
            cipher.decipher(path, Integer.parseInt(key));
        } else if(choice == 3){
            System.out.println("No problem. Provide me the file path");
            String path = reader.readLine();
            cipher.bruteForce(path);
        } else if (choice == 0) {
            System.out.println("Okay, have a goodnight sleep, love.");
        } else {
            System.out.println("You're definitely drunk, go to bed!");
        }
    }
}
