import java.io.*;
import java.net.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Scanner;
import javax.crypto.*;

public class CERTClient {
	public static void main(String[] args) throws Exception 
	{
	String host = "cse01.cse.unt.edu";
	int port = 7999;
	Socket ss = new Socket(host, port);
	System.out.println("print");
	ObjectOutputStream objstream = new ObjectOutputStream(ss.getOutputStream());	//Creating the stream
    InputStream inStream = new FileInputStream("/home/vm0251/CSPro/cert.cer");	//Taking the Certificate file into input stream
    X509Certificate cert1 = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(inStream);		//Getting the instance of the certificate type
	System.out.println(cert1.toString())      
try
      {
  	   cert1.checkValidity();													//Checking the validity of the certificate
  	   System.out.println("The certificate is valid.");
      } catch (Exception e){
  	   System.out.println(e);  
      }
      System.out.println("Input String:");						//Taking the input stream
      Scanner input = new Scanner(System.in);
      String message= input.nextLine();
      RSAPublicKey sv = (RSAPublicKey) cert1.getPublicKey();		//getting the RSA public key from the certificate
      Cipher text_cipher = Cipher.getInstance("RSA");						//Creating the cipher RSA instance so that the input text can be ciphered.
      text_cipher.init(Cipher.ENCRYPT_MODE,sv);						//Encrypt mode with the public key we got from the certificate
      byte[] Text = text_cipher.doFinal(message.getBytes());			//Ecnrypting the text	
      objstream.writeObject(Text);										//Writing the crypted object.
      objstream.flush();	
	}



}