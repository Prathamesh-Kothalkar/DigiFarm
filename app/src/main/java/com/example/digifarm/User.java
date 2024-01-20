package com.example.digifarm;

public class User {

        public String name;
        public String city;

        public String phoneNumber;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String phoneNumber, String name, String city) {
            this.phoneNumber=phoneNumber;
            this.name = name;
            this.city = city;

        }
}


