package ir.javaclass.config;

import java.math.BigInteger;

public class Commons {

    public final static String OWNER_PROJECT = "f3e5ca9e13ca42a6bfb9e4ab05bcbffc7b8f8e9fcc021ec096616e75f667c052";
    public final static String CHUNK_SUFFIX = "-chunk";
    public final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    public final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    public final static String FILE_CONTRACT_ADDRESS = "0x2b5dae791191bff37bd19df9bfb37a62d484f485";
    public final static long MAX_ITEM_THRESHOLD_COUNT = 100;
    public final static String SYNC_FOLDER_ADDRESS = "E:\\blockchain-cs\\user1-syncs";
    public final static String TEMP_FOLDER_ADDRESS = "E:\\blockchain-cs\\user1-temp";
    public final static String TEMP_CHUNK_ADDRESS = "E:\\blockchain-cs\\user1-temp\\chunks\\";
    public final static String TEMP_ENCRYPTED_CHUNK_ADDRESS = TEMP_CHUNK_ADDRESS + "encrypted";
    public final static String TEMP_DOWNLOAD_ADDRESS = "E:\\blockchain-cs\\user1-temp\\download";
    public final static String TEMP_DECRYPTED_DOWNLOAD_ADDRESS = "E:\\blockchain-cs\\user1-temp\\download\\decrypted";
    public final static int CHUNK_SIZE_IN_MB = 1;
    public final static long KB = 1024;
    public final static long MB = KB * 1024;
    public final static long GB = MB * 1024;
    public static final String USER_PUBLIC_KEY ="0x7D848C5971d65C729c7B0aE323B4538af5F7a89d" ; /// second account in Ganache
    public final static  String USER_PRIVATE_KEY = "33b0eed314624561d47bfd1bb8b0eb3d8d5ec28298b988a6e677828246f5ce89";
}
