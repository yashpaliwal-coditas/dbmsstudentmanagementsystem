package dbmsstudentmanagement;
import java.util.Scanner;
public class Manu {
    void display(){
        boolean flag=true;
        Scanner sc = new Scanner(System.in);
        Manage manage = new Manage();
        while (flag){
            System.out.println("1:Create Database\n2: Add a student\n3: Display student \n4: Add student marks\n5: Display marks\n0: Exit");
            int choice = sc.nextInt();
            switch (choice){
                case 1: manage.createDatabase();
                break;
                case 2: manage.get();
                break;
                case 3: manage.display();
                break;
                case 4: manage.addMarks();
                break;
                case 5: manage.DisplayResult();
                    break;
                case 0: flag = false;
            }
        }
    }
}
