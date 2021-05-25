import java.util.Random;

public class Computer {
	private String mode;		//ハード、ノーマル、イージー
	private int myColor,yourColor;		//自分,相手の色
	private int [][] grids = new int[10][10];		//基本の盤面
	int [][] grids_possible = new int[10][10];		//打てるマスを3とする盤面
	int [][] grids_temp1 = new int[10][10];		//1手先の盤面
	int [][] grids_temp1_possible = new int[10][10];		//打てるマスを3とする盤面
	int [][] grids_temp2 = new int[10][10];		//2手先の盤面
	int [][] grids_temp2_possible = new int[10][10];		//打てるマスを3とする盤面
	int [][] grids_keep = new int[10][10];			//確定石を数えるために使う
	int [] point1 = new int[65];		//1手先の評価値を保持する配列
	int [] point2 = new int[65];		//2手先の評価値を保持する配列
	int select1,select2;		//暫定的に1番優秀な手(1手先と2手先)

	//コンストラクタ
	public Computer(String color, String mode) {	//人(相手)側の色、難易度
		this.mode = mode;
		if(color == "white") {
			myColor = 1;		//黒=1
			yourColor = 2;
		}else if(color == "black") {
			myColor = 2;		//白=2
			yourColor = 1;
		}
		grids = new int[][]
			{{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
			 {-1, 0, 0, 0, 0, 0, 0, 0, 0,-1},
			 {-1, 0, 0, 0, 0, 0, 0, 0, 0,-1},
			 {-1, 0, 0, 0, 0, 0, 0, 0, 0,-1},
			 {-1, 0, 0, 0, 2, 1, 0, 0, 0,-1},
			 {-1, 0, 0, 0, 1, 2, 0, 0, 0,-1},
			 {-1, 0, 0, 0, 0, 0, 0, 0, 0,-1},
			 {-1, 0, 0, 0, 0, 0, 0, 0, 0,-1},
			 {-1, 0, 0, 0, 0, 0, 0, 0, 0,-1},
			 {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};

		copy_grids(grids_possible,grids);
	}


	//コピーメソッド
	public void copy_grids(int a[][],int b[][]) {		//aにbを書き写す、a←b
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				a[i][j]=b[i][j];
			}
		}
	}
	//ここまでコピーメソッド



	//ひっくり返すメソッド
	public void turnover(int a[][],int x,int y,int c1,int c2) {
		int i=0;
		//置くマス
		a[x][y] = c1;

		//下
		if(a[x+1][y] == c2) {
			do{
				i++;
			}while(a[x+i][y] == c2);
			if(a[x+i][y] == c1) {
				for(int j=1;j<i;j++) {
					a[x+j][y] = c1;
				}
			}
		}

		//左下
		i=0;
		if(a[x+1][y-1] == c2) {
			do{
				i++;
			}while(a[x+i][y-i] == c2);
			if(a[x+i][y-i] == c1) {
				for(int j=1;j<i;j++) {
					a[x+j][y-j] = c1;
				}
			}
		}

		//左
		i=0;
		if(a[x][y-1] == c2) {
			do{
				i++;
			}while(a[x][y-i] == c2);
			if(a[x][y-i] == c1) {
				for(int j=1;j<i;j++) {
					a[x][y-j] = c1;
				}
			}
		}

		//左上
		i=0;
		if(a[x-1][y-1] == c2) {
			do{
				i++;
			}while(a[x-i][y-i] == c2);
			if(a[x-i][y-i] == c1) {
				for(int j=1;j<i;j++) {
					a[x-j][y-j] = c1;
				}
			}
		}

		//上
		i=0;
		if(a[x-1][y] == c2) {
			do{
				i++;
			}while(a[x-i][y] == c2);
			if(a[x-i][y] == c1) {
				for(int j=1;j<i;j++) {
					a[x-j][y] = c1;
				}
			}
		}

		//右上
		i=0;
		if(a[x-1][y+1] == c2) {
			do{
				i++;
			}while(a[x-i][y+i] == c2);
			if(a[x-i][y+i] == c1) {
				for(int j=1;j<i;j++) {
					a[x-j][y+j] = c1;
				}
			}
		}

		//右
		i=0;
		if(a[x][y+1] == c2) {
			do{
				i++;
			}while(a[x][y+i] == c2);
			if(a[x][y+i] == c1) {
				for(int j=1;j<i;j++) {
					a[x][y+j] = c1;
				}
			}
		}

		//右下
		i=0;
		if(a[x+1][y+1] == c2) {
			do{
				i++;
			}while(a[x+i][y+i] == c2);
			if(a[x+i][y+i] == c1) {
				for(int j=1;j<i;j++) {
					a[x+j][y+j] = c1;
				}
			}
		}
	}
	//ここまでひっくり返すメソッド



