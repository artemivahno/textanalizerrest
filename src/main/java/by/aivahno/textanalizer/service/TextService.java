package by.aivahno.textanalizer.service;

import by.aivahno.textanalizer.model.Analyze;
import by.aivahno.textanalizer.model.Text;

import java.io.File;

/**
 * Service for work with Text and Analyze  components
 *
 * @author Denis Lesheniuk
 * @version 1.0
 *
 */
public interface TextService {
    public File getFile();
    public void setFile(File texts);
    public boolean isFileExist(File text);
    public void deleteFile();
    public Text getText();
    public Analyze topMatchedWordsTextAnalyze();
    public Analyze bracketCheckTextAnalyze();
}
