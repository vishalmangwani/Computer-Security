import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Date;

public class ProtectedClient
{
	public void sendAuthentication(String user, String password, OutputStream outStream) throws IOException, NoSuchAlgorithmException 
	{
		DataOutputStream out = new DataOutputStream(outStream);		//Stream from sending data over server.		
		Date d=new Date();											//creating object for date class as we need timestamp to digest.
		
		long timestamp=d.getTime();									//getting the Time form the date class for timestamp
		double n=Math.random();
		byte[] digest=Protection.makeDigest(user,password,timestamp,n); //creating a digest. The digest function finishes the computation with padding. The digest gets back to start after execution.		
																		//we input the username passowrd, timestamp and and a random number
																		//We use the Protection class digest dunction to do this. i.e. the digest function definition is from the Protection file.
		long timestamp1=d.getTime();									//creating the timestamp2 for the second digest
		double n1=Math.random();										//Another random number
		double n2=Math.random();		
byte[] digest1 =Protection.makeDigest(digest, timestamp1, n1);	//from the above same implementation of digest
																		//Here we take the above digest generated and add that to the function along with another timestamp and random number
		
		out.writeUTF(user);												//Writing username as String UTF object to the datastream
		out.writeLong(timestamp);										//Writing first timestamp to the datastream.
		out.writeDouble(n);												//Writing the random number 1 to the data stream
		out.writeLong(timestamp1);										//Writing the second time stamp to the stream    (NOTE that we do not write the digest1 obtained to the stream)
		out.writeDouble(n2);											//Writing the second random number to the stream
		int x=digest1.length;
		out.writeInt(x);									//Writing the digest length so that on the Server side we get to know how many bytes are to be read from the stream
		out.write(digest1);												//Writing the digest value to the server
				
		out.flush();
	}

	public static void main(String[] args) throws Exception 
	{
		String host = "cse01.cse.unt.edu";
		int port = 7999;
		String user = "Vishal";
		String password = "abc123";
		Socket s = new Socket(host, port);

		ProtectedClient client = new ProtectedClient();
		client.sendAuthentication(user, password, s.getOutputStream());

		s.close();
	}
}