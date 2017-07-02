import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherServer
{
	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket ss = new ServerSocket(port);								//Creating a server socket
		Socket s = ss.accept();													//variable that accepts a servers request
		ObjectInputStream input = new ObjectInputStream(s.getInputStream());       // Object stream for taking the key from the clients stream as input
		Key key = (Key) input.readObject(); 										// Key is read from the input stream
		System.out.println("The ciphered key is :"+key);
		Cipher c=Cipher.getInstance("DES");										//getting the DES instance of cipher
		c.init(Cipher.DECRYPT_MODE, key);
		System.out.println(c);										// initializing the decrypting mode with the key from the stream.
		CipherInputStream cis = new CipherInputStream(s.getInputStream(), c);	//cis will then contain ascii values of the text.
		int var;
		System.out.print("The data sent by client is:");
		while((var=cis.read())!=-1)												//x has ascii values which are read one by one.
			System.out.print(((char)var));										//converting the ascii values to the regular chars and printing it on the console. Note the data here is not store but just displayed on the console.
		cis.close();															
		ss.close();
	}
}