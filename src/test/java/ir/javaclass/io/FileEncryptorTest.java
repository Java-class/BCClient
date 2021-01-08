package ir.javaclass.io;

import org.junit.jupiter.api.Test;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class FileEncryptorTest {

    @Test
    void doEncryption() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //File origFile = new File("E:\\blockchain-cs\\user1-syncs\\Introducing Ethereum and Solidity.pdf");
        File origFile = new File("E:\\blockchain-cs\\user1-syncs\\big.mkv");
        FileEncryptor encryptor = new FileEncryptor("123456789",origFile);
        File encryptedFile = encryptor.doEncryption();
    }

    @Test
    void doDecryption() throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
        File encryptedFile = new File("E:\\blockchain-cs\\user1-temp\\ed833490fc56cef275e46e1bd4bfe711049c9bc68aec479638d0c8c09a3921b9");
        File decryptedFile = new File("E:\\blockchain-cs\\user1-temp\\Introducing Ethereum and Solidity.pdf");
        if(encryptedFile.exists()){
            FileEncryptor encryptor = new FileEncryptor("123456789",encryptedFile);
            encryptor.doDecryption(decryptedFile);
        }
    }
    @Test
    public void test() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {

        File encryptedFile = new File("E:\\blockchain-cs\\user1-temp\\2dd43f27eabd3f981bbeb47a37146c5c24a67aa0c05302d042064fe8d7c3dc8f");
        File origFile = new File("E:\\blockchain-cs\\user1-syncs\\Introducing Ethereum and Solidity.pdf");
        File decryptedFile = new File("E:\\blockchain-cs\\user1-temp\\Introducing Ethereum and Solidity.pdf");


        AESEncryptor fileEncrypterDecrypter = new AESEncryptor("secretKey");

        fileEncrypterDecrypter.encrypt(origFile, encryptedFile);

        fileEncrypterDecrypter.decrypt(encryptedFile, decryptedFile);
    }
}