package com.renan.domain;

public class Histograma {
	
	private int[] r;
	private int[] g;
	private int[] b;
	private int[] intensidade;
	private int[] acumuladoI, acumuladoR, acumuladoG, acumuladoB;
	private int qtR, qtG, qtB;
	
	public Histograma(int[]... canais) {
		r = canais[0];
		g = canais[1];
		b = canais[2];
		intensidade = canais[3];
	}
	
	public int[] getR() {
		return r;
	}
	
	public void setR(int[] r) {
		this.r = r;
	}
	
	public int[] getG() {
		return g;
	}
	
	public void setG(int[] g) {
		this.g = g;
	}
	
	public int[] getB() {
		return b;
	}
	
	public void setB(int[] b) {
		this.b = b;
	}

	public int[] getIntensidade() {
		return intensidade;
	}

	public void setIntensidade(int[] intensidade) {
		this.intensidade = intensidade;
	}
	
	public int[] getAcumuladoI() {
		return acumuladoI;
	}

	public void setAcumuladoI(int[] acumuladoI) {
		this.acumuladoI = acumuladoI;
	}

	public int[] getAcumuladoR() {
		return acumuladoR;
	}

	public void setAcumuladoR(int[] acumuladoR) {
		this.acumuladoR = acumuladoR;
	}

	public int[] getAcumuladoG() {
		return acumuladoG;
	}

	public void setAcumuladoG(int[] acumuladoG) {
		this.acumuladoG = acumuladoG;
	}

	public int[] getAcumuladoB() {
		return acumuladoB;
	}

	public void setAcumuladoB(int[] acumuladoB) {
		this.acumuladoB = acumuladoB;
	}
	
	public int getQtR() {
		return qtR;
	}

	public void setQtR(int qtR) {
		this.qtR = qtR;
	}

	public int getQtG() {
		return qtG;
	}

	public void setQtG(int qtG) {
		this.qtG = qtG;
	}

	public int getQtB() {
		return qtB;
	}

	public void setQtB(int qtB) {
		this.qtB = qtB;
	}

	public void geraHistogramaAcumuladoI() {
		geraHistogramaAcumulado(intensidade, -1);
	}
	
	public void geraHistogramaAcumuladoR() {
		geraHistogramaAcumulado(r, Pixel.COR_R);
	}
	
	public void geraHistogramaAcumuladoG() {
		geraHistogramaAcumulado(g, Pixel.COR_G);
	}
	
	public void geraHistogramaAcumuladoB() {
		geraHistogramaAcumulado(b, Pixel.COR_B);
	}
	
	public void geraHistRGB() {
		geraHistogramaAcumuladoR();
		geraHistogramaAcumuladoG();
		geraHistogramaAcumuladoB();
	}
	
	private void geraHistogramaAcumulado(int[] src, int color) {
		int[] acumulado = new int[256];
		acumulado[0] = src[0];
		int qtCor = 0;
		for (int i = 1; i < 256; i++) {
			acumulado[i] = acumulado[i - 1] + src[i];
			if (src[i] > 0) {
				qtCor++;
			}
		}
		switch (color) {
		case Pixel.COR_R:
			qtR = qtCor;
			setAcumuladoR(acumulado);
			break;
		case Pixel.COR_G:
			qtG = qtCor;
			setAcumuladoG(acumulado);
			break;
		case Pixel.COR_B:
			qtB = qtCor;
			setAcumuladoB(acumulado);
			break;
		default:
			setAcumuladoI(acumulado);
			break;
		}
	}
	
}
