package by.aivahno.textanalizer.service.analizator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



@Service("textAnalizer")
public class TextAnalizerImpl implements TextAnalyzer {

    @Autowired
    private StopWords stopWords;

    ////Method for word processing and sorting
    @Override
    public List <Map.Entry<String, Integer>> topTenRepeatingWords(String text) {
        String cleaneText = excludeSpecifiedWords(text);
        String[] words = cleaneText.split("[^а-яА-ЯёЁ+-]+");
        Map<String, Integer> rating = new HashMap<>();
        for(String word: words){
            if(!word.equals("")&&!word.equals("-")) {
                if (rating.containsKey(word))
                    rating.put(word, rating.get(word) + 1);
                else rating.put(word, 1);
            }
        }
        List<Map.Entry<String, Integer>> sortedRating = rating.entrySet().stream()
                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toList());


        return sortedRating;
    }

    @Override
    public String bracketChecker(String text) {
        String result = "";
        boolean correctMark = true;
        LinkedList<Character> stack = new LinkedList<>();
        for(int i = 0 ; i < text.length(); i++){
            Character ch = text.charAt(i);

            switch (ch){
                case '{':
                case '(':
                case '[':
                    stack.push(ch);
                    break;
                case '}':
                case ')':
                case ']':
                    if(!stack.isEmpty()){
                        Character chr = stack.pop();
                        if(ch.equals('}') && !chr.equals('{') ||
                                ch.equals(')') && !chr.equals('(') ||
                                ch.equals('[') && !chr.equals(']'))
                                    result = "incorrect";
                                    correctMark = false;

                    }else{
                        result = "incorrect";
                        correctMark = false;
                    }
                    break;
                    default:
                        break;
            }
        }
        if(!stack.isEmpty()){
            result = "incorrect";
            correctMark = false;
        }

        if(correctMark) {
            result = "correct";
        }

        return result;
    }
        //The method removes all StopWords from the text.
    private String excludeSpecifiedWords(String text){

        String allStopWords = stopWords.getAllStopWords();
        String [] allStopWordsArr = allStopWords.split("[^а-яА-ЯёЁ+-]+");

        //get rid of duplicates
        Set<String> stopWordsWithoutDuplicates = new HashSet<>();
        for(String stopWord: allStopWordsArr){
            stopWordsWithoutDuplicates.add(stopWord);
        }


        String resultText = text;
        for(String stopWord: stopWordsWithoutDuplicates){
            resultText = Pattern.compile("\\b(" + stopWord + ")\\b", Pattern.CASE_INSENSITIVE |
                    Pattern.UNICODE_CASE).matcher(resultText).replaceAll("");
        }
        return resultText;
    }
}
