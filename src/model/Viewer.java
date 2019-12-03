package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class Viewer extends JPanel {

	private BufferedImage image;

	public void setImage(int result[][]) {
		image = new BufferedImage(result.length, result[0].length, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < result.length; x++) {
			for (int y = 0; y < result[0].length; y++) {
				int a = result[x][y];
				int r = (a>>16)&0xFF;
				int g = (a>>8)&0xFF;
				int b = (a>>0)&0xFF;
				Color newColor = new Color(r, g, b);
				image.setRGB(x, y, newColor.getRGB());
			}
		}
//		  File outputFile = new File("/output.bmp");
//		  ImageIO.write(image, "bmp", outputFile);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, this);
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
