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

		System.out.println(primes.length + " " + roots[0].length);

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
			return;
		}

		// check if p is a prime number
		for (int i = 2; i <= p/2; ++i) {
			if (p % i == 0) {
				System.out.println("p = " + p + " is not a prime number. Exit!");
				return;
			}
		}

		// enter g here
		System.out.println("Enter primitive root of "+p);
		int g=sc.nextInt();

		// prime number check
		boolean found = false;  // indicates if a number is in primes[] or roots[][]
		int index = 0;

		for (int i = 0; i < primes.length; ++i) {
			if (primes[i] == p) {
				found = true;
				index = i;
				break;
			}
		}

		if (!found) {
			System.out.println(p + " is not in the desired range.");
			return;
		}

		// get a number of participants
		System.out.println("Enter number of participants: ");
		int n=sc.nextInt();

		// get secret keys
		int[] secrets = new int[n];

		for (int i = 0; i < n; ++i) {
			System.out.println("Enter secret for participant " + i + ":");
			// get and store secret to array
			secrets[i] = sc.nextInt();
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

				if (k != 0) {
					j = i + 1;
					if ( j >= n)
						j = 0;
					base = public_keys[j][k-1];
				}

				public_keys[i][k] = (int)Math.pow(base, secrets[i])%p;
				//System.out.println(public_keys[i][k]);
			}
		}

		// calculate shared keys
		int[] shared_keys = new int[n];

		for (int i = 0; i < n; ++i) {
			j = i + 1;
			if (j >= n)
				j = 0;
			shared_keys[i] = (int)Math.pow(public_keys[j][n-2],secrets[i])%p;

			System.out.println("Shared secret key for participant "+ i + ": " + shared_keys[i]);
		}
		
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
}