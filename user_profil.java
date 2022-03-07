package com.example.learningharmonizer;

final class user_profil{
        private final String e_mail;
        private final String phone_number;
        private final String pseudo;
        private final String password;

    public user_profil(String first, String second, String third, String fourth) {
        this.e_mail = first;
        this.phone_number = second;
        this.pseudo = third;
        this.password = fourth;
    }

    public String getFirst() {
        return e_mail;
    }

    public String getSecond() {
        return phone_number;
    }

    public String getThird (){return pseudo;}

    public String getFourth (){return password;}

    }

