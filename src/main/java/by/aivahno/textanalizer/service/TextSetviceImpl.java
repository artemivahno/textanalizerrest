package by.aivahno.textanalizer.service;

import by.aivahno.textanalizer.model.Analyze;
import by.aivahno.textanalizer.model.Text;
import by.aivahno.textanalizer.service.analizator.FileReader;
import by.aivahno.textanalizer.service.analizator.TextAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("textService")
public class TextSetviceImpl implements TextService {

    @Autowired
    private TextAnalyzer textAnalyzer; // Сервис для анализа текста
    @Autowired
    private FileReader fileReaderImpl;// Сервис для загрузки текста
    @Autowired
    private Text text;
    @Autowired
    private Analyze analyze;

    //Method return field file from Text object
    public File getFile() {
        return text.getFile();
    }

    //Method set field file from Text object
    public void setFile(File file) {
        text.setFile(file);
    }

    //Method return true if field file in the Text object exist
    public boolean isFileExist(File file) {
        return file.exists();
    }

    //Method to delete file
    public void deleteFile(){
        if(text.getFile().exists())
            text.getFile().delete();

    }
    //Method return Text object
    public Text getText(){
        return text;
    }



     //Method does text analysis to get the 10 most frequently used words in the text
    public Analyze topMatchedWordsTextAnalyze() {
        String txt = "";
        txt = fileReaderImpl.textExtractor(text.getFile());
        List<Map.Entry<String, Integer>> sortedReiting = textAnalyzer.topTenRepeatingWords(txt);
        List<Map.Entry<String, Integer>> topMatchedWords = new ArrayList<>();
        int count = 0;
        List <String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> word: sortedReiting){
            if(count == 10 && !word.getValue().equals(""))break;
            topMatchedWords.add(word);
            count++;
        }
        analyze.setTopMatchedWords(topMatchedWords);
        analyze.setBracketCheck(null);
        return analyze;
    }

    //Method does text analysis to get the iformation about the correctness of the brackets in the text
    @Override
    public Analyze bracketCheckTextAnalyze() {
        String txt = "";
        txt = fileReaderImpl.textExtractor(text.getFile());

        String bracketResult = textAnalyzer.bracketChecker(txt);
        analyze.setTopMatchedWords(null);
        analyze.setBracketCheck(bracketResult);
        return analyze;
    }
}
