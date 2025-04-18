package me.doublenico.rm.utils;

import java.io.File;

public class FileManager {

    public File getInstallationFolder() {
        return new File(System.getProperty("user.dir"));
    }
}