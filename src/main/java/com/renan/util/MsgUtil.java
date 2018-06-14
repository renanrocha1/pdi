package com.renan.util;

import java.util.*;

import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

public final class MsgUtil {
	
	private MsgUtil() {}
	
	private static ResourceBundle getResourceBundle() {
		Locale l = new Locale("pt", "BR");
		return ResourceBundle.getBundle("MessageBundle", l);
	}
	
	public static void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alertWindow = new Alert(tipo);
		alertWindow.setTitle(titulo);
		alertWindow.setContentText(msg);
		alertWindow.setHeaderText(cabecalho);
		alertWindow.show();
	}
	
	public static void exibeMsgErro(String keyTitulo, String keyCabecalho, String keyMsg) {
		ResourceBundle rb = getResourceBundle();
		exibeMsg(rb.getString(keyTitulo), rb.getString(keyCabecalho), rb.getString(keyMsg), AlertType.ERROR);
	}
	
	public static void exibeMsgInfo(String keyTitulo, String keyCabecalho, String... keyMsg) {
		ResourceBundle rb = getResourceBundle();
		exibeMsg(rb.getString(keyTitulo), rb.getString(keyCabecalho), getContentText(keyMsg), AlertType.INFORMATION);
	}
	
	private static String getContentText(String... keys) {
		ResourceBundle rb = getResourceBundle();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != "") {
				sb.append(rb.getString(keys[i])).append(" ");
			}
		}
		return sb.toString();
	}
	
	private static void exibeMsgConfirm(String keyTitulo, String keyCabecalho, String keyMsg) {
		// TODO pesquisar retorno
		Alert alertWindow = new Alert(AlertType.CONFIRMATION);
		alertWindow.show();
	}
	
	public static void exibePlainMsg(String keyTitulo, String keyCabecalho, String keyMsg) {
		ResourceBundle rb = getResourceBundle();
		exibeMsg(rb.getString(keyTitulo), rb.getString(keyCabecalho), rb.getString(keyMsg), AlertType.NONE);
	}
	
	public static String getMessage(String key) {
		return getResourceBundle().getString(key);
	}

}
