package net.kadirderer.btc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashingUtil {
	
	private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public static String generateSalt() {
		//Always use a SecureRandom generator
	    String saltString = null;
		try {
			SecureRandom sr = null;
			sr = SecureRandom.getInstance("SHA1PRNG");
			//Create array for salt
		    byte[] salt = new byte[16];
		    //Get a random salt
		    sr.nextBytes(salt);		    
		    saltString = salt.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    
	    return saltString;
	}
	
	public static String generateSecurePassword(String passwordToHash, String salt)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return generatedPassword;
    }
	
	public static String generateHashFor(String value) {
		String generatedHash = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(value.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }
		
        return generatedHash;
	}
	
	public static BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return bCryptPasswordEncoder;
	}
	
	public static String generateBCryptedHash(String value) {
		return bCryptPasswordEncoder.encode(value);
	}
	
}
