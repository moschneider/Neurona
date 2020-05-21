package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Dados.ListaConjuntos;
import IO.Window;
import RNA.ANN;

public class AdminConjuntos {
	
	ListaConjuntos lista;
	Window tela;
	
	int posicao = 0;
	
	JLabel posicaoLabel, entradasLabel, saidasLabel, descricaoLabel;
	
	JPanel buttonPanel;
	
	JButton first, last, up, down, newSet, exit;
	
	JPanel[] entradaPanel, saidaPanel;
	
	JButton[] entrada, saida;
	
	ActionListener[] entradaAction, saidaAction;
	
	int entradaPanels, saidaPanels;
	
	ANN mlp;

	public AdminConjuntos(ListaConjuntos lista, ANN mlp)
	{
		tela = new Window("Programação de Conjuntos",false);
		
		this.mlp=mlp;
		
		entradaPanels = (mlp.A / 10) + 1;
		saidaPanels = (mlp.C / 10) + 1;
		
		entradasLabel = new JLabel("Entradas do Conjunto de Teste");
		saidasLabel = new JLabel("Saidas do Conjunto de Teste");
		descricaoLabel = new JLabel("-");
		
		entrada  = new JButton[mlp.A];
		entradaAction = new ActionListener[mlp.A];
		saida = new JButton[mlp.C];
		saidaAction = new ActionListener[mlp.C];
		
		entradaPanel=new JPanel[entradaPanels];
		saidaPanel=new JPanel[saidaPanels];
		
		for(int i=0;i<entradaPanels;i++)
			entradaPanel[i]=new JPanel();
		for(int i=0;i<saidaPanels;i++)
			saidaPanel[i]=new JPanel();
		
		for(int i=0;i<mlp.A;i++)
		{
			entrada[i]=new JButton("" + i);
			entrada[i].setSelected(false);
			entrada[i].addActionListener(new entradaAction(i));
			entradaPanel[(i / 10)].add(entrada[i]);
		}
		
		for(int i=0;i<mlp.C;i++)
		{
			saida[i]=new JButton("" + i);
			saida[i].setSelected(false);
			saida[i].addActionListener(new saidaAction(i));
			saidaPanel[(i / 10)].add(saida[i]);
		}
			
		posicaoLabel = new JLabel("Posição: " + posicao);
		
		tela.addLeftLn(posicaoLabel);
		
		tela.addLineLn(1);
		
		tela.addCenterLn(entradasLabel);
		
		for(int i=0;i<entradaPanels;i++)
			tela.addCenterLn(entradaPanel[i]);
		
		tela.addLineLn(1);
		
		tela.addCenterLn(saidasLabel);
		
		for(int i=0;i<saidaPanels;i++)
			tela.addCenterLn(saidaPanel[i]);
		
		tela.addLineLn(1);
		
		first = new JButton("<<");
		last = new JButton(">>");
		up = new JButton("<");
		down = new JButton(">");
		newSet = new JButton("Novo");
		exit = new JButton("Sair");
		
		exit.addActionListener(new ExitListener());
		
		buttonPanel = new JPanel();
		
		buttonPanel.add(newSet);
		buttonPanel.add(first);
		buttonPanel.add(up);
		buttonPanel.add(down);
		buttonPanel.add(last);
		buttonPanel.add(exit);
		
		tela.addCenterLn(descricaoLabel);
		
		tela.addLineLn(1);
		
		tela.addCenterLn(buttonPanel);
		
		tela.standardPack();
		
		tela.centerPos();
		
		tela.show();
	}
	
	class entradaAction implements ActionListener {

		int nr;
		boolean firstClick;
		
		public entradaAction(int nr)
		{
			this.nr=nr;
			firstClick=true;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			if(firstClick)
			{
				descricaoLabel.setText("Entrada " + nr + ": " + mlp.descEnt[nr]);
			}
			
			firstClick=!firstClick;
		}
		
	}
	
	class saidaAction implements ActionListener {

		int nr;
		boolean firstClick;
		
		public saidaAction(int nr)
		{
			this.nr=nr;
			firstClick=true;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			if(firstClick)
			{
				descricaoLabel.setText("Saida " + nr + ": " + mlp.descSai[nr]);
			}
			
			firstClick=!firstClick;
		}
		
	}
	
	class ExitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			tela.hide();
		}
		
	}
}
