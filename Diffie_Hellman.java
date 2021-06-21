import java.util.*;

  class Diffie_Hellman
  {
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);

		//generate prime numbers 2 to 20, store in primes array
		int[] primes = new int[] {2, 3, 5, 7, 11, 13, 17, 19};
		int[][] roots = new int[][] {
			{1, 1, 1, 1, 1, 1, 1, 1}, // 2
			{2, 2, 2, 2, 2, 2, 2, 2}, // 3
			{2, 3, 3, 3, 3, 3, 3, 3}, // 5
			{3, 5, 5, 5, 5, 5, 5, 5}, // 7
			{2, 6, 7, 8, 8, 8, 8, 8}, // 11
			{2, 6, 7, 11, 11 ,11 ,11 ,11}, // 13
			{3, 5, 6, 7, 10, 11, 12, 14}, // 17
			{2, 3, 10, 13, 14, 15, 15, 15}}; // 19

		System.out.println("primes: their roots");

		for (int i = 0; i < primes.length; ++i) {
			System.out.print(primes[i] + ": ");
			for (int j = 0; j < roots[0].length; ++j)
				System.out.print(roots[i][j] + " ");

			System.out.println();
		}

		// get prime number and primitive root
		int min = 2;
		int max = 20;

		System.out.println("Enter prime number (" + min + " < p < " + max + ") :");
		int p=sc.nextInt();

		// check if p is between min and max
		if (p < min || p > max) {
			System.out.println("p = " + p + " is not in desired range. Exit!");
			closeScannerAndExit(sc);
		}

		// check if p is a prime number
		for (int i = 2; i <= p/2; ++i) {
			if (p % i == 0) {
				System.out.println("p = " + p + " is not a prime number. Exit!");
				closeScannerAndExit(sc);
			}
		}

		// enter g here
		System.out.println("Enter primitive root 'g' of "+p);
		int g=sc.nextInt();

		// prime number check
		int index = locate(p, primes); // locate position of 'number' in primes[]
		boolean found = index != primes.length; // check if index in valid range

		if (!found) {
			System.out.println(p + " is not in the desired range. Exit!");
			closeScannerAndExit(sc);
		}

		// primitive root check
		int index_g = locate(g, roots[index]);
		found = index_g != roots[index].length;

		if (!found) {
			System.out.println(g + " is not a primitive root of " + p + ". Exit!");
			closeScannerAndExit(sc);
		}

		// check if g smaller p and greater 0
		if (g >= p || g < 0) {
			System.out.println("g needs to be smaller p and greater 0. Exit!");
			closeScannerAndExit(sc);
		}

		// get a number of participants
		System.out.println("Enter number of participants: ");
		int n=sc.nextInt();

		// check number of participants
		if (n < 2) {
			System.out.println("Not enough participants. Exit!");
			closeScannerAndExit(sc);
		}

		// get secret keys
		int[] secrets = new int[n];

		for (int i = 0; i < n; ++i) {
			System.out.println("Enter secret for participant " + i + ":");

			// get and store secret to array
			secrets[i] = sc.nextInt();

			// check for negative or zero
			while (secrets[i] <= 0) {
				System.out.println("Please enter positive integer: ");
				secrets[i] = sc.nextInt();
			}

			// check if secret is smaller p
			while (secrets[i] > p) {
				System.out.println("Please enter a number smaller " + p);
				secrets[i] = sc.nextInt();
			}
		}

		sc.close();
		
		// print all secret keys
		for (int i = 0; i < n; ++i) {
			System.out.println("Secret key for participant "+ i + ": " + secrets[i]);
		}

		// calculate public keys
		int[][] public_keys = new int[n][n-1];
		int base = g;
		int j;

		for (int k = 0; k < n-1; ++k) {      // public keys
			for (int i = 0; i < n; ++i) {    // participants

				if (k != 0) {                // first run using g as base
					j = i + 1;               // after above; gives keys in circles to all participants
					if ( j >= n)             // key of last participant is given to first participant
						j = 0;
					base = public_keys[j][k-1];
				}

				public_keys[i][k] = (int)Math.pow(base, secrets[i])%p;
				//System.out.println(public_keys[i][k]);
			}
		}

		// calculate shared keys
		int[] shared_keys = calculateSharedKeys(p, n, secrets, public_keys);
		
		// compare shared keys for compatibility
		for (int i = 0; i < n-1; ++i) {
			for (int k = i+1; k < n; ++k) {
				if (shared_keys[i] == shared_keys[k]) {
					System.out.println("Participant " + i + " and " + k + " can communicate.");
				}
				else {
					System.out.println("Participant " + i + " and " +  k + " cannot communicate.");
				}
			}
		}
	}

	/**
	 * @param sc
	 */
	private static void closeScannerAndExit(Scanner sc) {
		sc.close();      // close scanner and exit
		System.exit(1);
	}

	/**
	 * @param p           prime number
	 * @param n           participants
	 * @param secrets     secret keys of participants
	 * @param public_keys public keys of participants
	 * @return
	 */
	private static int[] calculateSharedKeys(int p, int n, int[] secrets, int[][] public_keys) {
		int j;
		int[] sk = new int[n];

		for (int i = 0; i < n; ++i) {
			j = i + 1;
			if (j >= n)
				j = 0;
			sk[i] = (int)Math.pow(public_keys[j][n-2],secrets[i])%p; // use 'last' public keys (n-2) as base

			System.out.println("Shared secret key for participant "+ i + ": " + sk[i]);
		}
		return sk;
	}

	// locate 'number' in 'array'
	// returns index of 'number' in 'array':
	// - 0..length-1, if 'number' is in 'array', or
	// - length, if 'number' is not in 'array'
	static int locate(int number, int[] array) {
		int j;

		for ( j = 0; j < array.length; ++j) {
			if (number == array[j]) {
				break;
			}
		}

		return j;
	}
}