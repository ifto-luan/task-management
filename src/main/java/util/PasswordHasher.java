package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHasher {

	public static byte[] getSalt() throws NoSuchAlgorithmException {

		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;

	}

	public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(salt);
		byte[] hashedPassword = md.digest(password.getBytes());

		StringBuilder sb = new StringBuilder();

		for (byte b : salt) {

			sb.append(String.format("%02x", b));

		}

		for (byte b : hashedPassword) {

			sb.append(String.format("%02x", b));

		}

		return sb.toString();

	}

	public static boolean verifyPassword(String storedPassword, String passwordAttempt) throws NoSuchAlgorithmException {

		String saltHex = storedPassword.substring(0, 32);
		byte[] salt = new byte[16];

		for (int i = 0; i < salt.length; i++) {

			salt[i] = (byte) Integer.parseInt(saltHex.substring(i * 2, i * 2 + 2), 16);

		}

		String hashAttempt = hashPassword(passwordAttempt, salt);

		return hashAttempt.equals(storedPassword);

	}

}
