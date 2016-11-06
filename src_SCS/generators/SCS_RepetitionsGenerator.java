package generators;

public class SCS_RepetitionsGenerator extends SCS_RandGenerator
{
	public static void main(String[] args)
	{
		SCS_RandGenerator g = new SCS_RepetitionsGenerator();
		g.mainWork(args);
	}
	
	@Override
	public int[] generateTemplateString(int length, int arity)
	{
		int[] retVal = new int[length];

		int patternlessBuffer = 5;
		for (int i = 0; i < retVal.length; i++, patternlessBuffer--)
		{
			if (patternlessBuffer > 0 || Math.random() < 0.95)
				retVal[i] = (int)(Math.random()*arity);
			else
			{
				int patternLength = (int)((Math.random() * 2) + 2);
				int[] pattern = new int[patternLength];
				int noiseLimit = (maxSubstrLen - minSubstrLen) / 3;
				int segmentLength = (int)((Math.random() * noiseLimit) + minSubstrLen);

				for (int j = 0; j < pattern.length; j++)
					pattern[j] = (int)(Math.random()*arity);

				for (int j = 0; j < segmentLength && i < retVal.length; i++,j++)
				{
					retVal[i] = pattern[j % patternLength];
				}

				patternlessBuffer = 5;
			}
			//			System.out.print(retVal[i] + " "); 
		}

		return retVal;
	}
	
}
