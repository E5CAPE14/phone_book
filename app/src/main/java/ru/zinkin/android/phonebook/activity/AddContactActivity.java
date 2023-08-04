package ru.zinkin.android.phonebook.activity;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.parser.ParserTextFile;
import ru.zinkin.android.phonebook.pojo.Contact;
import ru.zinkin.android.phonebook.services.ContactService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {


    private ContactService contactService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.layout_create_refresh);
        contactService = ContactService.getInstance();
        EditText nickname = findViewById(R.id.input_nickname);
        EditText number = findViewById(R.id.input_phone_number);
        EditText age = findViewById(R.id.input_age);
        Button add = findViewById(R.id.add_contact_btn);

        add.setOnClickListener(x -> {
            add_contact(nickname,number,age);
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(swipeRefreshLayout);
            }
        });

    }

    private void refresh(SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void add_contact(EditText nickname,EditText number,EditText age){
        try {
            contactService.saveContact(
                    new Contact(nickname.getText().toString(),number.getText().toString(),Integer.parseInt(age.getText().toString())));
            ArrayAdapter<Contact> adapter = (ArrayAdapter<Contact>) ((ListView)findViewById(R.id.contacts_item)).getAdapter();
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            Toast.makeText(this,"Не удалось сохранить клиента",Toast.LENGTH_SHORT).show();
        }
    }


}