public class ComputerDriver {
	public static void main(String [] args) throws Exception{
		System.out.println("人側が黒、難易度hard、でComputerクラスのインスタンスを生成");
		Computer computer = new Computer("black","hard");
		System.out.println("人側が19(上から3行目・左から4列目)に打つ");
		System.out.println("get_select(19)出力: " + computer.get_select(19) + "\n\n");
		
		System.out.println("人側が黒、難易度normal、でComputerクラスのインスタンスを生成");
		computer = new Computer("black","normal");
		System.out.println("人側が19(上から3行目・左から4列目)に打つ");
		System.out.println("get_select(19)出力: " + computer.get_select(19) + "\n\n");
		
		System.out.println("人側が黒、難易度easy、でComputerクラスのインスタンスを生成");
		computer = new Computer("black","easy");
		System.out.println("人側が19(上から3行目・左から4列目)に打つ");
		System.out.println("get_select(19)出力: " + computer.get_select(19) + "\n\n");
		
		System.out.println("人側が白、難易度hard、でComputerクラスのインスタンスを生成");
		computer = new Computer("white","hard");
		System.out.println("人側が後手のため最初はパスをするという形で呼び出す");
		System.out.println("get_select(-1)出力: " + computer.get_select(-1) + "\n");
		System.out.println("hardの場合CPUは19に置くため、人側は18(上から3行目・左から3列目)に置いてみる");
		System.out.println("get_select(18)出力: " + computer.get_select(18) + "\n\n");
		
		System.out.println("人側が白、難易度normal、でComputerクラスのインスタンスを生成");
		computer = new Computer("white","normal");
		System.out.println("人側が後手のため最初はパスをするという形で呼び出す");
		System.out.println("get_select(-1)出力: " + computer.get_select(-1) + "\n\n");
		
		System.out.println("人側が白、難易度easy、でComputerクラスのインスタンスを生成");
		computer = new Computer("white","easy");
		System.out.println("人側が後手のため最初はパスをするという形で呼び出す");
		System.out.println("get_select(-1)出力: " + computer.get_select(-1) + "\n");
		System.out.println("easyの場合CPUは19に置くため、人側は18(上から3行目・左から3列目)に置いてみる");
		System.out.println("get_select(18)出力: " + computer.get_select(18) + "\n\n");
	}
}

