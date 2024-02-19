package com.example.digifarm.model;

public class User {

        public String name;
        public String city;

        public String phoneNumber;
        public String type;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String phoneNumber, String name, String city,String type) {
            this.phoneNumber=phoneNumber;
            this.name = name;
            this.city = city;
            this.type= type;

        }

        public String getName(){
            return this.name;
        }
        public String getType(){return this.type;}
        public  String getCity(){return this.city;}
        public String getPhoneNumber(){return this.phoneNumber;}
}


