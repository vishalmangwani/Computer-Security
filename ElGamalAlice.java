import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Arrays;
import java.util.Random;
import java.math.BigInteger;

public class ElGamalAlice
{
	private static BigInteger computeY(BigInteger p, BigInteger g, BigInteger d)
	{
		BigInteger y=g.modPow(d, p);				//computing g(pow(d))*mod(p)
		return y;
	}

	private static BigInteger computeK(BigInteger p)
	{
		int i=0;								
		Random nr= new Random();												//A new random number is generated
		BigInteger k=new BigInteger(1024,nr);									//The random number is then set to be in between 1 to (2pow(1024) - 1)
		BigInteger p1=p.subtract(BigInteger.ONE);   							//p=p-1
		BigInteger[] bign=new BigInteger[1000];     																//We insert all previous occurances of k in array bign so that later we can compare if it is a new number or not
		while(!k.gcd(p1).equals(BigInteger.ONE) && !(Arrays.asList(bign).contains(k)))  // While loop executes untill we find a value for k which is coprime with p-1 and is unique in the list bign 
		{
			bign[++i]=k;			
			k=new BigInteger(1024,nr);										    // Untill a number that satisfies the above condition is obtained we keep generating new numbers.

		}
		return k;
	}
	private static BigInteger computeA(BigInteger p, BigInteger g, BigInteger k)
	{ 
		BigInteger a=g.modPow(k, p);											//computing a=g(pow(k))mod(p)
		return a;
	}
	private static BigInteger computeB(	String message, BigInteger d, BigInteger a, BigInteger k, BigInteger p)
	{
		BigInteger m = new BigInteger(message.getBytes()); 						//convert string to BigInteger
		BigInteger pm = p.subtract(BigInteger.ONE); 							// p1=p-1
		BigInteger p1 = pm;
		BigInteger x = BigInteger.ZERO;										//0
		BigInteger x1 = BigInteger.ONE;											//1
		BigInteger x2 = k;
		BigInteger z, p2, p3;
		while(!x2.equals(BigInteger.ZERO))
		{
			p = p1.divide(x2);													//p1/x2
			p2 = p1.subtract(x2.multiply(p));									//p1-x2*z
			p1 = x2;
			x2 = p2;
			p3 = x.subtract(x1.multiply(p));									//x0-x1*z
			x = x1;
			x1 = p3;
		}
		BigInteger b = x.multiply(m.subtract(d.multiply(a))).mod(pm);			//b = ((m-da)/k) mod (p-1) 
		return b;
	}
	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "cse01.cse.unt.edu";
		int port = 7999;
		Socket s = new Socket(host, port);										//Socket creation
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());	//creating an object stream.

		// You should consult BigInteger class in Java API documentation to find out what it is.
		BigInteger y, g, p; 													// public key
		BigInteger d; 															// private key

		int mStrength = 1024; 													// key bit length
		SecureRandom mSecureRandom = new SecureRandom(); 						// a cryptographically strong pseudo-random number

		// Create a BigInterger with mStrength bit length that is highly likely to be prime.
		// (The '16' determines the probability that p is prime. Refer to BigInteger documentation.)
		p = new BigInteger(mStrength, 16, mSecureRandom);
		
		// Create a randomly generated BigInteger of length mStrength-1
		g = new BigInteger(mStrength-1, mSecureRandom);
		d = new BigInteger(mStrength-1, mSecureRandom);

		y = computeY(p, g, d);

		// At this point, you have both the public key and the private key. Now compute the signature.

		BigInteger k = computeK(p);
		BigInteger a = computeA(p, g, k);
		BigInteger b = computeB(message, d, a, k, p);

		// send public key
		os.writeObject(y);
		os.writeObject(g);
		os.writeObject(p);

		// send message
		os.writeObject(message);
		
		// send signature
		os.writeObject(a);
		os.writeObject(b);
		
		s.close();
	}
}