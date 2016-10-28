package gaPack;

import gaPack.builderPack.Default_SCS_Builder;
import gaPack.builderPack.SCS_Builder;
import gaPack.fitnessPack.SCS_Fitness;
import gaPack.indPack.SCS_Individual;
import popPack.Base_Runner;
import popPack.GA_Population;
import popPack.indPack.GA_Individual;
import runs.Runner_SCS;

public class SCS_Population extends GA_Population
{
	
	SCS_Builder m_builder = new Default_SCS_Builder();

	public SCS_Population()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SCS_Population(Runner_SCS r, SCS_Builder b)
	{
		m_pop = new SCS_Individual[r.SIZE_OF_POPULATION];
//		m_archive = new SCS_Individual[m_archiveSize];
//		m_archiveBenchmarkScores = new double[archiveSize];
		m_xoProb = r.CROSSOVER_PROB;
		m_mutProb = r.MUTATION_PROB;
		m_eliteRatio = r.ELITE_RATIO;
		m_fitness = (SCS_Fitness)r.fitnessObj;
		tournamentSize = r.TOURNAMENT_SIZE;
		tournamentWinnerNum = r.NUMBER_OF_TOUR_WINNERS;
		
		m_crowdingFlag = r.USE_CROWDING_FLAG;
		m_neighborDistanceRatio = r.MAXIMAL_NEIGHBOR_DISTANCE_RATIO;
		m_maxNumOfNeighbors = r.MAXIMUM_NEIGHBOR_NUMBER;
		
		setBuilder(b);
		
		if(Runner_SCS.savePop == null)
		{
			for (int i = 0; i < m_pop.length; i++)
			{
				m_pop[i] = new SCS_Individual(SCS_Individual.init_genome_size, getBuilder(), getFitnessObj());
			}
		}
		else //(Runner.savePop == p)
		{
			SCS_Population p = (SCS_Population)Base_Runner.savePop;
			for (int i = 0; i < m_pop.length; i++)
			{
				m_pop[i] = p.getIndividual(i % p.getPopSize()).selfReplicate();

				SCS_Individual ind = (SCS_Individual)m_pop[i];
				ind.setFitnessObj(getFitnessObj());
				ind.setBuilder(getBuilder());
			}
		}

	}

	public SCS_Builder getBuilder()
	{
		return m_builder;
	}

	public SCS_Fitness getFitnessObj()
	{
		return (SCS_Fitness)m_fitness;
	}

	public String generateDataTableHeader()
	{
		String retVal = new String("");
		GA_Individual best = getBestEverIndividual();
		
		//retVal = getFitnessObj().generateFitnessDataTableHeader((SCS_Individual)best);
		//TODO Broken.
		return retVal;
	}


}
