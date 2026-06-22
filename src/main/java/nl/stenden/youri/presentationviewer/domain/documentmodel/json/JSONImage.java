package nl.stenden.youri.presentationviewer.domain.documentmodel.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * JSONImage is een implementatie van JSONSlide die een plaatje representeert.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize
public class JSONImage extends JSONSlide {
    @JsonProperty("src")
    private String src;

    @JsonProperty("indentation")
    private int indentation;

    public String getType() {
        return "image";
    }

    public String getSrc() {
        return src;
    }

    public int getIndentation() {
        return indentation;
    }
}
