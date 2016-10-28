package gaPack.fitnessPack;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import gaPack.indPack.SCS_Individual;
import popPack.indPack.GA_Individual;
import popPack.indPack.GA_Individual.*;
import popPack.indPack.GA_IntVector_Individual.*;

public class SCS_Fitness1 implements SCS_Fitness
{
	int m_arity = 2;
	int [] m_string = null;
	int [][] m_substrings = null;

	@Override
	public String generateFitnessDataText(GA_Individual ind)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateFitnessDataLine(GA_Individual ind)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateFitnessDataTableHeader()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double calculateFitness(SCS_Individual ind)
	{
		double retVal = 0.0;
		GA_Atom[] genome = (GA_Atom[])ind.getGenome();
		ArrayList<Integer> the_string = getStringFromGenome(genome); 
		retVal += the_string.size();

		ind.setMarkerArray(ind.getGenomeInInts());
		
		return -retVal;
	}

	private ArrayList<Integer> getStringFromGenome(GA_Atom[] genome)
	{
		ArrayList<Integer> retVal = new ArrayList<Integer>();
		
		boolean[] containedSubstrings = new boolean[this.m_substrings.length];
		
		// Build string from genome.
		for (int i = 0; i < genome.length; i++)
		{
			int substringIdx = ((GA_Int_Atom) genome[i]).getGene() % this.m_substrings.length;
			containedSubstrings[substringIdx] = true;
			int[] substring = this.m_substrings[substringIdx];
			int maxPrefixSize = 0; 
			for (int j = substring.length; maxPrefixSize == 0 && j > 0; j--)
			{
				boolean prefixFits = true;
				for (int k = 0; prefixFits && k < j; k++)
				{
					if (retVal.size() - j + k < 0
							|| retVal.get(retVal.size() - j + k) != substring[k])
						prefixFits = false;
				}

				if (prefixFits)
				{
					maxPrefixSize = j;
				}
			}

			for (int k = maxPrefixSize; k < substring.length; k++)
			{
				retVal.add(substring[k]);
			}
		}
		
		// Add missing substrings.
		for (int i = 0; i < this.m_substrings.length; i++)
		{
			if (!containedSubstrings[i])
			{
				if (isSubstring(this.m_substrings[i],retVal))
					containedSubstrings[i] = true;
				else
					for (int j = 0; j < this.m_substrings[i].length; j++)
					{
						retVal.add(this.m_substrings[i][j]);
					}
			}
		}

		return retVal;
	}

	private boolean isSubstring(int[] substr, ArrayList<Integer> str)
	{
		boolean retVal = false;
		if(str.size() < substr.length)
		{
			retVal = false;
		}
		else
			for (int i = 0; !retVal && i < str.size() - substr.length + 1; i++)
			{
				int j;
				for (j = 0; j < substr.length && str.get(i+j) == substr[j]; j++);
				if (j == substr.length)
					retVal = true;
			}
			
		return retVal;
	}

	@Override
	public double calculateBenchmark(SCS_Individual ind)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double fetchTaskData(String fileName) throws Exception
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

		return 0;
	}

}
