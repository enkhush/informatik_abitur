import java.util.*;

  class Diffie_Hellman
  {
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);

		// get prime number and primitive root
		System.out.println("Enter modulo(p)");
		int p=sc.nextInt();
		System.out.println("Enter primitive root of "+p);
		int g=sc.nextInt();

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