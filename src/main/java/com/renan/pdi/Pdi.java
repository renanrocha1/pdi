package com.renan.pdi;

import java.util.*;

import com.renan.domain.*;

import javafx.scene.image.*;
import javafx.scene.paint.*;

public final class Pdi {
	
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
	
	public static Image deNoise(Image img, int tipoVizinhanca, int tipoCalculo) {
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Pixel p = getPixel(pr, i, j, tipoVizinhanca, w, h);
				pw.setColor(i, j, Pixel.MEDIA == tipoCalculo ? p.getMedia(tipoVizinhanca, p.getColor().getOpacity()) : p.getMediana(tipoVizinhanca, p.getColor().getOpacity()));
			}
		}
		return wi;
	}
	
	private static Pixel getPixel(PixelReader pr, int i, int j, int tipoVizinhanca, int maxW, int maxH) {
		Pixel p = new Pixel();
		p.setColor(pr.getColor(i, j));
		p.setI(i);
		p.setJ(j);
		switch (tipoVizinhanca) {
		case Pixel.VIZ_3x3:
			p.setVizCruz(getPixelListCruz(pr, i, j, maxW, maxH));
			p.setVizX(getPixelListX(pr, i, j, maxW, maxH));
			p.setViz3(new ArrayList<>(p.getVizCruz()));
			p.getViz3().addAll(p.getVizX());
			break;
		case Pixel.VIZ_CRUZ:
			p.setVizCruz(getPixelListCruz(pr, i, j, maxW, maxH));
			break;
		case Pixel.VIZ_X:
			p.setVizX(getPixelListX(pr, i, j, maxW, maxH));
			break;
		}
		return p;
	}
	
	private static ArrayList<Pixel> getPixelListCruz(PixelReader pr, int i, int j, int maxW, int maxH) {
		ArrayList<Pixel> pixels = new ArrayList<>();
		if (j + 1 < maxH) {
			Pixel p = new Pixel();
			p.setColor(pr.getColor(i, j + 1));
			p.setI(i);
			p.setJ(j + 1);
			pixels.add(p);
		}
		if (j - 1 >= 0) {
			Pixel p = new Pixel();
			p.setColor(pr.getColor(i, j-1));
			p.setI(i);
			p.setJ(j - 1);
			pixels.add(p);
		}
		if (i + 1 < maxW) {
			Pixel p = new Pixel();
			p.setColor(pr.getColor(i + 1, j));
			p.setI(i + 1);
			p.setJ(j);
			pixels.add(p);
		}
		if (i - 1 >= 0) {
			Pixel p = new Pixel();
			p.setColor(pr.getColor(i - 1, j));
			p.setI(i - 1);
			p.setJ(j);
			pixels.add(p);
		}
		return pixels;
	}
	
	private static ArrayList<Pixel> getPixelListX(PixelReader pr, int i, int j, int maxW, int maxH) {
		ArrayList<Pixel> pixels = new ArrayList<>();
		if (j + 1 < maxH && i + 1 < maxW) {
			Pixel p = new Pixel();
			p.setColor(pr.getColor(i + 1, j + 1));
			p.setI(i + 1);
			p.setJ(j + 1);
			pixels.add(p);
		}
		if (j - 1 >= 0 && i - 1 >= 0) {
			Pixel p = new Pixel();
			p.setColor(pr.getColor(i - 1, j - 1));
			p.setI(i - 1);
			p.setJ(j - 1);
			pixels.add(p);
		}
		if (i + 1 < maxW && j - 1 >= 0) {
			Pixel p = new Pixel();
			p.setColor(pr.getColor(i + 1, j - 1));
			p.setI(i + 1);
			p.setJ(j - 1);
			pixels.add(p);
		}
		if (i - 1 >= 0 && j + 1 < maxH) {
			Pixel p = new Pixel();
			p.setColor(pr.getColor(i - 1, j + 1));
			p.setI(i - 1);
			p.setJ(j + 1);
			pixels.add(p);
		}
		return pixels;
	}
	
}
