import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherClient
{
	public static void main(String[] args) throws Exception 
	{
		String msg = "The quick brown fox jumps over the lazy dog.";//The message to be sent
		System.out.println("The Message being sent is"+msg);
		String host = "cse01.cse.unt.edu";
		int port = 7999;
		Socket s = new Socket(host, port);														//creating a socket
		Key DesSec = KeyGenerator.getInstance("DES").generateKey();								//KeyGenerator generates DES KEY
		File f = new File("data.txt");													//Creating a file (file key)
		ObjectOutputStream objstream = new ObjectOutputStream(s.getOutputStream());				//Creating a New object stream to write objects
		Cipher c=Cipher.getInstance("DES");														// Encrypting the message with the DES algo.
		CipherOutputStream cos=new CipherOutputStream(s.getOutputStream(), c);					//Creating a cipher stream to send the ciphered data
		c.init(Cipher.ENCRYPT_MODE, DesSec);													//initializing the encrypting mode and the key.
	    objstream.writeObject(DesSec);															//writing the key object over the stream
	    objstream.flush();																		//writing the message over the stream
		cos.write(msg.getBytes());																	// Encrypts and writes to the stream because cos is ciphered stream.
		cos.close();
		s.close();
	}
}