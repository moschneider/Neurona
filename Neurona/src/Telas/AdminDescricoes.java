package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import IO.WinIO;
import IO.Window;
import RNA.ANN;

public class AdminDescricoes {
	
	ANN mlp;
	Window tela;
	JTextField[] descricao;
	JPanel[] descricaoPanel;
	int numeroPanels = 0, limite = 0;
	JPanel buttonPanel;
	JButton ok, cancelar;
	WinIO winIO;
	boolean ent;
	
	public AdminDescricoes(ANN mlp, boolean ent)
	{
		this.mlp=mlp;
		this.ent=ent;
		
		winIO = new WinIO();
		
		if(ent)
		{
			tela=new Window("Descrição Entradas",false);
			descricao=new JTextField[mlp.A];
			for(int i=0;i<mlp.A;i++)
				descricao[i]=new JTextField(mlp.descEnt[i],10);
			numeroPanels=mlp.A/5+1;
			limite=mlp.A;
		}
		else
		{
			tela=new Window("Descrição Saídas",false);
			descricao=new JTextField[mlp.C];
			for(int i=0;i<mlp.C;i++)
				descricao[i]=new JTextField(mlp.descSai[i],10);
			numeroPanels=mlp.C/5+1;
			limite=mlp.C;
		}
		
		descricaoPanel=new JPanel[numeroPanels];
		
		for(int i=0;i<numeroPanels;i++)
			descricaoPanel[i]=new JPanel();
		
		for(int i=0;i<limite;i++)
		{
			descricaoPanel[i/5].add(new JLabel("   " + i +" "));
			descricaoPanel[i/5].add(descricao[i]);
		}
		
		for(int i=0;i<numeroPanels;i++)
		{
			tela.addCenterLn(descricaoPanel[i]);
		}
		
		ok = new JButton("OK");
		cancelar = new JButton("Cancelar");
		
		buttonPanel = new JPanel();
		
		buttonPanel.add(ok);
		buttonPanel.add(cancelar);
		
		ok.addActionListener(new OkListener());
		cancelar.addActionListener(new CancelarListener());
		
		tela.addLineLn(1);
		
		tela.addCenterLn(buttonPanel);
		
		tela.standardPack();
		
		tela.centerPos();
		
		tela.show();
	
	}
	
	class OkListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			for(int i=0;i<limite;i++)
			{
				if(ent)
					mlp.descEnt[i]=descricao[i].getText();
				else
					mlp.descSai[i]=descricao[i].getText();
			}
			
			tela.hide();
		}
		
	}
	
	class CancelarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(winIO.yesNoQuestion("Abandonar alterações?", "Sair do cadastro")==0)
				tela.hide();
		}
		
	}

}
