package ru.zinkin.android.phonebook.parser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class AuthParser{
    private File file;

    public AuthParser(File file) {
        this.file = file;
    }
    public boolean existFile() {
        return file.exists();
    }

    public boolean createFile() throws IOException {
        return file.createNewFile();
    }

    public String getPassword() throws IOException {
        try(Scanner scanner = new Scanner(file)){
            if(scanner.hasNextLine()){
                return scanner.nextLine();
            }
        }
        return null;
    }

    public void save(String str) throws IOException {
        clearTextFile();
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(file.getAbsolutePath())));
        bos.write(str.getBytes(StandardCharsets.UTF_8));
        bos.close();
    }

    public void clearTextFile() throws IOException {
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.flush();
        writer.close();
    }
}
