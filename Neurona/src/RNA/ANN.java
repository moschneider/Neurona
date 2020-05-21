package RNA;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import IO.Window;

public class ANN {
	
	public final int GENEREC = 0;
	public final int BACKPROPAGATION = 1;

	/* Rede */
	
	public double x[], h[], o[], y[], w[][], q[][], hPlus[], hMinus[];
	
	public int A, B, C;
	
	public int i, j, k;
	
	/* Backpropagation */
	
	public double u[], s[], f[], deltaQ[][], deltaW[][];
	
	public double N;
	
	public String descEnt[], descSai[];
	
	public String nomeArquivo = "";
	
	public int algoritmo;
	
	/**
	 * 
	 * REDE NEURAL ARTIFICIAL (PERCEPTRON COM 3 CAMADAS)
	 * -------------------------------------------------
	 * 
	 * A = numero de entradas da rede
	 * B = numero de neuronios escondidos
	 * C = numero de saidas
	 * 
	 * x[i] = entradas (i = 0,..,A-1)
	 * h[j] = neuronios escondidos (j = 0,..,B-1)
	 * o[k] = saida (calculada) da rede (k = 0,..,C-1)
	 * y[k] = saida desejada da rede (k = 0,..,C-1)
	 * 
	 * w[i][j] = sinapses entre a camada de entrada e a camada escondida (i = 0,..,A-1; j = 0,..,B-1)
	 * q[j][k] = sinapses entre a camada escondida e camada de saida (j = 0,..,B-1; k = 0,..,C-1)
	 * 
	 * 
	 * BACKPROPAGATION
	 * ---------------
	 * 
	 * u[k] = erros nas saidas (k = 0,..,C-1)
	 * s[j] = somas temporarias na camada escondida (j = 0,..,B-1)
	 * f[j] = erros na camada escondida (j = 0,..,B-1)
	 * 
	 * deltaQ[j][k] = diferenca calculada nas sinapses entre camada escondida e camada de saida (j = 0,..,B-1; k = 0,..,C-1)
	 * deltaW[i][j] = diferenca calculada nas sinapses entre camada de entrada e camada escondida (i = 0,..,A-1; j = 0,..,B-1)
	 *
	 * N = taxa de aprendizado (ex. 0,75)
	 */
	
	/**
	 * Montagem da rede, montagem de parametros para backpropagation e inicializacao
	 * 
	 * @param numEntradas	Numero de entradas
	 * @param numEscondidos	Numero de neuronions na camada escondida
	 * @param numSaidas		Numero de saidas
	 */
	
	boolean erroEncontrado = false; 	// para interpretacao
	
	public final int VALIDAR = 1;
	public final int TREINAR = 2;
	public int TESTAR = 3;
	
	public int modo = TREINAR;
	
	BufferedReader d;
	
	JTextArea textOut;
	
	public ANN(int numEntradas, int numEscondidos, int numSaidas, double taxaAprendizado)
	{
		A=numEntradas;
		B=numEscondidos;
		C=numSaidas;
		N=taxaAprendizado;
		
		algoritmo=BACKPROPAGATION;
		
		/** inicialização e criação da rede conforme os números fornecidos **/
		
		x = new double[A];
		h = new double[B];	s = new double[B]; f = new double[B];
		o = new double[C];	
		y = new double[C];	u = new double[C];
		
		w = new double[A][B];	deltaW = new double[A][B];
		q = new double[B][C];	deltaQ = new double[B][C];
		
		hPlus = new double[B]; hMinus = new double[B];
		
		/** inicialização das descrições **/
		
		descEnt = new String[A];
		descSai = new String[C];
		
		initRede();
		
		d = new BufferedReader(new InputStreamReader(System.in));
		
		textOut = new JTextArea(30,80);
		
	}
	
