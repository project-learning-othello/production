import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.io.PrintWriter;
import java.io.IOException;

public class ServerSample2{
	private int port; // サーバの待ち受けポート
	private boolean [] online; //オンライン状態管理用配列
	private PrintWriter [] out; //データ送信用オブジェクト
	private Receiver [] receiver; //データ受信用オブジェクト
	private int i = 0; //プレイヤーの先行・後攻の判断のときに使う変数
	File file; //ログファイル操作用
	FileWriter filewriter; //ログファイル操作用
	Date date; //現在時刻取得用

	//コンストラクタ
	public ServerSample2(int port) { //待ち受けポートを引数とする
		this.port = port; //待ち受けポートを渡す
		out = new PrintWriter [2]; //データ送信用オブジェクトを2クライアント分用意
		receiver = new Receiver [2]; //データ受信用オブジェクトを2クライアント分用意
		online = new boolean[2]; //オンライン状態管理用配列を用意
	}

	// データ受信用スレッド(内部クラス)
	class Receiver extends Thread {
		private InputStreamReader sisr; //受信データ用文字ストリーム
		private BufferedReader br; //文字ストリーム用のバッファ
		private int playerNo; //プレイヤを識別するための番号


		// 内部クラスReceiverのコンストラクタ
		Receiver (Socket socket, int playerNo){
			try{
				this.playerNo = playerNo; //プレイヤ番号を渡す
				sisr = new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(sisr);
				online[playerNo] = true;

			} catch (IOException e) {
				System.err.println("データ受信時にエラーが発生しました: " + e);
			}

			
		}
		// 内部クラス Receiverのメソッド
		public void run(){
			try{
				while(true) {// データを受信し続ける
					String inputLine = br.readLine();//データを一行分読み込む
					if (inputLine != null){ //データを受信したら
						System.out.println("プレイヤー " + playerNo + " から " + inputLine + " が送信されました");
						
						if(inputLine.equals("対局終了")){
							finishGame();
						}

						forwardMessage(inputLine, playerNo); //もう一方に転送する
					}
				}
			} catch (IOException e){ // 接続が切れたとき
				System.err.println("プレイヤ " + playerNo + "との接続が切れました．");
				online[playerNo] = false; //プレイヤの接続状態を更新する
				printStatus(); //接続状態を出力する
			}
		}
	}

	// メソッド

	public void acceptClient(){ //クライアントの接続(サーバの起動)
		try {
			System.out.println("サーバが起動しました．");
			ServerSocket ss = new ServerSocket(port); //サーバソケットを用意
			while (true) {

				Socket socket = ss.accept(); //新規接続を受け付ける
				System.out.println("クライアントと接続しました．"); //テスト用出力
				out[i] = new PrintWriter(socket.getOutputStream(), true);//データ送信オブジェクトを用意
				receiver[i] = new Receiver(socket, i);//データ受信オブジェクト(スレッド)を用意
				receiver[i].start();//データ送信オブジェクト(スレッド)を起動
				sendColor(i);
				
				if(i == 0){

					date = new Date();
					file = new File("log.txt");
					filewriter = new FileWriter(file, true);
					filewriter.write("プレイヤー 1, ipアドレス 〇〇, 時刻 " + date + "\r\n");
					filewriter.close();

				}else if(i == 1){

					date = new Date();
					file = new File("log.txt");
					filewriter = new FileWriter(file, true);
					filewriter.write("プレイヤー 2, ipアドレス 〇〇, 時刻 " + date + "\r\n");
					filewriter.write("対局開始\r\n");
					filewriter.close();


				}else{
					System.out.println("対局開始前のログ操作がおかしいよ．"); //テスト用出力
				}
				i++;
			}
		} catch (Exception e) {
			System.err.println("ソケット作成時にエラーが発生しました: " + e);
		}
	}

	public void printStatus(){ //クライアント接続状態の確認

		if(!online[0]){
			out[1].println("[プレイヤー1との接続が切れました]");//受信データをバッファに書き出す
			out[1].flush();//受信データをそのまま返信する	
		}else if(!online[1]){
			out[0].println("[プレイヤー2との接続が切れました]");//受信データをバッファに書き出す
			out[0].flush();//受信データをそのまま返信する	
		}else{
			System.out.println("接続確認がおかしいよ．"); //テスト用出力
		}

	}

	public void sendColor(int playerNo){ //先手後手情報(白黒)の送信

		if(playerNo == 0){
			out[playerNo].println("[あなた：プレイヤー1、先行、黒の石を操作してください]");//受信データをバッファに書き出す
			out[playerNo].flush();//受信データをそのまま返信する	
		}else if(playerNo == 1){
			out[playerNo].println("[あなた：プレイヤー2、後攻、白の石を操作してください]");//受信データをバッファに書き出す
			out[playerNo].flush();//受信データをそのまま返信する	
		}else{
			System.out.println("人数がおかしいよ．"); //テスト用出力
		}
	}

	public void forwardMessage(String msg, int playerNo){ //操作情報の転送
		if(playerNo == 0){
			out[1].println("[プレイヤー 1 から、" + msg + " が送信されました]");//受信データをバッファに書き出す
			out[1].flush();//受信データをそのまま返信する	
		}else if(playerNo == 1){
			out[0].println("[プレイヤー 2 から、" + msg + " が送信されました]");//受信データをバッファに書き出す
			out[0].flush();//受信データをそのまま返信する	
		}else{
			System.out.println("人数がおかしいよ．"); //テスト用出力
		}

		try{

			date = new Date();
			file = new File("log.txt");
			filewriter = new FileWriter(file, true);

			filewriter.write("プレイヤー "+ playerNo +", 配置場所 "+ msg +", 時刻 "+ date + "\r\n");
			filewriter.close();

		}catch(IOException e){
			System.out.println(e);
			System.out.println("forwardMessageがおかしいよ．"); //テスト用出力
		}
	}

	public void finishGame(){
		date = new Date();
		try {

			file = new File("log.txt");
			filewriter = new FileWriter(file, true);

			filewriter.write("対局終了\r\n\r\n");
			filewriter.close();

		} catch (Exception e) {
			System.out.println("finishGameがおかしいよ．"); //テスト用出力
		}
		
	}

	public static void main(String[] args){ //main
		ServerSample2 server = new ServerSample2(10000); //待ち受けポート10000番でサーバオブジェクトを準備
		server.acceptClient(); //クライアント受け入れを開始
	}
}