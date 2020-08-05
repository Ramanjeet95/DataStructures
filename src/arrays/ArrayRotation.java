package arrays;

public class ArrayRotation
{
	public static void main(String[] args) 
	{
		int a[] = {1,2,3,4,5,6,7};
		
		int n = 1;
		//a = leftRotation(a, n);
		a = rightRotation(a, n);
		for(int i = 0; i<a.length; i++)
			System.out.print(a[i] + " ");
	}
	
	private static int[] leftRotation(int a[], int n)
	{
		for(int i = 0; i < n; i++)
		{
			int temp = a[0];
			for(int j = 0; j < a.length - 1; j++)
				a[j] = a[j+1];
			a[a.length-1] = temp;
		}
		return a;
	}
	
	private static int[] rightRotation(int a[], int n)
	{
		for(int i = 0; i < n; i++)
		{
			int temp = a[a.length-1];
			for(int j = a.length - 1; j > 0; j--)
				a[j] = a[j-1];
			a[0] = temp;
		}
		return a;
	}
}
