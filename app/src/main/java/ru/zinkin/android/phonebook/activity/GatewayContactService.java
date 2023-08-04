package ru.zinkin.android.phonebook.activity;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.fragments.ContactsAddSpamByPhone;
import ru.zinkin.android.phonebook.fragments.ContactsFindByPhone;

public class GatewayContactService extends AppCompatActivity {

    private Button fragment1;
    private Button fragment2;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway_contact_service);

        fragment1 = findViewById(R.id.page_find_by_phone);
        fragment2 = findViewById(R.id.page_vote_for_spam);

        fragment1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fragment = new ContactsFindByPhone();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_contacts,fragment);
                ft.commit();
            }
        });
        fragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ContactsAddSpamByPhone();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_contacts,fragment);
                ft.commit();
            }
        });
    }
}