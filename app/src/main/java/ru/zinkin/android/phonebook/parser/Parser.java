package ru.zinkin.android.phonebook.parser;

import ru.zinkin.android.phonebook.pojo.Contact;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Parser<T> {

    boolean existFile();
    boolean createFile() throws IOException;
    List<T> getAll() throws IOException;
    void saveAll(List<T> list) throws IOException;
    void clearTextFile() throws IOException;

    Contact getContactByNumberPhone(String phone) throws IOException;
}
