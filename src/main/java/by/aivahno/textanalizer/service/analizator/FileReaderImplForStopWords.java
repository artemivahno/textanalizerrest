package by.aivahno.textanalizer.service.analizator;

import org.springframework.stereotype.Service;

import java.io.*;

@Service("fileReaderImpForStopWords")
public class FileReaderImplForStopWords implements FileReader {

    public String textExtractor(String filePath) {

        StringBuilder textBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + filePath),
                    "UTF-8"))) {
                while (bufferedReader.ready()) {
                    textBuilder.append(bufferedReader.readLine()).append("/n");
                }

            } catch (IOException exc) {
                exc.printStackTrace();
            }
        return textBuilder.toString();
    }


    public String textExtractor(File file) {
        StringBuilder textBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            while (bufferedReader.ready()) {
                textBuilder.append(bufferedReader.readLine()).append("\n");
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return textBuilder.toString();
    }



}
