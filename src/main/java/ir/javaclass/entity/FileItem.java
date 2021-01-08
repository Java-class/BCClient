package ir.javaclass.entity;

import ir.javaclass.io.FileUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;
import org.web3j.tuples.generated.Tuple6;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class FileItem {
    private BigInteger index;
    private String owner;
    private String origName;
    private FileInfo fileInfo;
    private DateInfo dateInfo;
    private List<String> storage_address;

//    public FileItem(@NotNull File inputFile, List<String> storage_address){
//        this.name = inputFile.getName();
//
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setContentType(FilenameUtils.getExtension(inputFile.getName()));
//        fileInfo.setSize(inputFile.length());
//        fileInfo.setVersion(1);
//        fileInfo.setHashContent(FileUtil.getSHA256(inputFile));
//        fileInfo.setChunkIndex(1);
//        this.fileInfo = fileInfo;
//
//        DateInfo dateInfo = new DateInfo();
//        dateInfo.setCreateDate(new Date());
//        dateInfo.setLastAccessDate(new Date());
//        dateInfo.setModifiedDate(new Date());
//        this.dateInfo = dateInfo;
//
//        this.storage_address = storage_address;
//    }

    public FileItem(@NotNull File inputFile, String origName ,String chunkName, int chunkIndex, List<String> storage_address){
        this.origName = origName;
        FileInfo fileInfo = new FileInfo();
        fileInfo.setContentType(FilenameUtils.getExtension(inputFile.getName()));
        fileInfo.setSize(inputFile.length());
        fileInfo.setVersion(1);
        fileInfo.setHashContent(FileUtil.getSHA256(inputFile));
        fileInfo.setChunkIndex(chunkIndex);
        fileInfo.setChunkName(chunkName);
        this.fileInfo = fileInfo;

        DateInfo dateInfo = new DateInfo();
        dateInfo.setCreateDate(new Date());
        dateInfo.setLastAccessDate(new Date());
        dateInfo.setModifiedDate(new Date());
        this.dateInfo = dateInfo;

        this.storage_address = storage_address;
    }

    public FileItem(Tuple6<BigInteger, String, String, String, String, String> value) throws Exception {
        this.index = value.component1();
        this.owner = value.component2();
        this.origName = value.component3();
        this.fileInfo = new FileInfo(value.component4());
        this.dateInfo = new DateInfo(value.component5());
        this.storage_address = getStorageAsList(value.component6());
    }

    public String getStorageListAsString(){
        StringBuilder result = new StringBuilder();
        for(String add : storage_address){
            result.append("*").append(add);
        }
        return result.toString();
    }

    public List<String> getStorageAsList(String input){
        List<String> list = new ArrayList<>();
        String[] split = input.split("\\*");
        Collections.addAll(list, split);
        return list;
    }


}
