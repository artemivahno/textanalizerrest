package by.aivahno.textanalizer.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// модель сущностей для анализа
@Component
public class Analyze {
    private List<Map.Entry<String, Integer>> topMatchedWords;
    private String bracketCheck;

    public List<Map.Entry<String, Integer>> getTopMatchedWords() {
        return topMatchedWords;
    }

    public void setTopMatchedWords(List<Map.Entry<String, Integer>> topMatchedWords) {
        this.topMatchedWords = topMatchedWords;
    }

    public String getBracketCheck() {
        return bracketCheck;
    }

    public void setBracketCheck(String bracketCheck) {
        this.bracketCheck = bracketCheck;
    }
}