	//置けるマスチェックメソッド	(bを元にaに3を追加する、手番はc1)
	public void turnover_possible(int a[][],int b[][],int c1,int c2) {
		int k=0,flag=0;
		for(int i=1;i<9;i++) {
			for(int j=1;j<9;j++) {
				if(b[i][j] == 0) {
					//下
					k=0;
					if(a[i+1][j] == c2) {
						do{
							k++;
						}while(a[i+k][j] == c2);
						if(a[i+k][j] == c1) {
							flag = 1;
						}
					}

					//左下
					k=0;
					if(a[i+1][j-1] == c2) {
						do{
							k++;
						}while(a[i+k][j-k] == c2);
						if(a[i+k][j-k] == c1) {
							flag = 1;
						}
					}

					//左
					k=0;
					if(a[i][j-1] == c2) {
						do{
							k++;
						}while(a[i][j-k] == c2);
						if(a[i][j-k] == c1) {
							flag = 1;
						}
					}

					//左上
					k=0;
					if(a[i-1][j-1] == c2) {
						do{
							k++;
						}while(a[i-k][j-k] == c2);
						if(a[i-k][j-k] == c1) {
							flag = 1;
						}
					}

					//上
					k=0;
					if(a[i-1][j] == c2) {
						do{
							k++;
						}while(a[i-k][j] == c2);
						if(a[i-k][j] == c1) {
							flag = 1;
						}
					}

					//右上
					k=0;
					if(a[i-1][j+1] == c2) {
						do{
							k++;
						}while(a[i-k][j+k] == c2);
						if(a[i-k][j+k] == c1) {
							flag = 1;
						}
					}

					//右
					k=0;
					if(a[i][j+1] == c2) {
						do {
							k++;
						}while(a[i][j+k] == c2);
						if(a[i][j+k] == c1) {
							flag = 1;
						}
					}

					//右下
					k=0;
					if(a[i+1][j+1] == c2) {
						do{
							k++;
						}while(a[i+k][j+k] == c2);
						if(a[i+k][j+k] == c1) {
							flag = 1;
						}
					}
				}
				if(flag==1) {
					a[i][j] = 3;
					flag=0;
				}
			}
		}
	}
	//ここまで置けるマスチェックメソッド



	//探索
	public void search() {
		if(mode == "hard") {
			search_hard();
		}
		if(mode == "normal") {
			search_normal();
		}
		if(mode == "easy") {
			search_easy();
		}
	}
	//ここまで探索



	//ハードモード
	public void search_hard() {
		int count1=0,count2=0;
		select1=64;select2=64;
		reset_point(point1,-10000);
		for(int i=1;i<9;i++) {
			for(int j=1;j<9;j++) {
				if(grids_possible[i][j] == 3) {
					copy_grids(grids_temp1,grids);
					turnover(grids_temp1,i,j,myColor,yourColor);
					copy_grids(grids_temp1_possible,grids_temp1);
					turnover_possible(grids_temp1_possible,grids_temp1,yourColor,myColor);
					count2=0;
					reset_point(point2,10000);
					for(int k=1;k<9;k++) {
						for(int l=1;l<9;l++) {
							if(grids_temp1_possible[k][l] == 3) {
								point2[count2]=0;
								copy_grids(grids_temp2,grids_temp1);
								turnover(grids_temp2,k,l,yourColor,myColor);
								copy_grids(grids_temp2_possible,grids_temp2);
								turnover_possible(grids_temp2_possible,grids_temp2,myColor,yourColor);
								//評価値計算
								point2[count2]+=count_possible(grids_temp2_possible);	//自分の打てるマス数

								copy_grids(grids_temp2_possible,grids_temp2);
								turnover_possible(grids_temp2_possible,grids_temp2,yourColor,myColor);
								//評価値計算
								point2[count2]-=count_possible(grids_temp2_possible);	//相手の打てるマス数

								//評価値計算
								point2[count2]+=count_x(grids_temp2,yourColor,myColor)*25;	//角なし時の相手のX数
								point2[count2]-=count_x(grids_temp2,myColor,yourColor)*25;	//角なし時の自分のX数

								copy_grids(grids_keep,grids_temp2);
								//評価値計算
								point2[count2]+=count_keep(grids_keep,myColor)*100;		//自分の確定石数(角は1000)

								copy_grids(grids_keep,grids_temp2);
								//評価値計算
								point2[count2]-=count_keep(grids_keep,yourColor)*100;	//相手の確定石数(角は1000)

								if(point2[select2]>point2[count2]) {
									select2=count2;
								}
							}
							count2++;
						}
					}
					point1[count1]=point2[select2];
					if(point1[select1]<point1[count1]) {
						select1=count1;
					}
				}
				count1++;
			}
		}
	}
	//ここまでハードモード



