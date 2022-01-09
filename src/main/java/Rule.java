import lombok.Data;

import java.util.List;

@Data
public class Rule {
    private String substringToReplace;
    private String substringToReplaceWith;
    private double occurrenceProbability;
}
