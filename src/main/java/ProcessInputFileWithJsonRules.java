import lombok.Builder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    public String applyRules(String originalSentence, List<Rule> rules) throws IOException {

        //        Generating errors from confusion set
        SpellCandidateGenerator spellCandidateGenerator = new SpellCandidateGenerator();
        StringBuilder originalSentenceCopy = new StringBuilder(spellCandidateGenerator.generateSentenceWithReplacedWord(originalSentence));

        //        Generating spelling errors from rules
        List<Integer> startingPositionsList = new ArrayList<Integer>();
        for (Rule rule : rules) {
            String substringToReplace = rule.getSubstringToReplace();
            String substringToReplaceWith = rule.getSubstringToReplaceWith();
            int startingPosition = originalSentenceCopy.indexOf(rule.getSubstringToReplace());
            int endPosition = startingPosition + substringToReplace.length();
            if (startingPosition > 0 && !startingPositionsList.contains(startingPosition)) {
                originalSentenceCopy.replace(startingPosition, endPosition, substringToReplaceWith);
            }
            startingPositionsList.add(startingPosition);
        }

        SpellingErrorsGenerator spellingErrorsGenerator = new SpellingErrorsGenerator();
        return spellingErrorsGenerator.transformSentence(originalSentenceCopy.toString());
    }

}
