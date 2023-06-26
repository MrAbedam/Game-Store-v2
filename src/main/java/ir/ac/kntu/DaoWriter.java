package ir.ac.kntu;

import ir.ac.kntu.AdminPages.Admin;
import ir.ac.kntu.AdminPages.AdminMainPage;
import ir.ac.kntu.Products.Item;
import ir.ac.kntu.UserPages.User;

import java.io.*;
import java.util.ArrayList;

import static ir.ac.kntu.AdminPages.AdminGameList.listOfItems;
import static ir.ac.kntu.AdminPages.AdminMainPage.allAdmins;
import static ir.ac.kntu.HelperClasses.UserLoginHelper.allUsers;

public class DaoWriter {

    public static void writeItems(ArrayList<Item> allItemsList) throws IOException {
        File file = new File("Items.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(allItemsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
      //  System.out.println("Items are now saved in the Items.txt file!");
    }

    public static void writeAdmin(ArrayList<Admin> allAdminsList) throws IOException {
        File file = new File("Admins.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(allAdminsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       // System.out.println("Admins are now saved in the Admins.txt file!");
    }

    public static void writeUsers(ArrayList<User> allUsersList) throws IOException {
        File file = new File("Users.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(allUsersList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       // System.out.println("Users are now saved in the Users.txt file!");
    }

    public static ArrayList<Admin> readAdmin() throws IOException, ClassNotFoundException {
        ArrayList<Admin> allAdminsList = null;
        File file = new File("Admins.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            allAdminsList = (ArrayList<Admin>) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allAdminsList;
    }

    public static ArrayList<Item> readItem() throws IOException, ClassNotFoundException {
        ArrayList<Item> allItemsList = null;
        File file = new File("Items.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            allItemsList = (ArrayList<Item>) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allItemsList;
    }

    public static ArrayList<User> readUsers() throws IOException, ClassNotFoundException {
        ArrayList<User> allUsersList = null;
        File file = new File("Users.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            allUsersList = (ArrayList<User>) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allUsersList;
    }

    public static void writeAndReadAllLists(){
        try {
            DaoWriter.writeAdmin(allAdmins);
            DaoWriter.writeUsers(allUsers);
            DaoWriter.writeItems(listOfItems);
            allAdmins = DaoWriter.readAdmin();
            allUsers = DaoWriter.readUsers();
            listOfItems = DaoWriter.readItem();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

    }
}