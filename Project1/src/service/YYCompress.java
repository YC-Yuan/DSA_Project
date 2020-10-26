package service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface YYCompress {
    public String destinationPath = null;

    public void compress(String originalPath) throws IOException;

    public void fileCompress(String filePath) throws IOException;

    public void folderCompress(String folderPath) throws IOException;

    public void depress(String filePath);

    void createFolder(String folderPath);
}
