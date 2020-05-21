package Telas;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Dados.ListaConjuntos;
import IO.Panel;
import IO.WinIO;
import IO.Window;
import RNA.ANN;

public class AdminConjuntosDescricoes {

	Window win;
	WinIO winIO;
	ANN mlp;
	ListaConjuntos lista;
	JLabel[] nroS, nroE;
	JTextField[] descS, descE, valS, valE;
	JLabel pos;
	JTextField posT;
	JButton ant, prox, prim, ult, salvar, sair;
	Panel entradasP, saidasP;
	JScrollPane entradasS, saidasS;
	
	public AdminConjuntosDescricoes(ANN mlp, ListaConjuntos lista)
	{
		this.lista=lista;
		this.mlp=mlp;
		win = new Window("Administração Descrições e Conjuntos",false);
		winIO = new WinIO();
		
		nroS = new JLabel[mlp.C];
		
		for(int i=0;i<mlp.C;i++)
		{
			nroS[i] = new JLabel("" + i);
		}
		
		nroE = new JLabel[mlp.A];
		
		for(int i=0;i<mlp.A;i++)
		{
			nroE[i] = new JLabel("" + i);
		}
		
		
		/* JTextField[] 
		descS
		descE
		valS, valE;
		JLabel pos;
		JTextField posT;
		JButton ant, prox, prim, ult, salvar, sair; */
	}
	
}
