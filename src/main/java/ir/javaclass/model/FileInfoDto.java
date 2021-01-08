package ir.javaclass.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Getter
@Setter
@ToString
public class FileInfoDto {
    private final String name;
    private final String hash;
    private final long size;
    private final long lastModification;

    public FileInfoDto(String jsonInput) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonInput);
        this.name = (String)jsonObject.get("name");
        this.hash = (String)jsonObject.get("hash");
        this.size = (Long)jsonObject.get("size");
        this.lastModification = (Long)jsonObject.get("lastModification");
    }
}