package com.example.digifarm;

public class User {

        public String name;
        public String city;

        public String phoneNumber;
        public String occu;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String phoneNumber, String name, String city,String occu) {
            this.phoneNumber=phoneNumber;
            this.name = name;
            this.city = city;
            this.occu=occu;

        }
}


