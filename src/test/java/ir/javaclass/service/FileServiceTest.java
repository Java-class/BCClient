package ir.javaclass.service;

import ir.javaclass.config.Commons;
import ir.javaclass.entity.FileItem;
import ir.javaclass.model.FileInfoDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@RequiredArgsConstructor
class FileServiceTest {

    private final FileService fileService;


    @Test
    void listFile() throws Exception {
        List<FileItem> fileItems = fileService.listFile(Commons.USER_PRIVATE_KEY);

        Map<String, List<FileItem>> map = fileItems.stream().collect(Collectors.groupingBy(FileItem::getOrigName));
        System.out.println(map.size());

        for(FileItem fileItem :fileItems) {
            System.out.println("### " + fileItem.toString());
//            File file = FileService.downloadFile(Commons.USER_PUBLIC_KEY, Commons.USER_PRIVATE_KEY, fileItem);
//            if(file.exists())
//                System.out.println(file.getAbsolutePath());

        }
    }

    @Test
    void uploadFile() {
    }

    @Test
    void upload() throws Exception {
        File file = new File("E:\\blockchain-cs\\user1-syncs\\Introducing Ethereum and Solidity.pdf");
        if(file.exists()) {
            List<TransactionReceipt> transactionReceipts = fileService.uploadFile(Commons.USER_PUBLIC_KEY, Commons.USER_PRIVATE_KEY, file, null);
            for (TransactionReceipt transactionReceipt : transactionReceipts) {
               /* System.out.println("#### status: " + transactionReceipt.getStatus());
                System.out.println("#### tx index: " + transactionReceipt.getTransactionIndex());
                System.out.println("#### tx hash: " + transactionReceipt.getTransactionHash());
                System.out.println("#### block number: " + transactionReceipt.getBlockNumber());
                System.out.println("#### block hash: " + transactionReceipt.getBlockHash());
                System.out.println("#### gas used: " + transactionReceipt.getGasUsed());*/
            }
        }
    }

    @Test
    void downloadFromCloud()throws Exception{
        File test = new File("E:\\blockchain-cs\\user1-syncs\\big2.mkv");
        List<FileItem> fileItems = fileService.listFile(Commons.USER_PRIVATE_KEY);
        Map<String, List<FileItem>> map = fileItems.stream().collect(Collectors.groupingBy(FileItem::getOrigName));
        System.out.println(map.size());
//        FileService.downloadFromCloud(Commons.USER_PUBLIC_KEY, Commons.USER_PRIVATE_KEY, map.get("Introducing Ethereum and Solidity.pdf"),  test);
//        System.out.println("### file size: " + test.length());
    }

    @Test
    void getFileInfo() throws IOException, ParseException {
        FileInfoDto infoDto = fileService.getFileInfo(Commons.USER_PUBLIC_KEY, "48a4caa75077c98520fe6edfcb7212ef8d34cb528adbc483756fd0c2a4bab38a", "http://192.168.1.105:8080");
        assert infoDto != null;
        System.out.println(infoDto.toString());
    }

    @Test
    void testSync(){
        String syncFolderAdd = "E:\\blockchain-cs\\user1-syncs\\";
        fileService.sync(Commons.USER_PUBLIC_KEY, Commons.USER_PRIVATE_KEY, syncFolderAdd);
    }
}