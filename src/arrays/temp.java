package arrays;

import java.util.Scanner;

public class temp 
{
		private static boolean validMoveAvailable(int index, int leap, int[] game)
		{
			return false;
		}
	    public static boolean canWin(int leap, int[] game)
	    {
	        // Return true if you can win the game; otherwise, return false.
        	int size = game.length;
        	
	    	for(int i = 0; i<size;)
	        {
	    		if(leap + i > size -1 || i + 1 > size - 1)
	    			return true;
	    		
				if(leap + i < size - 1)
				{
					if(game[leap + i] == 0)
					{
						i = i+leap;
						continue;
					}
				}
				if(i + 1 < size - 1)
				{
					if(game[i + 1] == 0)
					{
						i++;continue;
					}
				}
				if(i - 1 >= 0)
				{
					if(game[i - 1] == 0)
					{
						i--;continue;
					}			
				}
				return false;
			}
			return false;
	    }

	    public static void main(String[] args) 
	    {
	        Scanner scan = new Scanner(System.in);
	        int q = scan.nextInt();
	        while (q-- > 0) {
	            int n = scan.nextInt();
	            int leap = scan.nextInt();
	            
	            int[] game = new int[n];
	            for (int i = 0; i < n; i++) {
	                game[i] = scan.nextInt();
	            }

	            System.out.println( (canWin(leap, game)) ? "YES" : "NO" );
	        }
	        scan.close();
	    }
	
}
