
import java.io.*;
import java.util.*;

//import tools.utils;

import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.cj.x.protobuf.MysqlxExpect.Open.Condition;

class employee implements tools.utils{
    
    String name,id,desig,dept,yoj;
    HashMap<String,String> map_dept=new HashMap<String,String>();
    HashMap<String,String> map_desig=new HashMap<String,String>();
    HashMap<String,Long> map_sal=new HashMap<String,Long>(); 
    ArrayList<String> empData=new ArrayList<String>();
    salary Salary=new salary();
    //employee cls=new employee();
    Scanner scan=new Scanner(System.in);
    
    void initMaps(){
        try{  
            final String db_url="jdbc:mysql://127.0.0.1:3306/ems";
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                Connection con=DriverManager.getConnection(db_url,"root","root");   
                Statement stmt=con.createStatement(); 
                ResultSet rs_1=stmt.executeQuery("select * from emsDept");  
                while(rs_1.next())  
                map_dept.put(rs_1.getString(1), rs_1.getString(2));
                
                Statement stmt_2=con.createStatement(); 
                ResultSet rs_2=stmt_2.executeQuery("select * from emsSal");  
                while(rs_2.next()){  
                map_desig.put(String.valueOf(rs_2.getInt(1)), rs_2.getString(2));
                map_sal.put(rs_2.getString(2),Long.valueOf(rs_2.getString(3)));
                }

                con.close();  
        }
        catch(Exception e){
            System.out.println("exception via init maps");
        }
    }

    void viewEmp(){
        initMaps();
        tools homescreen=new tools();
        Scanner scan=new Scanner(System.in);
        System.out.print("\tEMPLOYEE SECTION\n\n");
        System.out.println("1) View existing employees");
        System.out.println("2) Add an employee");
        System.out.println("3) Delete an employee record");
        System.out.println("\nAny) Back to Main menu");
        System.out.println("\nInput: ");
        String inp_1=scan.next();
        if(inp_1.equals("1")){
            try{  
                final String db_url="jdbc:mysql://localhost:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                    //System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
                    Class.forName("com.mysql.cj.jdbc.Driver"); 
                    //forName("com.mysql.cj.jdbc.Driver"); 
                    Connection con=DriverManager.getConnection(db_url,"root","root");   
                    Statement stmt=con.createStatement(); 
                    //ResultSet input=stmt.executeQuery("sql");
                    ResultSet rs=stmt.executeQuery("select * from emsEmp order by id");  
                    while(rs.next())  
                    System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));//+"\t"+rs.getString(6));  
                    System.out.println('\n');
                    
                    System.out.print("Enter anything to navigate back: ");
                    //System.out.print("Enter employee-ID to see individual details: ");
                    String id=scan.next();
                    // System.out.println();
                    // Statement stmt_1=con.createStatement(); 
                    // ResultSet rs_1=stmt_1.executeQuery("select * from emsEmp where id=\""+id.toUpperCase()+"\"");  
                    // if(rs_1.next()){
                    //     System.out.print("\033[H\033[2J");System.out.flush();
                    //     while(rs_1.next()){  
                    //         int exp=Calendar.getInstance().get(Calendar.YEAR)-Integer.valueOf(rs_1.getString(5));
                    //         Long OrigSal=Long.valueOf(rs_1.getString(6));
                    //         Long sal=OrigSal+(Salary.taxSlabSal(OrigSal)*OrigSal/100)+Salary.increSlabSal(exp);
                    //         System.out.println("\tEMP-ID: "+id.toUpperCase());
                    //         System.out.println("Employee's Name: "+rs_1.getString(2));
                    //         System.out.println("Department: "+rs_1.getString(4));
                    //         System.out.println("Designation: "+rs_1.getString(3));
                    //         System.out.println("Work experience: "+exp+" [Joined: "+rs_1.getString(5)+"]");
                    //         System.out.println("Current salary: "+sal);
                    //     }  

                    // }else viewEmp();
                    if(id.length()>0)
                    viewEmp();
                    //TimeUnit.SECONDS.sleep(5);
                    con.close();  
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        else if(inp_1.equals("2"))
        addEmp();
        else if(inp_1=="4")
            //ems.main("args");
            homescreen.home();
        
        else if(inp_1.equals("3"))
        delEmp();
        else homescreen.home();
        scan.close();
    }