	public void titulo()
	{
		System.out.println("+-------------------------------------------------------------------------------------+");
		System.out.println("| Biblioteca para Implementacao de Sistema Inteligente usando Perceptron Multicamadas |");
		System.out.println("| 2011 por Marvin Oliver Schneider                                                    |");
		System.out.println("| Gnu Public License                                                                  |");
		System.out.println("+-------------------------------------------------------------------------------------+");
		System.out.println();
		System.out.println("Obs.: Apos a definicao da arquitetura, comecar com [ 1 - Criar Novo MLP ].");
	}
	
	/**
	 * Mostra uma mensagem de erro na tela
	 * 
	 * @param mensagem
	 */
	
	public void mostraErro(String mensagem)
	{
		System.out.println("[Atencao] Erro! " + mensagem);
	}
	
	/**
	 * Escrever "zero" nas entradas e saidas desejadas (para apresentar um novo conjunto de teste)
	 */
	
	public void zeraEntradasSaidasDesejadas()
	{
		for(int i=0;i<A;i++)
			x[i]=0;
		
		for(int k=0;k<C;k++)
			y[k]=0;	
	}
	
	/**
	 * Escrever "zero" nas ativacoes da camada escondida e nas saidas (para poder funcionar a rede)
	 */
	
	public void zeraEscondidosSaidas()
	{
		for(int j=0;j<B;j++)
			h[j]=0;
		
		for(int k=0;k<C;k++)
			o[k]=0;
	}
	
	/**
	 * Primeira inicializacao da rede
	 */
	
	public void initRede()
	{
		zeraEntradasSaidasDesejadas();
		
		/** inicializar sinapses com valores entre -0.1 e 0.1 **/
		
		for(int i=0;i<A;i++)
			for(int j=0;j<B;j++)
				w[i][j]=(Math.random() / 5) - 0.1;
				
		for(int j=0;j<B;j++)
			for(int k=0;k<C;k++)
				q[j][k]=(Math.random() / 5) - 0.1;
		
		for(int i=0;i<A;i++)
			descEnt[i]="";
		
		for(int k=0;k<C;k++)
			descSai[k]="";
	}
	
	/**
	 * Escrever os valores da rede em console
	 */
	
	public void dumpRede()
	{
		System.out.println("Camada de Entradas (x[i])");
		
		for(int i=0;i<A;i++)
			System.out.print("[" + i + "] = " + x[i] + "   ");
		
		System.out.println("\n");
		
		
		
		System.out.println("Camada Escondida (h[j])");
		
		for(int j=0;j<B;j++)
			System.out.print("[" + j + "] = " + h[j] + "   ");
		
		System.out.println("\n");
		
		
		
		System.out.println("Camada de Saidas (o[k])");
		
		for(int k=0;k<C;k++)
			System.out.print("[" + k + "] = " + o[k] + "   ");
		
		System.out.println("\n");
		
		
		
		System.out.println("Camada de Saidas Desejadas (y[k])");
		
		for(int k=0;k<C;k++)
			System.out.print("[" + k + "] = " + y[k] + "   ");
		
		System.out.println("\n");
		
		
		
		System.out.println("Sinapses entre Entradas e Camada Escondida (w[i][j])");
		
		for(int i=0;i<A;i++)
		{
			for(int j=0;j<B;j++)
				System.out.print("[" + i + "/" + j + "] = " + w[i][j] + "   ");
			
			System.out.println();
		}
		
		System.out.println();
		
		
		
		System.out.println("Sinapses entre Camada Escondida e Saidas (q[j][k])");
				
		for(int j=0;j<B;j++)
		{
			for(int k=0;k<C;k++)
				System.out.print("[" + j + "/" + k + "] = " + q[j][k] + "   ");
			
			System.out.println();
		}
		
		System.out.println();
		
		System.out.println("------------------------------------------------------\n");
	}
	
	/**
	 * Funcao de ativacao, retorna sigmoide de x
	 * 
	 * @param x
	 * @return
	 */
	
