import exceptions.InvalidArgumentsNumberException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidArgumentsNumberException, IOException {
        if (args.length >= 3) {
            ProcessInputFileWithJsonRules processInputFileWithJsonRules = ProcessInputFileWithJsonRules
                    .builder()
                    .inputFilePath(args[0])
                    .outputFilePath(args[1])
                    .configFilePath(args[2])
                    .configReader(new ConfigReaderFromJson())
                    .build();
            ErrorsGenerator errorsGenerator = ErrorsGenerator.builder()
                    .inputFileProcessor(processInputFileWithJsonRules)
                    .build();
            errorsGenerator.getInputFileProcessor().processInputFile();
        } else {
            throw new InvalidArgumentsNumberException("Invalid arguments number");
        }
    }
}
