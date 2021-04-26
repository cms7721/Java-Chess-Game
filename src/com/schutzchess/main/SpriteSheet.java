package com.schutzchess.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage sprite;

	public SpriteSheet(BufferedImage sheet) {
		this.sprite = sheet;
	}

	public BufferedImage getImage(int row, int col, int height, int width) {
		BufferedImage img = sprite.getSubimage(row * RenderObject.IMAGE_SIDE - RenderObject.IMAGE_SIDE,
				col * RenderObject.IMAGE_SIDE - RenderObject.IMAGE_SIDE, width, height);
		return img;
	}

}
