package com.terenko.musiccloud.Service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.logging.Logger;

@Service
public class FileService implements FileServiceInterface {
    private static Logger log = Logger.getLogger(FileService.class.getName());
    private static final int BUFFER_SIZE = 4096;

    final static String PATH = "E:\\MusicCloudCatalog\\";

    @Override
    public String addToCatalog(byte[] data, String name) {
        File file = new File(PATH + name);
        try {
            InputStream is = new ByteArrayInputStream(data);
            OutputStream os = new FileOutputStream(file);
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            log.info(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    @Override
    public byte[] getFromCatalog(String path) {
        File file = new File(path);

        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            return os.toByteArray();
        } catch (FileNotFoundException e) {
            log.info(e.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
