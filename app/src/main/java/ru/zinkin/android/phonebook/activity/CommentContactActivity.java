package ru.zinkin.android.phonebook.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.retrofit.interfaces.RequestPhoneBook;
import ru.zinkin.android.phonebook.retrofit.model.ResponseCommentDto;
import ru.zinkin.android.phonebook.retrofit.model.ResponseString;

public class CommentContactActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.10:8091/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RequestPhoneBook requestPhoneBook = retrofit.create(RequestPhoneBook.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_contact);
        EditText phoneView = findViewById(R.id.edit_text_phone_number_page_add_comment);
        EditText commentView = findViewById(R.id.edit_text_comment_page_add_comment);
        Button button = findViewById(R.id.btn_add_comment_page_add_comment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResponseCommentDto responseCommentDto = new ResponseCommentDto(phoneView.getText().toString(),commentView.getText().toString());
                requestPhoneBook.addComment(responseCommentDto).enqueue(new Callback<ResponseString>() {
                    @Override
                    public void onResponse(Call<ResponseString> call, Response<ResponseString> response) {
                        if(response.isSuccessful()){
                            if(response.body() != null){
                                Toast.makeText(CommentContactActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseString> call, Throwable t) {
                        Toast.makeText(CommentContactActivity.this,"Не удалось отправить запрос",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}