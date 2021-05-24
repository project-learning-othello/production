import java.awt.Color;

public class Option {
	private String StoneImage_w, StoneImage_b;
	private Color backColor;
	private boolean normalFlag, fiveFlag, starFlag;
	private boolean greenFlag, blueFlag, pinkFlag;

	public Option(){
		StoneImage_w = "White.jpg";
		StoneImage_b = "Black.jpg";

		backColor = new Color(0, 153, 0);

		normalFlag = true;
		fiveFlag = false;
		starFlag = false;

		greenFlag = true;
		pinkFlag = false;
		blueFlag = false;
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
		normalFlag = true;
		fiveFlag = false;
		starFlag = false;
	}

	public void changeToFive() {
		StoneImage_w = "Five_W.jpg";
		StoneImage_b = "Five_B.jpg";
		normalFlag = false;
		fiveFlag = true;
		starFlag = false;
	}

	public void changeToStar() {
		StoneImage_w = "Star_W.jpg";
		StoneImage_b = "Star_B.jpg";
		normalFlag = false;
		fiveFlag = false;
		starFlag = true;
	}

	public void changeToGreen() {
		backColor = new Color(0, 153, 0);
		greenFlag = true;
		pinkFlag = false;
		blueFlag = false;
	}

	public void changeToPink() {
		backColor = new Color(255, 153, 255);
		greenFlag = false;
		pinkFlag = true;
		blueFlag = false;
	}

	public void changeToBlue() {
		backColor = new Color(0, 204, 255);
		greenFlag = false;
		pinkFlag = false;
		blueFlag = true;
	}

	public boolean getFlag(String str) {
		switch(str) {
		case "normal":
			return normalFlag;

		case "five":
			return fiveFlag;

		case "star":
			return starFlag;

		case "green":
			return greenFlag;

		case "pink":
			return pinkFlag;

		case "blue":
			return blueFlag;

		default:
			return false;
		}
	}
}