package com.gamecouch.gcs.gamecouchsystem;


import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Password {
	
	private byte[] password;
	private static final int SALT_LENGTH = 32;
	
	public Password(String password) {
		setPassword(password);
	}
	
	public Password(byte[] password) {
		this.password = password;
	}
	
	public byte[] getPassword() {
		return password;
	}

	public void setPassword(String password) { //add hash function with dynamic salt
		try {
			//System.out.println("created hashed password for: " + password);
			byte[] salt = getSalt(); 
			//System.out.println("salt: " + new String(salt));
			byte[] hashed = createSaltedHash(password.strip(), salt);
			//System.out.println("hashed: " + new String( hashed));
			byte[] combined = new byte[hashed.length + salt.length];
			System.arraycopy(hashed, 0, combined, 0, hashed.length);
			System.arraycopy(salt, 0, combined, hashed.length, salt.length);
			this.password = combined;
		} catch (GeneralSecurityException e) {
			System.out.println("Failed to hash password.");
			e.printStackTrace();
		}
	}
	
    private byte[] createSaltedHash(String password, byte[] salt) throws GeneralSecurityException  {

    	KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    	return factory.generateSecret(spec).getEncoded();
    }
    
    private byte[] getSalt( ) {
    	//System.out.println("Creating salt");
    	SecureRandom random = new SecureRandom();
    	byte[] salt = new byte[SALT_LENGTH];
    	random.nextBytes(salt);
    	return salt;
    }
    
    public boolean verifyPassword(String input) throws GeneralSecurityException {
    	//System.out.println("Verifying: " + input);    	
    	byte[] salt = new byte[SALT_LENGTH];
    	int saltStart = password.length - SALT_LENGTH;
    	System.arraycopy(password, saltStart, salt, 0, SALT_LENGTH);
    	byte[] hashedInput = createSaltedHash(input, salt);
    	//System.out.println("comparing stored password against: " + new String(hashedInput));
    	byte[] cleanPassword = new byte[saltStart];
    	System.arraycopy(password, 0, cleanPassword, 0, saltStart);
    	//System.out.println("cleaned password: " + new String(cleanPassword));
    	return (Arrays.equals(cleanPassword, hashedInput));
    }
}
