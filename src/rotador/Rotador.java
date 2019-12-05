package rotador;

import model.ServidorCentral;

public class Rotador {
	
	private ServidorCentral sc;
	
	public Rotador(ServidorCentral sc) {
		this.sc = sc;
	}
	
	public void register() {
		sc.register(this);
	}
	
	public int[][] rotar(int[][] result, float radian, 
			int[][] resultante,int section,int avance, int xp, int yp,
						int trasX, int trasY) {
		int pivot = 0;
		pivot = avance* section;
		
		for (int i = 0; i < result.length; i++) {
			for (int j = pivot; j < pivot+avance; j++) {
				float t = i - xp;
				float v = j - yp;
				int xs = Math.round((int) (xp + t * Math.cos(radian) - v * Math.sin(radian)));
				int ys = Math.round((int) (yp + v * Math.cos(radian) + t * Math.sin(radian)));
				try {
					resultante[xs + trasX-1][ys + trasY] = result[i][j];
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(xs+trasX);
					System.out.println(ys+trasY);
				}
			}
		}
		return resultante;
	}
}
