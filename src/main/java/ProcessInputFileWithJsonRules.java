import lombok.Builder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Builder
public class ProcessInputFileWithJsonRules implements InputFileProcessor {

    private String inputFilePath;
    private String outputFilePath;
    private String configFilePath;
    private ConfigReaderFromJson configReader;

    @Override
    public void processInputFile() throws IOException {
        FileWriter output = new FileWriter(outputFilePath);
        Files.lines(Paths.get(inputFilePath))
                .map(sentence -> {
                    try {
                        return applyRules(sentence, configReader.readRulesConfig(configFilePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .forEach(sentence -> {
                    try {
                        output.write(sentence + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        output.close();
    }

    @Override
    public String applyRules(String originalSentence, List<Rule> rules) {
        StringBuilder sentenceWithError = new StringBuilder(originalSentence);
        for (Rule rule : rules) {
            String substringToReplace = rule.getSubstringToReplace();
            String substringToReplaceWith = rule.getSubstringToReplaceWith();
            int startingPosition = originalSentence.indexOf(rule.getSubstringToReplace());
            int endPosition = startingPosition + substringToReplace.length();
            if(startingPosition > 0 && sentenceWithError.charAt(startingPosition) == originalSentence.charAt(startingPosition)) {
                sentenceWithError.replace(startingPosition, endPosition, substringToReplaceWith);
            }

        }
        return sentenceWithError.toString();
    }
}
