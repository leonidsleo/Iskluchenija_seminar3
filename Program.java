package Homework3iskljuchenija;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;


public class Program {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные через пробел:\n фамилия\n имя\n отчество\n дата рождения в формате dd.mm.yyyy\n телефон в формате 80001112233\n пол одной буквой, если мужской m если женский f");
        String input = scanner.nextLine();

        try {
            String[] date = input.split(" ");
            if (date.length < 6 || date.length > 6) {
                throw new IllegalArgumentException("Вы ввели не все данные!");
            }

            String surname = date[0];
            String name = date[1];
            String middleName = date[2];
            
            DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
            format.setLenient(false);
            Date dateOfBirth;
            try {
                dateOfBirth = format.parse(date[3]);
            }catch (ParseException e) {
                throw new ParseException("Введен неверный формат даты. Необходимо дата.месяц.год(4 цифры).", e.getErrorOffset());
            }

            
            // // int phoneNumber; // не позволяет ввести номер телефона более 10 символов.
            long phoneNumber;
            try {
                // phoneNumber = Integer.parseInt(date[4]);
                phoneNumber = Long.parseLong(date[4]);
            }catch (NumberFormatException e) {
                throw new NumberFormatException("Неверный формат телефона. Введите только цифры.");
            }
            

            String phoneNumbers = date[4];
            if (phoneNumbers.length() != 11) { // взял просто обычные сотовые и городские телефоны
                throw new IllegalArgumentException("Вы ввели неверное количество цифр в телефоне!");
            }


            String sex = date[5];
            if (!sex.toLowerCase().equals("m") && !sex.toLowerCase().equals("f")) {
                throw new RuntimeException("Вы неправильно ввели пол. Введите только m или f.");
            }

            String fileUser = surname.toLowerCase() + ".txt";
            File file = new File(fileUser);
            FileWriter fileWriter = new FileWriter(file, true);

            try {
                if (file.length() > 0) {
                    fileWriter.write('\n');
                }
                fileWriter.write(String.format("%s %s %s %s %s %s", surname, name, middleName, format.format(dateOfBirth), phoneNumber, sex));
            } catch (IOException e) {
                throw new FileSystemException("Ошибка при работе с файлом");
            }
            finally {
                fileWriter.close();
            }


        } catch (IllegalArgumentException e) {
            System.out.println("Упс, что то пошло не так: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Ошибка! Исправьте: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Какого вы пола!?: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally{
            scanner.close();
        }     
        
    }
}