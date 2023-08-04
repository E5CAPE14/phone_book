package ru.zinkin.android.phonebook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.zinkin.android.phonebook.R;
import ru.zinkin.android.phonebook.retrofit.interfaces.RequestPhoneBook;
import ru.zinkin.android.phonebook.retrofit.model.ContactData;
import ru.zinkin.android.phonebook.retrofit.model.ResponseString;

public class ContactsAddSpamByPhone extends Fragment {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.10:8091/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RequestPhoneBook requestPhoneBook = retrofit.create(RequestPhoneBook.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contacts_add_spam_by_phone, container, false);
        EditText phone = v.findViewById(R.id.add_vote_spam_phoneNumber);
        TextView nameView = v.findViewById(R.id.vote_contact_info_nickname_text);
        TextView surname = v.findViewById(R.id.vote_contact_info_surname_text);
        TextView phoneView = v.findViewById(R.id.vote_contact_info_phone_text);
        Button button = v.findViewById(R.id.vote_contact_btn);

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(phone.getText().toString().isEmpty()){
                    String string = phoneView.getText().toString();
                    sendRequestVoteSpamToPhone(string);
                } else {
                    sendRequestFindUserToServer(phone.getText().toString()
                            ,nameView
                            ,surname
                            ,phoneView);
                    phone.setText("");
                }
            }
        });

        return v;
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
                Toast.makeText(getContext(), "Нажмите еще раз чтобы проголосовать.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ContactData> call, Throwable t) {
                Toast.makeText(getContext(), "Не удалось выполнить запрос.", Toast.LENGTH_SHORT)
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
                    Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseString> call, Throwable t) {
                Toast.makeText(getContext(), "Не удалось выполнить запрос", Toast.LENGTH_SHORT).show();
            }
        });
    }

}