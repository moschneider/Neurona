package Telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Dados.ListaConjuntos;
import IO.Window;
import RNA.ANN;

public class TesteLivre {
		
	final Color UM = Color.YELLOW;
	final Color ZERO = Color.WHITE;
	final Color MEIO = Color.LIGHT_GRAY;
	
	ListaConjuntos lista;
	Window tela;
	
	JLabel entradasLabel, saidasLabel, descricaoLabel;
	
	JPanel buttonPanel;
	
	JButton processar, ok;
	
	JPanel[] entradaPanel, saidaPanel;
	
	JTextField[] entrada, saida;
	JLabel[] entradaL, saidaL;
	
	ActionListener[] entradaAction, saidaAction;
	
	int entradaPanels, saidaPanels;
	
	ANN mlp;

	public TesteLivre(ANN mlp)
	{
		tela = new Window("Teste Livre",false);
		
		this.mlp=mlp;
		
		entradaPanels = (mlp.A / 5) + 1;
		saidaPanels = (mlp.C / 5) + 1;
		
		entradasLabel = new JLabel("Entradas");
		saidasLabel = new JLabel("Saidas");
		descricaoLabel = new JLabel("-");
		
		entrada  = new JTextField[mlp.A];
		entradaL = new JLabel[mlp.A];
		//entradaAction = new ActionListener[mlp.A];
		saida = new JTextField[mlp.C];
		saidaL = new JLabel[mlp.C];
		//saidaAction = new ActionListener[mlp.C];
		
		entradaPanel=new JPanel[entradaPanels];
		saidaPanel=new JPanel[saidaPanels];
		
		for(int i=0;i<entradaPanels;i++)
			entradaPanel[i]=new JPanel();
		for(int i=0;i<saidaPanels;i++)
			saidaPanel[i]=new JPanel();
		
		for(int i=0;i<mlp.A;i++)
		{
			entrada[i]=new JTextField("",5);
			if(mlp.descEnt[i].equals(""))
				entradaL[i]=new JLabel(i + " ---"); else
			entradaL[i]=new JLabel(i + " " + mlp.descEnt[i]);
			//entrada[i].setSelected(false);
			//entrada[i].addActionListener(new entradaAction(i,entrada[i]));
			entradaPanel[(i / 5)].add(entradaL[i]);
			entradaPanel[(i / 5)].add(entrada[i]);
		}
		
		for(int i=0;i<mlp.C;i++)
		{
			saida[i]=new JTextField("",5);
			saida[i].setEditable(false);
			if(mlp.descSai[i].equals(""))
				saidaL[i]=new JLabel(i + " ---"); else
			saidaL[i]=new JLabel(i + " " + mlp.descSai[i]);
			//saida[i].addActionListener(new saidaAction(i,saida[i]));
			saidaPanel[(i / 5)].add(saidaL[i]);
			saidaPanel[(i / 5)].add(saida[i]);
		}
			
		tela.addCenterLn(entradasLabel);
		
		for(int i=0;i<entradaPanels;i++)
			tela.addCenterLn(entradaPanel[i]);
		
		tela.addLineLn(1);
		
		tela.addCenterLn(saidasLabel);
		
		for(int i=0;i<saidaPanels;i++)
			tela.addCenterLn(saidaPanel[i]);
		
		tela.addLineLn(1);
		
		processar = new JButton("Processar");
		ok = new JButton("Voltar");
				
		processar.addActionListener(new ProcessarListener());
		ok.addActionListener(new OkListener());
		
		buttonPanel = new JPanel();
		
		buttonPanel.add(processar);
		buttonPanel.add(ok);
				
		//tela.addCenterLn(descricaoLabel);
		
		//tela.addLineLn(1);
		
		tela.addCenterLn(buttonPanel);
		
		tela.standardPack();
		
		tela.centerPos();
		
		tela.show();
	}
	
	class entradaAction implements ActionListener {

		int nr;
		boolean firstClick;
		JButton button;
		
		public entradaAction(int nr, JButton button)
		{
			this.nr=nr;
			firstClick=true;
			button.setBackground(ZERO);
			this.button=button;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			if(firstClick)
			{
				descricaoLabel.setText("Entrada " + nr + ": " + mlp.descEnt[nr]);
			} else
			{
				if(button.getBackground()==UM)
					button.setBackground(ZERO); else
						button.setBackground(UM);
			}
			
			firstClick=!firstClick;
		}
		
	}
	
	class saidaAction implements ActionListener {

		int nr;
		boolean selecionado = false;
				
		public saidaAction(int nr, JButton button)
		{
			this.nr=nr;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			descricaoLabel.setText("Saida " + nr + ": " + mlp.descSai[nr]);
		}
		
	}
	
	class OkListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			tela.hide();
		}
		
	}
	
	class ProcessarListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			for(int i=0;i<mlp.A;i++)
			{
				/* if(entrada[i].getBackground()==UM)
					mlp.x[i]=1; else
						mlp.x[i]=0; */
				
				mlp.x[i]=Double.parseDouble(entrada[i].getText());
			}
			
			mlp.funcionarRede();
			
			for(int i=0;i<mlp.C;i++)
			{
				if(mlp.o[i]<0.4)
					saida[i].setBackground(ZERO); else
						if(mlp.o[i]<0.6)
							saida[i].setBackground(MEIO); else
								saida[i].setBackground(UM);
				
				String resultado = String.format("%.2f", mlp.o[i]);
				
				saida[i].setText(resultado);
			}
			
			tela.standardPackSmall();
			
		}
		
	}
}
