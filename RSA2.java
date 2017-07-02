import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Scanner;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
public class RSA2 {
	public static void main(String[] args) throws Exception {
		String host = "cse01.cse.unt.edu";
		int port = 7999;
		byte[] bytes=new byte[500];
		System.out.println("1. Confidentiality");
	    System.out.println("2. Integrity and Authentication");
	    System.out.print("Enter your choice:");
		Scanner input = new Scanner(System.in);				//Scan functuon
		Socket s = new Socket(host, port);					//Socket defining
		ObjectOutputStream writeStream = new ObjectOutputStream(s.getOutputStream());	
		ObjectInputStream readStream = new ObjectInputStream(s.getInputStream());
		KeyPair keyPair  = KeyPairGenerator.getInstance("RSA").genKeyPair();//Generating instance of RSA and then their keys
	    RSAPublicKey Alicepub = (RSAPublicKey) keyPair.getPublic();			//Getting public key
	    RSAPrivateKey AlicePri = (RSAPrivateKey)keyPair.getPrivate();		//Getting Private key
	    writeStream.writeObject(Alicepub);											//Writing public key because it is sharable
	    RSAPublicKey Bobpub = (RSAPublicKey) readStream.readObject();				//Reading Public key of Bob
	    Cipher crypt = Cipher.getInstance("RSA");							//Defining the ciphering algorithm to be RSA
	    int choice= input.nextInt();										//Scanning choice of user.
		if(choice==1){														//for Choice 1
		System.out.println("You have selected Confidentiatlity ");		
		Scanner scan = new Scanner(System.in);			
		System.out.println("Enter data to send:");
		String scan1= scan.nextLine();
		crypt.init(Cipher.ENCRYPT_MODE, Bobpub);						//Initializaing Encrypting with bob's public key
 		bytes = crypt.doFinal(scan1.getBytes());					//doFinal converts it inro bytes and encrypts it. 
		for(int i=0;i<bytes.length;i++)
		System.out.println(bytes[i]);
		}
		else if(choice==2){
		System.out.println("You have selected Integertity/Authentication");
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter data to send:");		
		String scan2= scan.nextLine();
		crypt.init(Cipher.ENCRYPT_MODE, AlicePri);					//Intializing Ecrypting with alices Private key since it is integrity
 		bytes = crypt.doFinal(scan2.getBytes());				//Dofinal takes bytes and excrypts it.
		}
		else 
		System.out.println("Invalid choice");
		writeStream.writeInt(choice);											//Writing the choice selected over stream
		writeStream.writeObject(bytes);										//writing the bytes over the stream
		    s.close();
		    input.close();
		}
	}