package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import IO.*;
import RNA.*;
import Dados.*;

public class Principal {
		
	Window mainFrame;
	
	JMenuBar menuBar;
	
	JMenu rede, alterarRede;
	
	JMenuItem novaRede, inicializarRede, abrirRede, tipoRede, estruturaRede, taxaRede, entradasRede, saidasRede, dumpRede, salvarRede, salvarComoRede, sairRede, algoritmoRede, valoresReaisRede;
	
	JMenu treinar;
	
	JMenuItem treinarNovo, treinarAbrir, treinarProgramar, treinarTreinar, treinarTestar, treinarLivre, treinarSalvar, treinarSalvarComo;
	
	JMenu ajuda; 
	
	JMenuItem ajudaSobre;
	
	ANN mlp;
	
	WinIO winIO;
	
	JLabel configLabel;
	JLabel tipoLabel;
	JLabel entradasLabel;
	JLabel escondidosLabel;
	JLabel saidasLabel;
	JLabel taxaLabel;
	JLabel algoritmoLabel;
	JLabel arquivoLabel;
	JLabel valoresReaisLabel;
	
	public final int LIMITE_ENTRADAS = 50;
	public final int LIMITE_ESCONDIDOS = 150;
	public final int LIMITE_SAIDAS = 50;
	
	public final int SEM_VALOR = 0;
	
	int novasEntradas = SEM_VALOR;
	int novosEscondidos = SEM_VALOR;
	int novasSaidas = SEM_VALOR;
	
	ListaConjuntos conjuntos;
	
	boolean valoresReaisPermitidos = false;
	
