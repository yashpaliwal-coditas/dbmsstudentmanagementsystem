package dbmsstudentmanagement;
import java.sql.*;
import java.util.*;
public class Manage {
    final Connection con =Connectivity.connection();
    void createDatabase(){

        try {
            Statement statement = con.createStatement();
            try {
                ResultSet resultSet = statement.executeQuery("select * from students");

                System.out.println("Already exist");
            }catch (SQLException et){
                if(et.getMessage().equals("Table 'corejavajdbc.students' doesn't exist")){
                    statement.execute("create table students(rollno int primary key,age int,name varchar(30),division varchar(10),address varchar(50))");
                    statement.execute("create table result(rollno int primary key,resultId int,total int,mark1 int,mark2 int,mark3 int,mark4 int,mark5 int)");
                }
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }
    void get(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the roll number");
        int rollNo =sc.nextInt();
        boolean check = false;
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from students where rollno ="+rollNo);

        if(resultSet.next()){
            try{
                throw new DuplicateStudentException();
            }
            catch (DuplicateStudentException r){
                System.out.println("Please entered correct rollnumber");
            }
            return;
        }
        System.out.println("Enter the age");
        int age = sc.nextInt();
        System.out.println("Enter the name");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter the division");
        String division = sc.nextLine();
        System.out.println("Enter the address");
        String address = sc.nextLine();
        CallableStatement callableStatement = con.prepareCall("call InsertRecord(?,?,?,?,?)");
        callableStatement.setInt(1,rollNo);
        callableStatement.setInt(2,age);
        callableStatement.setString(3,name);
        callableStatement.setString(4,division);

        callableStatement.setString(5,address);
        boolean flag = callableStatement.execute();

//        String query = "insert into students values("+rollNo+","+age+",'"+name+"','"+division+"','"+address+"')";
//        statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    void display(){
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from students");

            while (resultSet.next()){
                System.out.println("Rollno: "+resultSet.getInt(1)+" ,age: "+resultSet.getInt(2)+" ,name: "+resultSet.getString(3)+" ,division: "+resultSet.getString(4)+" ,Address: "+resultSet.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    void addMarks(){
        Scanner sc = new Scanner(System.in);
        int grace=1;
        System.out.println("Enter the rollno");
        int rollNo = sc.nextInt();
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from students where rollno ="+rollNo);
            if(!resultSet.next()){
                try{
                    throw new RollNumberException();
                }
                catch (RollNumberException r){
                    System.out.println("Please entered correct rollnumber");
                }
            }
            else {
                System.out.println("Please enter result ID");
                int resultId = sc.nextInt();

                int total = 0;
                String subject[] = {"English","math","Hindi","physics","Chemistry"};
                int marks[] = new int[5];
                for (int i = 0; i < 5; i++) {
                    System.out.println("Enter marks for : " +
                            ""+subject[i]);
                    int mark = sc.nextInt();
                    if (mark < 0 || mark > 100) {
                        try {
                            throw new MarksNotInRangeException();
                        } catch (MarksNotInRangeException m) {
                            i--;
                            System.out.println("Please enter marks carefully");
                        }
                    } else {
                        if (mark < 35 && grace != 0) {
                            mark += 5;
                            grace--;
                        }
                        total += mark;
                        marks[i] = mark;
                    }
                }
                statement.execute("insert into result values("+rollNo+","+resultId+","+total+","+marks[0]+","+marks[1]+","+marks[2]+","+marks[3]+","+marks[4]+")");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void DisplayResult(){
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from students s join result r on s.rollno = r.rollno");
            while (resultSet.next()){
                System.out.println("Rollno: "+resultSet.getInt(1)+" ,age: "+resultSet.getInt(2)+" ,name: "+resultSet.getString(3)+" ,division: "+resultSet.getString(4)+" ,Address: "+resultSet.getString(5)+" ,ResultId: "+resultSet.getInt(7)+" ,Total: "+resultSet.getInt(8)+" ,Marks1: "+resultSet.getInt(9)+" ,Marks2: "+resultSet.getInt(10)+" ,Marks3: "+resultSet.getInt(11)+" ,Marks4: "+resultSet.getInt(12)+" ,Marks5: "+resultSet.getInt(13));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}