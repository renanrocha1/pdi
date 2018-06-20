package com.renan.controller;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;

import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.*;
import org.opencv.objdetect.*;

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
	private static boolean reverse;
	
	private static int x1, y1, x2, y2;
	
	private File f;

	public static Image getImagem1() {
		return imagem1;
	}

	private static void setImagem1(Image imagem1) {
		PrincipalController.imagem1 = imagem1;
	}

	public static Image getImagem2() {
		return imagem2;
	}

	private static void setImagem2(Image imagem2) {
		PrincipalController.imagem2 = imagem2;
	}

	public static Image getImagem3() {
		return imagem3;
	}

	private static void setImagem3(Image imagem3) {
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
	@FXML
	TextField txtAdd1;
	@FXML
	TextField txtAdd2;
	@FXML
	ColorPicker cPicker;
	@FXML
	TextField txtBorda;
	@FXML
	CheckBox ckInterno;
	@FXML
	TextField txtDist;
	@FXML
	ColorPicker colP;

	private File selecionaImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.add(new ExtensionFilter("Imagens", "*.jpg", "*.JPG", "*.png", "*.PNG", "*.bmp", "*.BMP"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Pictures"));
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
	public void clicou(MouseEvent event) {
		x1 = (int)event.getX();
		y1 = (int)event.getY();
	}
	
	@FXML
	public void soltou(MouseEvent event) {
		x2 = (int)event.getX();
		y2 = (int)event.getY();
		int x0 = x1 > x2 ? x2 : x1;
		int xf = x1 < x2 ? x2 : x1;
		int y0 = y1 > y2 ? y2 : y1;
		int yf = y1 < y2 ? y2 : y1;
		HashSet<String> cores = new HashSet<>(3);
		Image img = Pdi.desenha(imagem1, x0, y0, xf, yf, cores);
		abreImage(imgV3, img);
		setImagem3(img);
		MsgUtil.exibeMsgInfo("title_q3", "cores_selecionadas", cores.toArray(new String[cores.size()]));
	}
	
	@FXML
	public void soltouTomVermelho(MouseEvent event) {
		x1 = (int)event.getX();
		x2 = (int)event.getY();
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
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Pictures"));
			File f = fileChooser.showSaveDialog(null);
			if (f != null) {
				BufferedImage img = SwingFXUtils.fromFXImage(imagem3, null);
				try {
					ImageIO.write(img, "PNG", f);
					MsgUtil.exibeMsgInfo("salvar_imagem", "salvar_sucesso", "");
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
	public void geraImagemEqualizada() {
		Image img = Pdi.geraImagemEqualizada(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void geraImagemSegmentada() {
		Image img = Pdi.geraImagemSegmentada(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void geraImgAdicionada() {
		Image img = Pdi.adicao(imagem1, imagem2, Integer.valueOf(txtAdd1.getText()), Integer.valueOf(txtAdd2.getText()));
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void geraImgSubtraida() {
		Image img = Pdi.subtracao(imagem1, imagem2);
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void geraImgComBorda() {
		Image img = Pdi.geraImagemComBorda(imagem1, Integer.valueOf(txtBorda.getText()), ckInterno.isSelected(), cPicker.getValue());
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void giraImg() {
		Image img = Pdi.giraImagem(imagem3 == null ? imagem1 : imagem3, reverse);
		reverse = !reverse;
		abreImage(imgV3, img);
		setImagem3(img);
	}

	@FXML
	public void desafio3() {
		Image img = Pdi.desafio2(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}

	@FXML
	public void isQuadrado() {
		boolean isQuadrado = Pdi.contemQuadrado(imagem1);
		MsgUtil.exibeMsgInfo("title_figura", "tipo_quadrado", isQuadrado ? "msg_e_quadrado" : "msg_nao_quadrado");
	}
	
	@FXML
	public void isCirculo() {
		boolean isCirculo = Pdi.isCirculo(imagem1);
		MsgUtil.exibeMsgInfo("title_figura", "tipo_circulo", isCirculo ? "msg_e_circulo" : "msg_nao_circulo");
	}

	@FXML
	public void aumentaImg() {
		Image img = Pdi.aumentaImagem(imagem1);
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void provaQuestao1() {
		int dist = 0;
		try {
			dist = Integer.valueOf(txtDist.getText());
		} catch (Exception e) {
			MsgUtil.exibeMsgErro("title_erro", "sub_questao1", "dist_nao_selecionada");
			return;
		}
		if (imagem1 == null) {
			MsgUtil.exibeMsgErro("title_erro", "sub_questao1", "img_nao_selecionada");
		} else {
			Image img = Pdi.gradeVertical(imagem1, dist, colP.getValue());
			abreImage(imgV3, img);
			setImagem3(img);
		}
	}
	
	@FXML
	public void provaQuestao2() {
		if (imagem1 == null) {
			MsgUtil.exibeMsgErro("title_erro", "sub_questao1", "img_nao_selecionada");
		} else {
			Image img = Pdi.inverteMetadeInferior(imagem1);
			abreImage(imgV3, img);
			setImagem3(imagem3);
		}
	}
	
	@FXML
	public void identificaRostos() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		System.out.println("Detected " + faceDetections.toArray().length + " faces");
		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
		}
		MatOfByte mtb =  new MatOfByte();
		Imgcodecs.imencode(".png", image, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		abreImage(imgV3, imagem3);
	}
	
	@FXML
	public void dilata() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		Mat output = new Mat(image.rows(), image.cols(), image.type());
		Imgproc.dilate(image, output, new Mat(), new Point(-1, -1), 2);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", output, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		abreImage(imgV3, imagem3);
	}
	
	@FXML
	public void erosao() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(f.getAbsolutePath());
		Mat output = new Mat(image.rows(), image.cols(), image.type());
		Imgproc.erode(image, output, new Mat(), new Point(-1, -1), 2);
		MatOfByte mtb = new MatOfByte();
		Imgcodecs.imencode(".png", output, mtb);
		imagem3 = new Image(new ByteArrayInputStream(mtb.toArray()));
		abreImage(imgV3, imagem3);
	}
	
	@FXML
	public void cannyBorda() {
		Image img = Pdi.cannyBorda(f.getAbsolutePath());
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void laplaceBorda() {
		Image img = Pdi.laplaceBorda(f.getAbsolutePath());
		abreImage(imgV3, img);
		setImagem3(img);
	}
	
	@FXML
	public void testeTrabalho3() throws IOException {
		Image img = Pdi.transformadaDeHough(imagem1 , f.getAbsolutePath());
		abreImage(imgV3, img);
		setImagem3(img);
	}

	private Image abreImg(ImageView imgV) {
		f = selecionaImagem();
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