	public Principal()
	{
		winIO = new WinIO();
		
		mlp = new ANN(4,3,4,0.75);
		
		mainFrame = new Window("Neurona 0.3",true);
		mainFrame.setSize(500,400);
		
		// Declaração dos itens
		
		menuBar = new JMenuBar();
		rede = new JMenu("Rede Neural Artificial");
		novaRede = new JMenuItem("Nova Rede"); 
		inicializarRede = new JMenuItem("Inicializar Rede");
		abrirRede = new JMenuItem("Abrir Rede");
		alterarRede = new JMenu("Alterar Rede"); 
		tipoRede = new JMenuItem("Tipo"); 
		estruturaRede = new JMenuItem("Estrutura"); 
		algoritmoRede = new JMenuItem("Algoritmo de Aprezendizado");
		taxaRede = new JMenuItem("Taxa de Aprendizado");
		entradasRede = new JMenuItem("Descrição Entradas"); 
		saidasRede = new JMenuItem("Descrição Saídas");
		valoresReaisRede = new JMenuItem("Entradas Reais");
		dumpRede = new JMenuItem("Dump"); 
		salvarRede = new JMenuItem("Salvar"); 
		salvarComoRede = new JMenuItem("Salvar Como"); 
		sairRede  = new JMenuItem("Sair");
		
		treinar = new JMenu("Treinamento e Testes");
		treinarNovo = new JMenuItem("Nova Lista de Conjuntos");
		treinarAbrir = new JMenuItem("Abrir Arquivo e Validar");
		treinarProgramar = new JMenuItem("Programar Conjuntos");
		treinarTreinar = new JMenuItem("Treinar Conjuntos"); 
		treinarTestar = new JMenuItem("Testar Conjuntos"); 
		treinarLivre = new JMenuItem("Teste Livre"); 
		treinarSalvar = new JMenuItem("Salvar"); 
		treinarSalvarComo = new JMenuItem("Salvar Como");
		
		ajuda = new JMenu("Ajuda"); 
		ajudaSobre = new JMenuItem("Sobre");
		
		// Montagem do menu
		
		menuBar.add(rede);
		
		rede.add(novaRede);
		rede.add(inicializarRede);
		//rede.add(abrirRede);
		rede.addSeparator();
		rede.add(alterarRede);
		//alterarRede.add(tipoRede);
		alterarRede.add(estruturaRede);
		alterarRede.add(algoritmoRede);
		alterarRede.add(taxaRede);
		alterarRede.add(entradasRede);
		alterarRede.add(saidasRede);
		//alterarRede.add(valoresReaisRede);
		rede.add(dumpRede);
		rede.addSeparator();
		//rede.add(salvarRede);
		//rede.add(salvarComoRede);
		//rede.addSeparator();
		rede.add(sairRede);
		
		menuBar.add(treinar);
		
		//treinar.add(treinarNovo);
		treinar.add(treinarAbrir);
		treinar.addSeparator();
		//treinar.add(treinarProgramar);
		treinar.add(treinarTreinar);
		treinar.add(treinarTestar);
		treinar.add(treinarLivre);
		//treinar.addSeparator();
		//treinar.add(treinarSalvar);
		//treinar.add(treinarSalvarComo);
		
		menuBar.add(ajuda); 
		
		ajuda.add(ajudaSobre);
		
		
		mainFrame.frame.setJMenuBar(menuBar);
		
		// adicionar funcionalidades
		
		novaRede.addActionListener(new NovaRedeListener());
		inicializarRede.addActionListener(new InicializarListener());
		abrirRede.addActionListener(new AbrirRedeListener());
		tipoRede.addActionListener(new TipoRedeListener());
		tipoRede.setEnabled(false);
		estruturaRede.addActionListener(new EstruturaRedeListener());
		algoritmoRede.addActionListener(new AlgoritmoRedeListener());
		taxaRede.addActionListener(new TaxaRedeListener()); 
		entradasRede.addActionListener(new EntradasRedeListener()); 
		saidasRede.addActionListener(new SaidasRedeListener());
		valoresReaisRede.addActionListener(new ValoresReaisListener());
		dumpRede.addActionListener(new DumpRedeListener());
		salvarRede.addActionListener(new SalvarRedeListener());
		salvarComoRede.addActionListener(new SalvarComoRedeListener());
		sairRede.addActionListener(new SairRedeListener());
		
		treinarNovo.addActionListener(new TreinarNovoListener()); 
		treinarAbrir.addActionListener(new TreinarAbrirListener()); 
		treinarProgramar.addActionListener(new TreinarProgramarListener()); 
		treinarTreinar.addActionListener(new TreinarTreinarListener()); 
		treinarTestar.addActionListener(new TreinarTestarListener()); 
		treinarLivre.addActionListener(new TreinarLivreListener()); 
		
		treinarSalvar.addActionListener(new TreinarSalvarListener()); 
		treinarSalvarComo.addActionListener(new TreinarSalvarComoListener());
		
		ajudaSobre.addActionListener(new AjudaSobreListener());
		
		configLabel = new JLabel("...");
		tipoLabel = new JLabel("");
		entradasLabel = new JLabel("");
		escondidosLabel = new JLabel("");
		saidasLabel = new JLabel("");
		taxaLabel = new JLabel("");
		algoritmoLabel = new JLabel("");
		arquivoLabel = new JLabel("");
		valoresReaisLabel = new JLabel("");

		mainFrame.addLeftLn(configLabel);
		mainFrame.addLeftLn(new JLabel(""));
		mainFrame.addLeftLn(tipoLabel);
		mainFrame.addLeftLn(new JLabel(""));
		mainFrame.addLeftLn(entradasLabel);
		mainFrame.addLeftLn(escondidosLabel);
		mainFrame.addLeftLn(saidasLabel);
		mainFrame.addLeftLn(new JLabel(""));
		mainFrame.addLeftLn(algoritmoLabel);
		mainFrame.addLeftLn(taxaLabel);
		mainFrame.addLeftLn(new JLabel(""));
		mainFrame.addLeftLn(arquivoLabel);
		//mainFrame.addLeftLn(new JLabel(""));
		//mainFrame.addLeftLn(valoresReaisLabel);
		
		for(int i=0;i<15;i++)
			mainFrame.addLeftLn(new JLabel(""));
			
		mostraConfigAtual();
		
		mainFrame.centerPos();
		
		mainFrame.show();
		
		conjuntos=new ListaConjuntos(mlp);
	}
	
