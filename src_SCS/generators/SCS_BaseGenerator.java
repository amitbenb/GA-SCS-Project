package generators;

import java.io.FileWriter;
import java.io.IOException;

abstract public class SCS_BaseGenerator
{
	public static int length = 600, arity = 2;
	public static String path = "Z:\\", outFileName = "generated_SCS_input.txt";
	
	public void mainWork(String[] args)
	{
		FileWriter outF;
		try
		{
			outF = new FileWriter(path + outFileName);
			
			int[] randString = generateTemplateString(length, arity);
			
//			for (int i = 0; i < randString.length; i++)
//			{
//				System.out.print(randString[i] + " ");
//			}
			int[][] substrings = generateSubstrings(randString);

			// Debug output loop
//			for (int i = 0; i < substrings.length; i++)
//			{
//				for (int j = 0; j < substrings[i].length; j++)
//				{
//					System.out.print(substrings[i][j] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();
			
			substrings = SCS_BaseGenerator.pruneSubstrings(substrings);
			
			writeProblem(randString, substrings, outF);
			
			outF.close();
			
//			int[] aaa = {0,1,0,1}, bbb = {1,1};
//			System.out.println(isSubstring(bbb,aaa));
			
			// Debug output loop
//			for (int i = 0; i < substrings.length; i++)
//			{
//				for (int j = 0; j < substrings[i].length; j++)
//				{
//					System.out.print(substrings[i][j] + " ");
//				}
//				System.out.println();
//			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	abstract public int[][] generateSubstrings(int[] randString);

	abstract public int[] generateTemplateString(int length, int arity);

	public void writeProblem(int[] randString, int[][] substrings, FileWriter outFile)
			throws IOException
	{
		
		outFile.write(length + " " + arity + " " + substrings.length+ "\n");
		for (int i = 0; i < randString.length; i++)
		{
			outFile.write(randString[i] + " ");			
		}
		outFile.write("\n");			
		
		for (int i = 0; i < substrings.length; i++)
		{
			outFile.write(substrings[i].length + " ");
			for (int j = 0; j < substrings[i].length; j++)
			{
				outFile.write(substrings[i][j] + " ");
			}
			outFile.write("\n");;
		}

	}

	public static int[][] pruneSubstrings(int[][] substrings)
		{
			int[][] retVal;
			int actualLength = substrings.length;
	
			for (int i = 0; i < substrings.length; i++)
			{
				int[] str1 = substrings[i];
				for (int j = i + 1; str1 != null && j < substrings.length; j++)
				{
					int[] str2 = substrings[j];
					if (str2 != null && SCS_BaseGenerator.isSubstring(str1, str2))
					{
						if (str1.length < str2.length)
						{
							if(substrings[i] != null)
							{
								substrings[i] = null;
	//							System.out.println(actualLength);
								actualLength--;
							}
						}
						if (str1.length >= str2.length)
						{
							if (substrings[j] != null)
							{
								substrings[j] = null;
	//							System.out.println(actualLength);
								actualLength--;
								
							}
						}
					}
				}
			}
			
			retVal = new int[actualLength][];
			
			for (int i = 0, j = 0; i < retVal.length; i++, j++)
			{
				for (;substrings[j] == null;j++);
				retVal[i] = substrings[j];
			}
			
			return retVal;
		}

	/**
	 * 
	 * @param str1
	 * @param str2
	 * @return true iff one of the parameters is a substring of the other.
	 */
	public static boolean isSubstring(int[] str1, int[] str2)
	{
		boolean retVal = false;
		if(str1.length < str2.length)
		{
			int[] str3 = str1;
			str1 = str2;
			str2 = str3;
		}
		
		for (int i = 0; !retVal && i < str1.length - str2.length + 1; i++)
		{
			int j;
			for (j = 0; j < str2.length && str1[i+j] == str2[j]; j++);
			if (j == str2.length)
				retVal = true;
		}
		return retVal;
	}

	public static int[] generateRandomString(int length, int arity)	
		{
			int[] retVal = new int[length];
			
			for (int i = 0; i < retVal.length; i++)
			{
				retVal[i] = (int)(Math.random()*arity);
	//			System.out.print(retVal[i] + " "); 
			}
			
			return retVal;
		}

}
