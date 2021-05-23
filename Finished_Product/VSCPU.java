import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class VSCPU extends JFrame implements MouseListener {
	private JButton buttonArray[];// オセロ盤用のボタン配列
	private JLabel my_stone_num, opp_stone_num; // 駒数表示用の自分・相手の駒表示
	private JLabel my_num, opp_num;// 自分・相手の駒数表示
	private JLabel my_nameLabel, opp_nameLabel; // 自分・相手の手番表示
	private JLabel my_stone, opp_stone; // 自分・相手の駒表示
	private Font myFont;
	private Container c; // コンテナ
	private ImageIcon blackIcon, whiteIcon, boardIcon, putIcon; // アイコン
	private ImageIcon myIcon, oppIcon; // 自分・相手のアイコン
	private PrintWriter out;//データ送信用オブジェクト
	//private Receiver receiver; //データ受信用オブジェクト
	private Othello game; // Othelloオブジェクト
	private Player player;// Playerオブジェクト
	private Option option;
	private Computer cpu;
	private String [] grids;
	private int row; //オセロ盤の縦横マスの数
	private String color, opp_color; // 先手後手情報
	private boolean flag; // 初期設定用フラグ;
	private String mode;
	private int cpu_place;

	// コンストラクタ
	public VSCPU(Othello game, Player player, Option option, Computer cpu, ModalityType mt) {
		this.game = game; // 引数のOthelloオブジェクトを渡す
		this.player = player; // 引数のPlayerオブジェクトを渡す
		this.option = option;
		this.cpu = cpu;

		row = game.getRow(); //getRowメソッドによりオセロ盤の縦横マスの数を取得
		grids = new String[row * row];
		flag = true;
		myFont = new Font("Arial",Font.PLAIN,24);


		color = player.getColor();
		if(color.equals("black")) {
			game.okPut();
			opp_color = "white";
			myIcon = new ImageIcon(option.getBlackImage());
			oppIcon = new ImageIcon(option.getWhiteImage());
		}else {
			opp_color = "black";
			myIcon = new ImageIcon(option.getWhiteImage());
			oppIcon = new ImageIcon(option.getBlackImage());
		}
		grids = game.getGrids(); // getGridメソッドにより局面情報を取得

		// 自分の駒数
		my_num = new JLabel("");
		my_num.setBounds(230, 370, 45, 45); //境界を設定
		my_num.setFont(myFont);

		// 相手の駒数
		opp_num = new JLabel("");
		opp_num.setBounds(320, 370, 45, 45); //境界を設定
		opp_num.setFont(myFont);

		// 自分の表示
		my_nameLabel = new JLabel("You");
		my_nameLabel.setBounds(60, 450, 80, 40); // 境界を設定
		my_nameLabel.setFont(myFont);

		// 相手の表示
		opp_nameLabel = new JLabel("Opponent");
		opp_nameLabel.setBounds(220, 450, 120, 40); // 境界を設定
		opp_nameLabel.setFont(myFont);

		//ウィンドウ設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じる場合の処理
		setTitle("ネットワーク対戦型オセロゲーム");//ウィンドウのタイトル
		setSize(row * 45 + 25, row * 45 + 230);//ウィンドウのサイズを設定
		c = getContentPane();//フレームのペインを取得

		//アイコン設定(画像ファイルをアイコンとして使う)
		whiteIcon = new ImageIcon(option.getWhiteImage());
		blackIcon = new ImageIcon(option.getBlackImage());
		boardIcon = new ImageIcon("GreenFrame.jpg");
		putIcon = new ImageIcon("Put.jpg");

		updateDisp();
	}

/*
	// メソッド
	public void connectServer(String ipAddress, int port){	// サーバに接続
		Socket socket = null;
		try {
			socket = new Socket(ipAddress, port); //サーバ(ipAddress, port)に接続
			out = new PrintWriter(socket.getOutputStream(), true); //データ送信用オブジェクトの用意
			receiver = new Receiver(socket); //受信用オブジェクトの準備
			receiver.start();//受信用オブジェクト(スレッド)起動
			InetAddress addr = InetAddress.getLocalHost();
			out.println(addr.getHostAddress());
		} catch (UnknownHostException e) {
			System.err.println("ホストのIPアドレスが判定できません: " + e);
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("サーバ接続時にエラーが発生しました: " + e);
			System.exit(-1);
		}
	}
	public void sendMessage(String msg){	// サーバに操作情報を送信
		out.println(msg);//送信データをバッファに書き出す
		out.flush();//送信データを送る
		System.out.println("サーバにメッセージ " + msg + " を送信しました"); //テスト標準出力
	}
	// データ受信用スレッド(内部クラス)
	class Receiver extends Thread {
		private InputStreamReader sisr; //受信データ用文字ストリーム
		private BufferedReader br; //文字ストリーム用のバッファ
		// 内部クラスReceiverのコンストラクタ
		Receiver (Socket socket){
			try{
				sisr = new InputStreamReader(socket.getInputStream()); //受信したバイトデータを文字ストリームに
				br = new BufferedReader(sisr);//文字ストリームをバッファリングする
			} catch (IOException e) {
				System.err.println("データ受信時にエラーが発生しました: " + e);
			}
		}
		// 内部クラス Receiverのメソッド
		public void run(){
			try{
				while(true) {//データを受信し続ける
					String inputLine = br.readLine();//受信データを一行分読み込む
					if (inputLine != null){//データを受信したら
						receiveMessage(inputLine);//データ受信用メソッドを呼び出す
					}
				}
			} catch (IOException e){
				System.err.println("データ受信時にエラーが発生しました: " + e);
			}
		}
	}
	public void receiveMessage(String msg){	// メッセージの受信
		System.out.println("サーバからメッセージ " + msg + " を受信しました"); //テスト用標準出力
		if(flag) {
			player.setColor(msg);
			color = player.getColor();
			if(color.equals("black")) {
				game.okPut();
				opp_color = "white";
				myIcon = new ImageIcon("Black.jpg");
				oppIcon = new ImageIcon("White.jpg");
			}else {
				opp_color = "black";
				myIcon = new ImageIcon("White.jpg");
				oppIcon = new ImageIcon("Black.jpg");
			}
			flag = false;
			grids = game.getGrids(); // getGridメソッドにより局面情報を取得
			updateDisp();
		}else {
			acceptOperation(msg);
		}
	}
*/



	public void updateDisp(){ // 画面を更新する
		c.removeAll();
		c.setLayout(null);//
		//オセロ盤の生成
		buttonArray = new JButton[row * row];//ボタンの配列を作成
		for(int i = 0 ; i < row * row ; i++){
			if(grids[i].equals("black")){ buttonArray[i] = new JButton(blackIcon);}//盤面状態に応じたアイコンを設定
			if(grids[i].equals("white")){ buttonArray[i] = new JButton(whiteIcon);}//盤面状態に応じたアイコンを設定
			if(grids[i].equals("board")){ buttonArray[i] = new JButton(boardIcon);}//盤面状態に応じたアイコンを設定
			if(grids[i].equals("put")){
				buttonArray[i] = new JButton(putIcon); //盤面状態に応じたアイコンを設定
				buttonArray[i].addMouseListener(this);//マウス操作を認識できるようにする
			}
			c.add(buttonArray[i]);//ボタンの配列をペインに貼り付け
			// ボタンを配置する
			int x = (i % row) * 45;
			int y = (int) (i / row) * 45;
			buttonArray[i].setBounds(x, y, 45, 45);//ボタンの大きさと位置を設定する．
			buttonArray[i].setActionCommand(Integer.toString(i));//ボタンを識別するための名前(番号)を付加する
		}

		// 駒数表示用ラベル(自分)
		my_stone_num = new JLabel(myIcon);
		my_stone_num.setBounds(180, 370, 45, 45);
		c.add(my_stone_num);

		my_num.setText(String.valueOf(game.get_num(color)));
		c.add(my_num);

		// 駒数表示用ラベル(相手)
		opp_stone_num = new JLabel(oppIcon);
		opp_stone_num.setBounds(270, 370, 45, 45);
		c.add(opp_stone_num);

		opp_num.setText(String.valueOf(game.get_num(opp_color)));
		c.add(opp_num);

		// 名前表示用ラベル(自分)
		my_stone = new JLabel(myIcon);
		my_stone.setBounds(20, 450, 40, 40);
		c.add(my_stone);

		if(color.equals(game.getTurn())){
			my_nameLabel.setBorder(new LineBorder(Color.RED, 2, false));
		}else {
			my_nameLabel.setBorder(new LineBorder(Color.BLACK, 2, false));
		}
		c.add(my_nameLabel);

		// 名前表示用ラベル(相手)
		opp_stone = new JLabel(oppIcon);
		opp_stone.setBounds(180, 450, 40, 40);
		c.add(opp_stone);

		if(color.equals(game.getTurn())){
			opp_nameLabel.setBorder(new LineBorder(Color.BLACK, 2, false));
		}else {
			opp_nameLabel.setBorder(new LineBorder(Color.RED, 2, false));
		}
		c.add(opp_nameLabel);

		c.repaint();
	}

	public void acceptOperation(String command){	// プレイヤの操作を受付
		if(Integer.parseInt(command) != -1) {
			game.putStone(Integer.parseInt(command));
		}
		game.changeTurn();
		if(!game.okPut()) {
			if(game.isGameover()) {
				System.out.println(game.checkWinner());
			}else {
				//sendMessage("-1"); // メッセージを送信
				game.changeTurn();
			}
		}
		grids = game.getGrids(); //getGridメソッドにより局面情報を取得
		updateDisp(); // 画面を更新する
	}


  	//マウスクリック時の処理
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る
		String command = theButton.getActionCommand();//ボタンの名前を取り出す
		System.out.println("マウスがクリックされました。押されたボタンは " + command + "です。");//テスト用に標準出力
		game.putStone(Integer.parseInt(command)); // 駒をひっくり返す
		grids = game.getGrids(); //getGridメソッドにより局面情報を取得
		game.changeTurn(); // 手番変更
		updateDisp(); // 画面を更新する

		//sendMessage(command); // メッセージを送信
		cpu_Turn(command);
	}

	public void cpu_Turn(String command) {
		cpu_place = cpu.get_select(Integer.parseInt(command));
		if(cpu_place != -1) {
			game.putStone(cpu_place);
			grids = game.getGrids();
			updateDisp();
		}
		if(game.isGameover()) {
			System.out.println(game.checkWinner());
		}else {
			game.changeTurn();
			while(!game.okPut()) {
				cpu_place = cpu.get_select(-1);
				game.changeTurn();
				game.putStone(cpu_place);
				if(game.isGameover()) {
					System.out.println(game.checkWinner());
					break;
				}
				grids = game.getGrids();
				updateDisp();
				game.changeTurn();
			}
			grids = game.getGrids();
			updateDisp();
		}
	}

	public void mouseEntered(MouseEvent e) {}//マウスがオブジェクトに入ったときの処理
	public void mouseExited(MouseEvent e) {}//マウスがオブジェクトから出たときの処理
	public void mousePressed(MouseEvent e) {}//マウスでオブジェクトを押したときの処理
	public void mouseReleased(MouseEvent e) {}//マウスで押していたオブジェクトを離したときの処理

}