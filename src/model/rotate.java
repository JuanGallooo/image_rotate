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
	static int bound1,bound2,bound3,bound4,bound5;
	static int[][] resultante3;


	public static void main(String[] args) throws IOException {
		BufferedImage img = ImageIO.read(new File("docs/prueba.jpg"));
		numPC = 6;
		ArrayList<int[][]> result = getAllSections(img, numPC);
		distan = (int) Math.round(Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height / 2, 2)));
		trasX = (int) ((distan * 2 - width) / 2);
		trasY = (int) ((distan * 2 - height) / 2);
		xp = width / 2;
		yp = height / 2;
		resultante3 = new int[distan * 2][distan * 2];
		int grados = 9;
		float radian = (float) (grados * Math.PI / 180);
		ArrayList<int[][]> resultantes = new ArrayList<int[][]>();
		for (int i = 0; i < result.size(); i++) {
			int[][] r = new int[distan * 2][distan * 2];
			rotarMonolitico(result.get(i), radian, r,i);
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
		merge(resultantes, numPC, grados);
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(distan * 2, distan * 2);
		Viewer panel = new Viewer();
		jf.add(panel);
		jf.setVisible(true);
		panel.setImage(resultante3);
		panel.setSize(distan * 2, distan * 2);
		jf.validate();
		jf.repaint();
		
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
	private static void rotarMonolitico(int[][] result, float radian, int[][] resultante,int section) {
		int pivot = 0;
		int avance = (height/ numPC);
		int ex = 1;
		pivot = avance* section;
		
		int g = (int)(radian * 180/ Math.PI);
		if(g< 90 && g>45) {
			ex = g -45;
		}

		
		for (int i = 0; i < result.length; i++) {
			for (int j = pivot; j < pivot+avance; j++) {
				float t = i - xp;
				float v = j - yp;
				int xs = Math.round((int) (xp + t * Math.cos(radian) - v * Math.sin(radian)));
				int ys = Math.round((int) (yp + v * Math.cos(radian) + t * Math.sin(radian)));
				try {
					resultante[xs + trasX][ys + trasY] = result[i][j];
						if(g >= 0 && g<90) {
							if(i == 0 && j== ((pivot+avance)+ex)) { 
									bound1 = xs + trasX;

						}
							else if(g>=270 && g<360) {
								if(i == 0 && j== ((pivot+avance)+ex))bound1 = ys + trasY;}

						if(g>=90 && g<180) {
							if(i == 0 && j== (pivot+avance)+ex) {bound2 = ys + trasY;}
						}
						else if(g>=180 && g<270) {
							if(i == 0 && j== (pivot+avance)+ex) { bound2 = xs + trasX;}
						}

						}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void merge(ArrayList<int[][]> data, int n, int g){
		if(n == 2) {
			int[][] resul1 = data.get(0);
			int[][] resul2 = data.get(1);
			int w = resul1.length;
			int h = resul1[0].length;
			if(g >= 0 && g < 90) {
				for (int i = 0; i < w; i++) {
					for (int j = bound1; j < h; j++) {
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul2[i][j];
						}
					}
				}
				resultante3 = resul1;
			}
			else if(g>=90 && g<180) {
				for (int i = bound2; i < w; i++) {
					for (int j = 0; j < h; j++) {
						if(resul2[i][j] == 0) {
							resul2[i][j] = resul1[i][j];
						}
					}
				}
				resultante3 = resul2;
			}
			else if(g>=180 && g<270) {
					for (int i = 0; i < w; i++) {
						for (int j = bound2; j < h; j++) {
							if(resul2[i][j] == 0) {
								resul2[i][j] = resul1[i][j];
							}
						}
					}
					resultante3 = resul2;	
			}
			else if(g>=270 && g<360) {
				for (int i = bound1; i < w; i++) {
					for (int j = 0; j < h; j++) {
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul2[i][j];
						}
					}
				}
				resultante3 = resul1;
			}
		}
		if(n==6) {
			int[][] resul1 = data.get(0);
			int[][] resul2 = data.get(1);
			int[][] resul3 = data.get(2);
			int[][] resul4 = data.get(3);
			int[][] resul5 = data.get(4);
			int[][] resul6 = data.get(5);
			int w = resul1.length;
			int h = resul1[0].length;
			if(g >= 0 && g < 90) {
				
				for (int i = 0; i < w; i++) {
					for (int j = bound1; j < h; j++) {
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul2[i][j];							
						}
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul3[i][j];							
						}
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul4[i][j];							
						}
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul5[i][j];							
						}
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul6[i][j];							
						}

					}
				}
				resultante3 = resul1;
			}
			else if(g>=90 && g<180) {
				for (int i = bound2; i < w; i++) {
					for (int j = 0; j < h; j++) {
						if(resul6[i][j] == 0) {
							resul6[i][j] = resul1[i][j];
						}
						if(resul6[i][j] == 0) {
							resul6[i][j] = resul2[i][j];
						}
						if(resul6[i][j] == 0) {
							resul6[i][j] = resul3[i][j];
						}
						if(resul6[i][j] == 0) {
							resul6[i][j] = resul4[i][j];
						}
						if(resul6[i][j] == 0) {
							resul6[i][j] = resul5[i][j];
						}
					}		
				}
				resultante3 = resul6;	
			}
			else if(g>=180 && g<270) {
					for (int i = 0; i < w; i++) {
						for (int j = bound2; j < h; j++) {
							if(resul6[i][j] == 0) {
								resul6[i][j] = resul1[i][j];
							}
							if(resul6[i][j] == 0) {
								resul6[i][j] = resul2[i][j];
							}
							if(resul6[i][j] == 0) {
								resul6[i][j] = resul3[i][j];
							}
							if(resul6[i][j] == 0) {
								resul6[i][j] = resul4[i][j];
							}
							if(resul6[i][j] == 0) {
								resul6[i][j] = resul5[i][j];
							}
						}		
					}
					resultante3 = resul6;	
			}
			else if(g>=270 && g<360) {
				for (int i = bound1; i < w; i++) {
					for (int j = 0; j < h; j++) {
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul2[i][j];							
						}
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul3[i][j];							
						}
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul4[i][j];							
						}
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul5[i][j];							
						}
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul6[i][j];							
						}

					}
				}


				resultante3 = resul1;
			}
		}
	}
}
