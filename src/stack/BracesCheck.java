package stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BracesCheck 
{
	public static void main(String[] args) 
	{
		Scanner in  = new Scanner(System.in);
		System.out.println("Enter braces : ");
		String str = in.nextLine();
		in.close();
		System.out.println("Your input : " + str);
		System.out.println(isValidBracketSequence(str));
	}
	
	public static boolean isValidBracketSequence(String str)
	{
		if(str.length() % 2 != 0)
			return false;
		
		Map<Character, Character> reverseBrackets = new HashMap<Character, Character>();
		reverseBrackets.put('{', '}');
		reverseBrackets.put('[', ']');
		reverseBrackets.put('(', ')');
		ArrayStack<Character> stack = new ArrayStack<Character>();
		for(int i = 0; i < str.length(); i++)
		{
			Character c = str.charAt(i);
			if(reverseBrackets.keySet().contains(c))
				stack.push(c);
			else if(reverseBrackets.values().contains(c))
			{
				if(stack.isEmpty())
					return false;
				else
				{
					Character c2 = stack.pop();
					if(!c.equals(reverseBrackets.get(c2)))
						return false;
				}
					
			}
		}
		return stack.isEmpty();
	}
}
