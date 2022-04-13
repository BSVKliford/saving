package ru.netology.saving;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static final String absoluteName = "K:/Games/savegames/";
    static final String nameZip = "saving.zip";

    public static void main(String[] args) {
        GameProgress gp1 = new GameProgress(87, 42, 4, 4.5);
        GameProgress gp2 = new GameProgress(50, 78, 7, 65.5);
        GameProgress gp3 = new GameProgress(15, 42, 8, 80.4);
        List<String> fileSaving = new ArrayList<>();
        saveGame(absoluteName + "gp1.data", gp1);
        fileSaving.add(absoluteName + "gp1.data");
        saveGame(absoluteName + "gp2.data", gp2);
        fileSaving.add(absoluteName + "gp2.data");
        saveGame(absoluteName + "gp3.data", gp3);
        fileSaving.add(absoluteName + "gp3.data");
        zipFiles(absoluteName + nameZip, fileSaving);
        for (String s : fileSaving) {
            File dir = new File(s);
            dir.delete();
        }
    }

    public static void saveGame(String name, GameProgress gp) {
        try (FileOutputStream save = new FileOutputStream(name);
             ObjectOutputStream saveSer = new ObjectOutputStream(save)) {
            saveSer.writeObject(gp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String nameZip, List<String> fileSaving) {
        try (FileOutputStream fos = new FileOutputStream(nameZip);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            for (String s : fileSaving) {
                File srcFile = new File(s);
                ZipEntry zipEntry = new ZipEntry(srcFile.getName());
                zipOut.putNextEntry(zipEntry);
                zipOut.write(Files.readAllBytes(srcFile.toPath()));
                zipOut.closeEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}