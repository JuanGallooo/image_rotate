package model;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class rotate {
	//ANCHURA , ALTURA
	static int width, height;
	static int xp, yp, distan, trasX,trasY;
	static int[][] resultante;

	public static void main(String[] args) throws IOException {
		BufferedImage img = ImageIO.read(new File("docs\\prueba.jpg"));
		int[][] result = convertToArrayLocation(img);
		distan = (int) Math.round(Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height / 2, 2)));
		resultante = new int[distan*2][distan*2];
		trasX= (int)((resultante.length-width)/2);
		trasY= (int)((resultante.length-height)/2);
		xp= resultante.length/2;
		yp= resultante[0].length/2;
		int grados = 0;
		float radian = (float) (grados * Math.PI / 180);
		rotateM(result, radian);
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(distan*2, distan*2);
		Viewer panel = new Viewer();
		jf.add(panel);
		jf.setVisible(true);
		panel.setImage(resultante);
		jf.validate();
		jf.repaint();
	}

	private static int[][] convertToArrayLocation(BufferedImage inputImage) {
		WritableRaster wr = inputImage.getRaster();
		int[][] result = new int[wr.getWidth()][wr.getHeight()];
		width = inputImage.getWidth();
		height = inputImage.getHeight();
		try {
			for (int i = 0; i < wr.getWidth(); i++) {
			    for (int j = 0; j < wr.getHeight(); j++) {      
			        result[i][j]= wr.getSample(i, j, 0); // the sample in the specified band for the pixel at the specified coordinate.
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static void rotateM(int[][] result, float radian) {
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				float t = i - xp;
				float v = j - yp;
				int xs = Math.round((int) (xp + t * Math.cos(radian) - v * Math.sin(radian)));
				int ys = Math.round((int) (yp + v * Math.cos(radian) + t * Math.sin(radian)));
				try {
//					if(xs<0){
//						if(ys<0) {
//							resultante[xs+trasX][ys+trasY] = result[i][j];
//						}
//						else resultante[xs+trasX][ys+trasY] = result[i][j];
//					}
//					else if(ys<0){
//						resultante[xs+trasX][j+ys+trasY] = result[i][j];
//					}
//					else {						
						resultante[xs+trasX][ys+trasY] = result[i][j];
//					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(xs);
					System.out.println(ys);
					System.out.println(i);
					System.out.println(j);
				}
			}
		}
	}
}
