package main.java.com.loginapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDataBase {

    //Singleton instance
    private static UserDataBase instance;

    private final ArrayList<User> userList = new ArrayList<>();

    public UserDataBase() {
        File readFile = new File("Users.txt.txt"); //Getting the file contents

        try {
            Scanner reader = new Scanner(readFile); //Creating a file reader instance
            while (reader.hasNextLine()) {
                String line = reader.nextLine(); //Reads current line
                String lineSpacesFixed = line.replaceAll("\\s+", " "); //Removes extra spaces

                String[] tokens = lineSpacesFixed.split(" "); //Separating the username and password into 2 different parts
                String username = tokens[0]; //Extract username
                String password = tokens[1]; //Extract password

                try {
                    //Creating a new user based on the info of the current line and add it to the list
                    User usr = new User(username, password);
                    userList.add(usr);
                } catch (Exception e) {}
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR!!");
        }
    }

    public ArrayList<User> getUsers() {
        return this.userList;
    }

    public static synchronized UserDataBase getInstance() {
        if (instance == null) {
            instance = new UserDataBase();
        }
        return instance;
    }

    public synchronized void incrementUserAttempts(String username) {
        int index = -1;

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getEmail().equals(username)) {
                index = i;
                break;
            }
        }

        if (index > -1)
            userList.get(index).incrementAttempts();

    }

    public synchronized void blockUser(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getEmail().equals(username)) {
                userList.get(i).blockUser();
                break;
            }
        }
    }

    public synchronized void unblockUser(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getEmail().equals(username)) {
                userList.get(i).unblockUser();
                break;
            }
        }
    }

    public boolean validateInput(String username, String password) {
        try{
            User user = new User(username,password);
            return userList.contains(user);
        }
        catch(Exception e){
            return false;
        }
    }

}