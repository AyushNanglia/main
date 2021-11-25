import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Year;
// package org.code2care;
// import com.bethecoder.ascii_table.ASCIITable;
//import tools.utils;
//import java.util.jar.*;
//import com.mysql.cj.jdbc.Driver;



class tools{
ems cls=new ems();
employee emp=new employee();
employeeTwo emp_2=new employeeTwo();
department dept=new department();
salary sal=new salary();
int yearCurrent=Calendar.getInstance().get(Calendar.YEAR);

void home(){
    Scanner scan=new Scanner(System.in);  
    cls.clearScr();
        System.out.print("\tEMPLOYEE MANAGEMENT SYSTEM \n\n");
        System.out.print("1) Managers\t2) Employees\t3) Departments\n");
        System.out.print("4) Salary\n\n");
        System.out.print("Input: ");
        int inp=scan.nextInt();
        //int yearCurrent=Calendar.getInstance().get(Calendar.YEAR);
        switch (inp) {
            case 1:
            System.out.print(yearCurrent);
            break;
            
            case 2:
            cls.clearScr();
            emp.viewEmp();
            break;

            case 3:
            cls.clearScr();
            dept.deptHome();
            break;
            
            case 4:
            cls.clearScr();
            sal.home();
            break;

            default:
            System.out.println("\tInvalid Input");
            break;
        }
        scan.close();

}

interface utils{
    default void clearScr(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
}



// class salary{

// }

class ems implements tools.utils{

    // public void clearScr(){
    //     System.out.print("\033[H\033[2J");
    //     System.out.flush();
    // }
    void exeDB(){
        try{  
            final String db_url="jdbc:mysql://127.0.0.1:3306/db_sample";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                //System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                //forName("com.mysql.cj.jdbc.Driver"); 
                Connection con=DriverManager.getConnection(db_url,"root","root");   
                Statement stmt=con.createStatement(); 
                //ResultSet input=stmt.executeQuery("sql");
                ResultSet rs=stmt.executeQuery("select * from emp");  
                while(rs.next())  
                System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
                con.close();  
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        // employee emp=new employee();
        
        // department dept=new department();
        // ems cls=new ems();
        // ems db=new ems();
        //utils cls=new utils();
        //db.exeDB();
        tools homescreen=new tools();
        homescreen.home();

    }
}

