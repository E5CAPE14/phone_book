package ru.zinkin.android.phonebook.pojo;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode
@Builder
public class Contact {
    @NotNull
    private String nickname;
    @NotNull
    private String phoneNumber;
    @NotNull
    private Integer age;

    public Contact(@NotNull String nickname,
                   @NotNull String phoneNumber,
                   @NotNull Integer age) {

        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.age = age;

    }
    public Contact() {
    }

    @NotNull
    public String getNickname() {
        return nickname;
    }

    public void setNickname(@NotNull String nickname) {
        this.nickname = nickname;
    }

    @NotNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotNull
    public Integer getAge() {
        return age;
    }

    public void setAge(@NotNull Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Contact : " +
                "nickname=" + nickname +
                ", phoneNumber='" + phoneNumber +
                ", age=" + age;
    }
}
