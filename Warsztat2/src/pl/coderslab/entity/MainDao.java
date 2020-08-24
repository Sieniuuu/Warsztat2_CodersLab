package pl.coderslab.entity;

import pl.coderslab.workshop.User;

import java.util.Arrays;

public class MainDao {

    public static void main(String[] args) {

        User coders = new User();
        coders.setUserName("Coders");
        coders.setEmail("coders@lab.pl");
        coders.setPassword("gogmeogmw");


        //tworzenie nowego Usera

        UserDao codersDao = new UserDao();
        // towrzenie userDao

        //codersDao.create(coders);
        // dodanie usera do bazy

        User read = codersDao.read(9);
        // System.out.println(read);
        // odczytanie danych usera pod daynm ID

        // User change = codersDao.read(9);
        // change.setUserName("Artur");
        // change.setEmail("aa@aa.pl");
        // change.setPassword("dosbhmsi");
        // tworzenie usera, wczytanie do niego starych danych przez metode "read" i ustawienie seterów z nowymi danymi

        // codersDao.update(change);
        // wywołanie zmian

        // codersDao.delete(9);
        // wywołanie usunięcia usera o danym id

        User[] all = codersDao.findAll();
        for (User a : all) {
            System.out.println(a);
        }

    }
}

