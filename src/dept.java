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

class department implements tools.utils{

    String deptID, deptName, empCount, salBudget;

    void checkDuplicate(String value){
        try{  
            final String db_url="jdbc:mysql://127.0.0.1:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                //System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                //forName("com.mysql.cj.jdbc.Driver"); 
                Connection con=DriverManager.getConnection(db_url,"root","root");   
                Statement stmt=con.createStatement(); 
                //ResultSet input=stmt.executeQuery("sql");
                String query="select * from emsEmp where id="+"?";  
                PreparedStatement insertIntent = con.prepareStatement(query);
                insertIntent.setString(1,value);
                // while(rs.next())  
                // System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4));  
                con.close();  
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

    Scanner scan=new Scanner(System.in);
    void deptHome(){
        tools homescreen=new tools();
        System.out.print("\tDEPARTMENT SECTION\n\n");
        System.out.println("1) View Departments\t2) Add a Department"); 
        System.out.println("3) Navigate Back\n");
        System.out.print("Input: ");
        String str=scan.next();
        switch (str) {
            case "1":
            System.out.print("\033[H\033[2J");
                System.out.flush();    
            allDepts();
                break;
            case "2":
                addDept();
                break;
            default:
                System.out.print("\033[H\033[2J");
                System.out.flush();
                homescreen.home();
                break;
        }

    }
    
    void allDepts(){
    //System.out.print("\tDeptID\tDepartment\n");
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
    System.out.print("Input anything to navigate back: ");
    String str=scan.next();
    System.out.println();
    if(str.length()>0){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        deptHome();
    }
    
    }

    void addDept(){
        department cls=new department();
        System.out.print("\tNEW DEPARTMENT\n\n");
        System.out.print("Dept Code: ");
        deptID=scan.next();           //empData.add(id);
        System.out.print("Department Name: ");
        deptName=scan.next();
        System.out.print("Number of employees(Enter '-' for void entry): ");
        empCount=scan.next(); //if(empCount==null){empCount="-";}
        System.out.print("Salary Budget(Enter '-' for void entry): ");
        salBudget=scan.next(); //if(salBudget==null){salBudget="-";}

        try{  
            final String db_url="jdbc:mysql://127.0.0.1:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                //forName("com.mysql.cj.jdbc.Driver"); 
                Connection con=DriverManager.getConnection(db_url,"root","root");   
                Statement stmt=con.createStatement(); 
                //ResultSet input=stmt.executeQuery("sql");
                ResultSet rs=stmt.executeQuery("select * from emsDept where deptID=\""+deptID+"\";");
                if(rs.next()){
                String ID=rs.getString(1);
                if(ID.equals(deptID)){
                    System.out.println("\n\t[ Department with same ID already exists in database ]");
                    TimeUnit.SECONDS.sleep(3);
                    deptHome();
                }}
                // while(rs.next())  
                else{
                String query=" insert into emsDept (deptID, deptName, empCount, salBudget)"
                + " values (?, ?, ?, ?)";
                  
                PreparedStatement insertIntent = con.prepareStatement(query);
                insertIntent.setString(1,deptID.toUpperCase());
                insertIntent.setString(2,deptName);
                insertIntent.setString(3,empCount);
                insertIntent.setString(4,salBudget);
                insertIntent.executeUpdate();
            }
            // System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
            con.close();
                System.out.println("\n\tDepartment added to the database");
                TimeUnit.SECONDS.sleep(3);
                //cls.clearScr();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                deptHome();             
                
                
        }
        catch(Exception e){
            //System.out.println("[ Database Error! ]");
            System.out.println(e);
            //TimeUnit.SECONDS.sleep(5);

        }
        
    }

    
}