package ir.javaclass.io;

import ir.javaclass.config.Commons;
import ir.javaclass.config.FileDelimiter;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSplitter {

    private File origFile;
    private int splitSize;
    private File outDirectory;
    private String chunkSuffix = Commons.CHUNK_SUFFIX;

    public FileSplitter(File origFile, int splitSize, File outDirectory) {
        this.origFile = origFile;
        this.splitSize = splitSize;
        this.outDirectory = outDirectory;
    }


    public List<File> doSplit() throws IOException {
        List<File> files = new ArrayList<>();
        int partCounter = 1;
        int sizeOfFiles = 1024 * 1024 * splitSize;// 1MB
        byte[] buffer = new byte[sizeOfFiles];
        String tempChunkDir = outDirectory.getAbsolutePath() + FileDelimiter.getSystemDelimiter() + System.currentTimeMillis() + FileDelimiter.getSystemDelimiter();
        String fixPart = outDirectory.getAbsolutePath() + FileDelimiter.getSystemDelimiter() + System.currentTimeMillis() + FileDelimiter.getSystemDelimiter() + origFile.getName() + chunkSuffix;
        FileUtils.forceMkdir(new File(outDirectory.getAbsolutePath() + FileDelimiter.getSystemDelimiter() + System.currentTimeMillis() + FileDelimiter.getSystemDelimiter()));
        try (FileInputStream fis = new FileInputStream(origFile);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            int bytesAmount = 0;
            while ((bytesAmount = bis.read(buffer)) > 0) {
                String filePartName = generateChunkName(fixPart, partCounter++);
                File newFile = new File(filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
                files.add(newFile);
            }
        }
        return files;
    }

    private String generateChunkName(String fixPart, int counter){
        int nDigits = (int) ((Math.floor(Math.log10(Math.abs(counter)))) + 1);
        String dynamicPart = "00000";
        dynamicPart = dynamicPart.substring(0,(4-nDigits)) + counter;
        return fixPart + dynamicPart;
    }

}
