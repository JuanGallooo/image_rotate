package model;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class rotate {
	// ANCHURA , ALTURA
	static int width, height;
	static int xp, yp, distan, trasX, trasY, numPC;
	static int xp2, yp2, distan2, trasX2, trasY2;
	static int bound1;
	static int[][] resultante;
	static int[][] resultante2;
	static int[][] resultante3;

	public static void main(String[] args) throws IOException {
		BufferedImage img = ImageIO.read(new File("docs/prueba.jpg"));
		numPC = 2;
		if (numPC == 1) {
			int[][] result = convertToArrayLocation(img, 1, 1);
			distan = (int) Math.round(Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height / 2, 2)));
			resultante = new int[distan * 2][distan * 2];
			trasX = (int) ((resultante.length - width) / 2);
			trasY = (int) ((resultante.length - height) / 2);
			xp = width / 2;
			yp = height / 2;
			int grados = 45;
			float radian = (float) (grados * Math.PI / 180);
			rotateM(result, radian, resultante, 0);

			FileWriter fichero = null;
			PrintWriter pw = null;
			fichero = new FileWriter("docs/datosPruebas/DatosOriginal.txt");
			pw = new PrintWriter(fichero);

			for (int i = 0; i < resultante.length; i++) {
				for (int j = 0; j < resultante.length; j++) {
					pw.print(resultante[i][j] + " ");
				}
				pw.println();
			}
			pw.close();

			JFrame jf = new JFrame();
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setSize(distan * 2, distan * 2);
			Viewer panel = new Viewer();
			jf.add(panel);
			jf.setVisible(true);
			panel.setImage(resultante);
			panel.setSize(distan * 2, distan * 2);
			jf.validate();
			jf.repaint();
		}
		if (numPC == 2) {
			int[][] result = convertToArrayLocation(img, 2, 1);
			distan = (int) Math.round(Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height / 2, 2)));
			resultante = new int[distan * 2][distan * 2];
			trasX = (int) ((resultante.length - width) / 2);
			trasY = (int) ((resultante.length - height) / 2);
			xp = width / 2;
			yp = height / 2;
			int grados = 45;
			float radian = (float) (grados * Math.PI / 180);
			rotateM(result, radian, resultante, 0);

			JFrame jf = new JFrame();
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setSize(distan * 2, distan * 2);
			Viewer panel = new Viewer();
			jf.add(panel);
			jf.setVisible(true);
			panel.setImage(resultante);
			panel.setSize(distan * 2, distan * 2);
			jf.validate();
			jf.repaint();

			int[][] result2 = convertToArrayLocation(img, 2, 2);
			distan2 = (int) Math.round(Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height / 2, 2)));
			resultante2 = new int[distan2 * 2][distan2 * 2];

			trasX2 = (int) ((resultante2.length - width) / 2);
			trasY2 = (int) ((resultante2.length - height) / 2);

			xp2 = width / 2;
			yp2 = height / 2;
			int grados2 = 45;
			float radian2 = (float) (grados2 * Math.PI / 180);
			rotateM(result2, radian2, resultante2, 1);

			JFrame jf2 = new JFrame();
			jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf2.setSize(distan2 * 2, distan2 * 2);
			
			resultante3 = new int[distan2 * 2][distan2 * 2];
			ArrayList<int[][]> data = new ArrayList<int[][]>();
			data.add(resultante);
			data.add(resultante2);
			merge(data, 2);
			
			Viewer panel2 = new Viewer();
			jf2.add(panel2);
			jf2.setVisible(true);
			
			panel2.setImage(resultante3);
			panel2.setSize(distan2 * 2, distan2 * 2);
			jf2.validate();
			jf2.repaint();
		}
	}

	private static int[][] convertToArrayLocation(BufferedImage inputImage, int sections, int numSection) {
		WritableRaster wr = inputImage.getRaster();
		int[][] result = null;
		if (sections == 1) {
			result = new int[wr.getWidth()][wr.getHeight()];
			width = inputImage.getWidth();
			height = inputImage.getHeight();
			try {
				for (int i = 0; i < wr.getWidth(); i++) {
					for (int j = 0; j < wr.getHeight(); j++) {
						result[i][j] = wr.getSample(i, j, 0); // the sample in the specified band for the pixel at the
																// specified coordinate.
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (sections == 2) {
			if (numSection == 1) {
				result = new int[wr.getWidth()][wr.getHeight()];
				// ANCHURA , ALTURA
				width = inputImage.getWidth();
				height = inputImage.getHeight();
				try {
					for (int i = 0; i < result.length; i++) {
						for (int j = 0; j < wr.getHeight() / 2; j++) {
							result[i][j] = wr.getSample(i,j,0); // the sample in the specified band for the pixel at
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				result = new int[wr.getWidth()][wr.getHeight()];
				try {
					for (int i = 0; i < wr.getWidth(); i++) {
						for (int j = (wr.getHeight() / 2); j < wr.getHeight() - 1; j++) {
							result[i][j] = wr.getSample(i, j, 0); // the sample in the specified band for the pixel at
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private static void rotateM(int[][] result, float radian, int[][] resultante, int n) {
		if (n == 0) {
			int g = (int) (radian * (180/Math.PI));
			int ex = 1;
			if(g< 90 && g>45) {
				ex = g -45;
			}
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < (result[0].length/2); j++) {
					float t = i - xp;
					float v = j - yp;
					int xs = Math.round((int) (xp + t * Math.cos(radian) - v * Math.sin(radian)));
					int ys = Math.round((int) (yp + v * Math.cos(radian) + t * Math.sin(radian)));
					try {
						resultante[xs + trasX][ys + trasY] = result[i][j];
					
					if(g >= )
						if(i == 0 && j== (result[0].length/2)+ex) {
							
								bound1 = xs + trasX;
//							else if(g<=180 && g>90) bound1 = 1;
							

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (n == 1) {
			for (int k = 0; k < result.length; k++) {
				for (int l = (result[0].length/2); l < result[0].length; l++) {
					float t = k - xp2;
					float v = l - yp2;
					int xs = Math.round((int) (xp2 + t * Math.cos(radian) - v * Math.sin(radian)));
					int ys = Math.round((int) (yp2 + v * Math.cos(radian) + t * Math.sin(radian)));
					try {
						resultante[xs + trasX2][ys + trasY2] = result[k][l];
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	public static void merge(ArrayList<int[][]> data, int n){
		if(n == 2) {
			int[][] resul1 = data.get(0);
			int[][] resul2 = data.get(1);
			int w = resul1.length;
			int h = resul1[0].length;
			for (int i = 0; i < w; i++) {
				for (int j = bound1; j < h; j++) {
					if(resul1[i][j] == 0) {
						resul1[i][j] = resul2[i][j];
					}
				}
			}
			resultante3 = resul1;
		}
	}
}
