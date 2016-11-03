package generators;

import java.util.ArrayList;

/**
 * 
 * @author amitbenb
 *
 */

public class SCS_RandGenerator extends SCS_BaseGenerator
{
	public static int minSubstrLen = 20, maxSubstrLen = 30, numOfCopies = 40;

	public static void main(String[] args)
	{
		SCS_RandGenerator g = new SCS_RandGenerator();
		g.mainWork(args);
	}
	
	/**
	 * 
	 * @param length
	 * @param arity
	 * @return String to use as template. Method exists to be overridden.
	 */
	public int[] generateTemplateString(int length, int arity)
	{
		return SCS_BaseGenerator.generateRandomString(length, arity);
	}

	public int[][] generateSubstrings(int[] superstring, int minSubstrLen,
			int maxSubstrLen, int numOfCopies)
	{
		int[][] retVal = null;
		ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < numOfCopies; i++)
		{
			arr.addAll(splitString(superstring, minSubstrLen, maxSubstrLen));
		}

		retVal = new int[arr.size()][];
		for (int i = 0; i < arr.size(); i++)
		{
			ArrayList<Integer> tmp = arr.get(i);
			retVal[i] = new int[tmp.size()];
			for (int j = 0; j < retVal[i].length; j++)
			{
				retVal[i][j] = tmp.get(j);
			}
		}

		// Randomize order
		for (int i = 0; i < retVal.length - 1; i++)
		{
			int j = (int)(Math.random()*(retVal.length - i) + i);
			int[] tmp = retVal[i];
			retVal[i] = retVal[j];
			retVal[j] = tmp; 
		}
		
		return retVal;
	}

	public static ArrayList<ArrayList<Integer>> splitString(int[] superstring,
			int minSubstrLen, int maxSubstrLen)
	{
		ArrayList<ArrayList<Integer>> retVal = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < superstring.length;)
		{
			int tmpLength =
					minSubstrLen + (int) (Math.random() * (maxSubstrLen - minSubstrLen + 1));
//			if (i+tmpLength > superstring.length)
//				i = superstring.length - tmpLength;
			if (i+tmpLength > superstring.length)
			{
				i = 0;
				retVal.clear();
				continue;
			}
			ArrayList<Integer> tmpArr = new ArrayList<Integer>();
			for (int k = 0; k < tmpLength; k++)
			{
				tmpArr.add(new Integer(superstring[i+k]));
			}

			retVal.add(tmpArr);
			i=i+tmpLength;
		}

		// Debug output loop
//		for (Iterator iterator = retVal.iterator(); iterator.hasNext();)
//		{
//			ArrayList<Integer> t = (ArrayList<Integer>) iterator.next();
//			
//			System.out.println(t.toString());
//		}

		return retVal;
	}

	@Override
	public int[][] generateSubstrings(int[] randString)
	{
		return generateSubstrings(randString, minSubstrLen, maxSubstrLen, numOfCopies);
	}

}


