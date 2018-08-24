package by.aivahno.textanalizer.model;

import org.springframework.stereotype.Component;

import java.io.File;

//модель загружаемых файлов
@Component
public class Text {
    private File file;

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
