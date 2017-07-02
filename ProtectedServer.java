import java.io.*;
import java.net.*;
import java.security.*;

public class ProtectedServer
{
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException 
	{
		DataInputStream in = new DataInputStream(inStream);	//InputStream object creation
		String user=in.readUTF();							//Reading the username as UTS format that we had written to the stream
		String password=lookupPassword(user);				//Here since this is a small program we have predefined the passowrd in the lookup function. Usually this is a database query to read the password.
		long timestamp=in.readLong();						//Reading the timestamp 1 that we had written to the stream
		double n=in.readDouble();							//Reading the random number that we had written to the stream
		long timestamp1=in.readLong();						//Reading the timestamp 2 from the stream
		double n1=in.readDouble();						//Reading the random number two from the stream
		int len=in.readInt();									//Reading the length of the digest.
		byte[] digest1=new byte[len];
		in.readFully(digest1);								//Reading the digest from the stream
		
		
		byte[] compare=Protection.makeDigest(user,password ,timestamp ,n);		//Creating the first digest with username password timestamp and randomnumber (Similar to the digest we created in the client file)
		byte[] compare1=Protection.makeDigest(compare,timestamp1 ,n1);			//Creating the second digest as it was created from the client file. We do the same process here as well.
																				//The make digest definitions are from the Protection class.
		return MessageDigest.isEqual(digest1, compare1);						//Comparing the digests from the client and created at the server.
	}

	protected String lookupPassword(String user) { return "abc123"; }

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket ss = new ServerSocket(port);
		Socket client = ss.accept();

		ProtectedServer server = new ProtectedServer();
		if (server.authenticate(client.getInputStream()))			
		  System.out.println("Program Executed. Login Successful");
		else
		  System.out.println("Program Executed.Login Failed.");

		ss.close();
	}
}