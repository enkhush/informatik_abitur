import java.util.*;

  class Diffie_Hellman
  {
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter modulo(p)");
		int p=sc.nextInt();
		System.out.println("Enter primitive root of "+p);
		int g=sc.nextInt();
		System.out.println("Choose 1st secret no(Alice)");
		int a=sc.nextInt();
		System.out.println("Choose 2nd secret no(BOB)");
		int b=sc.nextInt();
		System.out.println("Choose 3rd secret no(Charlie)");
		int c=sc.nextInt();
		
		int A = (int)Math.pow(g,a)%p;
		int B = (int)Math.pow(g,b)%p;
		int C = (int)Math.pow(g,c)%p;
		
		int AB = (int)Math.pow(B,a)%p;
		int BC = (int)Math.pow(C,b)%p;
		int CA = (int)Math.pow(A,c)%p;

		int ABC = (int)Math.pow(BC,a)%p;
		int BAC = (int)Math.pow(CA,b)%p;
		int CAB = (int)Math.pow(AB,c)%p;

		sc.close();
		
		if(ABC==BAC && ABC==CAB)
		{
			System.out.println("ALice, Bob and Charlie can communicate with each other!!!");
			System.out.println("They share a secret no = "+ABC);
		}
		
		else
		{
			System.out.println("ALice, Bob and Charlie cannot communicate with each other!!!");
		}
	}	
}