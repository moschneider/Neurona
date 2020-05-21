package IO;

import javax.swing.*;

public class TextWindow {

	public static final int CENTER = 0;
	public static final int BELOW = 1;
	public static final int ABOVE = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	
	public final int MAX_LINES = 30;
	
	public final int OK_BUTTON = 0;
	public final int DUMP_ONLY = 1;
	
	public final int MARGIN_X = 100;
	public final int MARGIN_Y = 80;

	JButton okButton;
	JLabel title;
	Window GUI;
	JSeparator s1, s2;
	JLabel[] text;
	int xSize, ySize;
	WinIO IO;
	
	int currentPos = 0;
	
	int mode = OK_BUTTON;
	
	int position;
	
	public TextWindow(String titleStr, int xSize, int ySize, JButton okButton, int position)
	{
		IO = new WinIO();
		
		this.position=position;
		this.okButton = okButton;
		this.xSize = xSize;
		this.ySize = ySize;
		text = new JLabel[MAX_LINES];
		title = new JLabel(titleStr);
		GUI = new Window("Neurona",false);
		
		s1 = new JSeparator();
		s2 = new JSeparator();
		
		GUI.addCenter(title);
		
		GUI.cr();
		
		GUI.addLine(s1, 1);
		
		GUI.cr();
		
		mode = OK_BUTTON;
	}
	
	public TextWindow(String titleStr, int xSize, int ySize, int position)
	{
		IO = new WinIO();
		
		this.position = position;
		this.xSize = xSize;
		this.ySize = ySize;
		text = new JLabel[MAX_LINES];
		title = new JLabel(titleStr);
		GUI = new Window("Neurona",false);
		
		s1 = new JSeparator();
		s2 = new JSeparator();
		
		GUI.addCenter(title);
		
		GUI.cr();
		
		GUI.addLine(s1, 1);
		
		GUI.cr();
		
		mode = DUMP_ONLY;
	}
	
	public void setPos()
	{
		int xFinal=0, yFinal=0;
		
		switch(position)
		{
		case CENTER: xFinal=(IO.getScreenWidth()-GUI.frame.getWidth())/2; 
						yFinal=(IO.getScreenHeight()-GUI.frame.getHeight())/2; 
						break;
		case BELOW: xFinal=(IO.getScreenWidth()-GUI.frame.getWidth())/2; 
					yFinal=(IO.getScreenHeight()-MARGIN_Y-GUI.frame.getHeight());
			break;
		case ABOVE: xFinal=(IO.getScreenWidth()-GUI.frame.getWidth())/2;
					yFinal=MARGIN_Y;
			break;
		case LEFT: xFinal=MARGIN_X; 
					yFinal=(IO.getScreenHeight()-GUI.frame.getHeight())/2;
					break;
		case RIGHT: xFinal=(IO.getScreenWidth()-MARGIN_X-GUI.frame.getWidth());
					yFinal=(IO.getScreenHeight()-GUI.frame.getHeight())/2;
					break;
		}
		
		GUI.setPosWindow(xFinal, yFinal);
	}
	
	public void appendText(String textIn)
	{
		text[currentPos]=new JLabel(textIn);
		
		GUI.addCenter(text[currentPos]);
		
		currentPos++;
		
		GUI.cr();
	}
	
	public void show()
	{
		if(mode==OK_BUTTON)
		{
		
			GUI.cr();
		
			GUI.addLine(s2, 1);
		
			GUI.cr();
		
			GUI.addCenter(okButton);
			
		}
		
		GUI.setSize(xSize, ySize);
		
		GUI.setResizeable(false);
		
		setPos();
		
		GUI.show();
		
		GUI.frame.setDefaultCloseOperation(0);
		
		GUI.frame.addWindowListener(new WinListener(GUI.getFrame()));
	}
	
	public void hide()
	{
		GUI.hide();
	}
	
}