	//ノーマルモード
	public void search_normal() {
		int count1,count2=0;
		select1=64;
		count1=count_possible(grids_possible);
		if(count1!=0) {
			Random r = new Random();
			int n =r.nextInt(count1);
			for(int i=1;i<9;i++) {
				for(int j=1;j<9;j++) {
					if(grids_possible[i][j] == 3) {
						if(count2==n) {
							select1=(i-1)*8+(j-1);
						}
						count2++;
					}
				}
			}
		}
	}
	//ここまでノーマルモード



	//イージーモード
	public void search_easy() {
		int count1=0,count2=0;
		select1=64;select2=64;
		reset_point(point1,10000);
		for(int i=1;i<9;i++) {
			for(int j=1;j<9;j++) {
				if(grids_possible[i][j] == 3) {		//パスの場合を考える必要あり
					copy_grids(grids_temp1,grids);
					turnover(grids_temp1,i,j,myColor,yourColor);
					copy_grids(grids_temp1_possible,grids_temp1);
					turnover_possible(grids_temp1_possible,grids_temp1,yourColor,myColor);
					count2=0;
					reset_point(point2,-10000);
					for(int k=1;k<9;k++) {
						for(int l=1;l<9;l++) {
							if(grids_temp1_possible[k][l] == 3) {		//パスの場合を考える必要あり
								point2[count2]=0;
								copy_grids(grids_temp2,grids_temp1);
								turnover(grids_temp2,k,l,yourColor,myColor);
								copy_grids(grids_temp2_possible,grids_temp2);
								turnover_possible(grids_temp2_possible,grids_temp2,myColor,yourColor);
								//評価値計算
								point2[count2]+=count_possible(grids_temp2_possible);	//自分の打てるマス数

								copy_grids(grids_temp2_possible,grids_temp2);
								turnover_possible(grids_temp2_possible,grids_temp2,yourColor,myColor);
								//評価値計算
								point2[count2]-=count_possible(grids_temp2_possible);	//相手の打てるマス数

								//評価値計算
								point2[count2]+=count_x(grids_temp2,yourColor,myColor)*25;	//角なし時の相手のX数
								point2[count2]-=count_x(grids_temp2,myColor,yourColor)*25;	//角なし時の自分のX数

								copy_grids(grids_keep,grids_temp2);
								//評価値計算
								point2[count2]+=count_keep(grids_keep,myColor)*100;		//自分の確定石数(角は1000)

								copy_grids(grids_keep,grids_temp2);
								//評価値計算
								point2[count2]-=count_keep(grids_keep,yourColor)*100;	//相手の確定石数(角は1000)

								if(point2[select2]<point2[count2]) {
									select2=count2;
								}
							}
							count2++;
						}
					}
					point1[count1]=point2[select2];
					if(point1[select1]>point1[count1]) {
						select1=count1;
					}
				}
				count1++;
			}
		}
	}
	//ここまでイージーモード




	//探索の部品(ポイントリセット)
	public void reset_point(int a[],int b) {		//bは初期ポイント値
		for(int i=0;i<65;i++) {
			a[i]=b;
		}
	}

	//探索の部品(打てるマス数、3をカウント)
	public int count_possible(int a[][]) {
		int count=0;
		for(int i=1;i<9;i++) {
			for(int j=1;j<9;j++) {
				if(a[i][j]==3) {
					count++;
				}
			}
		}
		return count;
	}

	//探索の部品(確定石のカウント)
	public int count_keep(int a[][],int c) {
		int count=0;
		int flag=0,flag_temp=0;
		do{
			flag=0;
			for(int i=1;i<9;i++){
				for(int j=1;j<9;j++){
					if(grids_keep[i][j]==c){
						flag_temp=count_keep_change(grids_keep,i,j);
						if(flag_temp==1){
							flag=1;
							count++;
							flag_temp=0;
						}
					}
				}
			}
		}while(flag==1);
		if(a[1][1]==-1) {count=count+9;}		//角は10、他の確定石は1、×100
		if(a[1][8]==-1) {count=count+9;}
		if(a[8][1]==-1) {count=count+9;}
		if(a[8][8]==-1) {count=count+9;}

		return count;
	}

