//パッケージのインポート
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class Client extends JFrame implements MouseListener {
	private JButton buttonArray[];//オセロ盤用のボタン配列
	private JLabel numLabel1,numLabel2,numLabel_white,numLabel_black;// 駒数表示用
	private JLabel nameLabel1,nameLabel2,nameLabel_white,nameLabel_black;// 手番表示用
	private Container c; // コンテナ
	private ImageIcon blackIcon, whiteIcon, boardIcon, putIcon; //アイコン
	private PrintWriter out;//データ送信用オブジェクト
	private Receiver receiver; //データ受信用オブジェクト
	private Othello game; // Othelloオブジェクト
	private Player player; // Playerオブジェクト
	private String [] grids;
	private int row; //オセロ盤の縦横マスの数
	private String color; // 先手後手情報
	private String turn; // 手番情報

	// コンストラクタ
	public Client(Othello game, Player player) {
		this.game = game; // 引数のOthelloオブジェクトを渡す
		row = game.getRow(); //getRowメソッドによりオセロ盤の縦横マスの数を取得
		grids = new String[row * row];
		grids = game.getGrids(); //getGridメソッドにより局面情報を取得
		this.player = player; // 引数のPlayerオブジェクトを渡す
		color = player.getColor();
		if(color == "black") {
			game.okPut(color);
		}

		//ウィンドウ設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じる場合の処理
		setTitle("ネットワーク対戦型オセロゲーム");//ウィンドウのタイトル
		setSize(row * 45 + 25, row * 45 + 250);//ウィンドウのサイズを設定
		c = getContentPane();//フレームのペインを取得

		//アイコン設定(画像ファイルをアイコンとして使う)
		whiteIcon = new ImageIcon("White.jpg");
		blackIcon = new ImageIcon("Black.jpg");
		boardIcon = new ImageIcon("GreenFrame.jpg");
		putIcon = new ImageIcon("Put.jpg");

		//描画
		paint();
	}

	// メソッド
	public void paint() { // 画面を描画する
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

		//駒数表示用ラベル
		numLabel1 = new JLabel(whiteIcon);
		numLabel1.setBounds(180, 370, 45, 45);
		c.add(numLabel1);
		numLabel_white = new JLabel(Integer.toString(game.get_num_white()));
		numLabel_white.setBounds(230, 370, 45, 45); //境界を設定
		numLabel_white.setFont(new Font("Arial",Font.PLAIN,24));
		c.add(numLabel_white);

		numLabel2 = new JLabel(blackIcon);
		numLabel2.setBounds(270, 370, 45, 45);
		c.add(numLabel2);
		numLabel_black = new JLabel(Integer.toString(game.get_num_black()));
		numLabel_black.setBounds(320, 370, 45, 45); //境界を設定
		numLabel_black.setFont(new Font("Arial",Font.PLAIN,24));
		c.add(numLabel_black);

		//名前表示用ラベル
		nameLabel_white = new JLabel(whiteIcon);
		nameLabel_white.setBounds(20, 450, 40, 40);
		nameLabel1 = new JLabel("あなた");
		nameLabel1.setBounds(60, 450, 80, 40); //境界を設定
		nameLabel1.setFont(new Font("Arial",Font.PLAIN,24));
		nameLabel1.setBorder(new LineBorder(Color.YELLOW, 2, false));
		c.add(nameLabel1);
		c.add(nameLabel_white);

		nameLabel_black = new JLabel(blackIcon);
		nameLabel_black.setBounds(180, 450, 40, 40);
		nameLabel2 = new JLabel("相手");
		nameLabel2.setBounds(220, 450, 80, 40); //境界を設定
		nameLabel2.setFont(new Font("Arial",Font.PLAIN,24));
		nameLabel2.setBorder(new LineBorder(Color.BLACK, 2, false));
		c.add(nameLabel2);
		c.add(nameLabel_black);
	}

	public void connectServer(String ipAddress, int port){	// サーバに接続
		Socket socket = null;
		try {
			socket = new Socket(ipAddress, port); //サーバ(ipAddress, port)に接続
			out = new PrintWriter(socket.getOutputStream(), true); //データ送信用オブジェクトの用意
			receiver = new Receiver(socket); //受信用オブジェクトの準備
			receiver.start();//受信用オブジェクト(スレッド)起動
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
	}

	public void updateDisp(){	// 画面を更新する
		c.removeAll();
		paint();
	}

	public void acceptOperation(String command){	// プレイヤの操作を受付
		turn = game.getTurn();

		if(turn == color) {

		}
	}

  	//マウスクリック時の処理
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．キャストを忘れずに
		String command = theButton.getActionCommand();//ボタンの名前を取り出す
		System.out.println("マウスがクリックされました。押されたボタンは " + command + "です。");//テスト用に標準出力
		sendMessage(command); //テスト用にメッセージを送信
		grids[Integer.parseInt(command)] = color;
		updateDisp();
	}
	public void mouseEntered(MouseEvent e) {}//マウスがオブジェクトに入ったときの処理
	public void mouseExited(MouseEvent e) {}//マウスがオブジェクトから出たときの処理
	public void mousePressed(MouseEvent e) {}//マウスでオブジェクトを押したときの処理
	public void mouseReleased(MouseEvent e) {}//マウスで押していたオブジェクトを離したときの処理

	//テスト用のmain
	public static void main(String args[]){
		Othello game = new Othello();
		Player player = new Player();
		player.setColor("black");
		Client oclient = new Client(game, player);
		oclient.setVisible(true);
		oclient.connectServer("192.168.1.5", 10000);
	}
}