package ir.javaclass.config;

import ir.javaclass.model.UserPlan;

import java.math.BigInteger;

public class Commons {

    public final static String OWNER_PROJECT = "4f52d372fa04e39f8b1c8c52139cce057d8598b969e3706b65adca1cd4343045";
    public final static String CHUNK_SUFFIX = "-chunk";
    public final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    public final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    public final static String PEER_CONTRACT_ADDRESS = "0x2e3963255d2911153ab85095a23ed2476dff83f8";
    public final static String FILE_CONTRACT_ADDRESS = "0x2b5dae791191bff37bd19df9bfb37a62d484f485";
    public final static long MAX_ITEM_THRESHOLD_COUNT = 100;
    public final static String SYNC_FOLDER_ADDRESS = "/home/mostafa/00-Developing/blockchain-cs/user1-syncs";
    public final static String TEMP_FOLDER_ADDRESS = "/home/mostafa/00-Developing/blockchain-cs/user1-temp";
    public final static String TEMP_CHUNK_ADDRESS = "/home/mostafa/00-Developing/blockchain-cs/user1-temp/chunks/";
    public final static String TEMP_ENCRYPTED_CHUNK_ADDRESS = TEMP_CHUNK_ADDRESS + "encrypted";
    public final static String TEMP_DOWNLOAD_ADDRESS = "/home/mostafa/00-Developing/blockchain-cs/user1-temp/download";
    public final static String TEMP_DECRYPTED_DOWNLOAD_ADDRESS = "/home/mostafa/00-Developing/blockchain-cs/user1-temp/download/decrypted";
    public final static int CHUNK_SIZE_IN_MB = 1;
    public final static long KB = 1024;
    public final static long MB = KB * 1024;
    public final static long GB = MB * 1024;
    public static final String USER_PUBLIC_KEY ="0xf6397948abB6398fBc8CcACd699134B3CEA9dF72" ; /// second account in Ganache
    public final static  String USER_PRIVATE_KEY = "ec3483c22db9a614e0099e86a92d13146e20e844acc3b6fd126022007b28ba74";
    public final static UserPlan USER_PLAN = UserPlan.NORMAL;
}
