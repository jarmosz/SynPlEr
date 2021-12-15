import lombok.Data;

@Data
public class Rule {
    private String substringToReplace;
    private String substringToReplaceWith;
    private double occurrenceProbability;
}