    void enterDept(){
        System.out.print("\033[H\033[2J");System.out.flush();
        System.out.println("\tCHOOSE DEPARTMENT\n");
        for (Map.Entry<String,String> val : map_dept.entrySet())
        System.out.println(val.getKey()+" "+val.getValue());
        System.out.print("\nEnter department code: ");
        String code=scan.next();
        if(!map_dept.containsKey(code.toUpperCase()))
        enterDept();
        else this.dept=map_dept.get(code.toUpperCase());
    }
    void enterDesig(){

        System.out.print("\033[H\033[2J");System.out.flush();
        System.out.println("\tCHOOSE DESIGNATION\n");
        for (Map.Entry<String,String> val : map_desig.entrySet())
        System.out.println(val.getKey()+" : "+val.getValue());
        System.out.print("\nEnter token corresponding to designation: ");
        String token=scan.next();
        try{
            if(!map_desig.containsKey(token)){
                System.out.print("[ Invalid Input ]");
                System.out.print("\033[H\033[2J");System.out.flush();
                enterDesig();
            }
            else this.desig=map_desig.get(token);}
        catch(InputMismatchException input_ex){
            System.out.print("\033[H\033[2J");System.out.flush();
            enterDesig();
        }
    }
    
    void addEmp(){
        initMaps();
        employee cls=new employee();
        System.out.print("\tNEW EMPLOYEE\n\n");
        System.out.print("ID: ");
        id=scan.next();           //empData.add(id);
        System.out.print("Name(Separated by -): ");
        name=scan.next(); 
        System.out.print("Year of Joining: ");
        yoj=scan.next();
        // System.out.print("Department: ");
        // dept=scan.next();
        enterDept();
        // System.out.print("Designation: ");
        // desig=scan.next();
        enterDesig();
        //System.out.print("\tNEW EMPLOYEE\n\n");
        System.out.print("\033[H\033[2J");System.out.flush();
        System.out.println("\nID: "+id);
        System.out.println("Name: "+name);
        System.out.println("Department: "+dept);
        System.out.println("Designation: "+desig);
        System.out.println("Year of Joining: "+yoj);
        //System.out.println("Base Salary: "+map_sal.get(map_desig.get(desig)));
        System.out.print("\nPress Y to confirm, N otherwise: ");
        String yn=scan.next();
        if(yn.toUpperCase().equals("Y")){
            try{  
                final String db_url="jdbc:mysql://localhost:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                    
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection(db_url,"root","root");   
                    //Statement stmt=con.createStatement(); 
                    //ResultSet input=stmt.executeQuery("sql");
                    String query=" insert into emsEmp (id, name, designation, department, yoj, salary)"
                    + " values (?, ?, ?, ?, ?, ?)";
                    
                    PreparedStatement insertIntent = con.prepareStatement(query);
                    insertIntent.setString(1,id);
                    insertIntent.setString(2,name);
                    insertIntent.setString(3,desig);
                    insertIntent.setString(4,dept);
                    insertIntent.setString(5,yoj);
                    insertIntent.setString(6,map_desig.get(desig));
                    insertIntent.executeUpdate();
                    // while(rs.next())  
                    // System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
                    con.close();
                    System.out.println("\n\t[ Employee added to the database ]");
                    TimeUnit.SECONDS.sleep(3);
                    cls.clearScr();
                    viewEmp();
                    
                    
            }
            catch(Exception e){
                //System.out.println("[ Database Error! ]");
                System.out.println(e);
                //TimeUnit.SECONDS.sleep(5);

            }
        }else{
            System.out.print("\033[H\033[2J");System.out.flush();
            addEmp();
        }
        scan.close();
        //viewEmp();
        
    }
    void delEmp(){
        Scanner scan=new Scanner(System.in);
        employee cls=new employee();
        try{  
            final String db_url="jdbc:mysql://127.0.0.1:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                //System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                //forName("com.mysql.cj.jdbc.Driver"); 
                Connection con=DriverManager.getConnection(db_url,"root","root");   
                Statement stmt=con.createStatement(); 
                //ResultSet input=stmt.executeQuery("sql");
                ResultSet rs=stmt.executeQuery("select * from emsEmp");  
                while(rs.next())  
                System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));  
                con.close();  
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.print("\nEnter Employee's ID: ");
        String toDel=scan.next();

        try{  
            final String db_url="jdbc:mysql://127.0.0.1:3306/ems";//?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                //System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                //forName("com.mysql.cj.jdbc.Driver"); 
                Connection con=DriverManager.getConnection(db_url,"root","root");   
                Statement stmt=con.createStatement(); 
                //ResultSet input=stmt.executeQuery("sql");
                //ResultSet rs=
                ResultSet rs=stmt.executeQuery("select * from emsEmp where id=\""+toDel+"\";");
                if(rs.next()){
                stmt.executeUpdate("delete from emsEmp where id=\""+toDel+"\";");  
                System.out.println("\n\t[ Record deleted ]");}
                else System.out.println("\n\t[ Record does not exist ]");
                TimeUnit.SECONDS.sleep(2);
                cls.clearScr();
                viewEmp();
                con.close();  
        }
        catch(Exception e){
            System.out.println(e);
        }

        scan.close();
    }
}