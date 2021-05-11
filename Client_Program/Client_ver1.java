import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

import java.net.*;
import java.io.*;

public class ClientSample1 extends JFrame implements MouseListener {
	private JButton buttonArray[][];
	//private JButton stop, pass;
	//private JLabel colorLabel;
	private JLabel numLabel1;
	private JLabel numLabel2;
	private JLabel numLabelW;
	private JLabel numLabelB;
	private JLabel nameLabel11;
	private JLabel nameLabel22;
	private JLabel nameLabel1;
	private JLabel nameLabel2;
	private Container c;
	private ImageIcon blackIcon, whiteIcon, boardIcon, yesIcon;
	private PrintWriter out;//データ送信用オブジェクト
	
	//テスト用に局面情報を初期化
		String [][] grids = 
			{{"board","board","board","board","board","board","board","board"},
			 {"board","board","board","board","board","board","board","board"},
			 {"board","board","board","yes","board","board","board","board"},
			 {"board","board","yes","white","black","board","board","board"},
			 {"board","board","board","black","white","yes","board","board"},
			 {"board","board","board","board","yes","board","board","board"},
			 {"board","board","board","board","board","board","board","board"},
			 {"board","board","board","board","board","board","board","board"}};

	public ClientSample1() {
		
		int row = 8; //オセロ盤の縦横マスの数
		
		//ウィンドウ設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ウィンドウを閉じる場合の処理
		setTitle("ネットワーク対戦型オセロゲーム"); 
		setSize(row * 45 + 10, row * 45 + 200); //ウィンドウのサイズを設定
		c = getContentPane(); //フレームのペインを取得
		
		//アイコン設定(画像ファイルをアイコンとして使う)
		whiteIcon = new ImageIcon("White.jpg");
		blackIcon = new ImageIcon("Black.jpg");
		boardIcon = new ImageIcon("GreenFrame.jpg");
		yesIcon = new ImageIcon("Yes.jpg");
		
		c.setLayout(null);
		
		//オセロ盤の生成
		buttonArray = new JButton[row][row]; //ボタンの配列を作成
		for(int i = 0 ; i < row ; i++){
			for(int j = 0; j < row ; j++) {
				if(grids[i][j].equals("black")){ buttonArray[i][j] = new JButton(blackIcon);} //盤面状態に応じたアイコンを設定
				if(grids[i][j].equals("white")){ buttonArray[i][j] = new JButton(whiteIcon);} //盤面状態に応じたアイコンを設定
				if(grids[i][j].equals("board")){ buttonArray[i][j] = new JButton(boardIcon);} //盤面状態に応じたアイコンを設定
				if(grids[i][j].equals("yes")){ buttonArray[i][j] = new JButton(yesIcon);} //盤面状態に応じたアイコンを設定
				c.add(buttonArray[i][j]); //ボタンの配列をペインに貼り付け
			
				// ボタンを配置する
				int x = j * 45;
				int y = i * 45;
				buttonArray[i][j].setBounds(x, y, 45, 45); //ボタンの大きさと位置を設定する
				buttonArray[i][j].addMouseListener(this); //マウス操作を認識できるようにする
				buttonArray[i][j].setActionCommand(Integer.toString(i*10+j)); //ボタンを識別するための名前(番号)を付加する
			}
		}
		
		/*
		//終了ボタン
		stop = new JButton("終了");
		c.add(stop);
		stop.setBounds(0, row * 45 + 30, (row * 45 + 10) / 2, 30); //終了ボタンの境界を設定
		stop.addMouseListener(this); //マウス操作を認識できるようにする
		stop.setActionCommand("stop"); //ボタンを識別するための名前を付加する
		*/
		
		/*
		//パスボタン
		pass = new JButton("パス");
		c.add(pass);
		pass.setBounds((row * 45 + 10) / 2, row * 45 + 30, (row * 45 + 10 ) / 2, 30); //パスボタンの境界を設定
		pass.addMouseListener(this); //マウス操作を認識できるようにする
		pass.setActionCommand("pass"); //ボタンを識別するための名前を付加する
		*/
		
		/*
		//色表示用ラベル
		colorLabel = new JLabel("プレイヤ名  あなたは黒です");
		colorLabel.setBounds(10, row * 45 + 60 , row * 45 + 10, 30); //境界を設定
		c.add(colorLabel);
		*/
		
		numLabel1 = new JLabel(whiteIcon);
		numLabel1.setBounds(4*45, 8*45+10, 45, 45);
		c.add(numLabel1);		
		numLabelW = new JLabel(Integer.toString(getNW()));
		numLabelW.setBounds(5*45+5, 8*45+10, 45, 45); //境界を設定
		numLabelW.setFont(new Font("Arial",Font.PLAIN,24));
		c.add(numLabelW);
		
		numLabel2 = new JLabel(blackIcon);
		numLabel2.setBounds(6*45, 8*45+10, 45, 45);
		c.add(numLabel2);
		numLabelB = new JLabel(Integer.toString(getNB()));
		numLabelB.setBounds(7*45+5, 8*45+10, 45, 45); //境界を設定
		numLabelB.setFont(new Font("Arial",Font.PLAIN,24));
		c.add(numLabelB);
		
		//名前表示用ラベル
		nameLabel11 = new JLabel(whiteIcon);
		nameLabel11.setBounds(20, 10* 45, 40, 40);
		nameLabel1 = new JLabel("あなた");
		nameLabel1.setBounds(20+40, 10*45, 80, 40); //境界を設定
		nameLabel1.setFont(new Font("Arial",Font.PLAIN,24));
		nameLabel1.setBorder(new LineBorder(Color.BLACK, 2, false));
		c.add(nameLabel1);
		c.add(nameLabel11);
		
		nameLabel22 = new JLabel(blackIcon);
		nameLabel22.setBounds(180, 10*45, 40, 40);
		nameLabel2 = new JLabel("相手");
		nameLabel2.setBounds(180+40, 10*45, 80, 40); //境界を設定
		nameLabel2.setFont(new Font("Arial",Font.PLAIN,24));
		nameLabel2.setBorder(new LineBorder(Color.BLACK, 2, false));
		c.add(nameLabel2);
		c.add(nameLabel22);
	}