	public double sigmoide(double x)
	{
		return (1 / (1 + Math.exp(-x)));
	}
	
	/**
	 * Funcionar a rede
	 */
	
	public void funcionarRede()
	{
		zeraEscondidosSaidas();
		
		for(i=0;i<A;i++)
			for(j=0;j<B;j++)
				h[j]=h[j]+x[i]*w[i][j];
		
		for(j=0;j<B;j++)
			h[j]=sigmoide(h[j]);
		
		for(j=0;j<B;j++)
			for(k=0;k<C;k++)
				o[k]=o[k]+h[j]*q[j][k];
		
		for(k=0;k<C;k++)
			o[k]=sigmoide(o[k]);
		
	}
	
	/**
	 * Executar o algoritmo de Backpropagation
	 */
	
	public void backPropagation()
	{
		/** Calculo dos erros nas saidas **/
		
		for(k=0;k<C;k++)
			u[k]=o[k]*(1.0-o[k])*(y[k]-o[k]);
		
		/** Calculo dos erros na camada escondida **/
		
		for(j=0;j<B;j++)
		{
			s[j]=0.0;
			
			for(k=0;k<C;k++)
				s[j]=s[j]+u[k]*q[j][k];
			
			f[j]=h[j]*(1.0-h[j])*s[j];
		}
		
		/** Delta nas sinapses entre camada escondida e saida **/
		
		for(j=0;j<B;j++)
			for(k=0;k<C;k++)
				deltaQ[j][k]=N*u[k]*h[j];
		
		/** Delta nas sinapses entre entradas e camanda escondida **/
		
		for(i=0;i<A;i++)
			for(j=0;j<B;j++)
				deltaW[i][j]=N*f[j]*x[i];
		
		/** Execucao de ajustes **/
		
		for(j=0;j<B;j++)
			for(k=0;k<C;k++)
				q[j][k]=q[j][k]+deltaQ[j][k];
		
		for(i=0;i<A;i++)
			for(j=0;j<B;j++)
				w[i][j]=w[i][j]+deltaW[i][j];
		
	}
	
	/**
	 * Execute GeneRec
	 * 
	 */
	
	public void geneRec(boolean onlyMinus)
	{
		double sum1, sum2, sum3, sum4;
		
		sum1=0; sum2=0; sum3=0; sum4=0;
		
		for(int j=0;j<B;j++)
		{
			for(int i=0;i<A;i++)
			{
				sum1=sum1+w[i][j]*x[i];
			}
			
			for(int k=0;k<C;k++)
			{
				sum2=sum2+q[j][k]*y[k];
			}
			
			for(int k=0;k<C;k++)
			{
				sum3=sum3+q[j][k]*o[k];
			}
			
			if(onlyMinus==false) hPlus[j]=sigmoide(sum1+sum2);	// positive activation signal (1)
			
			hMinus[j]=sigmoide(sum1+sum3); // negative activation signal (2)
			
			sum1=0;
			sum2=0;
			sum3=0;
		}
		
		sum4=0;
		
		for(int k=0;k<C;k++)
		{
			for(int j=0;j<B;j++)
			{
				sum4=sum4+q[j][k]*hMinus[j];	// next activation (3)
			}
			
			o[k]=sigmoide(sum4);
			
			sum4=0;
			
		}
		
		if(onlyMinus==false)
		{
		
			for(int j=0;j<B;j++)
			{
				for(int k=0;k<C;k++)
				{
					q[j][k]=q[j][k]+N*(y[k]-o[k])*hMinus[j];	// adjustments 1 (4)
				}
			}
		
			for(int i=0;i<A;i++)
			{
				for(int j=0;j<B;j++)
				{
					w[i][j]=w[i][j]+N*(hPlus[j]-hMinus[j])*x[i];	// adjustments 2 (5)
				}
			}
		}
	}
	
	/**
	 * Escreve os valores calculados pelo Backpropagation em console
	 */
	
