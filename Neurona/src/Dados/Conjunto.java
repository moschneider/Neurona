package Dados;

public class Conjunto {

	int entradas, saidas;
	
	int[] entradaBox, saidaBox;
	
	String aGravar = "";
	
	public Conjunto proximo;

	public Conjunto anterior;
	
	public Conjunto(int entradas, int saidas)
	{
		this.entradas=entradas;
		this.saidas=saidas;
		
		entradaBox=new int[entradas];
		saidaBox=new int[saidas];
		
		init();
	}
	
	public void init()
	{
		for(int i=0;i<entradas;i++)
			entradaBox[i]=0;
		
		for(int i=0;i<saidas;i++)
			saidaBox[i]=0;
	}
	
}
