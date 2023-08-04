package ru.zinkin.android.phonebook.parser;

import android.content.res.Resources;
import ru.zinkin.android.phonebook.pojo.Contact;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParserTextFile implements Parser<Contact>{
    private File file;

    public ParserTextFile(File file) {
        this.file = file;
    }

    @Override
    public boolean existFile() {
        return file.exists();
    }
    @Override
    public boolean createFile() throws IOException {
        return file.createNewFile();
    }

    @Override
    public List<Contact> getAll() throws IOException {
        Scanner scanner = new Scanner(file);
        List<Contact> contacts = new ArrayList<>();
        String[] el;
        String sc;
        while (scanner.hasNextLine()){
            sc = scanner.nextLine();
            if(sc.equals("")){
                return new ArrayList<>();
            }
            el = sc.split(";");
            contacts.add(new Contact(el[0],el[1],Integer.parseInt(el[2])));
        }
        scanner.close();
        return contacts;
    }

    @Override
    public void saveAll(List<Contact> list) throws IOException {
        clearTextFile();
        StringBuilder sb = new StringBuilder();
        for (Contact el:list) {
            sb.append(el.getNickname().toString()).append(';')
                    .append(el.getPhoneNumber()).append(';')
                    .append(el.getAge()).append(';')
                    .append('\n');
        }
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(file.getAbsolutePath())));
        bos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        bos.close();
    }
    @Override
    public void clearTextFile() throws IOException {
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.flush();
        writer.close();
    }
    @Override
    public Contact getContactByNumberPhone(String phone) throws IOException {
        List<Contact> contacts = getAll();
        Contact contact = contacts.stream()
                .filter(x -> x.getPhoneNumber().equals(phone))
                .findAny()
                .orElseThrow(Resources.NotFoundException::new);

        return contact;
    }
}
