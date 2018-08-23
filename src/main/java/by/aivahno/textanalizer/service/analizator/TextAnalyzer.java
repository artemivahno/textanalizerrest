package by.aivahno.textanalizer.service.analizator;

import java.util.List;
import java.util.Map;

public interface TextAnalyzer {
    public List<Map.Entry<String, Integer>> topTenRepeatingWords(String text);

    public String bracketChecker(String text);
}
