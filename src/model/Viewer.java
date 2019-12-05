package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import rotador.Rotador;

@SuppressWarnings("serial")
class Viewer extends JPanel {

	public static void main(String[] args) throws InterruptedException, IOException {
		int num = 6;
		ServidorCentral s = new ServidorCentral("galaxia", "tif", num, 152);
		for (int i = 0; i < num; i++) {
			Rotador r1 = new Rotador(s);
			r1.attach();
			Thread.sleep(1000);
		}
//		FileWriter fichero = null;
//		PrintWriter pw = null;
//		fichero = new FileWriter("docs/datosPrueba/DatosOriginal.txt");
//		pw = new PrintWriter(fichero);
//
//		int[][] r = s.getResultante();
//
//		for (int k = 0; k < r.length; k++) {
//			for (int j = 0; j < r.length; j++) {
//				pw.print(r[k][j] + " ");
//			}
//			pw.println();
//		}
//		pw.close();

		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(s.getDistan() * 2, s.getDistan() * 2);
		Viewer v = new Viewer();
		jf.add(v);
		jf.setVisible(true);
		v.setImage(s.getResultante());
		v.setSize(s.getDistan() * 2, s.getDistan() * 2);
		jf.validate();
		jf.repaint();

	}

	private BufferedImage image;

	public void setImage(int result[][]) {
		image = new BufferedImage(result.length, result[0].length, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < result.length; x++) {
			for (int y = 0; y < result[0].length; y++) {
				int a = result[x][y];
				int r = (a >> 16) & 0xFF;
				int g = (a >> 8) & 0xFF;
				int b = (a >> 0) & 0xFF;
				Color newColor = new Color(r, g, b);
				image.setRGB(x, y, newColor.getRGB());
			}
		}
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
