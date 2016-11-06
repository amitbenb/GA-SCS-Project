package generators;

public class SCS_CopiesGenerator extends SCS_RandGenerator
{
	int numberOfCopies = 40;
	
	public static void main(String[] args)
	{
		SCS_RandGenerator g = new SCS_CopiesGenerator();
		g.mainWork(args);
	}
	
	@Override
	public int[] generateTemplateString(int length, int arity)
	{
				
		int[] retVal = super.generateTemplateString(length, arity);
		
		for (int i = 0; i < numberOfCopies; i++)
		{
			makeCopy(retVal); 
		}

		return retVal;
	}

	private void makeCopy(int[] superstring)
	{
		int copyLength = (int)(Math.random() * maxSubstrLen - minSubstrLen) + minSubstrLen + 1;
		int destinationGap = (int)(Math.random() * 3) + 1;
		int sourceIdx = (int)(Math.random() * superstring.length);
		
		for (int i = 0; i < copyLength; i++)
		{
			superstring[(sourceIdx + copyLength + destinationGap + i) % superstring.length] =
					superstring[(sourceIdx + i) % superstring.length];
		}
		
	}

}
