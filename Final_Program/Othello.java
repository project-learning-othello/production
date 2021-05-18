public class Othello {
	private int row = 8;	// オセロ盤の縦横マス数
	private String [][] grids = new String [row][row]; // 局面情報
	private String turn; // 手番
	private int num_turn; // 手数
	private static int direction[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
	                                    //   UP,    DOWN,    LEFT,   RIGHT,  UPLEFT,  UPRIGHT, DOWNLEFT, DOWNRIGHT
	private static int direction_num = 8;

	// コンストラクタ
	public Othello(){
		int center = row / 2;

		turn = "black"; // 黒が先手
		num_turn = 0;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < row; j++) {
				grids[j][i] = "board"; // 初めは石が置かれていない
				grids[center - 1][center - 1] = "white";
				grids[center - 1][center] = "black";
				grids[center][center - 1] = "black";
				grids[center][center] = "white";
			}
		}
	}

	// メソッド
	public String checkWinner(){	// 勝敗を判断
		if(get_num_black() == get_num_white()) {
			return "draw";
		}else if(get_num_black() > get_num_white()) {
			return "black";
		}else {
			return "white";
		}
	}

	public String getTurn(){ // 手番情報を取得
		return turn;
	}

	public String [] getGrids(){ // 局面情報を取得
		String[] one_dimentional_grids = new String[row * row];

		for(int i = 0; i < row; i++){ // 2次元配列を1次元配列に変換
			for(int j = 0; j < row; j++) {
				one_dimentional_grids[j + i * row] = grids[i][j];
			}
		}

		return one_dimentional_grids;
	}

	public void changeTurn(){ // 手番を変更
		if(turn == "black") {
			turn = "white";
		}else {
			turn = "black";
		}
	}

	public boolean isGameover(){ // 対局終了を判断
		if(okPut("black") == false) {
			remove_put();
			if(okPut("white") == false) {
				remove_put();
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public boolean okPut(String color) {
		boolean board_flag[] = {false, false, false, false, false, false, false, false};
		boolean put_flag = false;
		String notColor;

		if(color == "black") {
			notColor = "white";
		}else {
			notColor = "black";
		}

		for(int i = 0; i < row; i++) {
			for(int j = 0; j < row; j++) {
				if(grids[j][i] == notColor) {
					for(int k = 0; k < direction_num; k++) {
						if(j + direction[k][0] >= 0 && j + direction[k][0] <= row - 1 && i + direction[k][1] >= 0 && i + direction[k][1] <= row - 1) {
							if(grids[j + direction[k][0]][i + direction[k][1]] == "board") {
								board_flag[k] = true;
							}
						}

						int x = i, y = j;

						if(board_flag[k]) {
							while(grids[y][x] == notColor) {
								if(y - direction[k][0] >= 0 && y - direction[k][0] <= row - 1 && x - direction[k][1] >= 0 && x - direction[k][1] <= row - 1) {
									y = y - direction[k][0];
									x = x - direction[k][1];
								}
							}

							if(grids[y][x] == color) {
								grids[j + direction[k][0]][i + direction[k][1]] = "put";
								put_flag = true;
							}
						}
					}
				}
			}
		}

		return put_flag;
	}

	public void putStone(int put_place, String color){ // (操作を)局面に反映
		String notColor;

		if(color == "black") {
			notColor = "white";
		}else {
			notColor = "black";
		}

		for(int k = 0; k < direction_num; k++) {
			int x = put_place % row, y = put_place / row;
			boolean notColor_flag = false;

			if(y + direction[k][0] >= 0 && y + direction[k][0] <= row - 1 && x + direction[k][1] >= 0 && x + direction[k][1] <= row - 1) {
				y = y + direction[k][0];
				x = x + direction[k][1];
				System.out.print(x);
				System.out.println(y);
			}

			while(grids[y][x] == notColor) {
				if(y + direction[k][0] >= 0 && y + direction[k][0] <= row - 1 && x + direction[k][1] >= 0 && x + direction[k][1] <= row - 1) {
					y = y + direction[k][0];
					x = x + direction[k][1];
					notColor_flag = true;
				}
			}

			if(grids[y][x] == color && notColor_flag) {
				while(x != put_place % row || y != put_place / row) {
					y = y - direction[k][0];
					x = x - direction[k][1];
					grids[y][x] = color;

					System.out.print(x);
					System.out.println(y);
				}
			}

			remove_put();
		}
	}

	public void remove_put() { // "put"を戻す
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < row; j++) {
				if(grids[j][i] == "put") {
					grids[j][i] = "board";
				}
			}
		}
	}

	public int getRow(){ //縦横のマス数を取得
		return row;
	}

	public int get_num_white() { // 白の駒数を取得
		int nw = 0;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < row; j++) {
				if(grids[j][i] == "white") {
					nw++;
				}
			}
		}
		return nw;
	}

	public int get_num_black() { // 黒の駒数を取得
		int nb = 0;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < row; j++) {
				if(grids[j][i] == "black") {
					nb++;
				}
			}
		}
		return nb;
	}
}