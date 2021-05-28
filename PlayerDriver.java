public class PlayerDriver {
	public static void main(String [] args) throws Exception{
		Player player = new Player();
		System.out.println("setColorで「black」を入力します");
		player.setColor("black");
		System.out.println("getColor出力: " + player.getColor());
	}
}