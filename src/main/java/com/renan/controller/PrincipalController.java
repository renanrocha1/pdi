package com.renan.controller;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import com.renan.domain.*;
import com.renan.pdi.*;
import com.renan.util.*;

import javafx.embed.swing.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.stage.FileChooser.*;

public class PrincipalController {

	// private enum ImageNum {
	// IMAGEM1,
	// IMAGEM2,
	// IMAGEM3;
	//
	// private Image image;
	//
	// public void setImage(Image i) {
	// image = i;
	// }
	//
	// public Image getImage() {
	// return image;
	// }
	// }

	private static Image imagem1;
	private static Image imagem2;
	private static Image imagem3;

	public static Image getImagem1() {
		return imagem1;
	}

	public static void setImagem1(Image imagem1) {
		PrincipalController.imagem1 = imagem1;
	}

	public static Image getImagem2() {
		return imagem2;
	}

	public static void setImagem2(Image imagem2) {
		PrincipalController.imagem2 = imagem2;
	}

	public static Image getImagem3() {
		return imagem3;
	}

	public static void setImagem3(Image imagem3) {
		PrincipalController.imagem3 = imagem3;
	}

	@FXML
	Label lblR;
	@FXML
	Label lblG;
	@FXML
	Label lblB;
	@FXML
	ImageView imgV1;
	@FXML
	ImageView imgV2;
	@FXML
	ImageView imgV3;
	@FXML
	TextField txtR;
	@FXML
	TextField txtG;
	@FXML
	TextField txtB;
	@FXML
	Button btOk;
	@FXML
	Slider sliderLimiar;
	@FXML
	RadioButton rdCruz;
	@FXML
	RadioButton rdX;
	@FXML
	RadioButton rd3;

	private File selecionaImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.add(new ExtensionFilter("Imagens", "*.jpg", "*.JPG", "*.png", "*.PNG", "*.bmp", "*.BMP"));
		fileChooser.setInitialDirectory(new File("C:\\Users\\Renan\\Pictures"));
		return fileChooser.showOpenDialog(null);
	}

	@FXML
	public void teste() {
		Image img = Pdi.exemploModificarPixel(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}

	// private void atualizaImg3() {
	// imgV3.setImage(ImageNum.IMAGEM3.getImage());
	// imgV3.setFitHeight(ImageNum.IMAGEM3.getImage().getHeight());
	// imgV3.setFitWidth(ImageNum.IMAGEM3.getImage().getWidth());
	// }

	@FXML
	public void escalaCinzaMediaArit() {
		Image img = Pdi.escalaCinzaPorMediaAritmetica(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}

	@FXML
	public void habilitaMediaPonderada() {
		txtR.setDisable(false);
		txtG.setDisable(false);
		txtB.setDisable(false);
		btOk.setDisable(false);
	}

	@FXML
	public void escalaCinzaMediaPonderada() {
		Image img = Pdi.escalaCinzaPorMediaPonderada(imagem1, Integer.parseInt(txtR.getText()),
				Integer.parseInt(txtG.getText()), Integer.parseInt(txtB.getText()));
		abreImage(imgV3, img);
		setImagem3(img);
	}

	@FXML
	public void limiar() {
		Image imgGreyscale = Pdi.escalaCinzaPorMediaAritmetica(imagem1);
		Image img = Pdi.limiar(imgGreyscale, sliderLimiar.getValue());
		abreImage(imgV3, img);
		setImagem3(img);
	}

	@FXML
	public void negativaBIRL() {
		Image img = Pdi.negativa(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}

	@FXML
	public void rasterImg(MouseEvent evt) {
		ImageView iv = (ImageView) evt.getTarget();
		if (iv.getImage() != null) {
			verificaCor(iv.getImage(), (int) evt.getX(), (int) evt.getY());
		}
	}

	@FXML
	public void desafio1() {
		Image img = Pdi.desafio(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void deNoiseMedia() {
		Image img = Pdi.deNoise(imagem1, getTipoVizinhanca(), Pixel.MEDIA);
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void deNoiseMediana() {
		Image img = Pdi.deNoise(imagem1, getTipoVizinhanca(), Pixel.MEDIANA);
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	private int getTipoVizinhanca() {
		if (rdX.isSelected()) {
			return Pixel.VIZ_X;
		} else if (rdCruz.isSelected()) {
			return Pixel.VIZ_CRUZ;
		} else if (rd3.isSelected()) {
			return Pixel.VIZ_3x3;
		}
		return 0;
	}

	@FXML
	public void abreImg1() {
		Image img = abreImg(imgV1);
		if (img != null) {
			setImagem1(img);
		}
	}

	@FXML
	public void abreImg2() {
		Image img = abreImg(imgV2);
		if (img != null) {
			setImagem2(img);
		}
	}

	@FXML
	public void salvar() {
		if (imagem3 != null) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters()
					.add(new ExtensionFilter("Imagens", "*.jpg", "*.JPG", "*.png", "*.PNG", "*.bmp", "*.BMP"));
			fileChooser.setInitialDirectory(new File("C:\\Users\\Renan\\Pictures"));
			File f = fileChooser.showSaveDialog(null);
			if (f != null) {
				BufferedImage img = SwingFXUtils.fromFXImage(imagem3, null);
				try {
					ImageIO.write(img, "PNG", f);
					MsgUtil.exibeMsgInfo("salvar_imagem", "salvar_sucesso", null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				MsgUtil.exibeMsgErro("salvar_imagem", "salvar_erro", "salvar_erro.nada_selecionado");
			}
		}
	}
	
	@FXML
	public void geraHistograma(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HistogramaModal.fxml"));
			Parent root =  fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.setTitle(MsgUtil.getMessage("histograma_title"));
//			stage.initModality(Modality.WINDOW_MODAL); para n�o deixar usuario fazer a��o na tela debaixo
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.show();
			
			HistogramaModalController histogramaController = fxmlLoader.getController();
			if (imagem1 != null) {
				Pdi.setGrafico(imagem1, histogramaController.hist1);
			}
			if (imagem2 != null) {
				Pdi.setGrafico(imagem2, histogramaController.hist2);
			}
			if (imagem3 != null) {
				Pdi.setGrafico(imagem3, histogramaController.hist3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void geraImagemEqualizada() {
		Image img = Pdi.geraImagemEqualizada(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}

	private Image abreImg(ImageView imgV) {
		File f = selecionaImagem();
		Image i = null;
		if (f != null) {
			abreImage(imgV, i = new Image(f.toURI().toString()));
		}
		return i;
	}

	private void abreImage(ImageView imgV, Image img) {
		imgV.setImage(img);
		imgV.setFitHeight(img.getHeight());
		imgV.setFitWidth(img.getWidth());
	}

	private void verificaCor(Image img, int x, int y) {
		try {
			Color cor = img.getPixelReader().getColor(x, y);
			lblR.setText("R: " + (int) (cor.getRed() * 255));
			lblG.setText("G: " + (int) (cor.getGreen() * 255));
			lblB.setText("B: " + (int) (cor.getBlue() * 255));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}