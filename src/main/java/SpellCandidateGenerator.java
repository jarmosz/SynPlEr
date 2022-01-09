import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellChecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class SpellCandidateGenerator {

    private final double percentageOfWordToChangeWithConfusionSet = 0.8;
    private final double percentageOfWordToDelete = 0.1;
    private final double percentageOfWordToReplaceWithAdjecent = 0.1;

    private SpellChecker spellChecker;
    private SpellDictionaryHashMap dictionaryHashMap;

    SpellCandidateGenerator() throws IOException {
        File dict = new File("dictionary/pl_PL.dic");
        dictionaryHashMap = new SpellDictionaryHashMap(dict);
        spellChecker = new SpellChecker(dictionaryHashMap);
    }

    public String getSuggestion(String misspelledWord) {

        @SuppressWarnings("unchecked")
        List<Word> wordSuggestions = spellChecker.getSuggestions(misspelledWord, 0);
        List<String> suggestions = new ArrayList<String>();
        for (Word suggestion : wordSuggestions) {
            suggestions.add(suggestion.getWord());
        }
        Random rand = new Random();
        if(suggestions.isEmpty()){
            return misspelledWord;
        }
        String suggestion = suggestions.get(Math.abs(rand.nextInt(suggestions.size())));
        return suggestion;
    }

    private String[] deleteWord(String[] words, int idx){
        List<String> listOfWords = new ArrayList<String>(Arrays.asList(words));
        listOfWords.remove(idx);
        return listOfWords.toArray(String[]::new);
    }

    private String[] substituteWord(String[] words, int idx){
        String wordToReplace = words[idx];
        if(idx == words.length - 1){
            words[idx] = words[idx-1];
            words[idx-1] = wordToReplace;
        }
        else {
            words[idx] = words[idx+1];
            words[idx+1] = wordToReplace;
        }
        return words;
    }

    public String generateSentenceWithReplacedWord(String originalSentence){
        String[] words = originalSentence.split(" ");

        Random random = new Random();
        int idx = 0;
        for (String word : words) {
            if (random.nextFloat() <= percentageOfWordToDelete) {
                int temp = (Math.random() <= 0.5) ? 1 : 2;
                if (temp == 1) {
                    words = deleteWord(words, idx);
                    idx--;
                }
                else {
                    words = substituteWord(words, idx);
                }
            } else if (random.nextFloat() <= percentageOfWordToChangeWithConfusionSet) {
                if (word.length() >= 3) {
                    words[idx] = getSuggestion(word);
                }
            }
            idx++;
        }
        return String.join(" ", words);
    }


}