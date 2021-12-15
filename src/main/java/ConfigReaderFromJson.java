import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigReaderFromJson implements RulesConfigReader{
    @Override
    public List<Rule> readRulesConfig(String configFileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonConfigFile = Files.readString(Path.of(configFileName));
        List<Rule> rulesList = new ArrayList<>();
        rulesList = Arrays.asList(mapper.readValue(jsonConfigFile, Rule[].class));
        return rulesList;
    }
}
