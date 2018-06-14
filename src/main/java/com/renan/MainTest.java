package com.renan;

import java.sql.*;

import org.opencv.core.*;

public class MainTest {
	
	public static void main(String[] args) throws SQLException {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
		System.out.println( "mat = " + mat.dump() );
	}

}
