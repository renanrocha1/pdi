package com.renan.pdi;

import javafx.scene.image.*;
import javafx.scene.paint.*;

public class Pdi {
	
	public static Image exemploModificarPixel(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		Color cor = pr.getColor(10, 10);
		System.out.println(cor.getRed() + " ," + cor.getGreen() + " ," + cor.getBlue());
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color c2 = pr.getColor(i, j);
				Color cornao = new Color(c2.getBlue(), c2.getGreen(), c2.getRed(), c2.getOpacity());
				pw.setColor(i, j, cornao);
			}
		}
//		pw.setColor(10, 10, new Color(1, 1, 1, cor.getOpacity()));
		return wi;
	}
	
	public static int getDoubleColor(int range255) {
		return range255 / 255;
	}
	
	public static Image escalaCinzaPorMediaAritmetica(Image img) {
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color src = pr.getColor(i, j);
				double mediaCor = (src.getRed() + src.getGreen() + src.getBlue()) / 3;
				pw.setColor(i, j, new Color(mediaCor, mediaCor, mediaCor, src.getOpacity()));
			}
		}
		return wi;
	}
	
	public static Image escalaCinzaPorMediaPonderada(Image img, int pesoR, int pesoG, int pesoB) {
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color src = pr.getColor(i, j);
				double mediaCor = (src.getRed() * pesoR + src.getGreen() * pesoG + src.getBlue() * pesoB) / 100;
				pw.setColor(i, j, new Color(mediaCor, mediaCor, mediaCor, src.getOpacity()));
			}
		}
		return wi;
	}
	
	public static Image limiar(Image img, double limiar) {
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		double limiarDouble = limiar/255;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color src = pr.getColor(i, j);
				if (src.getRed() > limiarDouble) {
					pw.setColor(i, j, new Color(1, 1, 1, src.getOpacity()));
				} else {
					pw.setColor(i, j, new Color(0, 0, 0, src.getOpacity()));
				}
			}
		}
		return wi;
	}
	
	public static Image negativa(Image img) {
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color src = pr.getColor(i, j);
				pw.setColor(i, j, new Color(1 - src.getRed(), 1 - src.getGreen(), 1 - src.getBlue(), src.getOpacity()));
			}
		}
		return wi;
	}
	
	public static Image desafio(Image img) {
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		int div = w / 3;
		double limiar = Double.valueOf(170) / 255;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color src = pr.getColor(i, j);
				if (i <= div) {
					double mediaCor = (src.getRed() + src.getGreen() + src.getBlue()) / 3;
					pw.setColor(i, j, new Color(mediaCor, mediaCor, mediaCor, src.getOpacity()));
				} else if (i > div && i <= div * 2) {
					double mediaCor = (src.getRed() + src.getGreen() + src.getBlue()) / 3;
					if (mediaCor > limiar) {
						pw.setColor(i, j, new Color(1, 1, 1, src.getOpacity()));
					} else {
						pw.setColor(i, j, new Color(0, 0, 0, src.getOpacity()));
					}
				} else {
					pw.setColor(i, j, new Color(1 - src.getRed(), 1 - src.getGreen(), 1 - src.getBlue(), src.getOpacity()));
				}
			}
		}
		return wi;
	}
	
}
