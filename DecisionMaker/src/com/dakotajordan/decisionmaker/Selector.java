package com.dakotajordan.decisionmaker;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Selector {
	public static int makeChoice(){
		return randomSelect(Randomize.PlaceholderFragment.getListCount());
	}
	
	//Returns randomly-generated int
		public static int generate() {
			try {
				// Create a secure random number generator using the SHA1PRNG algorithm
				SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");
				// Get 128 random bytes
				byte[] randomBytes = new byte[128];
				secureRandomGenerator.nextBytes(randomBytes);
	    
				return new Integer(secureRandomGenerator.nextInt());
			}
			catch (NoSuchAlgorithmException e) {
				return -1;
			}
		}
		
		//Take int input of the sample size
		//Returns randomly-selected number from sample
		public static int randomSelect(int size){
			int n = -1;
			while(n == -1){
				//generate() returns a random integer, or -1 if error occurs
		  		n = generate();
			}
			return Math.abs(n) % size;
		}
}
