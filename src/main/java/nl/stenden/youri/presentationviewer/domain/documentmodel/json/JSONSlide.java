package nl.stenden.youri.presentationviewer.domain.documentmodel.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Setter;

/**
 * Abstract class voor de verschillende JSON slide elementen
 * deze zijn van het type title, text of image,
 * Jackson gebruikt deze structuur om een json file op de juiste manier in te lezen en te splitsen naar een juist document model type zoals titel, text of image
 */
@Data
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JSONTitle.class, name = "title"),
        @JsonSubTypes.Type(value = JSONText.class, name = "text"),
        @JsonSubTypes.Type(value = JSONImage.class, name = "image")
})
public abstract class JSONSlide {
    private String type;
}