	public void dumpBackPropagation()
	{
		System.out.println("Erros nas Saidas (u[k])");
		
		for(int k=0;k<C;k++)
			System.out.print("[" + k + "] = " + u[k] + "   ");
		
		System.out.println("\n");
		
		
		System.out.println("Somas temporarias na camada escondida (s[j])");
		
		for(int j=0;j<B;j++)
			System.out.print("[" + j + "] = " + s[j] + "   ");
		
		System.out.println("\n");
		
		
		System.out.println("Erros na camada escondida (f[j])");
		
		for(int j=0;j<B;j++)
			System.out.print("[" + j + "] = " + f[j] + "   ");
		
		System.out.println("\n");
		
		
		
		System.out.println("Ajuste nas Sinapses entre Camada Escondida e Saidas (deltaQ[j][k])");
				
		for(int j=0;j<B;j++)
		{
			for(int k=0;k<C;k++)
				System.out.print("[" + j + "/" + k + "] = " + deltaQ[j][k] + "   ");
			
			System.out.println();
		}
		
		System.out.println();
		
		
		
		System.out.println("Ajuste nas Sinapses entre Entradas e Camada Escondida (deltaW[i][j])");
		
		for(int i=0;i<A;i++)
		{
			for(int j=0;j<B;j++)
				System.out.print("[" + i + "/" + j + "] = " + deltaW[i][j] + "   ");
			
			System.out.println();
		}
		
		System.out.println();
		
	
	}
	
	public void setAlgoritmo(int algoritmo)
	{
		this.algoritmo=algoritmo;
	}
	
	/**
	 * Coloca valor "um" em determinada entrada (com validacao)
	 * 
	 * @param pos
	 */
	
	public void setEntrada(int pos)
	{
		if(pos<A && pos>-1)
			
			x[pos]=1;
		
		else
			
			System.out.println("\n>>Erro: Impossivel setar entrada " + pos + "(fora do intervalo).\n");
	}
	
	/**
	 * Coloca valor "um" em determinada saida (com validacao)
	 * 
	 * @param pos
	 */
	
	public void setSaidaDesejada(int pos)
	{
		if(pos<C && pos>-1)
			
			y[pos]=1;
		
		else
			
			System.out.println("\n>>Erro: Impossivel setar entrada " + pos + "(fora do intervalo).\n");
	}
	
	public void erroSintaxe(String mensagem, String mensagem2)
	{
		System.out.println("[Atencao] Erro de sintaxe! Esperando " + mensagem + ".\n");
		System.out.println("[Atencao] Linha encontrada: " + mensagem2 + "\n");
		System.out.println("[Atencao] Parando interpretacao do arquivo.....\n");
		
		erroEncontrado = true;
	}
	
	public void aviso(String mensagem)
	{
		if(modo==VALIDAR) 
			System.out.println("[Aviso] " + mensagem + ".\n");
	}
	
/*	public int formatacaoOk(String valores)
	{
		int numero = 0;
		
		for(int g=0;g<valores.length();g++)
		{
			if(valores.charAt(g)=='0' || valores.charAt(g)=='1' || valores.charAt(g)==',')
			{
				if(g%2==0)
				{
					if(valores.charAt(g)==',')
						return -1;
					
					numero ++;
					
				} else
					if(valores.charAt(g)!=',')
						return -1;
			} else
				if(g==valores.length())
					if(valores.charAt(g)!=';')
						return -1;
		}
		
		return numero;
	}
	
	public void valoresParaEntradas(String inStr)
	{
		int valor = 0;
		
		for(int g=0;g<inStr.length();g++)
		{
			if(g%2 == 0)
			{
				if(inStr.charAt(g)=='1' || inStr.charAt(g)=='0')
				{
				
					if(inStr.charAt(g)=='1') x[valor]=1;
					
					if(inStr.charAt(g)=='0') x[valor]=0;
					
					valor++;
				}
			}
		}
	} */
	
