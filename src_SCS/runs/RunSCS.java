package runs;

import popPack.Base_Runner;
import popPack.indPack.GA_Individual;

public class RunSCS
{

	public static void main(String[] args)
	{
		try
		{
			mainWork(args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
				
	}

	public static void mainWork(String[] args) throws Exception
	{
		if(args.length > 0)
			Runner_SCS.mainDir = new String(args[0]);
		else
			Runner_SCS.mainDir = new String("Z:\\SCS_Exp\\000\\");


		if(args.length > 1)
			Runner_SCS.ParameterFilePath = new String(Runner_SCS.mainDir + args[1]);
		else
			Runner_SCS.ParameterFilePath = new String(Runner_SCS.mainDir + "parameters.txt");
		
		Runner_SCS.collectParameters(Runner_SCS.ParameterFilePath);
		
		GA_Individual.init_genome_size = 10;
		GA_Individual.genome_size_limit = 500;

		for (int i = 0; i < Runner_SCS.NUMBER_OF_EXPERIMENTS; i++)
		{
			Base_Runner[] r = Runner_SCS.runningStages;

			for (int j = 0; j < r.length; j++)
			{
				((Runner_SCS)r[j]).runEvoCycle(i,j);
			}
			
			// For next experiment clear memory.
			Runner_SCS.savePop = null;
		}

		// TODO Auto-generated method stub
		
	}
}
