package generators;

import scs_algs.GreedyAlgorithmSCS;

public class SCS_IterInstanceFinder
{
	static String dirName  = "Z:\\";
	static String fileName  = "generated_SCS_input.txt";

	public SCS_IterInstanceFinder()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)
	{
		SCS_IterInstanceFinder f = new SCS_IterInstanceFinder();
		
		args = new String[2];
		args[0] = dirName;
		args[1] = fileName;
		f.mainWork(args);
		
	}

	public void mainWork(String[] args)
	{
		// TODO Auto-generated method stub
		SCS_RandGenerator g = new SCS_CopiesGenerator();
		GreedyAlgorithmSCS gr = new GreedyAlgorithmSCS();
		
//		System.out.println(args.length);
		int[] bestSolution = null;
		for (int i = 0; i < 10000; i++)
		{
			int[][] instance = g.mainWork(args);
			int[] solution = gr.mainWork(args);
			
			if (bestSolution == null || solution.length > bestSolution.length)
			{
				bestSolution = solution;
				g.writeProblem(gr.m_string, gr.m_substrings, args[0]+args[1]+"_best");
			}
			
			System.out.println(i + " " + solution.length + " (" + bestSolution.length + ")");
		}

		
	}

}
