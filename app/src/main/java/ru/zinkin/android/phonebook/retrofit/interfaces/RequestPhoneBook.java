package ru.zinkin.android.phonebook.retrofit.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.zinkin.android.phonebook.retrofit.model.ContactData;
import ru.zinkin.android.phonebook.retrofit.model.ResponseCommentDto;
import ru.zinkin.android.phonebook.retrofit.model.ResponseString;

public interface RequestPhoneBook {
    @GET("api/contacts/info")
    Call<ResponseString> getInfo();

    @GET("api/contacts/find/{phone}")
    Call<ContactData> getContact(@Path("phone") String phone);

    @PUT("api/contacts/add/spam")
    Call<ResponseString> voiceSpam(@Body String phone);

    @PUT("api/contacts/add/comment")
    Call<ResponseString> addComment(@Body ResponseCommentDto responseCommentDto);
}
