package ru.zinkin.android.phonebook.activity;

import android.content.Intent;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.pojo.Contact;
import ru.zinkin.android.phonebook.retrofit.interfaces.RequestPhoneBook;
import ru.zinkin.android.phonebook.retrofit.model.ResponseString;
import ru.zinkin.android.phonebook.services.ContactService;

public class PhoneActivity extends AppCompatActivity {

    private ContactService contactService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.layout_refresh_phone);
        contactService = ContactService.getInstance();
        Button intentDelete = findViewById(R.id.delete_contact_page_btn);
        Button intentCreate = findViewById(R.id.create_contact_page_btn);
        Button infoMessage = findViewById(R.id.info_microservices);
        Button nextPage = findViewById(R.id.next_page_btn);
        ListView listView = findViewById(R.id.contacts_item);
        ArrayAdapter<Contact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,contactService.getAll());
        listView.setAdapter(adapter);



        intentDelete.setOnClickListener(x -> {
            Intent intent = new Intent(this, DeleteContactActivity.class);
            startActivity(intent);
        });
        intentCreate.setOnClickListener(x -> {
            Intent intent = new Intent(this,AddContactActivity.class);
            startActivity(intent);
        });
        infoMessage.setOnClickListener(x -> {
            Intent intent = new Intent(this,GatewayContactService.class);
            startActivity(intent);
        });
        nextPage.setOnClickListener(x -> {
            Intent intent = new Intent(this, PhoneActivity2.class);
            startActivity(intent);
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(swipeRefreshLayout);
                nextPage(FindContactActivity.class);
            }
        });

    }

    public void nextPage(Class<?> c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

    private void refresh(SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setRefreshing(true);
        ((ArrayAdapter<Contact>) ((ListView) findViewById(R.id.contacts_item))
                .getAdapter())
                .notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

//    private void getInfo(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.0.10:8091/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RequestPhoneBook requestPhoneBook = retrofit.create(RequestPhoneBook.class);
//
//        requestPhoneBook.getInfo().enqueue(new Callback<ResponseString>() {
//            @Override
//            public void onResponse(Call<ResponseString> call, Response<ResponseString> response) {
//                Toast.makeText(PhoneActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseString> call, Throwable t) {
//                Toast.makeText(PhoneActivity.this, "Не удалось выполнить запрос", Toast.LENGTH_SHORT).show();
//                t.printStackTrace();
//            }
//        });
//    }
}