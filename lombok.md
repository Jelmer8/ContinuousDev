# Lombok

Java Lombok is een hulpprogramma (bibliotheek) voor Java-ontwikkelaars dat helpt om boilerplate code (zoals getters,
setters, constructors, etc.) te verminderen.

Het gebruikt annotaties die tijdens het compileren worden verwerkt om de benodigde code automatisch te genereren,
waardoor de code schoner en compacter wordt.

gebruik:

```pom.xml
  <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
<dependency>
```

voorbeeld met de volgende Lombok annotaties,

```
@EqualsAndHashCode(callSuper = true)  roept de equals en hashCode methoden aan van de superclass BaseDto in dit geval 
@Data(genereert getters, setters,toString),
@Builder (genereert een builder patroon),
@NoArgsConstructor  (genereert een no-argument constructor),
@AllArgsConstructor  (genereert een constructor met alle velden)
public class TextBaseDto extends BaseDto {
    private String font;
}
```

zonder lombok hadden we dat er zelf getters, setters, equals, hashCode, toString, Builders en andere veelvoorkomende
methoden hadden moeten schrijven.
en dat had er zo uit gezien , even zonder Builder want deze is vrij groot en niet relevant voor dit voorbeeld

```
    public class TextBaseDto extends BaseDto {
    private String font;

    public TextBaseDto(String font) {
        this.font = font;
    }

    public TextBaseDto() {
    }

    public static TextBaseDtoBuilder builder() {
        return new TextBaseDtoBuilder();
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String toString() {
        return "TextBaseDto(font=" + this.getFont() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TextBaseDto)) return false;
        final TextBaseDto other = (TextBaseDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (!super.equals(o)) return false;
        final Object this$font = this.getFont();
        final Object other$font = other.getFont();
        if (this$font == null ? other$font != null : !this$font.equals(other$font)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TextBaseDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $font = this.getFont();
        result = result * PRIME + ($font == null ? 43 : $font.hashCode());
        return result;
    }

    public static class TextBaseDtoBuilder {
        private String font;

        TextBaseDtoBuilder() {
        }

        public TextBaseDtoBuilder font(String font) {
            this.font = font;
            return this;
        }

        public TextBaseDto build() {
            return new TextBaseDto(this.font);
        }

        public String toString() {
            return "TextBaseDto.TextBaseDtoBuilder(font=" + this.font + ")";
        }
    }
}
```
