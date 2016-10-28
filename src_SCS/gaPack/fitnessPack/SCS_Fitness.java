package gaPack.fitnessPack;

import gaPack.indPack.*;
import popPack.Base_Fitness;

public interface SCS_Fitness extends Base_Fitness
{
	abstract public double calculateFitness(SCS_Individual ind);
	abstract public double calculateBenchmark(SCS_Individual ind);
	abstract public double fetchTaskData(String fileName) throws Exception;
}
