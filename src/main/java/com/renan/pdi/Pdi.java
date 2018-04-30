package com.renan.pdi;

import java.util.*;

import com.renan.domain.*;
import com.renan.util.*;

import javafx.scene.chart.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

public final class Pdi {
	
	private Pdi() {}
	
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
		double limiar = 170d / 255;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color src = pr.getColor(i, j);
				if (i <= div) {
					double mediaCor = (src.getRed() + src.getGreen() + src.getBlue()) / 3;
					pw.setColor(i, j, new Color(mediaCor, mediaCor, mediaCor, src.getOpacity()));
				} else if (i <= div * 2) {
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
	
	private static Histograma getHistograma(Image img) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		PixelReader pr = img.getPixelReader();
		int[] r = new int[256], g = new int[256], b = new int[256], intensidade = new int[256];
		int rgb;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				rgb = (int)(pr.getColor(i, j).getRed()*255);
				intensidade[rgb]++;
				r[rgb]++;
				rgb = (int)(pr.getColor(i, j).getGreen()*255);
				intensidade[rgb]++;
				g[rgb]++;
				rgb = (int)(pr.getColor(i, j).getBlue()*255);
				intensidade[rgb]++;
				b[rgb]++;
				
			}
		}
		return new Histograma(r, g, b, intensidade);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setGrafico(Image img, BarChart<String, Number> grafico){
		CategoryAxis eixoX = new CategoryAxis();
		NumberAxis eixoY = new NumberAxis();
	    eixoX.setLabel(MsgUtil.getMessage("label_canal"));       
	    eixoY.setLabel(MsgUtil.getMessage("label_valor"));
	    XYChart.Series vlrR = new XYChart.Series();
	    vlrR.setName("Intensidade");
	    Histograma hist = getHistograma(img);
	    hist.geraHistogramaAcumuladoI();
	    int[] array = hist.getR();
	    for (int i = 0; i < array.length; i++) {
	    	vlrR.getData().add(new XYChart.Data(String.valueOf(i), array[i]));
		}
	    XYChart.Series vlrG = new XYChart.Series();
	    vlrG.setName("G");
	    array = hist.getG();
	    for (int i = 0; i < array.length; i++) {
	    	vlrG.getData().add(new XYChart.Data(String.valueOf(i), array[i]));
	    }
	    XYChart.Series vlrB = new XYChart.Series();
	    vlrB.setName("B");
	    array = hist.getB();
	    for (int i=0; i < array.length; i++) {
	    	vlrB.getData().add(new XYChart.Data(String.valueOf(i), array[i]));
	    }
	    grafico.getData().addAll(vlrR, vlrG, vlrB);
	}
	
	public static Image geraImagemEqualizada(Image img) {
		int h = (int)img.getHeight();
		int w = (int)img.getWidth();
		double n = h * w;
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		Histograma hist = getHistograma(img);
		hist.geraHistRGB();
		double r, g, b;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color color = pr.getColor(i, j);
				r = ((hist.getQtR() - 1d) / n) * hist.getAcumuladoR()[(int)(color.getRed()*255)];
				g = ((hist.getQtG() - 1d) / n) * hist.getAcumuladoG()[(int)(color.getGreen()*255)];
				b = ((hist.getQtB() - 1d) / n) * hist.getAcumuladoB()[(int)(color.getBlue()*255)];
				pw.setColor(i, j, new Color(r / 255, g / 255, b / 255, color.getOpacity()));
			}
		}
		return wi;
	}
	
	public static Image geraImagemSegmentada(Image img) {
		int h = (int)img.getHeight();
		int w = (int)img.getWidth();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		Histograma hist = getHistograma(img);
		int[] rangeR = getRange(hist.getR());
		int[] rangeG = getRange(hist.getG());
		int[] rangeB = getRange(hist.getB());
		int fatorDivisaoR = (rangeR[1] - rangeR[0]) / 3;
		int fatorDivisaoG = (rangeG[1] - rangeG[0]) / 3;
		int fatorDivisaoB = (rangeB[1] - rangeB[0]) / 3;
		int[] histg = hist.getR();
		int maxR1 = getMaxColor(rangeR[0], Arrays.copyOfRange(histg, rangeR[0], fatorDivisaoR));
		int maxR2 = getMaxColor(fatorDivisaoR, Arrays.copyOfRange(histg, fatorDivisaoR, fatorDivisaoR * 2));
		int maxR3 = getMaxColor(fatorDivisaoR * 2, Arrays.copyOfRange(histg, fatorDivisaoR * 2, rangeR[1]));
		histg = hist.getG();
		int maxG1 = getMaxColor(rangeG[0], Arrays.copyOfRange(histg, rangeG[0], fatorDivisaoG));
		int maxG2 = getMaxColor(fatorDivisaoG, Arrays.copyOfRange(histg, fatorDivisaoG, fatorDivisaoG * 2));
		int maxG3 = getMaxColor(fatorDivisaoG * 2, Arrays.copyOfRange(histg, fatorDivisaoG * 2, rangeG[1]));
		histg = hist.getB();
		int maxB1 = getMaxColor(rangeB[0], Arrays.copyOfRange(histg, rangeB[0], fatorDivisaoB));
		int maxB2 = getMaxColor(fatorDivisaoB, Arrays.copyOfRange(histg, fatorDivisaoB, fatorDivisaoB * 2));
		int maxB3 = getMaxColor(fatorDivisaoB * 2, Arrays.copyOfRange(histg, fatorDivisaoB * 2, rangeB[1]));
		Color c1 = new Color(maxR1 / 255d, maxG1 / 255d, maxB1 / 255d, 1);
		Color c2 = new Color(maxR2 / 255d, maxG2 / 255d, maxB2 / 255d, 1);
		Color c3 = new Color(maxR3 / 255d, maxG3 / 255d, maxB3 / 255d, 1);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color color = pr.getColor(i, j);
				Color x = getDifColors2(color, c1, c2, c3);
				pw.setColor(i, j, x);
			}
		}
		return deNoise(wi, Pixel.VIZ_CRUZ, Pixel.MEDIANA);
	}
	
	private static int getMaxColor(int start, int[] bloco) {
		int max = 0;
		int col = 0;
		for (int i = 0; i < bloco.length; i++) {
			if (bloco[i] > max) {
				max = bloco[i];
				col = i;
			}
		}
		return start + col;
	}
	
	private static Color getDifColors2(Color c1, Color... colors) {
		int index = -1;
		double diference = Integer.MAX_VALUE;
		Color c;
		for (int i = 0; i < colors.length; i++) {
			c = colors[i];
			double calc = (c1.getRed() + c1.getGreen() + c1.getBlue()) - (c.getRed() + c.getGreen() + c.getBlue());
			calc = calc < 0 ? -calc : calc;
			if (diference > calc) { 
				diference = calc;
				index = i;
			}
		}
		return colors[index];
	}
	
	private static int[] getRange(int[] histI) {
		int start = 0, finish = 254;
		while (histI[start] == 0) {
			start++;
		}
		while (histI[finish] == 0) {
			finish--;
		}
		return new int[] {start, finish};
	}
	
	public static Image geraImagemComBorda(Image img, int borderSize, boolean interna, Color borderColor) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		if (!interna) {
			w += borderSize * 2;
			h += borderSize * 2;
		}
		WritableImage wi = new WritableImage(interna ? w : w + borderSize * 2, interna ? h : h + borderSize * 2);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (i <= borderSize || j <= borderSize || i >= w - borderSize || j >= h - borderSize) {
					pw.setColor(i, j, borderColor);
				} else {
					if (interna) {
						pw.setColor(i, j, pr.getColor(i, j));
					} else {
						pw.setColor(i, j, pr.getColor(i - borderSize, j - borderSize));
					}
				}
			}
		}
		return wi;
	}
	
	public static Image adicao(Image img1, Image img2, int percIm1, int percIm2) {
		int w = Math.min((int)img1.getWidth(), (int)img1.getHeight());
		int h = Math.min((int)img2.getWidth(), (int)img2.getHeight());
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr1 = img1.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		Color c1, c2;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				c1 = pr1.getColor(i, j);
				c2 = pr2.getColor(i, j);
				pw.setColor(i, j, new Color((c1.getRed()*(percIm1/100d)) + (c2.getRed()*(percIm2/100d)), (c1.getGreen()*(percIm1/100d)) + (c2.getGreen()*(percIm2/100d)), (c1.getBlue()*(percIm1/100d)) + (c2.getBlue()*(percIm2/100d)), c1.getOpacity() > c2.getOpacity() ? c1.getOpacity() : c2.getOpacity()));
			}
		}
		return wi;
	}
	
	public static Image subtracao(Image img1, Image img2) {
		int w = Math.min((int)img1.getWidth(), (int)img1.getHeight());
		int h = Math.min((int)img2.getWidth(), (int)img2.getHeight());
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr1 = img1.getPixelReader();
		PixelReader pr2 = img2.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		Color c1, c2;
		double r,g,b;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				c1 = pr1.getColor(i, j);
				c2 = pr2.getColor(i, j);
				r = c1.getRed() - c2.getRed();
				g = c1.getGreen() - c2.getGreen();
				b = c1.getBlue() - c2.getBlue();
				pw.setColor(i, j, new Color(r < 0 ? -r : r, g < 0 ? -g : g, b < 0 ? -b : b, c1.getOpacity()));
			}
		}
		return wi;
	}
	
	public static Image desenha(Image img, int x1, int y1, int x2, int y2) {
		int w = (int) img.getWidth();
		int h = (int) img.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (((j == y1 || j == y2) && (i >= x1 && i <= x2)) || ((i == x1 || i == x2) && (j >= y1 && j <= y2))) {
					pw.setColor(i, j, Color.BLACK);
				} else {
					pw.setColor(i, j, pr.getColor(i, j));
				}
			}
		}
		return wi;
	}
	
	public static Image giraImagem(Image img, boolean reverse) {
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		WritableImage wi = new WritableImage(h, w);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		if (reverse) {
			for (int i = w - 1; i > 0; i--) {
				for (int j = h - 1; j > 0; j--) {
					pw.setColor(j, i, pr.getColor((w - 1) - i, (h - 1) - j));
				}
			}
		} else {
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					pw.setColor(j, i, pr.getColor(i, j));
				}
			}
		}
		return wi;
	}

	public static Image desafio2(Image img) {
		int h = (int)img.getHeight();
		int w = (int)img.getWidth();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();
		int h2 = h/2;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h2; j++) {
				Color src = pr.getColor(i, j);
				pw.setColor(i, j, new Color(1 - src.getRed(), 1 - src.getGreen(), 1 - src.getBlue(), src.getOpacity()));
			}
		}
		for (int i = 0; i < w; i++) {
			for (int j = h2; j < h; j++) {
				Color src = pr.getColor(i, j);
				double mediaCor = (src.getRed() + src.getGreen() + src.getBlue()) / 3;
				pw.setColor(i, j, new Color(mediaCor, mediaCor, mediaCor, src.getOpacity()));
			}
		}
		return wi;
	}
	
}
