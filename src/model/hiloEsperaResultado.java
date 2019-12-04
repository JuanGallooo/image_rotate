package model;

import rotador.Rotador;

public class hiloEsperaResultado implements Runnable{
	
	private ServidorCentral sc;
	
	private Rotador r;
	private int[][] result;
	private float radian; 
	private int[][] resultante;
	private int section;
	private int avance;
	private int xp; 
	private int yp;
	private int trasX;
	private int trasY;
	
	
	public hiloEsperaResultado(ServidorCentral sc, Rotador r, int[][] result, float radian, int[][] resultante,
			int section, int avance, int xp, int yp, int trasX, int trasY) {
		this.sc = sc;
		this.r = r;
		this.result = result;
		this.radian = radian;
		this.resultante = resultante;
		this.section = section;
		this.avance = avance;
		this.xp = xp;
		this.yp = yp;
		this.trasX = trasX;
		this.trasY = trasY;
	}

	@Override
	public void run() {
		r.rotar(result, radian, resultante, section, avance, xp, yp, trasX, trasY);
		sc.agregar(resultante);
	}
}
