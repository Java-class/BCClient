package ir.javaclass.io;
import org.apache.commons.io.FileUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

class AESEncryptor {

    private final String cipherMode = "AES/CBC/PKCS5Padding";
    private static SecretKeySpec secretKey;
    private static byte[] key;

    //private SecretKey secretKey;
    private Cipher cipher;

    AESEncryptor(String pk) throws NoSuchPaddingException, NoSuchAlgorithmException {
        setKey(pk);
        this.cipher = Cipher.getInstance(cipherMode);
    }

    void encrypt(File orig, File outputFile) throws InvalidKeyException, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();
        try (
                FileOutputStream fileOut = new FileOutputStream(outputFile);
                CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)
        ) {
            fileOut.write(iv);
            cipherOut.write(Files.readAllBytes(orig.toPath()));
        }
    }

    void decrypt(File inputFile, File output) throws InvalidAlgorithmParameterException, InvalidKeyException, IOException {

        try (FileInputStream fileIn = new FileInputStream(inputFile)) {
            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));
            try (
                    CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                    InputStreamReader inputReader = new InputStreamReader(cipherIn);
                    //BufferedReader reader = new BufferedReader(inputReader)
            ) {
                FileUtils.copyToFile(cipherIn,output);
            }

        }
    }

    private void setKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}