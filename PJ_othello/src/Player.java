public class Player {

	private String myColor; //先手後手情報(白黒)

	// メソッド
	public void setColor(String c){ // 先手後手情報の受付
		myColor = c;
	}
	public String getColor(){ // 先手後手情報の取得
		return myColor;
	}
}