	public void definirTipo(boolean alterar)
	{
		if(alterar)
			winIO.error("Nesta versão essa funcionalidade ainda não está suportada.");
	}
	
	public void definirTaxa()
	{
		double flag;
		
		flag=winIO.readDouble("Taxa de Aprendizado (0.1-0.99)");
		
		if(flag!=winIO.DOUBLE_INVALIDO)
		{
		
			//if(flag>=0.1 && flag<=0.99)
				mlp.N=flag;
			//else
				//winIO.error("Taxa inválida");
		
		}
		
		mostraConfigAtual();
	}
	
	public boolean definirEntradas()
	{
		int flag;
		
		novasEntradas=SEM_VALOR;
		
		flag=winIO.readNumber("Número de Entradas (1-" + LIMITE_ENTRADAS + ")");
		
		if(flag!=winIO.INT_INVALIDO)
		{
			if(flag>=1 && flag<=LIMITE_ENTRADAS)
			{
				novasEntradas=flag;
				
				return true;
			} else winIO.error("Valor fora dos limites");
		}
		
		return false;
	}
	
	public boolean definirEscondidos()
	{
		int flag;
		
		novosEscondidos=SEM_VALOR;
		
		flag=winIO.readNumber("Número de Neurônios Escondidos (1-" + LIMITE_ESCONDIDOS + ")");
		
		if(flag!=winIO.INT_INVALIDO)
		{
			if(flag>=1 && flag<=LIMITE_ESCONDIDOS)
			{
				novosEscondidos=flag;
				
				return true;
			} else winIO.error("Valor fora dos limites");
		}
		
		return false;
	}
	
	public boolean definirSaidas()
	{
		int flag;
		
		novasSaidas=SEM_VALOR;
		
		flag=winIO.readNumber("Número de Saidas (1-" + LIMITE_SAIDAS + ")");
		
		if(flag!=winIO.INT_INVALIDO)
		{
			if(flag>=1 && flag<=LIMITE_SAIDAS)
			{
				novasSaidas=flag;
				
				return true;
			} else winIO.error("Valor fora dos limites");
		}
		
		return false;
	}
	
	public void definirEstrutura()
	{
		
		if(definirEntradas()) 
			if(definirEscondidos())
				definirSaidas();
		
		if(novasEntradas!=SEM_VALOR)
		{
			if(winIO.yesNoQuestion("Para alterar precisamos recriar a rede. Prosseguir?", "Deletar tudo")==0)
			{
				
				mlp.A=novasEntradas;
				if(novosEscondidos!=SEM_VALOR)
					mlp.B=novosEscondidos;
				if(novasSaidas!=SEM_VALOR)
					mlp.C=novasSaidas;
				
				mlp=new ANN(mlp.A,mlp.B,mlp.C,mlp.N);
				
				
			}
		}
		
		mostraConfigAtual();

	}
	
	public void definirDescricoesEntradas()
	{
		new AdminDescricoes(mlp,true);
	}
	
	public void definirDescricoesSaidas()
	{
		new AdminDescricoes(mlp,false);
	}
	
	public void definirValoresReais()
	{
		if(winIO.yesNoQuestion("Isso vai limpar a memória de conjuntos de casos. Prosseguir?", "Favor confirmar")==0)
		{
			if(winIO.yesNoQuestion("Permitir valores reais nas entradas?","Configurar entradas")==0)
				valoresReaisPermitidos=true; else
					valoresReaisPermitidos=false;
			
			conjuntos = new ListaConjuntos(mlp);
			
			mlp.nomeArquivo="";
			
			mostraConfigAtual();
		}
				
	}
	
