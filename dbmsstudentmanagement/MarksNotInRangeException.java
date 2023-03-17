package dbmsstudentmanagement;
public class MarksNotInRangeException extends Exception{
    MarksNotInRangeException(){
        System.out.println("You have entered wrong marks");
    }
}
