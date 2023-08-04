package ru.zinkin.android.phonebook.activity;

import android.content.res.Resources;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.parser.ParserTextFile;
import ru.zinkin.android.phonebook.pojo.Contact;
import ru.zinkin.android.phonebook.services.ContactService;

import java.io.IOException;

public class DeleteContactActivity extends AppCompatActivity {

    private ContactService contactService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.layout_delete_refresh);
        contactService = ContactService.getInstance();
        EditText searchNumber = findViewById(R.id.delete_phoneNumber_text);
        Button btn_info_del = findViewById(R.id.delete_contact_btn);

        TextView name = findViewById(R.id.delete_contact_info_nickname_text);
        TextView phone = findViewById(R.id.delete_contact_info_phone_text);
        TextView age = findViewById(R.id.delete_contact_info_age_text);

        btn_info_del.setOnClickListener(x -> {
            if(searchNumber.getText().toString().equals("")){
                delete_contact(phone.getText().toString());
            } else {
                print_info(searchNumber,name,phone,age);
            }
            searchNumber.setText("");
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(swipeRefreshLayout);
            }
        });

    }

    /*
        Моя реализация удаления
     */
    private void delete_contact(String searchNumber){
        try {
            contactService.deleteContact(searchNumber);
            ((ArrayAdapter<Contact>) ((ListView) findViewById(R.id.contacts_item))
                    .getAdapter())
                    .notifyDataSetChanged();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void print_info(EditText searchNumber,TextView name,TextView phone,TextView age){
        Contact contact = null;
        try {
            contact = contactService.getContactsByNumberPhone(searchNumber.getText().toString());
            name.setText(contact.getNickname());
            phone.setText(contact.getPhoneNumber());
            age.setText(contact.getAge().toString());
        }catch (Resources.NotFoundException e){
            Toast.makeText(this,"Данный контакт не найден",Toast.LENGTH_SHORT);
        }
    }

    private void refresh(SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setRefreshing(false);
    }

}