package ir.javaclass.service;

import ir.javaclass.config.Commons;
import ir.javaclass.config.FileDelimiter;
import ir.javaclass.entity.FileItem;
import ir.javaclass.io.FileEncryptor;
import ir.javaclass.io.FileSplitter;
import ir.javaclass.io.FileUtil;
import ir.javaclass.model.FileInfoDto;
import ir.javaclass.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.parser.ParseException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class FileService {

    public static List<FileItem> listFile(String pk) throws Exception {
        return FileItemService.list(pk);
    }

    public static List<TransactionReceipt> uploadFile(String publicKey, String privateKey, File inputFile) throws Exception {
        List<TransactionReceipt> receipts = new ArrayList<>();
        String peerAddress = "http://192.168.1.105:8080";
        List<String> peerList = new ArrayList<>();
        peerList.add(peerAddress);
        FileEncryptor encryptor;

        // update zero indexItem;
        FileItem zeroIndex = new FileItem(inputFile, inputFile.getName(), "zeroIndex", 0, peerList);
        zeroIndex.setOrigName(inputFile.getName());
        receipts.add(FileItemService.uploadFileInfo(privateKey, inputFile.getName(), zeroIndex));

        if (inputFile.length() > Commons.CHUNK_SIZE_IN_MB * Commons.MB) {
            /// split file to chunks.
            FileSplitter splitter = new FileSplitter(inputFile, Commons.CHUNK_SIZE_IN_MB, new File(Commons.TEMP_CHUNK_ADDRESS));
            List<File> chunks = splitter.doSplit();
            chunks = chunks.stream().sorted(Comparator.comparing(File::getName)).collect(Collectors.toList());
            Map<Integer, File> encryptedChunks = new HashMap<>();

            for (File chunk : chunks) {
                encryptor = new FileEncryptor(privateKey, chunk);
                File temp = encryptor.doEncryption();
                encryptedChunks.put(chunks.indexOf(chunk), temp);
            }

            for (Integer index : encryptedChunks.keySet()) {
                File encrypted = encryptedChunks.get(index);
                boolean uploadResult = uploadToPeer(encrypted, publicKey, peerAddress);
                log.info("result of upload file: " + encrypted.getAbsolutePath() + " to peer: " + peerAddress + " is: " + uploadResult);
                if (uploadResult) {
                    FileItem newItem = new FileItem(inputFile, inputFile.getName(), encrypted.getName(), index, peerList);
                    receipts.add(FileItemService.uploadFileInfo(privateKey, inputFile.getName(), newItem));
                }
            }
        } else {
            encryptor = new FileEncryptor(privateKey, inputFile);
            File encrypted = encryptor.doEncryption();
            boolean uploadResult = uploadToPeer(encrypted, publicKey, peerAddress);
            if (uploadResult) {
                FileItem newItem =new FileItem(encrypted, inputFile.getName(), encrypted.getName(), 1, peerList);
                receipts.add(FileItemService.uploadFileInfo(privateKey, inputFile.getName(), newItem));
            }
        }
        return receipts;
    }

    private static boolean uploadToPeer(File file, String publicKey, String storageAddress){
        AtomicBoolean result = new AtomicBoolean(false);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut putRequest = new HttpPut(storageAddress + "/file/upload");
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.addTextBody("public_key", publicKey);
            entityBuilder.addBinaryBody("file", file);
            HttpEntity multiPartHttpEntity = entityBuilder.build();
            putRequest.setEntity(multiPartHttpEntity);
            CloseableHttpResponse response = httpClient.execute(putRequest);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                result.set(true);
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.get();
    }




    public static Map<String, List<FileItem>> listCloudItem(String privateKey) throws Exception {
        List<FileItem> fileItems = listFile(privateKey);
        if(!fileItems.isEmpty())
            return fileItems.stream().collect(Collectors.groupingBy(FileItem::getOrigName));
        return null;
    }


    public static void downloadFromCloud(String publicKey, String privateKey, List<FileItem> fileItems, File outputFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        fileItems.sort(Comparator.comparing(fileItem -> fileItem.getFileInfo().getChunkIndex()));
        List<File> downloadedFiles = new ArrayList<>();
        for(FileItem fileItem : fileItems)
            downloadedFiles.add(downloadFile(publicKey, privateKey, fileItem));

        if(outputFile.exists())
            FileUtils.forceDelete(outputFile);

        for(File temp : downloadedFiles)
            Util.writeToFile(new FileInputStream(temp), outputFile.getAbsolutePath(), true);
    }

    private static File downloadFile(String publicKey, String privateKey, FileItem origFileItem) throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
        String storageSever = "http://192.168.1.105:8080";
        File download  = downloadFromPeer(publicKey, origFileItem.getFileInfo().getChunkName(), storageSever);
        FileEncryptor decryptor = new FileEncryptor(privateKey, download);
        File decryptedFile = new File(Commons.TEMP_DECRYPTED_DOWNLOAD_ADDRESS + FileDelimiter.getSystemDelimiter() + origFileItem.getFileInfo().getChunkName());
        if(decryptedFile.exists())
            FileUtils.forceDelete(decryptedFile);
        decryptedFile = decryptor.doDecryption(decryptedFile);
        return  decryptedFile;
    }

    private static File downloadFromPeer(String publicKey, String itemName, String storageAddress) throws IOException {
        File downloadFile  = new File(Commons.TEMP_DOWNLOAD_ADDRESS + FileDelimiter.getSystemDelimiter() + itemName);
        if(downloadFile.exists())
            FileUtils.forceDelete(downloadFile);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(storageAddress + "/file/download/" + itemName);
        getRequest.setHeader("public_key",publicKey);
        CloseableHttpResponse response =  client.execute(getRequest);
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (FileOutputStream outstream = new FileOutputStream(downloadFile)) {
                    entity.writeTo(outstream);
                }
            }
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        return downloadFile;
    }

    public static void sync(String  publicKey, String privateKey, String syncFolder) {
        log.info("*** Sync Algorithm Started... ");
        long startTime = System.currentTimeMillis();
        File userRootFolder = new File(syncFolder);
        if (!userRootFolder.exists()) {
            log.error("sync folder not found: " + syncFolder);
            return;
        }
        List<File> localFiles = Arrays.asList(Objects.requireNonNull(userRootFolder.listFiles()));
        Map<String, File> mapLocalFiles = localFiles.stream().collect(Collectors.toMap(File::getName, Function.identity()));
        int localCount = localFiles.size();
        int bcCount;
        log.info("*** sync folder size: " + localFiles.size());
        try {
            BigInteger count = FileItemService.getUserFileCount(privateKey);
            bcCount = count.intValue();
            if (bcCount == 0 && localCount == 0) {
                log.info("not item found for sync...");
            } else if (bcCount == 0) {
                log.info("all item new to upload in cloud...");
                for (File file : localFiles) {
                    if (file.exists()) {
                        uploadFile(publicKey, privateKey, file);
                        log.info("file: " + file.getName() + " uploaded...");
                    }
                }
            } /*else if (bcCount > 0 && localCount == 0) {
                log.info("download all item from cloud...");
            } */else {
                List<String> bc_name = FileItemService.getUserFilesName(privateKey);
                /*if (bc_name.size() != bcCount) {
                    log.error(" blockchain file name is not equal to blockchain user file count, bc_count: " + bcCount + " list file names: " + bc_name.size());
                } else {*/
                    //// upload item not exist in bc...
                    List<String> newItems = new ArrayList<>(CollectionUtils.removeAll(localFiles.stream().map(File::getName).collect(Collectors.toList()), bc_name));
                    for (String fileName : newItems) {
                        File file = mapLocalFiles.get(fileName);
                        if (file.exists()) {
                            uploadFile(publicKey, privateKey, file);
                            log.info("file: " + fileName + " uploaded...");
                        }
                    }

                    //// download item not exist in local
                    for (String fileName : bc_name) {
                        if (mapLocalFiles.get(fileName) != null) {
                            FileItem zeroIndex = FileItemService.getZeroIndex(privateKey, publicKey, fileName);
                            File localFile = mapLocalFiles.get(fileName);
                            if(isFileChanged(localFile, zeroIndex)){
                                log.info("start file versioning for file: " + localFile.getName());
                            }else{
                                log.info("skip file: " + fileName + " not changed during last sync.");
                            }
                        } else {
                            FileItem zeroIndex = FileItemService.getZeroIndex(privateKey, publicKey, fileName);
                            log.info("download item from cloud: " + zeroIndex.toString());
                            List<FileItem> chunkList = FileItemService.getAllFileMetadata(privateKey, publicKey, fileName);
                            chunkList.remove(0); //Todo: check  equals to zero index.
                            File outputFile = new File(syncFolder + "/" + fileName);
                            FileService.downloadFromCloud(publicKey, privateKey, chunkList, outputFile);
                            if (outputFile.exists()) {
                                if (zeroIndex.getFileInfo().getHashContent().equals(FileUtil.getSHA256(outputFile))) {
                                    log.info("download from cloud is successfully done. " + outputFile.getAbsolutePath());
                                }
                            }
                        }
                    }
               // }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            long endTime  = System.currentTimeMillis();
            log.info(" sync finished, duration: " + (endTime - startTime) + " milliseconds.");
            try{
                File chunkFolder  = new File(Commons.TEMP_CHUNK_ADDRESS);
                if(chunkFolder.exists() && chunkFolder.isDirectory()){
                    FileUtils.cleanDirectory(chunkFolder);
                }
                File downloadFolder  = new File(Commons.TEMP_DOWNLOAD_ADDRESS);
                if(downloadFolder.exists() && downloadFolder.isDirectory()){
                    FileUtils.cleanDirectory(downloadFolder);
                }
            } catch (IOException e) {
                log.error("error during clean user temp files...", e);
            }
        }
    }



    public static FileInfoDto getFileInfo(String publicKey, String itemName, String storageAddress) throws IOException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        String params = "?public_key=" + publicKey + "&file_name=" + itemName;
        HttpGet getRequest = new HttpGet(storageAddress + "/file/info/" + params);
        CloseableHttpResponse response =  client.execute(getRequest);
        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
               try(InputStream inputStream =  response.getEntity().getContent()) {
                   String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                   return new FileInfoDto(content);
               }
            }
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
        return null;
    }


    private static boolean isFileChanged(File localFile, FileItem  fileItem) {
        return !(localFile.length() == fileItem.getFileInfo().getSize() && FileUtil.getSHA256(localFile).equals(fileItem.getFileInfo().getHashContent()));
    }

}
