import java.io.*;
import java.net.* ;
import java.math.BigInteger;
 
public class ElGamalBob
{
	private static boolean verifySignature(	BigInteger y, BigInteger g, BigInteger p, BigInteger a, BigInteger b, String message)
	{
		BigInteger x=new BigInteger(message.getBytes());							//Converting into bytes and then BigInteger
		BigInteger x1=g.modPow(x, p);												//g(pow(x))mod p
		BigInteger x2 = (y.modPow(a, p).multiply(a.modPow(b, p))).mod(p);			//ypow(a)apow(d)mod(p)
		System.out.println("Signature for bob   : "+x1);									//Signature for bob										
		System.out.println("Signature for alice    :"+x2);								//Signature for alice
		boolean z=x1.equals(x2);
		return z;
	}

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();
		ObjectInputStream istream = new ObjectInputStream(client.getInputStream());

		// read public key
		BigInteger y = (BigInteger)istream.readObject();
		BigInteger y2 = (BigInteger)istream.readObject();
		BigInteger y3 = (BigInteger)istream.readObject();

		// read message
		String message = (String)istream.readObject();

		// read signature
		BigInteger a = (BigInteger)istream.readObject();
		BigInteger b = (BigInteger)istream.readObject();

		boolean result = verifySignature(y, y2, y3, a, b, message);

		System.out.println(message);

		if (result == true)
			System.out.println("Signature verified.");
		else
			System.out.println("Signature verification failed.");

		s.close();
	}
}