package model;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class rotate {
	static int width, height;
	static int xp, yp, distan, trasX, trasY, numPC;
	static int bound1;


	public static void main(String[] args) throws IOException {
		BufferedImage img = ImageIO.read(new File("docs/prueba.jpg"));
		numPC = 6;
		ArrayList<int[][]> result = getAllSections(img, numPC);
		distan = (int) Math.round(Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height / 2, 2)));
		trasX = (int) ((distan * 2 - width) / 2);
		trasY = (int) ((distan * 2 - height) / 2);
		xp = width / 2;
		yp = height / 2;
		int grados = 60;
		float radian = (float) (grados * Math.PI / 180);
		ArrayList<int[][]> resultantes = new ArrayList<int[][]>();
		for (int i = 0; i < result.size(); i++) {
			int[][] r = new int[distan * 2][distan * 2];
			rotarMonolitico(result.get(i), radian, r);
			
//			FileWriter fichero = null;
//			PrintWriter pw = null;
//			fichero = new FileWriter("docs/datosPruebas/DatosOriginal.txt");
//			pw = new PrintWriter(fichero);
//
//			for (int k = 0; k < r.length; k++) {
//				for (int j = 0; j < r.length; j++) {
//					pw.print(r[k][j] + " ");
//				}
//				pw.println();
//			}
//			pw.close();
			
			JFrame jf = new JFrame();
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setSize(distan * 2, distan * 2);
			Viewer panel = new Viewer();
			jf.add(panel);
			jf.setVisible(true);
			panel.setImage(r);
			panel.setSize(distan * 2, distan * 2);
			jf.validate();
			jf.repaint();
			
			resultantes.add(r);
		}
	}

	private static ArrayList<int[][]> getAllSections(BufferedImage inputImage, int sections) {
		WritableRaster wr = inputImage.getRaster();
		ArrayList<int[][]> retorno = new ArrayList<int[][]>();
		width = inputImage.getWidth();
		height = inputImage.getHeight();
		int avance = (wr.getHeight() / sections);
		int pivot = 0;
		
		for (int i = 0; i < sections; i++) {
			int[][] result = new int[wr.getWidth()][wr.getHeight()];
			try {
				for (int k = 0; k < wr.getWidth(); k++) {
					for (int j = pivot; j < (pivot + avance); j++) {
						result[k][j] = wr.getSample(k, j, 0); // the sample in the specified band for the pixel at the
					}
				}
				pivot += (wr.getHeight() / sections);
				retorno.add(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retorno;
	}
	private static void rotarMonolitico(int[][] result, float radian, int[][] resultante) {
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				float t = i - xp;
				float v = j - yp;
				int xs = Math.round((int) (xp + t * Math.cos(radian) - v * Math.sin(radian)));
				int ys = Math.round((int) (yp + v * Math.cos(radian) + t * Math.sin(radian)));
				try {
					resultante[xs + trasX][ys + trasY] = result[i][j];
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
