package com.renan.domain;

import java.util.*;

import javafx.scene.paint.*;

public class Pixel {
	
	public static final int VIZ_3x3 = 1;
	public static final int VIZ_CRUZ = 2;
	public static final int	VIZ_X = 3;
	
	public static final int COR_R = 1;
	public static final int COR_G = 2;
	public static final int COR_B = 3;
	
	public static final int MEDIA = 1;
	public static final int MEDIANA = 2;
	
	private Color color, deNoisedColor;
	private int i, j;
	private ArrayList<Pixel> viz3;
	private ArrayList<Pixel> vizCruz;
	private ArrayList<Pixel> vizX;
	
	public Pixel() {}
	
	public Pixel(int x, int y) {
		this.i = x;
		this.j = y;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public ArrayList<Pixel> getViz3() {
		return viz3;
	}
	
	public void setViz3(ArrayList<Pixel> viz3) {
		this.viz3 = viz3;
	}
	
	public ArrayList<Pixel> getVizCruz() {
		return vizCruz;
	}
	
	public void setVizCruz(ArrayList<Pixel> vizCruz) {
		this.vizCruz = vizCruz;
	}
	
	public ArrayList<Pixel> getVizX() {
		return vizX;
	}
	
	public void setVizX(ArrayList<Pixel> vizX) {
		this.vizX = vizX;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	public Color getMedia(int tipo, double opacity) {
		double somaR, somaG, somaB;
		somaR = somaG = somaB = 0d;
		List<Pixel> pixelList = getPixelArray(tipo);
		for (Pixel pixel : pixelList) {
			Color color = pixel.getColor();
			somaR += color.getRed();
			somaG += color.getGreen();
			somaB += color.getBlue();
		}
		int size = pixelList.size();
		return deNoisedColor = new Color(somaR/size, somaG/size, somaB/size, opacity);
	}

	private List<Pixel> getPixelArray(int tipo) {
		List<Pixel> pixelList = null;
		switch (tipo) {
		case VIZ_3x3:
			pixelList = viz3;
			break;
		case VIZ_CRUZ:
			pixelList = vizCruz;
			break;
		case VIZ_X:
			pixelList = vizX;
			break;
		}
		pixelList.add(this);
		return pixelList;
	}
	
	public Color getMediana(int tipo, double opacity) {
		List<Pixel> pixelList = getPixelArray(tipo);
		int size = pixelList.size();
		double medianaR, medianaB, medianaG;
		medianaR = medianaG = medianaB = 0d;
		pixelList.sort(getComparator(COR_R));
		medianaR = pixelList.get(size / 2).getColor().getRed();
		pixelList.sort(getComparator(COR_G));
		medianaG = pixelList.get(size / 2).getColor().getGreen();
		pixelList.sort(getComparator(COR_B));
		medianaB = pixelList.get(size / 2).getColor().getBlue();
		return deNoisedColor = new Color(medianaR, medianaG, medianaB, opacity);
	}
	
	private Comparator<Pixel> getComparator(int color) {
		switch (color) {
		case COR_R:
			return new Comparator<Pixel>() {
				@Override
				public int compare(Pixel o1, Pixel o2) {
					if (o1.getColor().getRed() > o2.getColor().getRed()) {
						return 1;
					} else if (o2.getColor().getRed() > o1.getColor().getRed()) {
						return -1;
					}
					return 0;
				}
			};

		case COR_B:
			return new Comparator<Pixel>() {
				@Override
				public int compare(Pixel o1, Pixel o2) {
					if (o1.getColor().getBlue() > o2.getColor().getBlue()) {
						return 1;
					} else if (o2.getColor().getBlue() > o1.getColor().getBlue()) {
						return -1;
					}
					return 0;
				}
			};
		case COR_G:
			return new Comparator<Pixel>() {
				@Override
				public int compare(Pixel o1, Pixel o2) {
					if (o1.getColor().getGreen() > o2.getColor().getGreen()) {
						return 1;
					} else if (o2.getColor().getGreen() > o1.getColor().getGreen()) {
						return -1;
					}
					return 0;
				}
			};
		}
		return null;
	}
	
	
}