	public void definirAlgoritmo()
	{
		int escolha = winIO.yesNoQuestion("Usar GeneRec?", "Algoritmo");
		
		mlp.setAlgoritmo(escolha);
		
		mostraConfigAtual();
	}
	
	public void mostraConfigAtual()
	{
		configLabel.setText(" CONFIGURAÇÃO ATUAL");
		tipoLabel.setText(" Tipo de Rede: Perceptron Multicamadas (MLP)");
		entradasLabel.setText(" Entradas: " + mlp.A);
		escondidosLabel.setText(" Escondidos: " + mlp.B);
		saidasLabel.setText(" Saidas: " + mlp.C);
		taxaLabel.setText(" Taxa de Aprendizado: " + mlp.N);
		String alg = "";
		
		if(mlp.algoritmo==mlp.BACKPROPAGATION)
			alg = "Backpropagation"; else
				alg = "GeneRec";
		algoritmoLabel.setText(" Algoritmo de Aprendizado: " + alg);
		String arquivoLabelStr = new String(" Arquivo de Conjuntos: ");
		if(mlp.nomeArquivo.equals(""))
			arquivoLabelStr = arquivoLabelStr + " (ainda não definido)"; else
				arquivoLabelStr = arquivoLabelStr + " " + mlp.nomeArquivo;
		arquivoLabel.setText(arquivoLabelStr);
		String valoresLabelStr = new String("Permitir entradas com valores reais: ");
			if(valoresReaisPermitidos)
				valoresLabelStr = valoresLabelStr + "Sim"; else
					valoresLabelStr = valoresLabelStr + "Não";
		valoresReaisLabel.setText(valoresLabelStr);
	}
	
