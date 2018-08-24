package by.aivahno.textanalizer.service;

import by.aivahno.textanalizer.model.Analyze;
import by.aivahno.textanalizer.model.Text;

import java.io.File;


// Сервис для работы с текстом и анализа компонентов

public interface TextService {
    public File getFile();
    public void setFile(File texts);
    public boolean isFileExist(File text);
    public void deleteFile();
    public Text getText();
    public Analyze topMatchedWordsTextAnalyze();
    public Analyze bracketCheckTextAnalyze();
}
