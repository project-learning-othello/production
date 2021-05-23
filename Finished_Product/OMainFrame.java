import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
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

	public OMainFrame(){
		this.setTitle("メインウインドウ");
		this.setSize(8 * 45 + 25, 8 * 45 + 230);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(5, 1));

		this.tytleLabel = new JLabel("REVERSI");
		this.add(this.tytleLabel);

		this.pvpButton = new JButton("対人対戦");
		this.pvpButton.addActionListener(this);
		this.add(this.pvpButton);

		this.pveButton = new JButton("CPU対戦");
		this.pveButton.addActionListener(this);
		this.add(this.pveButton);

		this.optionButton = new JButton("設定");
		this.optionButton.addActionListener(this);
		this.add(this.optionButton);

		game = new Othello();
		player = new Player();
		option = new Option();
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
			OptionWindow subWindow = new OptionWindow(option, this, ModalityType.DOCUMENT_MODAL);
			subWindow.setLocation(this.getLocation().x, this.getLocation().y);
			subWindow.setVisible(true);
		}
	}
}