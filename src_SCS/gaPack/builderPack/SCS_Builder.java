package gaPack.builderPack;

import popPack.BasicBuilder;
import popPack.indPack.GA_IntVector_Individual.GA_Int_Atom;

public interface SCS_Builder extends BasicBuilder
{
	abstract public int[] build(GA_Int_Atom[] genome);
}