	public int formatacaoOk(String inStr)
	{
		int numero = 0;
		
		for(int g=0;g<inStr.length();g++)
		{
			if(inStr.charAt(g)==',' || inStr.charAt(g)=='\n' || inStr.charAt(g)==';' || g==inStr.length()-1)
			{
				numero++;
			}
		}
		
		return numero;
	}
	
	public void valoresParaEntradas(String inStr)
	{
		int valor = 0;
		String flag = "";
		
		for(int g=0;g<inStr.length();g++)
		{
			if(inStr.charAt(g)==',' || inStr.charAt(g)=='\n' || inStr.charAt(g)==';' || g==inStr.length()-1)
			{
				System.out.println(flag);
				
				x[valor]=Double.parseDouble(flag);
				
				System.out.println(x[valor]);
				
				valor++;
				
				flag = "";
			} else
				flag = flag + inStr.charAt(g);
			}

	}
	
	public void valoresParaSaidas(String inStr)
	{
		int valor = 0;
		
		for(int g=0;g<inStr.length();g++)
		{
			if(g%2 == 0)
			{
				if(inStr.charAt(g)=='1' || inStr.charAt(g)=='0')
				{
				
					if(inStr.charAt(g)=='1') y[valor]=1;
					
					if(inStr.charAt(g)=='0') y[valor]=0;
					
					valor++;
				}
			}
		}
	}
	
	
	
	/**
	 * Rotina que lê o arquivo de treinamento e treina o contra a rede atual
	 * 
	 * @param nomeDoArquivo
	 */
	