	public class NovaRedeListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			if(winIO.yesNoQuestion("Criar nova rede (esvaziar memória atual)?", "Nova Rede")==0)
			{
				definirTipo(false);
				definirEstrutura();
				definirTaxa();
				definirDescricoesEntradas();
				definirDescricoesSaidas();
			}
			
		}
		
	}
	
	public class InicializarListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			if(winIO.yesNoQuestion("Inicializar Rede?", "Inicializar")==0)
			{
				mlp.initRede();
				
				winIO.info("Sinapses inicializadas");
			}
			
		}
		
	}
	
	public class AbrirRedeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
				
		}
		
	}
	
	public class TipoRedeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
	
			definirTipo(true);
		}
		
	}
	
	public class EstruturaRedeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
		
			definirEstrutura();
			
		}
		
	}
	
	public class AlgoritmoRedeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			definirAlgoritmo();
		}
		
	}
	
	public class TaxaRedeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			definirTaxa();
			
		}
		
	}
	
	public class EntradasRedeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			definirDescricoesEntradas();
		}
		
	}
	
	public class SaidasRedeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			definirDescricoesSaidas();
			
		}
		
	}
	
	public class ValoresReaisListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			definirValoresReais();
			
		}
		
	}
	
	public class DumpRedeListener implements ActionListener {

		Window tela;
		
		public void actionPerformed(ActionEvent e) {
			
			JTextArea textArea = new JTextArea(30,80);
			JScrollPane scrollPane = new JScrollPane(textArea);
			
			tela = new Window("Dump da Rede",false);
			
			JButton	ok = new JButton("OK");
			
			textArea.append("\n\nCamada de Entradas (x[i])\n\n");
			
			for(int i=0;i<mlp.A;i++)
				textArea.append("[" + i + "] = " + mlp.x[i] + "   ");
			
			textArea.append("\n\n\n");
			
			
			
			textArea.append("\n\nCamada Escondida (h[j])\n\n");
			
			for(int j=0;j<mlp.B;j++)
				textArea.append("[" + j + "] = " + mlp.h[j] + "   ");
			
			textArea.append("\n\n\n");
			
			
			
			textArea.append("\n\nCamada de Saidas (o[k])\n\n");
			
			for(int k=0;k<mlp.C;k++)
				textArea.append("[" + k + "] = " + mlp.o[k] + "   ");
			
			textArea.append("\n\n\n");
			
			
			
			textArea.append("\n\nCamada de Saidas Desejadas (y[k])\n\n");
			
			for(int k=0;k<mlp.C;k++)
				textArea.append("[" + k + "] = " + mlp.y[k] + "   ");
			
			textArea.append("\n\n\n");
			
			
			
			textArea.append("\n\nSinapses entre Entradas e Camada Escondida (w[i][j])\n\n");
			
			for(int i=0;i<mlp.A;i++)
			{
				for(int j=0;j<mlp.B;j++)
					textArea.append("[" + i + "/" + j + "] = " + mlp.w[i][j] + "   ");
				
				textArea.append("\n");
			}
			
			textArea.append("\n");
			
			
			
			textArea.append("\n\nSinapses entre Camada Escondida e Saidas (q[j][k])\n\n");
					
			for(int j=0;j<mlp.B;j++)
			{
				for(int k=0;k<mlp.C;k++)
					textArea.append("[" + j + "/" + k + "] = " + mlp.q[j][k] + "   ");
				
				textArea.append("\n");
			}
			
			tela.addCenterLn(scrollPane);
			
			tela.addCenterLn(ok);
			
			ok.addActionListener(new OkListener());
			
			tela.standardPackSmall();
			
			tela.centerPos();
			
			tela.show();
		}
		
		class OkListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				tela.hide();
			}
			
		}
		
	}
	
	public class SalvarRedeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class SalvarComoRedeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class SairRedeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		
			if(winIO.yesNoQuestion("Sair do sistema", "Encerrar")==0)
			{
				System.exit(0);
			}
		
		}
		
	}
	
	public class TreinarNovoListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(winIO.yesNoQuestion("Isso vai limpar a lista atual em memória. Prosseguir?", "Nova Lista de Conjuntos")==0)
			{
				conjuntos = new ListaConjuntos(mlp);
				
				mlp.nomeArquivo="";
				
				mostraConfigAtual();
			}
			
		}
		
	}
	
	public class TreinarAbrirListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			mlp.nomeArquivo=winIO.selectFile("Abrir", ".");
			mostraConfigAtual();
			mlp.modo=mlp.VALIDAR;
			mlp.mostraTextoEmJanela("Abrir e validar arquivo");
			mlp.treinaArquivo(mlp.nomeArquivo);
		}
		
	}
	
	public class TreinarProgramarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			new AdminConjuntos(conjuntos,mlp);
		}
		
	}
	
	public class TreinarTreinarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(mlp.nomeArquivo.equals(""))
				winIO.error("Favor definir o arquivo antes");
			else
			{
				mlp.modo=mlp.TREINAR;
				int limite=winIO.readNumber("Treinar quantas vezes?");
				for(int i=0;i<limite;i++)
				{
					System.out.print("#");
					mlp.treinaArquivo(mlp.nomeArquivo);
				}
				System.out.println("\n\nPronto\n\n");
			}
			
		}
		
	}
	
	public class TreinarTestarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(mlp.nomeArquivo.equals(""))
				winIO.error("Favor definir o arquivo antes");
			else
			{
				mlp.modo=mlp.TESTAR;
				mlp.mostraTextoEmJanela("Testar conjuntos de treinamento");
				mlp.treinaArquivo(mlp.nomeArquivo);
			}
			
		}
		
	}
	
	public class TreinarLivreListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(mlp.nomeArquivo.equals(""))
				winIO.error("Favor definir o arquivo antes");
			else
			{
			new TesteLivre(mlp);
			}
		}
		
	}
	
	public class TreinarSalvarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class TreinarSalvarComoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	public class AjudaSobreListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
				winIO.message("Sobre", "NEURONA 0.2\nSimulador de Redes Neurais Artificiais\nDesenvolvido por Marvin Schneider\nGNU General Public Licence");		
		}
		
	}
	

	public static void main(String[] args) {
		
		new Principal();

	}

}
