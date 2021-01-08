package ir.javaclass.service;

import ir.javaclass.config.Commons;
import ir.javaclass.entity.FileItem;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class FileItemServiceTest {

    //String pk = "6327f02491711c147b051276a7d97f02c48bdcd66b659bd36b6861940f921644";
    File testFile = new File("E:\\blockchain-cs\\user1-syncs\\1.txt");

    @Test
    void deployFileContract() {
        String file_contract_address = FileItemService.deployFileContract();
        System.out.println("address: " + file_contract_address);
    }

    @Test
    void testGetUserFileCount(){
        try {
            BigInteger count = FileItemService.getUserFileCount(Commons.USER_PRIVATE_KEY);
            System.out.println(count);
        }catch (Exception ex){
            fail();
        }
    }


    @Test
    void testGetUserFileNames(){
        try {
            List<String> list = FileItemService.getUserFilesName(Commons.USER_PRIVATE_KEY);
            System.out.println(list.size());
            for(String name:list)
                System.out.println("index: " + list.indexOf(name) + " name: " + name);
        }catch (Exception ex){
            ex.printStackTrace();
            fail();
        }
    }



    @Test
    void uploadFileInfo() {
        try {
            if (testFile.exists()) {
                for(int i=0; i<20;i++) {
                    //FileItem fileItem = new FileItem(testFile, Collections.singletonList("node1.cs.ir"));
                    //FileItemService.uploadFileInfo(Commons.USER_PRIVATE_KEY, testFile.getName(), fileItem);
                    System.out.println(" index: " + i);
                }
            } else
                fail(" file not found...");
        }catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    void list() throws Exception {
       List<FileItem> fileItems = FileItemService.list(Commons.USER_PRIVATE_KEY);
        for (FileItem item : fileItems)
            System.out.println("#### " + item.toString());
    }

    @Test
    void testGetZeroIndex() throws Exception {
        FileItem fileItem = FileItemService.getZeroIndex(Commons.USER_PRIVATE_KEY, Commons.USER_PUBLIC_KEY, "1.jpg");
        System.out.println(fileItem.toString());
    }

    @Test
    void getFileChunkCount() throws Exception {
        BigInteger count = FileItemService.getFileChunkCount(Commons.USER_PRIVATE_KEY, "1.jpg");
        System.out.println("count: " + count);
    }

    @Test
    void getAllFileMetadata()throws Exception{
        List<FileItem> items = FileItemService.getAllFileMetadata(Commons.USER_PRIVATE_KEY, Commons.USER_PUBLIC_KEY, "Introducing Ethereum and Solidity.pdf");
        for(FileItem item :items)
            System.out.println(item.toString());
    }

}