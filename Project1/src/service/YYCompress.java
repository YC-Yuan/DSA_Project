package service;

import java.io.IOException;

public interface YYCompress {

    public void compress(String originalPath) throws IOException;

    public void fileCompress(String filePath) throws IOException;

    public void folderCompress(String folderPath) throws IOException;

    public void depress(String originalPath) throws IOException, ClassNotFoundException;

    void fileDepress( String destinationPath) throws IOException, ClassNotFoundException;

    public void folderDepress() throws IOException, ClassNotFoundException;

    void createFolder(String folderPath);
}
