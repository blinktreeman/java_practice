package com.day05;

/*
    Список сотрудников некой фирмы хранится в виде, например, такой HashMap
    HashMap<Integer, ArrayList<String>> persons = new HashMap<>();
    где ключ - серия и номер паспорта сотрудника, а в списке хранятся его параметры
    1) Написать метод, возвращающий всех однофамильцев (или тёзок)
    2) Написать метод, возвращающий сотрудников, старше (младше) определенного возраста
    Note: Усложнение: хранить дату рождения в виде String определенного формата (например, DD-MM-YYYY),
    при вычислении преобразовывать в "датовый" тип и вычислять возраст.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Persons {

    // HashMap int - номер паспорта, Person - данные по сотруднику
    public static HashMap<Integer, Person> persons = new HashMap<>();

    public static void main(String[] args) {

        persons.put(322223, new Person("Ivan", "Ivanov", "1988-12-22"));
        persons.put(123456, new Person("Pyotr", "Petrov", "1989-02-13"));
        persons.put(654321, new Person("Ivan", "Sidorov", "1999-14-02"));
        persons.put(124587, new Person("Pyotr", "Ivanov", "2001-03-23"));
        persons.put(455487, new Person("Eugenii", "Vasiliev", "1978-12-22"));
        persons.put(658471, new Person("Alexandr", "Andreev", "2001-02-13"));
        persons.put(321945, new Person("Sergey", "Sidorov", "1998-14-02"));
        persons.put(352145, new Person("Vasilii", "Ivanov", "1992-03-23"));

        // Однофамильцы
        sameSurname();
        // Все старше 25 лет
        overAge(25);
    }

    public static void sameSurname() {
        // HashMap для фамилий. Ключ - фамилия, значение - список номеров паспортов
        HashMap<String, ArrayList<Integer>> surnameList = new HashMap<>();
        persons.forEach((key, value) -> {
            // Для каждого сотрудника если фамилия не присутствует в HashMap-е, заносим ключем и
            // добавляем в список номер паспорта.
            if (!surnameList.containsKey(value.surname)) surnameList.put(value.surname, new ArrayList<>());
            surnameList.get(value.surname).add(key);
        });
        System.out.println("Persons with the same surname:");
        // Для всех пар
        surnameList.forEach((key, value) -> {
            // если размер значения (списка) более 1 (есть однофамильцы)
            if (surnameList.get(key).size() > 1) {
                // для всех номеров паспортов вывод данных
                for (int i : value) {
                    System.out.printf("Passport #%d %s %s - %d years old\n", i,
                            persons.get(i).name, persons.get(i).surname, persons.get(i).getAge());
                }
            }
        });
    }

    public static void overAge(int age) {
        System.out.printf("Over than %d years old:\n", age);
        // Для всех сотрудников в persons с возрастом > age, вывод данных
        persons.forEach((key, value) -> {
            if (value.getAge() > age) {
                System.out.printf("Passport #%d %s %s - %d years old\n", key,
                        value.name, value.surname, value.getAge());
            }
        });
    }

    static class Person {
        String name;
        String surname;
        String birthDay;

        public Person(String name, String surname, String birthDay) {
            this.name = name;
            this.surname = surname;
            this.birthDay = birthDay;
        }

        int getAge() {
            try {
                // Формат сторки с датой рождения
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = formatter.parse(birthDay);
                // Текущая дата
                Date dateNow = new Date();
                // Возвращаем возраст в годах
                return (int) ((dateNow.getTime() - dateOfBirth.getTime()) / (1000L * 60 * 60 * 24 * 365));
            } catch (Exception exception) {
                System.out.println("Wrong birthday record");
                return 0;
            }
        }
    }

}
/*
    Output:
    Persons with the same surname:
    Passport #352145 Vasilii Ivanov - 30 years old
    Passport #124587 Pyotr Ivanov - 21 years old
    Passport #322223 Ivan Ivanov - 33 years old
    Passport #654321 Ivan Sidorov - 22 years old
    Passport #321945 Sergey Sidorov - 23 years old
    Over than 25 years old:
    Passport #123456 Pyotr Petrov - 33 years old
    Passport #352145 Vasilii Ivanov - 30 years old
    Passport #455487 Eugenii Vasiliev - 43 years old
    Passport #322223 Ivan Ivanov - 33 years old
 */
