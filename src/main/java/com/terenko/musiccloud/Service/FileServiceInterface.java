package com.terenko.musiccloud.Service;

public interface FileServiceInterface {
    String addToCatalog(byte[] data, String name);

    byte[] getFromCatalog(String path);

}
