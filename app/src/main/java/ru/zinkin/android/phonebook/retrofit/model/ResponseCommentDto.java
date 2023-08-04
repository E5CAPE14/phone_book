package ru.zinkin.android.phonebook.retrofit.model;

public class ResponseCommentDto {

    private String phone;
    private String comment;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ResponseCommentDto() {
    }

    public ResponseCommentDto(String phone, String comment) {
        this.phone = phone;
        this.comment = comment;
    }
}
