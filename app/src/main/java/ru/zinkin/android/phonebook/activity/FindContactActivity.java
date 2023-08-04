package ru.zinkin.android.phonebook.activity;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.retrofit.interfaces.RequestPhoneBook;
import ru.zinkin.android.phonebook.retrofit.model.ContactData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FindContactActivity extends AppCompatActivity {

    ContactData user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_contact);
        EditText phone = findViewById(R.id.find_Contact_page_btn);
        TextView nameView = findViewById(R.id.contact_info_name_text);
        TextView surname = findViewById(R.id.contact_info_surname_text);
        TextView phoneView = findViewById(R.id.contact_info_phone_text);
        TextView spamView = findViewById(R.id.contact_info_spam_text);
        ListView commentView = findViewById(R.id.contact_info_comment_list);
        Button button = findViewById(R.id.find_contact_page_btn);

        button.setOnClickListener(x -> {
            if(!phone.getText().toString().equals("")){
                sendRequestToServer(phone.getText().toString(),nameView,surname,phoneView,spamView,commentView);
            }
        });

    }

    private void sendRequestToServer(String phone){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.10:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestPhoneBook requestPhoneBook = retrofit.create(RequestPhoneBook.class);

        requestPhoneBook.getContact(phone).enqueue(new Callback<ContactData>() {
            @Override
            public void onResponse(Call<ContactData> call, Response<ContactData> response) {
                if(response.isSuccessful()) {
                    user = response.body();
                }
            }

            @Override
            public void onFailure(Call<ContactData> call, Throwable t) {
                Toast.makeText(FindContactActivity.this,"Не удалось выполнить запрос.",Toast.LENGTH_LONG)
                        .show();
            }
        });

    }
    private void sendRequestToServer(String phone,
                                     TextView nameView,
                                     TextView surnameView,
                                     TextView phoneView,
                                     TextView spamView,
                                     ListView commentsView){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.10:8091/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestPhoneBook requestPhoneBook = retrofit.create(RequestPhoneBook.class);

        requestPhoneBook.getContact(phone).enqueue(new Callback<ContactData>() {

            private String error = "ERROR";
            private String spam = "SPAMMER";
            private String noSpam = "Checked";

            @Override
            public void onResponse(Call<ContactData> call, Response<ContactData> response) {
                if(response.isSuccessful()) {
                    user = response.body();
                    if(user != null) {
                        nameView.setText(user.getName());
                        surnameView.setText(user.getSurname());
                        phoneView.setText(user.getPhone());
                        if(user.getSpam()){
                            spamView.setText(spam);
                        } else {
                            spamView.setText(noSpam);
                        }
                        List<String> comments = Arrays.stream(user.getComments()).map(String::new).collect(Collectors.toList());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindContactActivity.this, android.R.layout.simple_list_item_1,comments);
                        commentsView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactData> call, Throwable t) {
                Toast.makeText(FindContactActivity.this,"Не удалось выполнить запрос.",Toast.LENGTH_LONG)
                        .show();
                nameView.setText(error);
            }
        });

    }
}