	//駒数表示用ラベル
	public int getNW() {
		int nw=0;
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(grids[i][j].equals("white")){ nw++;}
			}
		}
		return nw;
	}
			
	public int getNB() {
		int nb = 0;
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(grids[i][j].equals("black")){ nb++;}
			}
		}
		return nb;
	}
	
	public void connectServer(String ipAddress, int port){
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
	public void sendMessage(String msg){
	}
	public void receiveMessage(String msg){
	}
	public void updateDisp(){	
		System.out.println("うんち");
		
		//buttonArray = new JButton[8][8]; //ボタンの配列を作成
		for(int i = 0 ; i < 8 ; i++){
			for(int j = 0; j < 8 ; j++) {
				if(grids[i][j].equals("black")){ buttonArray[i][j] = new JButton(blackIcon);} //盤面状態に応じたアイコンを設定
				if(grids[i][j].equals("white")){ buttonArray[i][j] = new JButton(whiteIcon);} //盤面状態に応じたアイコンを設定
				if(grids[i][j].equals("board")){ buttonArray[i][j] = new JButton(boardIcon);} //盤面状態に応じたアイコンを設定
				if(grids[i][j].equals("yes")){ buttonArray[i][j] = new JButton(yesIcon);} //盤面状態に応じたアイコンを設定
				c.add(buttonArray[i][j]); //ボタンの配列をペインに貼り付け
			}
		}
	}
	
	public void acceptOperation(String command){
	}
  	
	public void mouseClicked(MouseEvent e) { //マウスがクリックされたときの処理
		JButton theButton = (JButton)e.getComponent();
		String command = theButton.getActionCommand();
		System.out.println("マウスがクリックされました。押されたボタンは " + command + " です。");
		
		if(grids[Integer.parseInt(command)/10][Integer.parseInt(command)%10] == "yes") {
			//オセロクラスの〇〇メソッド（引数として置こうとするコマの位置（[Integer.parseInt(command)/10], [Integer.parseInt(command)%10]）をとる）を呼び出す。
			//〇〇のメソッドの戻り値をnewgridsとする
			//grids = newgrids
			grids[Integer.parseInt(command)/10][Integer.parseInt(command)%10] ="black";
			System.out.println(grids[Integer.parseInt(command)/10][Integer.parseInt(command)%10]);
			invalidate();
			validate();
			repaint();
		}
		
		if(command == "stop") {
			System.exit(0);
		}
	}
	public void mouseEntered(MouseEvent e) {} //マウスがオブジェクトに入ったときの処理
	public void mouseExited(MouseEvent e) {} //マウスがオブジェクトから出たときの処理
	public void mousePressed(MouseEvent e) {} //マウスでオブジェクトを押したときの処理
	public void mouseReleased(MouseEvent e) {} //マウスで押していたオブジェクトを離したときの処理
	
	public static void main(String args[]){ 
		ClientSample1 oclient = new ClientSample1();
		oclient.setVisible(true);
	}
}