
public class OptionDriver {
	public static void main(String [] args) throws Exception{
		Option option= new Option();
		System.out.println("コマのイラストと背景の色の初期値");
		System.out.println("getWhiteImage出力: " + option.getWhiteImage());
		System.out.println("getBlackImage出力: " + option.getBlackImage());
		System.out.println("getBackColor出力 : " + option.getBackColor());

		System.out.println("changeToFiveでコマのイラストを変更します。");
		option.changeToFive();
		System.out.println("getWhiteImage出力: " + option.getWhiteImage());
		System.out.println("getBlackImage出力: " + option.getBlackImage());

		System.out.println("changeToStarでコマのイラストを変更します。");
		option.changeToStar();
		System.out.println("getWhiteImage出力: " + option.getWhiteImage());
		System.out.println("getBlackImage出力: " + option.getBlackImage());

		System.out.println("changeToNormalでコマのイラストを変更します。");
		option.changeToNormal();
		System.out.println("getWhiteImage出力: " + option.getWhiteImage());
		System.out.println("getBlackImage出力: " + option.getBlackImage());

		System.out.println("changeToPinkで背景の色をを変更します。");
		option.changeToPink();
		System.out.println("getBackColor出力 : " + option.getBackColor());

		System.out.println("changeToBlueで背景の色をを変更します。");
		option.changeToBlue();
		System.out.println("getBackColor出力 : " + option.getBackColor());

		System.out.println("changeToGreenで背景の色をを変更します。");
		option.changeToGreen();
		System.out.println("getBackColor出力 : " + option.getBackColor());
	}

}
