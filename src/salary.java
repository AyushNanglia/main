import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class salary {
    HashMap<Integer,String> map_desig=new HashMap<Integer,String>();
    HashMap<String,Long> map_sal=new HashMap<String,Long>();
    
    void disp(){
        System.out.println(map_desig);
        System.out.println(map_sal);
    }
    void initMaps(){
        try{  
            final String db_url="jdbc:mysql://127.0.0.1:3306/ems";
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                Connection con=DriverManager.getConnection(db_url,"root","root");   
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery("select * from emsSal");  
                while(rs.next()){  
                    map_desig.put(Integer.valueOf(rs.getString(1)), rs.getString(2));
                    map_sal.put(rs.getString(2),Long.valueOf(rs.getString(3))); 
                    //System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
                }  
                con.close();  
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    void addDesig(){
        Scanner scan=new Scanner(System.in);
        System.out.println("\tNEW DESIGNATION\n");
        System.out.print("Title(Seperated by -): ");
        String desigTitle=scan.next();
        try{
        System.out.print("Base Salary(in INR): ");
        Long desigSal=scan.nextLong();
        //if()

        try{  
            final String db_url="jdbc:mysql://127.0.0.1:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                //forName("com.mysql.cj.jdbc.Driver"); 
                Connection con=DriverManager.getConnection(db_url,"root","root");   
                //Statement stmt=con.createStatement(); 
                //ResultSet input=stmt.executeQuery("sql");
                String query=" insert into emsSal (designation, salary)"
                + " values (?, ?)";
                  
                PreparedStatement insertIntent = con.prepareStatement(query);
                insertIntent.setString(1,desigTitle);
                insertIntent.setString(2,String.valueOf(desigSal));
                insertIntent.executeUpdate();
                // while(rs.next())  
                // System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
                con.close();
                System.out.println("\n\t[ Designation added to the database ]");
                //TimeUnit
                System.out.print("\033[H\033[2J");System.out.flush();
                home();
                              
                
        }
        catch(Exception e){
            //System.out.println("[ Database Error! ]");
            System.out.println(e);
            //TimeUnit.SECONDS.sleep(5);

        }}catch(InputMismatchException input_exc){
            System.out.print("\033[H\033[2J");System.out.flush();
            addDesig();
            }

        scan.close();
    }

    void home(){
        Scanner scan=new Scanner(System.in);
        System.out.print("\033[H\033[2J");System.out.flush();
        System.out.print("\tSALARY SECTION\n\n");
        System.out.println("1) Manage base salaries");
        System.out.println("2) Update tax slabs");
        System.out.println("3) Add Designation");
        System.out.println("\nAny) Back to Main menu");
        System.out.println("\nInput: ");
        String inp_1=scan.next();
        if(inp_1.equals("1")){
            initMaps();
            baseSal();
        }
        else if(inp_1.equals("3")) addDesig();
        scan.close();
    }

    void baseSal(){
        Scanner scan=new Scanner(System.in);
        System.out.println("\tMANAGE BASE SALARY");
        for (Map.Entry<Integer,String> val : map_desig.entrySet())
            System.out.println(val.getKey()+") "+val.getValue());
        try{
        System.out.println("\n(Invalid input will navigate you back)");
        System.out.print("Enter corresponding token number: ");
        int token=scan.nextInt();
        System.out.print("\033[H\033[2J");System.out.flush();
        System.out.println("Designation: "+map_desig.get(token));
        System.out.println("Current base salary: "+map_sal.get(map_desig.get(token)));
        System.out.print("Updated Salary: ");
        Long uptdSal=scan.nextLong();
        
            try{  
                final String db_url="jdbc:mysql://127.0.0.1:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                    //System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
                    Class.forName("com.mysql.cj.jdbc.Driver"); 
                    //forName("com.mysql.cj.jdbc.Driver"); 
                    Connection con=DriverManager.getConnection(db_url,"root","root");   
                    Statement stmt=con.createStatement(); 
                    //ResultSet input=stmt.executeQuery("sql");
                    String query="update emsSal set salary=? where id="+token+";";  
                    PreparedStatement insertIntent = con.prepareStatement(query);
                    insertIntent.setString(1,String.valueOf(uptdSal));
                    insertIntent.executeUpdate();
                    // while(rs.next())  
                    // System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4));  
                    con.close();  
            }
            catch(Exception e){
                System.out.println(e);
            }
        }catch(InputMismatchException input_exc){
            System.out.print("\033[H\033[2J");System.out.flush();
            home();
        }
        System.out.println("\t\n[ SALARY UPDATED ]");
        System.out.println();
       // System.out.print("\033[H\033[2J");System.out.flush();
        home();
        scan.close();
    }

    int taxSlabSal(Long salary){
        int tax;
        if(salary>0 && salary<90000)
        return 5;
        else if(salary>=90000 && salary<100000)
        return 10;
        else if(salary>=100000 && salary<250000)
        return 20;
        else return 30;
    }

    int increSlabSal(int yoj){
        if(yoj>=0 && yoj<3)
        return 5000;
        else if(yoj>=3 && yoj<7)
        return 8000;
        else return 10000;
    }
    
}
