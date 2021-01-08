package ir.javaclass.service;


import ir.javaclass.config.Commons;
import ir.javaclass.contract.FileList;
import ir.javaclass.entity.FileItem;
import ir.javaclass.util.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class FileItemService {
    public static String deployFileContract() {
        String contractAddress = null;
        try {
            //// owner of project deploy file contract.
            String pk = Commons.OWNER_PROJECT;
            Credentials credentials = Credentials.create(pk);
            Web3j web3j = ConnectionUtil.getWeb3jConnection();
            contractAddress = FileList.deploy(web3j, credentials, new DefaultGasProvider()).send().getContractAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contractAddress;
    }
    public static TransactionReceipt uploadFileInfo(String pk,String localFileName ,FileItem fileItem) throws Exception {
        Credentials credentials = Credentials.create(pk);
        FileList fileList = FileList.load(Commons.FILE_CONTRACT_ADDRESS, ConnectionUtil.getWeb3jConnection(), credentials, new DefaultGasProvider());
        TransactionReceipt tx = fileList.uploadFile(localFileName, fileItem.getFileInfo().serialize(), fileItem.getDateInfo().serialize(), fileItem.getStorageListAsString()).send();
        log.info("Result of fileItem:  " + fileItem.getOrigName() +
                " Contract Address: " + tx.getContractAddress() +
                " From: " + tx.getFrom() +
                " To: " + tx.getTo() +
                " Transaction Hash: " + tx.getTransactionHash() +
                " Block Hash: " + tx.getBlockHash() +
                " Gas Used: " + tx.getGasUsed() +
                " Status: " + tx.getStatus());
        return tx;
    }

    public static BigInteger getUserFileCount(String pk) throws Exception {
        Credentials credentials = Credentials.create(pk);
        FileList fileList = FileList.load(Commons.FILE_CONTRACT_ADDRESS, ConnectionUtil.getWeb3jConnection(), credentials, new DefaultGasProvider());
        return fileList.getUserFileCount().send();
    }

    public static List<String> getUserFilesName(String pk) throws Exception {
        Credentials credentials = Credentials.create(pk);
        FileList fileList = FileList.load(Commons.FILE_CONTRACT_ADDRESS, ConnectionUtil.getWeb3jConnection(), credentials, new DefaultGasProvider());
        long fileCount = fileList.getUserFileCount().send().longValue();
        log.info("User File Count: " + fileCount);
        List<String> list = new ArrayList<>();
        long startIndex = 0;
        long endIndex = 0;
        int i = 0;
        try {
            if (fileCount > 0) {
                do {
                    if (fileCount > Commons.MAX_ITEM_THRESHOLD_COUNT) {
                        if (i != fileCount / Commons.MAX_ITEM_THRESHOLD_COUNT) {
                            startIndex = i * Commons.MAX_ITEM_THRESHOLD_COUNT;
                            endIndex = i * Commons.MAX_ITEM_THRESHOLD_COUNT + Commons.MAX_ITEM_THRESHOLD_COUNT;
                            i++;
                        } else {
                            startIndex = endIndex;
                            endIndex = fileCount;
                        }
                    } else {
                        endIndex = fileCount;
                    }
                    log.info("start: " + startIndex + " end: " + endIndex);
                    List<String> tempList = fileList.getFileName(BigInteger.valueOf(startIndex), BigInteger.valueOf(endIndex)).send();
                    if (tempList.size() > 0) {
                        tempList.remove(0);
                        list.addAll(tempList);
                    }
                } while (endIndex != fileCount);
            }
        }finally {
            if(!list.isEmpty())
                list = list.stream().distinct().collect(Collectors.toList());
        }
        log.info("size of final List: " + list.size());
        return list;
    }

    public static List<FileItem> list(String pk) throws Exception {
        List<FileItem> fileItems = new ArrayList<>();
        Credentials credentials = Credentials.create(pk);
        FileList fileList = FileList.load(Commons.FILE_CONTRACT_ADDRESS, ConnectionUtil.getWeb3jConnection(), credentials, new DefaultGasProvider());
        //Tuple6<List<BigInteger>, List<String>, List<String>, List<String>, List<String>, List<String>> listTuple6 = fileList.getFileList().sendAsync();

        List<String> fileNames = getUserFilesName(pk);



        //byte[] bytes = (byte[])fileList.getFileNames().send().get(0);
       /* CompletableFuture<Tuple6<List<BigInteger>, List<String>, List<String>, List<String>, List<String>, List<String>>> future = fileList.getFileList().sendAsync();
//        int count = 0;
//        while(!future.isDone()){
//            count++;
//            if(count%100000==0)
//                log.info(" wait for fetch all data..." + count);
//        }

        Tuple6<List<BigInteger>, List<String>, List<String>, List<String>, List<String>, List<String>> listTuple6 = future.get(1,TimeUnit.MINUTES);
        BigInteger file_count = fileList.fileCount().send();
        log.info("all  tx file count: " + file_count);
        for(int i = 1;i<=file_count.intValue();i++) {
            //Tuple6<BigInteger, String, String, String, String, String> value = fileList.files(BigInteger.valueOf(i)).send();
            Tuple6<BigInteger, String, String, String, String, String> value = new Tuple6<>(listTuple6.component1().get(i),
                    listTuple6.component2().get(i),
                    listTuple6.component3().get(i),
                    listTuple6.component4().get(i),
                    listTuple6.component5().get(i),
                    listTuple6.component6().get(i));
            FileItem fileItem = new FileItem(value);
            fileItems.add(fileItem);
        }*/
        return fileItems;
    }

    //// fix inside contract add get peer method called just by owner created peers.
    public static FileItem getFileItem(String publicKey, String privateKey) throws Exception {
        List<FileItem> fileItems = list(privateKey);
       log.info("list of fileItems: " + fileItems);
        for(FileItem item :fileItems){
            if(item.getOwner().equalsIgnoreCase(publicKey))
                return item;
        }
        return null;
    }


    public static FileItem getZeroIndex(String privateKey,String publicKey, String fileName) throws Exception {
        Credentials credentials = Credentials.create(privateKey);
        FileList fileList = FileList.load(Commons.FILE_CONTRACT_ADDRESS, ConnectionUtil.getWeb3jConnection(), credentials, new DefaultGasProvider());
        Tuple6<BigInteger, String, String, String, String, String> tuple6 = fileList.files(publicKey, fileName, BigInteger.ZERO).send();
        return new FileItem(tuple6);
    }


    public static BigInteger getFileChunkCount(String privateKey, String fileName) throws Exception {
        Credentials credentials = Credentials.create(privateKey);
        FileList fileList = FileList.load(Commons.FILE_CONTRACT_ADDRESS, ConnectionUtil.getWeb3jConnection(), credentials, new DefaultGasProvider());
        return fileList.getFileChunkCount(fileName).send();
    }

    public static List<FileItem> getAllFileMetadata(String privateKey, String publicKey, String fileName) throws Exception {
        List<FileItem> list = new ArrayList<>();
        Credentials credentials = Credentials.create(privateKey);
        FileList fileList = FileList.load(Commons.FILE_CONTRACT_ADDRESS, ConnectionUtil.getWeb3jConnection(), credentials, new DefaultGasProvider());
        BigInteger chunkCount = fileList.getFileChunkCount(fileName).send();
        for (int i = 0; i < chunkCount.intValue(); i++) {
            Tuple6<BigInteger, String, String, String, String, String> tuple6 = fileList.files(publicKey, fileName, BigInteger.valueOf(i)).send();
            list.add(new FileItem(tuple6));
        }
        return list;
    }

}
