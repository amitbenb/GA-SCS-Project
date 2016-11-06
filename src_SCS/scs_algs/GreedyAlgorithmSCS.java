package scs_algs;

import generators.SCS_BaseGenerator;

import java.io.File;
import java.util.Scanner;

public class GreedyAlgorithmSCS
{
	public static String mainDir = new String("Z:\\");
	public static String ParameterFilePath = new String("Z:\\parameters.txt");

	int m_arity = 2;
	int [] m_string = null;
	int [][] m_substrings = null;


	public GreedyAlgorithmSCS()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void run()
	{
		int[][] working_copy = new int[m_substrings.length][];
		boolean[] available_substrings = new boolean[m_substrings.length];
		
		makeWorkingCopy(working_copy, available_substrings);
		
		while(greedy_merge(working_copy, available_substrings));
//			if(Math.random() < 0.2)
//				System.out.println(Math.random() + "!");
		
		
//		System.out.print("X");
		for (int i = 0; i < working_copy[0].length; i++)
		{
			System.out.print(working_copy[0][i] + "_");
		}
		System.out.println();
		System.out.println(working_copy[0].length);

	}
	
	/**
	 * Make working copy of substrings 
	 * 
	 * @param working_copy
	 * @param available_substrings
	 */
	public void makeWorkingCopy(int[][] working_copy, boolean[] available_substrings)
	{

		for (int i = 0; i < working_copy.length; i++)
		{
			available_substrings[i] = true;
			working_copy[i] = new int[m_substrings[i].length];
			for (int j = 0; j < working_copy[i].length; j++)
			{
				working_copy[i][j] = m_substrings[i][j];
			}
		}
	}
	
	public boolean greedy_merge(int[][] working_copy, boolean[] available_substrings)
	{
		// Meaning no merge found. Not even a 0 contraction merge.
		boolean retVal = false;
		int bestContraction = -1, bestIdx1 = -1, bestIdx2 = -1; 
		
		eliminateContainedSubstrings(working_copy, available_substrings);

		for (int i = 0; i < available_substrings.length; i++)
		{
			if(available_substrings[i])
			{
				for (int j = i+1; j < available_substrings.length; j++)
				{
					if(available_substrings[j])
					{
//						System.out.println("What?!?");
						int concat_length = working_copy[i].length + working_copy[j].length;
						int[] tmp_string1 = merge(working_copy[i], working_copy[j]);
						int[] tmp_string2 = merge(working_copy[j], working_copy[i]);
						
//						for (int k = 0; k < tmp_string1.length; k++)
//						{
//							System.out.print(tmp_string1[k]  + " ");
//						}
//						System.out.println();
//						for (int k = 0; k < tmp_string2.length; k++)
//						{
//							System.out.print(tmp_string2[k] + " ");
//						}
//						System.out.println();

						if (concat_length - tmp_string1.length > bestContraction)
						{
							bestContraction = concat_length - tmp_string1.length;
							bestIdx1 = i;
							bestIdx2 = j;
//							System.out.println("Not Here!");
						}

						if (concat_length - tmp_string2.length > bestContraction)
						{
							bestContraction = concat_length - tmp_string2.length;
							bestIdx1 = j;
							bestIdx2 = i;
//							System.out.println("Here!");
						}
					}
				}
			}
		}
		
		if(bestIdx1 >= 0)
		{
			retVal = true;
			if (bestIdx1 < bestIdx2)
			{
				working_copy[bestIdx1] = merge(working_copy[bestIdx1], working_copy[bestIdx2]);
				available_substrings[bestIdx2] = false;
			}
			else
			{
				working_copy[bestIdx2] = merge(working_copy[bestIdx1], working_copy[bestIdx2]);
				available_substrings[bestIdx1] = false;
			}
		}
		
		return retVal;
	}

	public int[] merge(int[] str1, int[] str2)
	{
		int[] retVal = null;
		int maxPrefixSize = 0;

		for (int i = str2.length; maxPrefixSize == 0 && i > 0; i--)
		{
			boolean prefixFits = true;
			for (int j = 0; prefixFits && j < i; j++)
			{
				if (str1.length - i + j < 0
						|| str1[str1.length - i + j] != str2[j])
					prefixFits = false;
			}

			if (prefixFits)
			{
				maxPrefixSize = i;
			}
		}
		

		retVal = new int[str1.length + str2.length - maxPrefixSize];
		
//		System.out.println(str1.length);
//		System.out.println(str2.length);
//		System.out.println(maxPrefixSize);
//		System.out.println(retVal.length);
		
		for (int i = 0; i < str1.length; i++)
		{
			retVal[i] = str1[i];
		}
		for (int i = maxPrefixSize; i < str2.length; i++)
		{
			retVal[str1.length + i - maxPrefixSize] = str2[i];
		}
		
//		System.out.println("Here! " + retVal[7]);

		return retVal;
	}

	/**
	 * Gets rid of contained strings. Always eliminates substring with larger
	 * index. Swaps if necessary
	 * 
	 * @param working_copy
	 * @param available_substrings
	 */
	public void eliminateContainedSubstrings(int[][] working_copy,
			boolean[] available_substrings)
	{
		for (int i = 0; i < working_copy.length; i++)
		{
			if(available_substrings[i])
			{
				for (int j = i+1; j < available_substrings.length; j++)
				{
					if(available_substrings[j])
					{
						if (SCS_BaseGenerator.isSubstring(working_copy[i], working_copy[j]))
						{
							// If working_copy[j] is longer, move is to smaller index i.
							if (working_copy[i].length < working_copy[j].length)
								working_copy[i] = working_copy[j];

							working_copy[j] = null;
							available_substrings[j] = false;
						}
					}
				}
			}
		}
	}

	public static void main(String[] args)
	{
		if(args.length > 0)
			mainDir = new String(args[0]);
		else
			mainDir = new String("Z:\\SCS_Exp\\instances\\");


		if(args.length > 1)
			ParameterFilePath = new String(mainDir + args[1]);
		else
			ParameterFilePath = new String(mainDir + "OneInstance.txt");

		GreedyAlgorithmSCS gr = new GreedyAlgorithmSCS();

		try
		{
			gr.fetchProblem(ParameterFilePath);

			gr.run();
			
//			System.out.println(gr.m_arity);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void fetchProblem(String fileName)throws Exception
	{
		Scanner inF = new Scanner(new File(fileName));
		
		m_string = new int[inF.nextInt()];
		m_arity = inF.nextInt();
		m_substrings = new int[inF.nextInt()][];
		
		for (int i = 0; i < m_string.length; i++)
		{
			m_string[i] = inF.nextInt();
		}
		
		for (int i = 0; i < m_substrings.length; i++)
		{
			m_substrings[i] = new int[inF.nextInt()];
			for (int j = 0; j < m_substrings[i].length; j++)
			{
				m_substrings[i][j] = inF.nextInt();
			}
		}

		inF.close();
		
	}

}
