import java.io.*;
import java.net.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
public class RSA1 {
	public static void main(String[] args) throws Exception {
	    int port = 7999;
		ServerSocket ss = new ServerSocket(port);
		Socket Alice = ss.accept();
		KeyPair keyss = KeyPairGenerator.getInstance("RSA").genKeyPair();				//Getting instance of RSA algorithm and generating key pairs
		ObjectInputStream readStream = new ObjectInputStream(Alice.getInputStream());//
		ObjectOutputStream writeStream = new ObjectOutputStream(Alice.getOutputStream());
	    RSAPublicKey BobPub = (RSAPublicKey) keyss.getPublic();						//getting public key for bob
	    RSAPrivateKey BobPri = (RSAPrivateKey)keyss.getPrivate();					//getting private key for bob
	    writeStream.writeObject(BobPub);													//writing bobs public key
	    RSAPublicKey Alicepub = (RSAPublicKey) readStream.readObject();					//reading alice public key
	    Cipher crypt = Cipher.getInstance("RSA");									//instance of RSA algroithm
	    int choice = readStream.readInt();											
		if(choice==1){
		System.out.println("You have selected Confidentiality");
		System.out.println("Text after decrypting");
		crypt.init(Cipher.DECRYPT_MODE, BobPri);						//Decrypt mode
		byte[] in1 = (byte[]) readStream.readObject();						//reading alice public key to integer
 		byte[] plaintText1 = crypt.doFinal(in1);							//decrypting the data
 		String x=new String(plaintText1);
		System.out.println("Decrypted text: " + x);					
		}
		else if(choice ==2){
		System.out.println("You have selected integrity/Authentication");
		System.out.println("Text after decrypting");
		crypt.init(Cipher.DECRYPT_MODE, Alicepub);						//Decrypting mode
		byte[] in2 = (byte[]) readStream.readObject();					//reading alice key
 		byte[] plaintText2 = crypt.doFinal(in2);
 		String x=new String(plaintText2);
		System.out.println("Decrypted text: " + x);	
			}
		else System.out.println("Wrong Choice");
		}
	}