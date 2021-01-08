package ir.javaclass.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@NoArgsConstructor
public class FileInfo {

    private String chunkName;
    private String hashContent;
    private String contentType;
    private long size;
    private long version;
    private long chunkIndex;

    public FileInfo(String input) throws Exception {
        String[] array = input.split("#");
        if (array.length == 6) {
            this.chunkName = array[0];
            this.hashContent = array[1];
            this.contentType = array[2];
            this.size = Long.parseLong(array[3]);
            this.version = Long.parseLong(array[4]);
            this.chunkIndex = Long.parseLong(array[5]);
        } else
            throw new Exception();
    }

    public String serialize() {
        return chunkName +
                "#" +
                hashContent +
                "#" +
                contentType +
                "#" +
                size +
                "#" +
                version +
                "#" +
                chunkIndex;
    }

}
