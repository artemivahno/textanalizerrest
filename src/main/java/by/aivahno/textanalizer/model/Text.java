package by.aivahno.textanalizer.model;

import org.springframework.stereotype.Component;

import java.io.File;


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
