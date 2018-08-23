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

/**
 * The method counts duplicate words in the text,
 * and return the result in the form of a word usage rating.
 * @param text is an incoming text for analyzing.
 * @return sorted rating List.
 * **/
    @Override
    public List <Map.Entry<String, Integer>> topTenRepeatingWords(String text) {
        String cleaneText = excludeSpecifiedWords(text);
        String[] words = cleaneText.split("[^а-яА-ЯёЁ+-]+");
        Map<String, Integer> rating = new HashMap<>();
        for(String word: words){
            if(!word.equals("")&&!word.equals("-")) {
               //word = word.toLowerCase();
                if (rating.containsKey(word))
                    rating.put(word, rating.get(word) + 1);
                else rating.put(word, 0);
            }
        }
        List<Map.Entry<String, Integer>> sortedRating = rating.entrySet().stream()
                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toList());


        return sortedRating;
    }

    @Override
    public String bracketChecker(String text) { //text - загруженный текст
        String result = "";
        boolean correctMark = true;
        if (text.isEmpty())
            return result = "correct";

        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < text.length(); i++)
        {
            char current = text.charAt(i);
            correctMark = true;
            if (current == '{' || current == '(' || current == '[')
            {
                stack.push(current);
            }


            if (current == '}' || current == ')' || current == ']')
            {
                if (stack.isEmpty())
                    return result = "incorrect";
                    correctMark = false;
                char last = stack.peek();
                if (current == '}' && last == '{' || current == ')' && last == '(' || current == ']' && last == '[')
                    stack.pop();

                else
                    return result = "incorrect";
                    correctMark = false;
            }


        }

        return result;

        /*String result = "";
        boolean correctMark = true;
        LinkedList<Character> stack = new LinkedList<>();
        for(int i = 0 ; i < text.length(); i++){
            correctMark = true;
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

        return result;*/
    }
        //Метод удаляет из текста allStopWords.
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