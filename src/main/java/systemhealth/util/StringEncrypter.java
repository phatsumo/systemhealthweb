/**
 *
 */
package systemhealth.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Utility to encrypt and decrypt {@link String} using 128 bit AES encryption.
 *
 * @author 1062992
 *
 */
public class StringEncrypter {

    // due to export controls, JDK restricts to use only 128 bit key (16
    // character key)
    private static final String cipherKey = "YoMamaSoSoMamaYo";

    private Key aesKey;
    private Cipher cipher;

    /**
     * Default constructor. Initializes the Java security {@link Key}, and
     * {@link Cipher} for use during encryption and decryption.
     *
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    public StringEncrypter() throws NoSuchAlgorithmException,
            NoSuchPaddingException {
        aesKey = new SecretKeySpec(cipherKey.getBytes(), "AES");
        cipher = Cipher.getInstance("AES");
    }

    /**
     * Encrypts the provided String and returns the cipher text.
     *
     * @param text
     *            the String to encrypt
     * @return the encrypted String
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public String encrypt(String text) throws InvalidKeyException,
    IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedText = cipher.doFinal(text.getBytes());

        return new String(Base64.encodeBase64(encryptedText));
    }

    /**
     * Decrypts the provided and String and returns the unencrypted String.
     *
     * @param cipherText
     *            the encrypted text to decrypt.
     * @return the unencrypted text
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public String decrypt(String cipherText) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        byte[] decodeBase64 = Base64.decodeBase64(cipherText.getBytes());
        String decryptedText = new String(cipher.doFinal(decodeBase64));
        return decryptedText;
    }

    /**
     * Main method, program will prompt for String to encrypt.
     *
     * @param args
     */
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            StringEncrypter encrypter = new StringEncrypter();

            System.out
                    .println("Enter 'E' to encrypting or 'D' for decrypting text, default [E]: ");
            String input = scanner.nextLine();

            switch (input.toLowerCase()) {

            case "d": {
                System.out.println("Enter text to be decrypted: ");

                String line = scanner.nextLine();

                String decryptedLine = encrypter.decrypt(line);

                System.out.println(String.format("Decrypted text: %s",
                        decryptedLine));
                break;
            }
            default: {
                System.out.println("Enter text to be encrypted: ");

                String line = scanner.nextLine();

                String encryptedLine = encrypter.encrypt(line);

                System.out.println(String.format("Encrypted text: %s",
                        encryptedLine));
                break;
            }
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {

            e.printStackTrace();
        }

    }

}
