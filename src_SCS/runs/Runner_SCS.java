package runs;

import gaPack.*;
import gaPack.builderPack.*;
import gaPack.fitnessPack.SCS_Fitness;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import popPack.Base_Runner;

public class Runner_SCS extends Base_Runner
{
	
	public void runEvoCycle(int expNumber, int stageNumber) throws IOException 
	{
		
		String fileIdentifier = Base_Runner.getFileIdentifier(expNumber,stageNumber);

		SCS_Builder b = new Default_SCS_Builder();
		SCS_Population p = new SCS_Population(this, b);
		
		String fitOutFilePath = new String(this.popDirFullPath + "FitOut" + fileIdentifier + ".txt");
		String runInfoFilePath = new String(this.popDirFullPath + "SmallOut" + fileIdentifier + ".txt");
		String ExperimentDataFilePath = new String(this.popDirFullPath + "ExpData_" + stageNumber + ".txt");
		

		if (Base_Runner.DEBUG_OUTPUT)
			System.out.println("Evo cycle identifier: " + fileIdentifier);
		if (Base_Runner.DEBUG_OUTPUT)
			System.out.print("Gen " + 0);
		p.evaluation();
		//			System.out.print(p.getBestIndividual().getFitness() + "\t");
		//			System.out.println(p.getAvgFitness() + " ");
		
		if (Base_Runner.DEBUG_OUTPUT)
		{
			System.out.print(" (Best Fitness " + p.getBestIndividual().getFitness());
			System.out.println(" Benchmark score " + p.getBenchmarkScore() + ")");
		}

		fitOut = new FileWriter(fitOutFilePath);
		fitOut.write(0 + "\t" + p.getBestIndividual().getFitness() + "\t" + p.getApproxAvgFitness()
				+ "\t" + p.getBenchmarkScore() + "\n");
		fitOut.close();


		boolean stageStillActive = true;
		for (int gen = 1; gen <= this.NUMBER_OF_GENERATIONS && stageStillActive; gen++)
		{
			if (Base_Runner.DEBUG_OUTPUT)
			{
				System.out.print("Gen " + gen);
			}
			p.selection();
			p.procreation();
			p.evaluation();
			
//			System.out.println("\n" + p.getBestIndividual().getGenome().length);

			if (Base_Runner.DEBUG_OUTPUT)
			{
				System.out.print(" (Best Fitness " + p.getBestIndividual().getFitness());
				System.out.println(" Benchmark score " + p.getBenchmarkScore() + ")");
			}
			//				System.out.print(p.getBestIndividual().getFitness() + "\t");
			//				System.out.println(p.getAvgFitness() + " ");
			fitOut = new FileWriter(fitOutFilePath, true);
			fitOut.write(gen + "\t" + p.getBestIndividual().getFitness() + "\t" + p.getApproxAvgFitness()
					+ "\t" + p.getBenchmarkScore() + "\n");
			fitOut.close();
			
//			System.out.println(this.FITNESS_STOP_FLAG);
			
			if(this.FITNESS_STOP_FLAG && p.getBestIndividual().getFitness() >= this.FITNESS_STOP_THRESHOLD)
			{
				if (Base_Runner.DEBUG_OUTPUT)
					System.out.println("Fitness condition met! Stage terminated!");
				stageStillActive = false;
			}
			if(this.BENCHMARK_STOP_FLAG && p.getBenchmarkScore() >= this.BENCHMARK_STOP_THRESHOLD)
			{
				if (Base_Runner.DEBUG_OUTPUT)
					System.out.println("Benchmark condition met! Stage terminated!");
				stageStillActive = false;
			}
		}
		
		// For run info. TODO.
		smallOut = new FileWriter(runInfoFilePath);
		if (expNumber == 0)
		{
			// First Experiment. New file.
			dataOut = new FileWriter(ExperimentDataFilePath, false);
			dataOut.write(p.generateDataTableHeader()); 
		}
		else
			dataOut = new FileWriter(ExperimentDataFilePath, true);

		writeRunInfo(smallOut, dataOut, p);
		dataOut.close();
		smallOut.close();

		// Saving population for next runner.
		Base_Runner.savePop = p;
		Base_Runner.RandomizeArray(p.getPop());
		
		// If enabled population restarts
//		Base_Runner.savePop = null;
	}
	
	
	private void writeRunInfo(FileWriter outF, FileWriter dataOutF, SCS_Population pop) throws IOException
	{
		//TODO Stub.
	}

	public static void collectParameters(String fileName) throws Exception
	{
		Scanner inF = new Scanner(new File(fileName));
		
		Base_Runner.collectBasicExperimentData(inF);
		
		Base_Runner.runningStages = new Runner_SCS[Base_Runner.NUMBER_OF_STAGES];
		for (int i = 0; i < Base_Runner.runningStages.length;i++)
			Base_Runner.runningStages[i] = new Runner_SCS();
		
		Base_Runner.collectBasicExperimentStageData(inF);
	}

	@Override
	public void collectFitParameters(String fileName, int idx) throws Exception
	{
		Scanner inF = new Scanner(new File(mainDir + fileName));

		Base_Runner r = Base_Runner.runningStages[idx];
		inF.nextLine(); // Getting rid of non-data line. 
		r.FITNESS_CLASS_NAME = new String(inF.nextLine());
		Class theClass = Class.forName(r.FITNESS_CLASS_NAME);
//		theClass.newInstance();
		r.fitnessObj = (SCS_Fitness)theClass.newInstance();
		
		inF.nextLine(); // Getting rid of non-data line. 
		String instanceFileName = new String(inF.nextLine());
		
		((SCS_Fitness)r.fitnessObj).fetchTaskData(mainDir + instanceFileName);
		
		inF.close();
	}

}
