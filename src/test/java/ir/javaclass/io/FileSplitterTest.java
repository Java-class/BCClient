package ir.javaclass.io;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FileSplitterTest {

    @Test
    void doSplit() throws IOException {

        File bigFile = new File("E:\\blockchain-cs\\user1-syncs\\big.mkv");
        System.out.println("hash orig File: " + FileUtil.getSHA256(bigFile));
        File chunkDir = new File("E:\\blockchain-cs\\user1-temp\\chunks\\");
        FileSplitter splitter = new FileSplitter(bigFile, 1, chunkDir);
        List<File> chunks = splitter.doSplit();
        chunks = chunks.stream().sorted( Comparator.comparing(File::getName)).collect(Collectors.toList());
        for(File f : chunks)
            System.out.println("chunk name: " + f.getName());
        File mergedFile  = new File("E:\\blockchain-cs\\user1-temp\\chunks\\append.mkv");
        for(File file : chunks) {
            System.out.println(file.getName());
            if(file.getName().contains("-chunk")) {
                FileInputStream inputStream = FileUtils.openInputStream(file);
                writeToFile(inputStream, mergedFile.getAbsolutePath(), true);
                inputStream.close();
            }
        }
        System.out.println("hash merged File: " + FileUtil.getSHA256(mergedFile));


    }

//    public static void mergeFiles(File[] files, File mergedFile) {
//
//        FileWriter fstream = null;
//        BufferedWriter out = null;
//        try {
//            fstream = new FileWriter(mergedFile, true);
//            out = new BufferedWriter(fstream);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//        for (File f : files) {
//            System.out.println("merging: " + f.getName());
//            FileInputStream fis;
//            try {
//                fis = new FileInputStream(f);
//                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//
//                String aLine;
//                while ((aLine = in.readLine()) != null) {
//                    out.write(aLine);
//                    out.newLine();
//                }
//
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void writeToFile(InputStream inputStream, String filePath,boolean append){
        OutputStream outputStream = null;
        try {
            outputStream =
                    new FileOutputStream(new File(filePath),append);
            int read = 0;
            byte[] bytes = new byte[4096];
            while (inputStream.available()>0) {
                read = inputStream.read(bytes);
                outputStream.write(bytes, 0, read);
            }
            //System.out.println("Write Data to File: " + filePath + " Done!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }
    }
}