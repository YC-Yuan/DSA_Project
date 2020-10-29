package service;

import java.io.IOException;

public interface YYCompress {

    public void compress(String originalPath) throws IOException;

    public void fileCompress(String filePath) throws IOException;

    public void folderCompress(String folderPath) throws IOException;

    public void decompress(String originalPath) throws IOException, ClassNotFoundException;

    public void fileDecompress(String destinationPath) throws IOException, ClassNotFoundException;

    public void folderDecompress() throws IOException, ClassNotFoundException;

    public void createFolder(String folderPath);
}
