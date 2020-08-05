package arrays;

import java.util.Scanner;

public class temp 
{
	    public static boolean canWin(int leap, int[] game)
	    {
	        // Return true if you can win the game; otherwise, return false.
	        for(int i = 0; i<game.length; i++)
	        {
	            if(i+leap > game.length - 1 || i+1 > game.length - 1)
	                return true;
	            
	            if(game[i+leap] == 0)
	            {
	                i = i + leap;
	                continue;
	            }

	            if(game[i + 1] == 0)
	            {
	                i++;continue;
	            }

	            if(i-1>=0 && game[i-1] == 0)
	            {
	                i--;continue;
	            }
	            return false;
	        }
	        return true;
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
