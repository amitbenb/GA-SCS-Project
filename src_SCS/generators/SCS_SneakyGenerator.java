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
//		sneakyfy(superString, minSubstrLen, maxSubstrLen, numOfCopies);
		return generateSubstrings(superString, minSubstrLen, maxSubstrLen, numOfCopies);
	}

	private int[][] sneakyfy(int[] superstring, int minSubstrLen, int maxSubstrLen,
			int numOfCopies)
	{
		int[][] retVal = null;
		ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
		//TODO Do something!

		int patternlessBuffer = 4;
		for (int i = 0; i < superstring.length; i++, patternlessBuffer--)
		{
			if (patternlessBuffer > 0 || Math.random() < 0.9)
				; // Nothing.
			else
			{
				int startingIdx = i;
				int patternLength = (int)((Math.random() * 1) + 2); // 2
				int[] pattern = new int[patternLength];
				int noiseLimit = (maxSubstrLen - minSubstrLen) / 1;
				int segmentLength = (int)((Math.random() * noiseLimit) + minSubstrLen); 
				segmentLength = (segmentLength / patternLength) * patternLength;

				boolean allTheSame = true; 
				for (int j = 0; j < pattern.length; j++)
				{
					pattern[j] = (int)(Math.random()*arity);
					
					if (j > 0 && pattern[j] != pattern[j-1])
						allTheSame = false;
					
					if (j == pattern.length - 1 && allTheSame)
						j = -1;
				}
				
				superstring[i] = (int)(Math.random());
				if (i+1 < superstring.length)
					superstring[i+1] = superstring[i];
				i += 2;

				for (int j = 0; j < segmentLength && i < superstring.length; i++,j++)
				{
					superstring[i] = pattern[j % patternLength];
				}

				if (i < superstring.length)
					superstring[i] = (int)(Math.random()*arity);
				if (i+1 < superstring.length)
					superstring[i+1] = superstring[i];
				i += 2;
				
				arr.add(new ArrayList<Integer>());
				arr.add(new ArrayList<Integer>());
				arr.add(new ArrayList<Integer>());
				for (int j = 0; j < segmentLength + 4; j++)
				{
					int currIdx = i - segmentLength - 4 + j;
					if (currIdx < superstring.length)
					{
						if (j<segmentLength)
							arr.get(arr.size() - 3).add(superstring[currIdx]);
						if (j >= 2 && j < segmentLength + 2)
							arr.get(arr.size() - 2).add(superstring[currIdx]);
						if (j>=4)
							arr.get(arr.size() - 1).add(superstring[currIdx]);
					}
				}

				patternlessBuffer = 4;
			}
			//			System.out.print(retVal[i] + " "); 
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
//		for (int i = 0; i < retVal.length - 1; i++)
//		{
//			int j = (int)(Math.random()*(retVal.length - i) + i);
//			int[] tmp = retVal[i];
//			retVal[i] = retVal[j];
//			retVal[j] = tmp; 
//		}
		
		return retVal;
	}
	
	@Override
	public int[][] generateSubstrings(int[] superstring, int minSubstrLen,
			int maxSubstrLen, int numOfCopies)
	{
		//TODO Make this sneaky.
		int[][] retVal = null;
		int[][] arr = sneakyfy(superstring, minSubstrLen, maxSubstrLen, numOfCopies);
		int[][] substrsTemp = super.generateSubstrings(superstring, minSubstrLen, maxSubstrLen, numOfCopies);
		
		retVal = new int[arr.length + substrsTemp.length][];
		
		for (int i = 0; i < arr.length; i++)
			retVal[i] = arr[i];
		for (int i = 0; i < substrsTemp.length; i++)
			retVal[i + arr.length] = substrsTemp[i];

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
	
	@Override
	public ArrayList<ArrayList<Integer>> splitString(int[] superstring, int minSubstrLen,
			int maxSubstrLen)
	{
		// TODO Auto-generated method stub
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

		return retVal;
	}



}