	//探索の部品(確定石のカウント)(のさらに部品)
	public int count_keep_change(int a[][],int i,int j) {
		int flag=0;
		if(((grids_keep[i+1][j]==-1) || (grids_keep[i-1][j]==-1))
		&& ((grids_keep[i+1][j-1]==-1) || (grids_keep[i-1][j+1]==-1))
		&& ((grids_keep[i][j-1]==-1) || (grids_keep[i][j+1]==-1))
		&& ((grids_keep[i-1][j-1]==-1) || (grids_keep[i+1][j+1]==-1))){
			grids_keep[i][j]=-1;
			flag=1;
		}
		return flag;
	}

	//探索の部品(角がないのにXやCに自分の色がある数）
	public int count_x(int a[][],int c1,int c2) {
		int count=0;
		int k=0,l=0;
		if(a[1][1]==0) {		//角が空いているなら
			if(a[2][2]==c1) {		//Xはアウト(-25)
				count++;
				k=0;
				do{
					k++;
				}while(a[1+k][1+k] == c1);		//自分の色が続かなくなるところまで進む
				if(a[1+k][1+k] == c2) {		//隣が相手の色ならアウト(星以外は更に-50=-75)
					count++;
					count++;
				}
			}
			if(a[1][2]==c1) {		//Cに自分の色があるなら
				k=0;
				do{
					k++;
				}while(a[1][1+k] == c1);		//自分の色が続かなくなるところまで進む
				if(a[1][1+k] == c2) {		//隣が相手の色ならアウト(-25)
					count++;
					l=0;
					do{
						l++;
					}while(a[1][1+k+l] == c2);		//相手の色が続かなくなるところまで進む
					if(a[1][1+k+l] == c1) {		//その先が自分の色ならアウト(更に-50=-75)
						count++;
						count++;
					}
				}else if(a[1][1+k] == 0) {		//隣があいているなら
					if(a[1][1+k+1] == c1) {		//その先が自分の色ならアウト(-50)
						count++;
						count++;
					}else if(a[1][1+k+1] == c2) {		//角,自分...自分,空,相手,なら
						l=0;
						do{
							l++;
						}while(a[1][1+k+l] == c2);		//相手の色が続かなくなるところまで進む
						if(a[1][1+k+l] == c1) {		//その先が自分の色ならアウト(-50)
							count++;
							count++;
						}
					}
				}
			}
			if(a[2][1]==c1) {
				k=0;
				do{
					k++;
				}while(a[1+k][1] == c1);
				if(a[1+k][1] == c2) {
					count++;
					l=0;
					do{
						l++;
					}while(a[1+k+l][1] == c2);
					if(a[1+k+l][1] == c1) {
						count++;
						count++;
					}
				}else if(a[1+k][1] == 0) {
					if(a[1+k+1][1] == c1) {
						count++;
						count++;
					}else if(a[1+k+1][1] == c2) {
						l=0;
						do{
							l++;
						}while(a[1+k+l][1] == c2);
						if(a[1+k+l][1] == c1) {
							count++;
							count++;
						}
					}
				}
			}
		}

		if(a[1][8]==0) {
			if(a[2][7]==c1) {
				count++;
				k=0;
				do{
					k++;
				}while(a[1+k][8-k] == c1);
				if(a[1+k][8-k] == c2) {
					count++;
					count++;
				}
			}
			if(a[1][7]==c1) {
				k=0;
				do{
					k++;
				}while(a[1][8-k] == c1);
				if(a[1][8-k] == c2) {
					count++;
					l=0;
					do{
						l++;
					}while(a[1][8-k-l] == c2);
					if(a[1][8-k-l] == c1) {
						count++;
						count++;
					}
				}else if(a[1][8-k] == 0) {
					if(a[1][8-k-1] == c1) {
						count++;
						count++;
					}else if(a[1][8-k-1] == c2) {
						l=0;
						do{
							l++;
						}while(a[1][8-k-l] == c2);
						if(a[1][8-k-l] == c1) {
							count++;
							count++;
						}
					}
				}
			}
			if(a[2][8]==c1) {
				k=0;
				do{
					k++;
				}while(a[1+k][8] == c1);
				if(a[1+k][8] == c2) {
					count++;
					l=0;
					do{
						l++;
					}while(a[1+k+l][8] == c2);
					if(a[1+k+l][8] == c1) {
						count++;
						count++;
					}
				}else if(a[1+k][8] == 0) {
					if(a[1+k+1][8] == c1) {
						count++;
						count++;
					}else if(a[1+k+1][8] == c2) {
						l=0;
						do{
							l++;
						}while(a[1+k+l][8] == c2);
						if(a[1+k+l][8] == c1) {
							count++;
							count++;
						}
					}
				}
			}
		}

		if(a[8][1]==0) {
			if(a[7][2]==c1) {
				count++;
				k=0;
				do{
					k++;
				}while(a[8-k][1+k] == c1);
				if(a[8-k][1+k] == c2) {
					count++;
					count++;
				}
			}
			if(a[8][2]==c1) {
				k=0;
				do{
					k++;
				}while(a[8][1+k] == c1);
				if(a[8][1+k] == c2) {
					count++;
					l=0;
					do{
						l++;
					}while(a[8][1+k+l] == c2);
					if(a[8][1+k+l] == c1) {
						count++;
						count++;
					}
				}else if(a[8][1+k] == 0) {
					if(a[8][1+k+1] == c1) {
						count++;
						count++;
					}else if(a[8][1+k+1] == c2) {
						l=0;
						do{
							l++;
						}while(a[8][1+k+l] == c2);
						if(a[8][1+k+l] == c1) {
							count++;
							count++;
						}
					}
				}
			}
			if(a[7][1]==c1) {
				k=0;
				do{
					k++;
				}while(a[8-k][1] == c1);
				if(a[8-k][1] == c2) {
					count++;
					l=0;
					do{
						l++;
					}while(a[8-k-l][1] == c2);
					if(a[8-k-l][1] == c1) {
						count++;
						count++;
					}
				}else if(a[8-k][1] == 0) {
					if(a[8-k-1][1] == c1) {
						count++;
						count++;
					}else if(a[8-k-1][1] == c2) {
						l=0;
						do{
							l++;
						}while(a[8-k-l][1] == c2);
						if(a[8-k-l][1] == c1) {
							count++;
							count++;
						}
					}
				}
			}
		}

		if(a[8][8]==0) {
			if(a[7][7]==c1) {
				count++;
				k=0;
				do{
					k++;
				}while(a[8-k][8-k] == c1);
				if(a[8-k][8-k] == c2) {
					count++;
					count++;
				}
			}
			if(a[8][7]==c1) {
				k=0;
				do{
					k++;
				}while(a[8][8-k] == c1);
				if(a[8][8-k] == c2) {
					count++;
					l=0;
					do{
						l++;
					}while(a[8][8-k-l] == c2);
					if(a[8][8-k-l] == c1) {
						count++;
						count++;
					}
				}else if(a[8][8-k] == 0) {
					if(a[8][8-k-1] == c1) {
						count++;
						count++;
					}else if(a[8][8-k-1] == c2) {
						l=0;
						do{
							l++;
						}while(a[8][8-k-l] == c2);
						if(a[8][8-k-l] == c1) {
							count++;
							count++;
						}
					}
				}
			}
			if(a[7][8]==c1) {
				k=0;
				do{
					k++;
				}while(a[8-k][8] == c1);
				if(a[8-k][8] == c2) {
					count++;
					l=0;
					do{
						l++;
					}while(a[8-k-l][8] == c2);
					if(a[8-k-l][8] == c1) {
						count++;
						count++;
					}
				}else if(a[8-k][8] == 0) {
					if(a[8-k-1][8] == c1) {
						count++;
						count++;
					}else if(a[8-k-1][8] == c2) {
						l=0;
						do{
							l++;
						}while(a[8-k-l][8] == c2);
						if(a[8-k-l][8] == c1) {
							count++;
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	//出力
	public int get_select(int a) {		//打たれた手が0～63の表記の場合の記述
		if(a!=-1) {
			turnover(grids,a/8+1,a%8+1,yourColor,myColor);		//ひっくり返す
		}
		copy_grids(grids_possible,grids);		//盤面をコピー
		turnover_possible(grids_possible,grids,myColor,yourColor);//打てるマス記入
		search();		//～～～～～これでselect1が最適の手になる～～～～～



		//ここからテスト
		//CPUの盤面の評価
		System.out.println(point1[select1]);
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				System.out.printf("%6d",point1[8*i+j]);
			}
			System.out.println("");
		}
		System.out.println("");
		//ここまでテスト



		if(select1!=64) {
			turnover(grids,select1/8+1,select1%8+1,myColor,yourColor);		//ひっくり返す
		}
		if(select1==64){
			select1=-1;
		}
		return select1;
	}


}