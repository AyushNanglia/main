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

class manager extends employee{


void homeManagers(){
    System.out.print("\tMANAGER SECTION\n\n");
        System.out.println("1) View Current Managers\t2) Appoint Manager");
        System.out.println("3) Change Manager(s)"); 
        int choose=scan.nextInt();
        switch (choose) {
            case 1:
                viewEmp();
                break;
        
            default:
                System.out.println("[ Invalid input ]"); 
                break;
        }
}

void viewManagers(){
    
}    

void appointManager(){
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.print("\tAPPOINT NEW MANAGER\n");
    try{  
        final String db_url="jdbc:mysql://127.0.0.1:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            //System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            //forName("com.mysql.cj.jdbc.Driver"); 
            Connection con=DriverManager.getConnection(db_url,"root","root");   
            Statement stmt=con.createStatement(); 
            //ResultSet input=stmt.executeQuery("sql");
            ResultSet rs=stmt.executeQuery("select * from emsDept");  
            while(rs.next())  
            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));  
            con.close();  
    }
    catch(Exception e){
        System.out.println(e);
    }
}

}
