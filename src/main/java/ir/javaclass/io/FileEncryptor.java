package ir.javaclass.io;

import ir.javaclass.config.Commons;
import ir.javaclass.config.FileDelimiter;
import org.apache.commons.io.FileUtils;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncryptor {

    private String pk;
    private File origFile;

    public FileEncryptor(String pk, File origFile) {
        this.pk = pk;
        this.origFile = origFile;
    }

    public File doEncryption() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        File encryptedFile = new File(Commons.TEMP_ENCRYPTED_CHUNK_ADDRESS + FileDelimiter.getSystemDelimiter() +  FileDelimiter.getSystemDelimiter() + FileUtil.getSHA256(origFile.getName() + System.currentTimeMillis()));
        if (encryptedFile.exists())
            FileUtils.forceDelete(encryptedFile);
        else
            FileUtils.forceMkdirParent(encryptedFile);

        AESEncryptor encryptor = new AESEncryptor(pk);
        encryptor.encrypt(origFile, encryptedFile);
        return encryptedFile;
    }

    public File doDecryption(File outputFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        if (outputFile.exists())
            FileUtils.forceDelete(outputFile);
        AESEncryptor encryptor = new AESEncryptor(pk);
        encryptor.decrypt(origFile,outputFile);
        return outputFile;
    }
}
