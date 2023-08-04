package ru.zinkin.android.phonebook.activity;

import android.view.View;
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
import ru.zinkin.android.phonebook.retrofit.model.ResponseString;

public class AddSpamActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.10:8091/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RequestPhoneBook requestPhoneBook = retrofit.create(RequestPhoneBook.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spam);
        Button button = findViewById(R.id.vote_contact_btn);
        EditText editText = findViewById(R.id.delete_phoneNumber_text);

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    String string = ((TextView)findViewById(R.id.vote_contact_info_phone_text)).getText().toString();
                    sendRequestVoteSpamToPhone(string);
                } else {
                    sendRequestFindUserToServer(editText.getText().toString()
                    ,findViewById(R.id.vote_contact_info_nickname_text)
                    ,findViewById(R.id.vote_contact_info_surname_text)
                    ,findViewById(R.id.vote_contact_info_phone_text));
                    editText.setText("");
                }
            }
        });
    }
    private void sendRequestFindUserToServer(String phone,
                                     TextView nameView,
                                     TextView surnameView,
                                     TextView phoneView) {

        //Отсылает строку в виде "Номер телефона" а надо Номер телефона
        requestPhoneBook.getContact(phone).enqueue(new Callback<ContactData>() {

            private String error = "ERROR";

            @Override
            public void onResponse(Call<ContactData> call, Response<ContactData> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        nameView.setText(response.body().getName());
                        surnameView.setText(response.body().getSurname());
                        phoneView.setText(response.body().getPhone());
                    }
                }
                Toast.makeText(AddSpamActivity.this, "Нажмите еще раз чтобы проголосовать.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ContactData> call, Throwable t) {
                Toast.makeText(AddSpamActivity.this, "Не удалось выполнить запрос.", Toast.LENGTH_SHORT)
                        .show();
                nameView.setText(error);
            }
        });
    }

    private void sendRequestVoteSpamToPhone(String phone){
        requestPhoneBook.voiceSpam(phone).enqueue(new Callback<ResponseString>() {
            @Override
            public void onResponse(Call<ResponseString> call, Response<ResponseString> response) {
                if(response.isSuccessful() && response.body() != null) {
                    String str = response.body().getMessage();
                    Toast.makeText(AddSpamActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseString> call, Throwable t) {
                Toast.makeText(AddSpamActivity.this, "Не удалось выполнить запрос", Toast.LENGTH_SHORT).show();
            }
        });
    }
}