	public void treinaArquivo(String nomeDoArquivo)
	{
		FileReader fr = null;
		BufferedReader br;
		String str, par = null;
		
		int conjuntosLidos = 0;
		double erroTotal = 0.0;
		int respostasErradas = 0;
				
		final int ESPERANDO_PAR = 0;
		final int ESPERANDO_ENTRADA = 1;
		final int ESPERANDO_ENTRADA_VALORES = 2;
		final int ESPERANDO_SAIDA = 3;
		final int ESPERANDO_SAIDA_VALORES = 4;
		
		int estado = ESPERANDO_PAR;
		
		int valoresRetornados = 0;
		
		// Abertura do arquivo
		
		try {
			fr = new FileReader(nomeDoArquivo);
		} catch (FileNotFoundException e) {
			
			textOut.append("\n\nERRO\n\nImpossivel criar FileReader no arquivo " + nomeDoArquivo);
			
		}
		
		br = new BufferedReader(fr);
		
		erroEncontrado = false;
		
		// Leitura das linhas
		
		try {
			while((str = br.readLine()) !=null && erroEncontrado == false)
			{
				//System.out.println(str);
				
				switch(estado)
				{
				case ESPERANDO_PAR:
					
					if(str.equals("")) break; // brancos podem ser inseridos antes de [PARn]
					
					if(str.startsWith("[PAR")){
					
						par = str.substring(4, str.length()-1);
						
						if(modo==VALIDAR) textOut.append("\n\nLendo par " + par);
						
						estado = ESPERANDO_ENTRADA;
						
						break;
					}
					
					textOut.append("\n\nERRO\n\n[PARn]\n" + str);
					
					break;
					
				case ESPERANDO_ENTRADA:
					
					if(str.equals(""))	// brancos podem ser inseridos antes de *Entrada*
						break;
					
					if(str.equals("*Entrada*"))
					{
						if(modo==VALIDAR) textOut.append("\n\nEntrada encontrada");
						
						estado = ESPERANDO_ENTRADA_VALORES;
						
						break;
					}
					
					textOut.append("\n\nERRO\n\n*Entrada*\n" + str);
					
					break;
					
				case ESPERANDO_ENTRADA_VALORES:
					
					if(str.equals(""))	// brancos podem ser inseridos antes dos valores de entrada
						break;
				
					valoresRetornados = formatacaoOk(str);
					
					if(valoresRetornados>0)
					{
						if(modo==VALIDAR) textOut.append("\n\nFormatacao de valores de entrada OK. Valores encontrados: " + valoresRetornados);
						
						if(valoresRetornados==A)
						{
							if(modo==VALIDAR) textOut.append("\n\nNumero de valores encontrados confere com as entradas da rede");
						} else
							textOut.append("\n\nERRO\n\n" + A + " valores de entrada\n" + str);
						
						if(modo==TREINAR)
						{
							valoresParaEntradas(str);
						}
						
						if(modo==TESTAR)
						{
							valoresParaEntradas(str);
						}
					
						estado = ESPERANDO_SAIDA;
					
						break;
					}
					
					textOut.append("\n\nERRO\n\nValores de saida formatados\n" + str);
					
				case ESPERANDO_SAIDA: 
					
					if(str.equals(""))	// brancos podem ser inseridos antes de *Saida*
						break;
					
					if(str.equals("*Saida*"))
					{
						if(modo==VALIDAR) textOut.append("\n\nSaida encontrada");
						
						estado = ESPERANDO_SAIDA_VALORES;
						
						break;
					}
					
					textOut.append("\n\nERRO\n\n*Saida*\n" + str);
					
					break;
					
				case ESPERANDO_SAIDA_VALORES:
				
					if(str.equals(""))	// brancos podem ser inseridos antes dos valores de entrada
						break;
				
					valoresRetornados = formatacaoOk(str);
					
					if(valoresRetornados>0)
					{
						if(modo==VALIDAR) textOut.append("\n\nFormatacao de valores de saida OK. Valores encontrados: " + valoresRetornados);
						
						if(valoresRetornados==C)
						{
							if(modo==VALIDAR) textOut.append("\n\nNumero de valores encontrados confere com as saidas da rede");
						} else
							textOut.append("\n\nERRO\n\n" + C + " valores de saida\n" + str);
					
						if(modo==TREINAR)
						{
							valoresParaSaidas(str);
							
							if(algoritmo==BACKPROPAGATION)
							{
							
								funcionarRede();
							
								backPropagation();
							} else
								geneRec(false);
						}
						
						if(modo==TESTAR)
						{
							conjuntosLidos++;
							
							valoresParaSaidas(str);
							
							if(algoritmo==BACKPROPAGATION)
							
								funcionarRede(); else
									geneRec(true);
							
							for(int t=0;t<C;t++)
							{
								if(y[t]==1 && o[t]<0.5) respostasErradas++;
								if(y[t]==0 && o[t]>0.5) respostasErradas++;
								
								erroTotal=erroTotal+Math.abs(y[t]-o[t]);
							}
						}
						
						estado = ESPERANDO_PAR;
					
						break;
					}
					
					textOut.append("\n\nERRO\n\nValores de saida formatados\n" + str);
				
				}
			}
		} catch (IOException e) {
			
			textOut.append("\n\nERRO\n\nImpossivel ler do arquivo " + nomeDoArquivo);
			
		}
		
		// Fechamento do arquivo
		
		try {
			br.close();
		} catch (IOException e) {
			
			textOut.append("\n\nERRO\n\nImpossivel fechar arquivo " + nomeDoArquivo);
			
		}
		
		if(modo==TESTAR)
		{
			textOut.append("\n\nRESULTADO DO TESTE");
			textOut.append("\n\n");
			
			textOut.append("\n\nConjuntos lidos:  " + conjuntosLidos);
			textOut.append("\n\nErro total:       " + erroTotal);
			textOut.append("\n\nErro relativo:    " + (erroTotal/(conjuntosLidos*C)));
			textOut.append("\n\nEscolhas erradas: " + respostasErradas + "\n\n\n\n");	
		}
		
	}
	
