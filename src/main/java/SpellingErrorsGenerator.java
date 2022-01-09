import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SpellingErrorsGenerator {

    final double percentageOfWordToChange = 10;

    public String transformSentence(String originalSentence) {
        String[] words = originalSentence.split(" ");
        int numberOfWordsToChange = (int)(words.length * (percentageOfWordToChange/100)) + 1;
        Random rand = new Random();
        int counter = 0;
        while(counter < numberOfWordsToChange){
            int randomWordPosition = ThreadLocalRandom.current().nextInt(0, words.length);
            int randomCategory = Math.abs(rand.nextInt(3 - 1) + 1);
            if(words[randomWordPosition].length() > 5) {
                switch (randomCategory) {
                    case 1:
                        words[randomWordPosition] = deleteRandomCharacter(words[randomWordPosition]);
                        break;
                    case 2:
                        words[randomWordPosition] = insertRandomCharacter(words[randomWordPosition]);
                        break;
                    case 3:
                        words[randomWordPosition] = substituteRandomCharacter(words[randomWordPosition]);
                        break;

                }
            }
            counter++;
        }
        return String.join(" ", words);
    }

    private String deleteRandomCharacter(String originalWord) {
        StringBuilder newWord = new StringBuilder(originalWord);
        Random rand = new Random();
        int pos = Math.abs(rand.nextInt(newWord.length() - 1));
        newWord.deleteCharAt(pos);
        return newWord.toString();
    }

    private String insertRandomCharacter(String originalWord){
        Random rand = new Random();
        int pos = Math.abs(rand.nextInt(originalWord.length() - 1));
        char randomCharacter = (char)Math.abs(rand.nextInt(26) + 'a');
        return originalWord.substring(0, pos) + randomCharacter + originalWord.substring(pos);
    }

    private String substituteRandomCharacter(String originalWord){
        StringBuilder newWord = new StringBuilder(originalWord);
        Random rand = new Random();
        int pos = Math.abs(rand.nextInt(originalWord.length() - 1));
        int nextPos = Math.abs(rand.nextInt(originalWord.length() - 1));
        while(pos == nextPos) {
            pos = Math.abs(rand.nextInt(originalWord.length() - 1));
            nextPos = Math.abs(rand.nextInt(originalWord.length() - 1));
        }
        char previousCharacter = originalWord.charAt(pos);
        newWord.replace(pos, pos, Character.toString(originalWord.charAt(nextPos)));
        newWord.replace(nextPos, nextPos, Character.toString(previousCharacter));

        return newWord.toString();
    }
}
