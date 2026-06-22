package nl.stenden.youri.presentationviewer.domain.documentmodel.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * JSONText is een implementatie van JSONSlide die een tekst element representeert.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize
public class JSONText extends JSONSlide {
    @JsonProperty("font")
    private String font;

    @JsonProperty("indentation")
    private int indentation;

    @JsonProperty("content")
    private String content;

    public String getType() {
        return "text";
    }

    public String getFont() {
        return font;
    }

    public int getIndentation() {
        return indentation;
    }

    public String getContent() {
        return content;
    }
}
