package ru.zinkin.android.phonebook.fragments;

import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.activity.FindContactActivity;
import ru.zinkin.android.phonebook.retrofit.interfaces.RequestPhoneBook;
import ru.zinkin.android.phonebook.retrofit.model.ContactData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ContactsFindByPhone extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.10:8091/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private RequestPhoneBook requestPhoneBook = retrofit.create(RequestPhoneBook.class);

    private ContactData user = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contacts_find_by_phone, container, false);
        EditText phone = v.findViewById(R.id.find_Contact_page_btn);
        TextView nameView = v.findViewById(R.id.contact_info_name_text);
        TextView surname = v.findViewById(R.id.contact_info_surname_text);
        TextView phoneView = v.findViewById(R.id.contact_info_phone_text);
        TextView spamView = v.findViewById(R.id.contact_info_spam_text);
        ListView commentView = v.findViewById(R.id.contact_info_comment_list);
        Button button = v.findViewById(R.id.find_contact_page_btn);
        button.setOnClickListener(x -> {
            if(!phone.getText().toString().equals("")){
                sendRequestToServer(phone.getText().toString(),nameView,surname,phoneView,spamView,commentView);
            }
        });
        return v;
    }

    private void sendRequestToServer(String phone){

        requestPhoneBook.getContact(phone).enqueue(new Callback<ContactData>() {
            @Override
            public void onResponse(Call<ContactData> call, Response<ContactData> response) {
                if(response.isSuccessful()) {
                    user = response.body();
                }
            }

            @Override
            public void onFailure(Call<ContactData> call, Throwable t) {
                Toast.makeText(getContext(),"Не удалось выполнить запрос.",Toast.LENGTH_LONG)
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,comments);
                        commentsView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactData> call, Throwable t) {
                Toast.makeText(getContext(),"Не удалось выполнить запрос.",Toast.LENGTH_LONG)
                        .show();
                nameView.setText(error);
            }
        });

    }
}