import java.awt.Color;

public class Option {
	private String StoneImage_w, StoneImage_b;
	private Color backColor;

	public Option(){
		StoneImage_w = "White.jpg";
		StoneImage_b = "Black.jpg";

		backColor = new Color(0, 153, 0);
	}

	public String getWhiteImage(){
		return StoneImage_w;
	}

	public String getBlackImage(){
		return StoneImage_b;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void changeToNormal() {
		StoneImage_w = "White.jpg";
		StoneImage_b = "Black.jpg";
	}

	public void changeToFive() {
		StoneImage_w = "Five_W.jpg";
		StoneImage_b = "Five_B.jpg";
	}

	public void changeToStar() {
		StoneImage_w = "Star_W.jpg";
		StoneImage_b = "Star_B.jpg";
	}

	public void changeToGreen() {
		backColor = new Color(0, 153, 0);
	}

	public void changeToBlue() {
		backColor = new Color(0, 204, 255);
	}

	public void changeToPink() {
		backColor = new Color(255, 153, 255);
	}
}