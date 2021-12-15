import java.io.IOException;
import java.util.List;

public interface RulesConfigReader {
    List<Rule> readRulesConfig(String configFileName) throws IOException;
}
