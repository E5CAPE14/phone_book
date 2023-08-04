package ru.zinkin.android.phonebook.activity;

import android.content.Intent;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.parser.AuthParser;
import ru.zinkin.android.phonebook.services.ContactService;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private TextView login;
    private EditText password;

    private AuthParser authParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.layout_refresh);
        login = findViewById(R.id.text_LOGIN);
        password = findViewById(R.id.log_password);

        /*
        Наш текстовый файл с уже готовым для нас API.
        < AuthParser >
        */
        authParser = new AuthParser(getBaseContext().getFileStreamPath("auth.txt"));
        ContactService contactService = ContactService.getInstance();
        /*
            Проверка на наличие файла
         */
        if(!authParser.existFile()){
            try {
                if(authParser.createFile()) {
                    authParser.save("0000");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        /*
            Авторизация на текст LOGIN
        */
        login.setOnClickListener(x -> {
            try {
                String password = authParser.getPassword();
                if(this.password.getText().toString().equals(password)){
                    Intent intent = new Intent(this, PhoneActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this,"Не верный пароль!!!",Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
}
