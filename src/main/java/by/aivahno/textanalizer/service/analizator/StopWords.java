package by.aivahno.textanalizer.service.analizator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StopWords {

    @Autowired
    private FileReader fileReaderImpForStopWords;
    //Loads StopWords
    public String getAllStopWords(){
        String stopWords = fileReaderImpForStopWords.textExtractor(
                "rusStopWords.txt");
        return stopWords;
    }
}