	public void mostraTextoEmJanela(String titulo)
	{
		textOut.setText("");
		
		JScrollPane scrollPane = new JScrollPane(textOut);
		
		Window tela = new Window(titulo,false);
		
		JButton	ok = new JButton("OK");
		
		tela.addCenterLn(scrollPane);
		
		tela.addCenterLn(ok);
		
		ok.addActionListener(new OkListener(tela));
		
		tela.standardPackSmall();
		
		tela.centerPos();
		
		tela.show();	

	}
	
	class OkListener implements ActionListener {
		
		Window tela;
		
		public OkListener(Window tela)
		{
			this.tela=tela;
		}

		public void actionPerformed(ActionEvent e) {
			tela.hide();
		}
		
	}
	
	public int leiaNumero()
	{
		String resposta = "";
		
		int flag;
		
		try {
			resposta=d.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mostraErro("Nao consigo ler do teclado.");
		}
		
		try{
		
		flag = Integer.parseInt(resposta);
		
		} catch(Exception e) {
			
			mostraErro("Favor digitar numero. Obrigado.");
			
			flag = -999;
		}
		
		return flag;
		
	}
	
	public double leiaDouble()
	{
		String resposta = "";
		
		double flag;
		
		try {
			resposta=d.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mostraErro("Nao consigo ler do teclado.");
		}
		
		try{
		
		flag = Double.parseDouble(resposta);
		
		} catch(Exception e) {
			
			mostraErro("Favor digitar numero. Obrigado.");
			
			flag = -999;
		}
		
		return flag;
		
	}
	
	public String leiaString()
	{
		String resposta = "";
		
		try {
			resposta=d.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mostraErro("Nao consigo ler do teclado.");
		}
		
		return resposta;
		
	}
	
	public void leiaDescEnt()
	{
		for(int i=0;i<A;i++)
		{
			System.out.print("Descricao Entrada " + i + " : ");
			descEnt[i]=leiaString();
		}
	}
	
	public void leiaDescSai()
	{
		for(int k=0;k<C;k++)
		{
			System.out.print("Descricao Entrada " + k + " : ");
			descSai[k]=leiaString();
		}
	}
	
	public void leiaNomeArquivo()
	{
		System.out.print("Nome do arquivo (com caminho completo!) : ");
		nomeArquivo=leiaString();
	}
	
	public void testeLivre()
	{
		double erroAbs = 0; 
		int decErr = 0;
		
		System.out.println("Teste Livre.");
		System.out.println("Por favor, forneca os valores das Entradas.");
		for(int i=0;i<A;i++)
		{
			System.out.print("Entrada " + i + " ( " + descEnt[i] + " ) : ");
			x[i]=leiaNumero();
			if(x[i]!=0 && x[i]!=1) x[i]=0;
			System.out.println("Valor lido: " + x[i]);
		}
		System.out.println();
		System.out.println("Por favor, forneca os valores das Saidas Esperadas.");
		for(int k=0;k<C;k++)
		{
			System.out.print("Saida " + k + " ( " + descSai[k] + " ) : ");
			y[k]=leiaNumero();
			if(y[k]!=0 && y[k]!=1) y[k]=0;
			System.out.println("Valor lido: " + y[k]);
		}
		
		if(algoritmo==BACKPROPAGATION)
			funcionarRede(); else
				geneRec(true);
			
		for(int k=0;k<C;k++)
		{
			System.out.println("Saida " + k + " ( " + descSai[k] + " ) : Esperado > " + y[k] + " Real > " + o[k]);
			erroAbs = erroAbs + Math.abs(y[k]-o[k]);
			
			if(y[k]==1 && o[k]<0.5) decErr++;
			if(y[k]==0 && o[k]>0.5) decErr++;
		}
		
		System.out.println();
		System.out.println("RESUMO");
		System.out.println("------");
		
		System.out.println("Decisoes erradas : " + decErr);
		System.out.println("Erro medio : " + (erroAbs/C));
		System.out.println();
	}
	
}