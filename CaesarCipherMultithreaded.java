package cc;
import java.util.Scanner;

class CaesarCipherThread extends Thread{
    private final char[] messageChars;
    private final int shift;
    private final int startIdx;
    private final int endIdx;

    CaesarCipherThread(char[] messageChars, int shift, int startIdx, int endIdx){
        this.messageChars = messageChars;
        this.shift = shift;
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }

@Override
    public void run(){
        for (int i = startIdx; i<endIdx; i++){
            if (Character.isLetter(messageChars[i])){
                char base = Character.isUpperCase(messageChars[i]) ? 'A' : 'a';
                messageChars[i] = (char) ((messageChars[i] - base + shift)%26 + base);
            }
        }
    }

public class CaesarCipherMultithreaded{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the message to encrypt: ");
        String originalMessage = scanner.nextLine().trim();

        System.out.print("Enter the encryption key (shift): ");
        int encryptionKey = scanner.nextInt();

        char[] messageChars = originalMessage.toCharArray();

        CaesarCipherThread[] threads = new CaesarCipherThread[4];
        int chunkSize = messageChars.length/threads.length;

        for (int i=0; i<threads.length; i++){
            int startIdx = i*chunkSize;
            int endIdx = (i==threads.length-1) ? messageChars.length : (i+1)*chunkSize;
            threads[i] = new CaesarCipherThread(messageChars, encryptionKey, startIdx, endIdx);
            threads[i].start();
        }

        for (CaesarCipherThread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String encryptedMessage = new String(messageChars);
        System.out.println("Original Message: "+originalMessage);
        System.out.println("Encrypted Message: "+encryptedMessage);

        scanner.close();
    }
}
}