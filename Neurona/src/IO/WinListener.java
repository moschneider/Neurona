package IO;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class WinListener implements WindowListener {
		
	WinIO IO;
	JFrame callingFrame;
		
	public WinListener(JFrame callingFrame)
	{
		IO = new WinIO();
		
		this.callingFrame = callingFrame;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
			
		callingFrame.setAlwaysOnTop(false);
		
		int answer = IO.yesNoQuestion("Leave the system?", "NGC4");
			
		if(answer==JOptionPane.YES_OPTION)
				
		System.exit(0);
	}

		@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
			
	}

		@Override
	public void windowDeiconified(WindowEvent arg0) {
	// TODO Auto-generated method stub
			
	}

		@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
			
	}

		@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
			
	}
		
}
