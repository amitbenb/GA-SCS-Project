package generators;

import java.util.ArrayList;

public class SCS_SneakyGenerator extends SCS_RandGenerator
{

	public static void main(String[] args)
	{
		SCS_SneakyGenerator g = new SCS_SneakyGenerator();
		g.mainWork(args);
	}

	@Override
	public int[][] generateSubstrings(int[] superString)
	{
		sneakyfy(superString, minSubstrLen, maxSubstrLen, numOfCopies);
		return generateSubstrings(superString, minSubstrLen, maxSubstrLen, numOfCopies);
	}

	private void sneakyfy(int[] superString, int minSubstrLen, int maxSubstrLen,
			int numOfCopies)
	{
		//TODO Do something!
	}
	
//	@Override
//	public int[][] generateSubstrings(int[] superstring, int minSubstrLen,
//			int maxSubstrLen, int numOfCopies)
//	{
//		//TODO Make this sneaky.
//		int[][] retVal = null;
//		ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
//		
//		for (int i = 0; i < numOfCopies; i++)
//		{
//			arr.addAll(splitString(superstring, minSubstrLen, maxSubstrLen));
//		}
//
//		retVal = new int[arr.size()][];
//		for (int i = 0; i < arr.size(); i++)
//		{
//			ArrayList<Integer> tmp = arr.get(i);
//			retVal[i] = new int[tmp.size()];
//			for (int j = 0; j < retVal[i].length; j++)
//			{
//				retVal[i][j] = tmp.get(j);
//			}
//		}
//
//		// Randomize order
//		for (int i = 0; i < retVal.length - 1; i++)
//		{
//			int j = (int)(Math.random()*(retVal.length - i) + i);
//			int[] tmp = retVal[i];
//			retVal[i] = retVal[j];
//			retVal[j] = tmp; 
//		}
//		
//		return retVal;
//	}



}
