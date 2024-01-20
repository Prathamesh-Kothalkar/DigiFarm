package com.example.digifarm;

public class User {

        public String name;
        public String city;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String name, String city) {
            this.name = name;
            this.city = city;
        }
}


