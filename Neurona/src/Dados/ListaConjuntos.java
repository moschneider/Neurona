package Dados;

import RNA.*;

public class ListaConjuntos {
	
	ANN mlp;
	public Conjunto primeiro, ultimo, atual;

	public ListaConjuntos(ANN mlp)
	{
		primeiro=new Conjunto(mlp.A,mlp.C);
		ultimo=new Conjunto(mlp.A,mlp.C);
		atual=new Conjunto(mlp.A,mlp.C);
		
		primeiro.proximo=ultimo;
		primeiro.anterior=null;
		ultimo.anterior=primeiro;
		ultimo.proximo=null;
		
		inicio();
	}
	
	public void inicio()
	{
		atual=primeiro;
	}
	
	public void termino()
	{
		atual=ultimo.anterior;
	}
	
	public boolean next()
	{
		if(atual==ultimo.anterior || atual==ultimo)
			return false;
		
		atual=atual.proximo;
		
		return true;
	}
}
