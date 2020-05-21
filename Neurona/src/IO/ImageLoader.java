package IO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageLoader {

	public static JLabel loadLabel(String path)
	{
	    File file = new File(path);
	    BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {

			e.printStackTrace(); // &&&
		}
		
	    return new JLabel(new ImageIcon(image));
	}
	
	public static ImageIcon loadIcon(String path)
	{
	    File file = new File(path);
	    BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {

			e.printStackTrace(); // &&&
		}
		
	    return new ImageIcon(image);
	}
	
	
}
