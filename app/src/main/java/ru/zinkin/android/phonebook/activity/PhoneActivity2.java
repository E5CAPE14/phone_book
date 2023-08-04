package ru.zinkin.android.phonebook.activity;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import ru.zinkin.android.phonebook.R;

public class PhoneActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_2);
        Button spam = findViewById(R.id.btn_spam_activity_phone_2);
        Button comment = findViewById(R.id.btn_comment_activity_phone_2);
        Button find = findViewById(R.id.btn_find_activity_phone_2);


        spam.setOnClickListener(x -> {
            Intent intent = new Intent(this, AddSpamActivity.class);
            startActivity(intent);
        });
        comment.setOnClickListener(x -> {
            Intent intent = new Intent(this, CommentContactActivity.class);
            startActivity(intent);
        });
        find.setOnClickListener(x -> {
            Intent intent = new Intent(this,FindContactActivity.class);
            startActivity(intent);
        });

    }
}