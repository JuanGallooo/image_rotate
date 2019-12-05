package model;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import rotador.Rotador;

public class ServidorCentral {
	
	private int grados;
	private int width;
	private int height;
	private int xp;
	private int yp;
	private int distan;
	private int trasX;
	private int trasY;
	private int numPC;
	private int avance;
	private int[][] resultante3;
	private Lector lector;
	private ArrayList<Rotador> rotadores;
	private ArrayList<int[][]> result;
	private HiloEsperaPC hiloEs;
	
	public ServidorCentral (String picName, String format, int numPC, int grados) {
		lector = new Lector(picName,format);
		this.numPC = numPC;
		this.grados = grados;
		
		hiloEs = new HiloEsperaPC(this);
		inicializarServidorCentral();
	}
		
	public int getDistan() {
		return distan;
	}

	public void setDistan(int distan) {
		this.distan = distan;
	}

	public int getNumPC() {
		return numPC;
	}

	public void setNumPC(int numPC) {
		this.numPC = numPC;
	}
	public int[][] getResultante(){
		return resultante3;
	}
	public ArrayList<Rotador> getRotadores(){
		return rotadores;
	}

	public void inicializarServidorCentral() {
		result = getAllSections(lector.getBufImg(), numPC);
		this.distan = (int) Math.round(Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height / 2, 2)));
		trasX = (int) ((distan * 2 - width) / 2);
		trasY = (int) ((distan * 2 - height) / 2);
		xp = width / 2;
		yp = height / 2;
		resultante3 = new int[distan * 2][distan * 2];
		rotadores = new ArrayList<Rotador>();
		Thread x = new Thread(hiloEs);
		x.run();
	}



	private  ArrayList<int[][]> getAllSections(BufferedImage inputImage, int sections) {
		WritableRaster wr = inputImage.getRaster();
		ArrayList<int[][]> retorno = new ArrayList<int[][]>();
		width = inputImage.getWidth();
		height = inputImage.getHeight();
		avance = (wr.getHeight() / sections);
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

	
	public void merge(ArrayList<int[][]> data, int n, int g){
		if(n==1) {
			resultante3 = data.get(0);
		}
		if(n == 2) {
			int[][] resul1 = data.get(0);
			int[][] resul2 = data.get(1);
			int w = resul1.length;
			int h = resul1[0].length;
			if(g >= 0 && g < 90) {
				for (int i = 0; i < w; i++) {
					for (int j = 0; j < h; j++) {
						if(resul1[i][j] == 0) {
							resul1[i][j] = resul2[i][j];
						}
					}
				}
				resultante3 = resul1;
			}
			else if(g>=90 && g<180) {
				for (int i = 0; i < w; i++) {
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
						for (int j = 0; j < h; j++) {
							if(resul2[i][j] == 0) {
								resul2[i][j] = resul1[i][j];
							}
						}
					}
					resultante3 = resul2;	
			}
			else if(g>=270 && g<360) {
				for (int i = 0; i < w; i++) {
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
			else if(g>=90 && g<180) {
				for (int i = 0; i < w; i++) {
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
			else if(g>=270 && g<360) {
				for (int i = 0; i < w; i++) {
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
	
	public void attach(Rotador nuevo) {
		rotadores.add(nuevo);
		hiloEs.verify();
	}
	ArrayList<int[][]> respuesta = new ArrayList<int[][]>();
	public void distribute(){
		float radian = (float) (grados * Math.PI / 180);		
		for (int i = 0; i < result.size(); i++) {
			int[][] r = new int[distan * 2][distan * 2];
			Thread x = new Thread(new hiloEsperaResultado(this, rotadores.get(i), result.get(i), radian, r, i,avance, xp,yp,trasX,trasY));
			x.run();		
//			respuesta.add(rotadores.get(i).rotar(result.get(i), radian, r,i,avance, xp,yp,trasX,trasY));
		}
		while (!(respuesta.size()<numPC)) {
			merge(respuesta, numPC, grados);
			break;
		}
	}
	public void agregar(int[][] resultado) {
		respuesta.add(resultado);
	}


	
}
