package gaPack.indPack;

import gaPack.builderPack.SCS_Builder;
import gaPack.fitnessPack.SCS_Fitness;
import popPack.indPack.GA_Individual;
import popPack.indPack.GA_IntVector_Individual;

public class SCS_Individual extends GA_IntVector_Individual
{
	SCS_Builder m_builder;
	SCS_Fitness m_fitness;

	public SCS_Individual(int length, SCS_Builder b, SCS_Fitness fit)
	{
		super(length);
		setBuilder(b);
		setFitnessObj(fit);
		
		// TODO Auto-generated constructor stub
	}

	public SCS_Individual(SCS_Individual ind)
	{
		super(ind);
		setBuilder(ind.getBuilder());
		setFitnessObj(ind.getFitnessObj());
		// TODO Auto-generated constructor stub
	}
	
	public SCS_Fitness getFitnessObj()
	{
		return m_fitness;
	}

	public SCS_Builder getBuilder()
	{
		return m_builder;
	}

	public void setFitnessObj(SCS_Fitness f)
	{
		m_fitness = f;
	}

	public void setBuilder(SCS_Builder b)
	{
		m_builder = b;
	}
	
	@Override
	/**
	 * 
	 * @param ind Individual to compare with.
	 * @return The edit distance between the marker vectors.
	 */
	public int markerDistance(GA_Individual ind)
	{
		int[] word1 = this.m_markers;
		int[] word2 = ind.getMarkerArray();
		
		int len1 = word1.length;
		int len2 = word2.length;
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			int num1 = word1[i];
			for (int j = 0; j < len2; j++) {
				int num2 = word2[j];
	 
				//if last two chars equal
				if (num1 == num2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];

	}

//	public static int minDistance(int[] word1, int[] word2)
//	{
//	}	

	@Override
	public GA_Individual selfReplicate()
	{
		return new SCS_Individual(this);
	}

	@Override
	public void calculateFitness()
	{
//		throw new RuntimeException("No fitness function defined");
		setStaticFitness(getFitnessObj().calculateFitness(this));
		// TODO Auto-generated method stub
	}
	
	@Override
	public double calculateBenchmark()
	{
//		throw new RuntimeException("No benchmark score defined");
		// TODO Auto-generated method stub
		return getFitnessObj().calculateBenchmark(this);
	}


}
