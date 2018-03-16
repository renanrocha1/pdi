package view;

import java.math.*;

import javafx.fxml.*;
import javafx.scene.control.*;

public class ImcController {
	
	@FXML TextField txtPeso;
	@FXML TextField txtAltura;
	@FXML Label lbResultado;
	
	@FXML
	public void calcular() {
		double peso = Double.parseDouble(txtPeso.getText());
		double altura = Double.parseDouble(txtAltura.getText());
		BigDecimal bd = new BigDecimal((peso/(altura*altura)));
		lbResultado.setText(String.valueOf(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
	}

}
