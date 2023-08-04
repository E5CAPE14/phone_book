package ru.zinkin.android.phonebook.services;
import android.content.res.Resources;
import ru.zinkin.android.phonebook.parser.ParserTextFile;
import ru.zinkin.android.phonebook.pojo.Contact;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContactService {
    private static ContactService contactService;

    private List<Contact> contacts;
    private ParserTextFile parserTextFile;
    private ContactService() {
        this.parserTextFile = new ParserTextFile(new File("/data/user/0/ru.zinkin.android.phonebook/files/contacts.txt"));
        try {
            this.contacts = this.parserTextFile.getAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ContactService getInstance() {
        if(contactService == null){
            contactService = new ContactService();
        }
        return contactService;
    }

    public List<Contact> getAll(){
        return this.contacts;
    }

    public void saveContact(Contact contact) throws IOException {
        this.contacts.add(contact);
        this.parserTextFile.saveAll(contacts);
    }

    public void deleteContact(String phoneNumber) throws IOException {
        Contact contact = contacts.stream()
                .filter(x -> x.getPhoneNumber().equals(phoneNumber))
                .findFirst().orElseThrow(Resources.NotFoundException::new);
        this.contacts.remove(contact);
        this.parserTextFile.saveAll(contacts);
    }

    public Contact getContactsByName(String name){
        return this.contacts.stream()
                .filter(x -> x.getNickname().equals(name))
                .findFirst().orElseThrow(Resources.NotFoundException::new);
    }

    public Contact getContactsByNumberPhone(String phoneNumber){
        return this.contacts.stream()
                .filter(x -> x.getPhoneNumber().equals(phoneNumber))
                .findFirst().orElseThrow(Resources.NotFoundException::new);
    }
}
