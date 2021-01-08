package ir.javaclass.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DateInfo {
    private Date createDate;
    private Date modifiedDate;
    private Date lastAccessDate;
    public DateInfo(String input) throws Exception {
        String[] array = input.split("#");
        if (array.length == 3) {
            this.createDate = new Date(Long.parseLong(array[0]));
            this.lastAccessDate = new Date(Long.parseLong(array[1]));
            this.modifiedDate = new Date(Long.parseLong(array[2]));
        } else
            throw new Exception();
    }

    public String serialize() {
        return createDate.getTime()
                + "#"
                + modifiedDate.getTime()
                + "#"
                + lastAccessDate.getTime();
    }

}



