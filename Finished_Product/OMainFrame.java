import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class OMainFrame extends JFrame implements ActionListener {
	private JLabel tytleLabel; // タイトル用ラベル
	private JButton pvpButton; // 対人戦用ボタン
	private JButton pveButton; // コンピュータ対戦用ボタン
	private JButton optionButton; // 設定用ボタン

	private Othello game; // Othelloオブジェクト
	private Player player; // Playerオブジェクト
	private Option option; // Optionオブジェクト
	private Computer cpu;

	private Container c;

	public OMainFrame(){

		this.setTitle("メインウインドウ");
		this.setSize(8 * 45 + 25, 8 * 45 + 230);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);

		tytleLabel = new JLabel("REVERSI");
		tytleLabel.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 50));
		tytleLabel.setBounds(90, 50, 300, 100);
		add(this.tytleLabel);

		pvpButton = new JButton("対人対戦");
		pvpButton.addActionListener(this);
		pvpButton.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		pvpButton.setBounds(60, 200, 250, 80);
		add(this.pvpButton);

		pveButton = new JButton("CPU対戦");
		pveButton.addActionListener(this);
		pveButton.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		pveButton.setBounds(60, 300, 250, 80);
		add(this.pveButton);

		optionButton = new JButton("設定");
		optionButton.addActionListener(this);
		optionButton.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		optionButton.setBounds(60, 400, 250, 80);
		add(this.optionButton);

		game = new Othello();
		player = new Player();
		option = new Option();

		c = getContentPane(); //フレームのペインを取得
		c.setBackground(option.getBackColor()); // 背景色

	}

	public void actionPerformed(ActionEvent e){

		if(e.getSource() == this.pvpButton){
			Client subWindow = new Client(game, player, option, cpu, true, ModalityType.DOCUMENT_MODAL);
		    subWindow.setLocation(this.getLocation().x, this.getLocation().y);
			subWindow.setVisible(true);
			subWindow.connectServer("localhost", 10001);
		}
		if(e.getSource() == this.pveButton){
			CPUOption subWindow = new CPUOption(game, player, option, this, ModalityType.DOCUMENT_MODAL);
			subWindow.setLocation(this.getLocation().x, this.getLocation().y);
			subWindow.setVisible(true);
		}
		if(e.getSource() == this.optionButton){
			OptionWindow subWindow = new OptionWindow(option, this, ModalityType.DOCUMENT_MODAL, c);
			subWindow.setLocation(this.getLocation().x, this.getLocation().y);
			subWindow.setVisible(true);
		}
	}
}