# プロジェクトラーニング　制作

## 最新版

・Finished_Product

　`Finished_Product`内のソースコード、画像を全部ダウンロードして、  
`Othellostart.java`を実行すれば動く.

## やること

### OMainFrame

・タイトルデザイン ..... 完了（5/24 12:00）


### CPUOption

・押したときにボタンの色を赤色に変更する.  ..... 完了（5/24 13:00）<br>
・先手後手のボタンの横に駒の画像を追加する.(あってもなくてもいい) ..... 完了（5/24 13:00）

### Client

・コンピュータが打った後に待つ処理を追加する. なぜかできなくて困り中.  
・一台目のコンピュータが接続後、二台目が接続するまで操作できないようにする. ..... 完了（5/24 17:00）<br>

### OMainFrame, CPUOption, Option

・背景の設定 ..... 完了（5/24 13:43）<br>
　Clientの背景設定を参考にしたらすぐ終わるはず. 

```
Container c;

c = getContentPane(); //フレームのペインを取得
c.setBackground(option.getBackColor()); // 背景色
・
・
・
c.add(～); // ボタンをcに貼りつける
```

### パワーポイント

### 動作確認テスト

・5/28まで

### マニュアル

・5/28まで

## 変更点

・`OMainFrame`で設定をおしたときに`OptionWindow`クラスに移るようにした.  
・`Option`クラスは内部設定  
・`VScpu`クラスはなくして、`Client`クラスと統合した  
・どいちゃんには悪いけど、戻るボタン消しました. ウインドウのxボタンで戻れるみたいなので...
