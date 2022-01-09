import java.io.IOException;
import java.util.List;

public interface InputFileProcessor {
    void processInputFile() throws IOException;
    String applyRules(String originalSentence, List<Rule> rule) throws IOException;
}
