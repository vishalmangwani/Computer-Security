import java.math.BigInteger;
import java.security.*;
import java.util.*;
public class MD5andSHA {

	public static void main(String[] args) 
	{
		MD5andSHA x= new MD5andSHA(); 							//creating object for the class so that we can access the functions
		System.out.println("Enter the String :");
		Scanner sc=new Scanner(System.in);						//Taking input
		String s=sc.nextLine();
		System.out.println("The entered string is "+s);			//displays the input string
		System.out.println("MD5 hash:"+x.MD5(s).toLowerCase());				//calls MD5 function directly while printing
		System.out.println("SHA hash:"+x.SHA(s).toLowerCase());				//calls SHA function directly while printing
	}
	
	// This function is for MD5 hashing
	public String  MD5(String s)
	{		
		MessageDigest md=null;								
		try{
			md = MessageDigest.getInstance("MD5");}				//getting instance of MD5 (Message digest is a class that takes input a string and the digest() creates the digest )
		catch(Exception e){
			e.printStackTrace();}
		
		byte[] md5sum = md.digest(s.getBytes());
		BigInteger b=new BigInteger(1, md5sum);
	    String output = String.format("%032X", b);			//The  md5sum has the bytes hashed values and then the BigInteger generates a new BigInteger for hashing and formats the string literal as per the format mentioned.
		return output;
	}
	
	public String SHA(String s){
		MessageDigest md=null;
		try{
			md = MessageDigest.getInstance("SHA");}				//getting instance of SHA
		catch(Exception e){
			e.printStackTrace();}
		
		byte[] md5sum = md.digest(s.getBytes());
		BigInteger b2=new BigInteger(1, md5sum);
	    String output = String.format("%032X", b2); //The  md5sum has the bytes hashed values and then the BigInteger generates a new BigInteger for hashing and formats the string literal as per the format mentioned.
		return output;
	
	
	}
	
	
}
