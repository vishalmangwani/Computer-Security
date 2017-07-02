import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CERTServer {

	public static void main(String[] args) throws Exception 
	{   
		
		String alias="Alias";							//The name should be same a sthta in the certificate
		char[] pass="vishal".toCharArray();				//The password for the keystore as when generated.
		
        int port = 7999;
		ServerSocket ss = new ServerSocket(port);		
		Socket s = ss.accept();							//Server to accept the incoming connecting
		ObjectInputStream input = new ObjectInputStream(s.getInputStream());
		KeyStore keystr = KeyStore.getInstance("jks");			//Creating the instance of the keystore
		keystr.load(new FileInputStream("/home/vm0251/CSPro/keystore.jks"), pass);//Accessing the keystore with the filename and the password provided.
        PrivateKey sev = (PrivateKey)keystr.getKey(alias, pass);		//getting the private key from the keystore
        Cipher cy = Cipher.getInstance("RSA");								//Creating an instance of the RSA algorithm
        byte[] bytes = (byte[]) input.readObject();									
        cy.init(Cipher.DECRYPT_MODE, sev);								//initializing Decrypt mode.
		byte[] plaintText = cy.doFinal(bytes);									//converting the ciphered text to bytes
		System.out.println("The text at server: " + new String(plaintText));		//printing the bytes to string
		ss.close();
	}

}