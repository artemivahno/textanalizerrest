package by.aivahno.textanalizer.service.analizator;

import java.io.File;

// Interface for processing text files
public interface FileReader {
    public String textExtractor(String filePath);

    public String textExtractor(File file);

};
