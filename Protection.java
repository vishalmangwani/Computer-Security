import java.io.*;
import java.security.*;

public class Protection
{
	public static byte[] makeBytes(long t, double q) 
	{    
		try 
		{
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();			
			DataOutputStream dataOut = new DataOutputStream(byteOut);
			dataOut.writeLong(t);
			dataOut.writeDouble(q);
			return byteOut.toByteArray();
		}
		catch (IOException e) 
		{
			return new byte[0];
		}
	}	

	public static byte[] makeDigest(byte[] mush, long t2, double q2) throws NoSuchAlgorithmException 
	{
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(mush);
		md.update(makeBytes(t2, q2));
		return md.digest();
	}
	

	public static byte[] makeDigest(String user, String password,long t1, double q1)throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA");		// We use the SHA algorithm for encryption
		md.update(user.getBytes());									//The update function does not take string for updating therefore we convert the string to bytes and then update them. Also this is because the bytes are digested and not the whole string
		md.update(password.getBytes());								//PAsssword to bytes and then updated.
		md.update(makeBytes(t1,q1));								//the random number and the timestamp are zipped together and bytes are made then on the whole it is updated to the digest variable.
		return md.digest();
	
	}
}