//パッケージのインポート
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class Client_ver2 extends JFrame implements MouseListener {
	private JButton buttonArray[];//オセロ盤用のボタン配列
	private JLabel numLabel1,numLabel2,numLabel_white,numLabel_black;
	private JLabel nameLabel1,nameLabel2,nameLabel_white,nameLabel_black;
	private Container c; // コンテナ
	private ImageIcon blackIcon, whiteIcon, boardIcon, putIcon; //アイコン
	private PrintWriter out;//データ送信用オブジェクト

	//テスト用に局面情報を初期化
	String [] grids =
		{"board","board","board","board","board","board","board","board",
		"board","board","board","board","board","board","board","board",
		"board","board","board","board","board","board","board","board",
		"board","board","board","white","black","board","board","board",
		"board","board","board","black","white","board","board","board",
		"board","board","board","board","board","board","board","board",
		"board","board","board","board","board","board","board","board",
		"board","board","board","board","board","board","board","board"};

	int row = 8; //オセロ盤の縦横マスの数

	// コンストラクタ
	public Client_ver2() {
		//ウィンドウ設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じる場合の処理
		setTitle("ネットワーク対戦型オセロゲーム");//ウィンドウのタイトル
		setSize(row * 45 + 10, row * 45 + 200);//ウィンドウのサイズを設定
		c = getContentPane();//フレームのペインを取得

		//アイコン設定(画像ファイルをアイコンとして使う)
		whiteIcon = new ImageIcon("White.jpg");
		blackIcon = new ImageIcon("Black.jpg");
		boardIcon = new ImageIcon("GreenFrame.jpg");

		paint();

		//駒数表示用ラベル
		numLabel1 = new JLabel(whiteIcon);
		numLabel1.setBounds(4*45, 8*45+10, 45, 45);
		c.add(numLabel1);
		numLabel_white = new JLabel(Integer.toString(get_num_white()));
		numLabel_white.setBounds(5*45+5, 8*45+10, 45, 45); //境界を設定
		numLabel_white.setFont(new Font("Arial",Font.PLAIN,24));
		c.add(numLabel_white);

		numLabel2 = new JLabel(blackIcon);
		numLabel2.setBounds(6*45, 8*45+10, 45, 45);
		c.add(numLabel2);
		numLabel_black = new JLabel(Integer.toString(get_num_black()));
		numLabel_black.setBounds(7*45+5, 8*45+10, 45, 45); //境界を設定
		numLabel_black.setFont(new Font("Arial",Font.PLAIN,24));
		c.add(numLabel_black);

		//名前表示用ラベル
		nameLabel_white = new JLabel(whiteIcon);
		nameLabel_white.setBounds(20, 10* 45, 40, 40);
		nameLabel1 = new JLabel("あなた");
		nameLabel1.setBounds(20+40, 10*45, 80, 40); //境界を設定
		nameLabel1.setFont(new Font("Arial",Font.PLAIN,24));
		nameLabel1.setBorder(new LineBorder(Color.BLACK, 2, false));
		c.add(nameLabel1);
		c.add(nameLabel_white);

		nameLabel_black = new JLabel(blackIcon);
		nameLabel_black.setBounds(180, 10*45, 40, 40);
		nameLabel2 = new JLabel("相手");
		nameLabel2.setBounds(180+40, 10*45, 80, 40); //境界を設定
		nameLabel2.setFont(new Font("Arial",Font.PLAIN,24));
		nameLabel2.setBorder(new LineBorder(Color.BLACK, 2, false));
		c.add(nameLabel2);
		c.add(nameLabel_black);
	}

	// メソッド
	public void paint() {
		c.setLayout(null);//
		//オセロ盤の生成
		buttonArray = new JButton[row * row];//ボタンの配列を作成
		for(int i = 0 ; i < row * row ; i++){
			if(grids[i].equals("black")){ buttonArray[i] = new JButton(blackIcon);}//盤面状態に応じたアイコンを設定
			if(grids[i].equals("white")){ buttonArray[i] = new JButton(whiteIcon);}//盤面状態に応じたアイコンを設定
			if(grids[i].equals("board")){ buttonArray[i] = new JButton(boardIcon);}//盤面状態に応じたアイコンを設定
			c.add(buttonArray[i]);//ボタンの配列をペインに貼り付け
			// ボタンを配置する
			int x = (i % row) * 45;
			int y = (int) (i / row) * 45;
			buttonArray[i].setBounds(x, y, 45, 45);//ボタンの大きさと位置を設定する．
			buttonArray[i].addMouseListener(this);//マウス操作を認識できるようにする
			buttonArray[i].setActionCommand(Integer.toString(i));//ボタンを識別するための名前(番号)を付加する
		}
	}

	public int get_num_white() {
		int nw=0;
		for(int i=0;i<row*row;i++) {
			if(grids[i].equals("white")){ nw++;}
		}
		return nw;
	}

	public int get_num_black() {
		int nb = 0;
		for(int i=0;i<row*row;i++) {
			if(grids[i].equals("black")){ nb++;}
		}
		return nb;
	}

	public void connectServer(String ipAddress, int port){	// サーバに接続
		Socket socket = null;
		try {
			socket = new Socket(ipAddress, port); //サーバ(ipAddress, port)に接続
			out = new PrintWriter(socket.getOutputStream(), true); //データ送信用オブジェクトの用意
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

	public void receiveMessage(String msg){	// メッセージの受信
		System.out.println("サーバからメッセージ " + msg + " を受信しました"); //テスト用標準出力
	}
	public void updateDisp(){	// 画面を更新する
		c.removeAll();
		paint();
	}
	public void acceptOperation(String command){	// プレイヤの操作を受付
	}

  	//マウスクリック時の処理
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．キャストを忘れずに
		String command = theButton.getActionCommand();//ボタンの名前を取り出す
		System.out.println("マウスがクリックされました。押されたボタンは " + command + "です。");//テスト用に標準出力
		sendMessage(command); //テスト用にメッセージを送信
		grids[Integer.parseInt(command)] = "black";
		updateDisp();
	}
	public void mouseEntered(MouseEvent e) {}//マウスがオブジェクトに入ったときの処理
	public void mouseExited(MouseEvent e) {}//マウスがオブジェクトから出たときの処理
	public void mousePressed(MouseEvent e) {}//マウスでオブジェクトを押したときの処理
	public void mouseReleased(MouseEvent e) {}//マウスで押していたオブジェクトを離したときの処理

	//テスト用のmain
	public static void main(String args[]){
		Client oclient = new Client();
		oclient.setVisible(true);
		oclient.connectServer("localhost", 10000);
	}
}