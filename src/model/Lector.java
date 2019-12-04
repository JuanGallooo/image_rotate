package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Lector {

	public static final String DIR_ENTRADA = "docs/";
	private BufferedImage img;
	
	
	
	public Lector(String picName, String format) {
		try {
			System.out.println(new File(DIR_ENTRADA +picName + "."+ format).getAbsolutePath());
			img = ImageIO.read(new File(DIR_ENTRADA +picName + "."+ format));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\nError al cargar la foto");
		}
	}
	
	public BufferedImage getBufImg() {
		return img;
	}
	
	
	

	
}
