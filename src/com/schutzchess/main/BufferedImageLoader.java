package com.schutzchess.main;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	BufferedImage img;

	public BufferedImage loadImage(String path) {
		try {
			img = ImageIO.read(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

}
