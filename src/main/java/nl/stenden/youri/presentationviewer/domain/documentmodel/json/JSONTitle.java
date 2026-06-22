package nl.stenden.youri.presentationviewer.domain.documentmodel.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * JSONTitle is een implementatie van JSONSlide die een tekst element representeert.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize
public class JSONTitle extends JSONSlide {

    @JsonProperty("font")
    private String font;

    @JsonProperty("content")
    private String content;

    public String getType() {
        return "title";
    }

    public String getFont() {
        return font;
    }

    public String getContent() {
        return content;
    }
}


