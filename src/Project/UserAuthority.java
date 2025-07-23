package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class UserAuthority {
	Scanner s=new Scanner(System.in);
	Connection conn;
	
	
	
	public void UseAuthority() throws Exception {
	Class.forName("com.mysql.cj.jdbc.Driver");
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcpro","root","root");
	}
	
	public void run() throws SQLException {
		while(true) {
			System.out.println("1.Register");
			System.out.println("2.Login");
			System.out.println("3.Logout");
			System.out.println("Enter your choice: ");
			int choice =s.nextInt();
			s.nextLine();
			
			if(choice==1) {
				registerUser();
			}
			else if(choice ==2){
				loginUser();
			}
			else if(choice == 3) {
				System.out.println("logout sucessfully....");
				System.exit(0);
			}
			else {
				System.out.println("Invalid choice...");
			}
		}
	}

	

	private void registerUser() throws SQLException {
		System.out.println("Enter  User_id:");
		int User_id=s.nextInt();
		s.nextLine();
		System.out.println("Enter name:");
		String name=s.next();
		System.out.println("Enter Email:");
		String Email=s.next();
		System.out.println("Enter MobNo:");
		long MobNo=s.nextLong();
		System.out.println("Enter DOB(yyy-mm-dd):");
		String DOB=s.next();
		
		String username=generateusername(name);
		String password=generatepassword();
		
		
		String insertData="insert into users values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt=conn.prepareStatement(insertData);
		pstmt.setInt(1, User_id);
		pstmt.setString(2, name);
		pstmt.setString(3, Email);
		pstmt.setLong(4, MobNo);
		pstmt.setString(5, DOB);
		pstmt.setString(6, username);
		pstmt.setString(7, password);
		pstmt.executeUpdate();
		
		
		System.out.println("User registered sucessfully....");
		System.out.println("your username is:" +username);
		System.out.println("your password is:" +password);
	}
	
	private void loginUser() throws SQLException {
		System.out.println("Enter username");
		String username=s.next();
		System.out.println("Enter passsword");
		String password=s.next();
		
		String str="select * from users where username=? AND password=?";
		PreparedStatement pstmt=conn.prepareStatement(str);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		ResultSet rs= pstmt.executeQuery();
		
		if(rs.next()) {
			System.out.println("login sucessfull...");
		}
		else {
			System.out.println("Invalid username or password...");
		}
		
	}
	private String generateusername(String name) {
		
		return name.toLowerCase().replaceAll(" ","") +new Random().nextInt(1000);
		
	}
	
		
	
	private String generatepassword() {
		String UppercaseLetter ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowercaseLetter ="abcdefghijklmnopqrstuvwxyz";
		String number="0123456789";
		String symbols = "@#&*";
		Random random = new Random();
		StringBuilder password=new StringBuilder();
		
		password.append(getRandomchar(UppercaseLetter,random));
		password.append(getRandomchar(lowercaseLetter,random));
		password.append(getRandomchar(number,random));
		password.append(getRandomchar(symbols,random));
		
		String allCharacters = UppercaseLetter + lowercaseLetter + number + symbols;
		for(int i=0;i<4;i++) {
			password.append(getRandomchar(allCharacters,random));
		}
		String passwordstr=password.toString();
		char[] passwordArr =passwordstr.toCharArray();
		shufflrArray(passwordArr,random);
		return passwordstr;
	}

	private Object getRandomchar(String str, Random random) {

		return str.charAt(random.nextInt(str.length()));
	}


	private void shufflrArray(char[] arr, Random random) {
		for(int i=arr.length-1;i>0;i--) {
			int index=random.nextInt(i+1);
			char temp=arr[index];
			arr[index]=arr[i];
			arr[i]=temp;
			}
		}

	public static void main(String[] args) throws Exception {
		UserAuthority u=new UserAuthority();
		u.UseAuthority();
		u.run();
		
	}
	
}
