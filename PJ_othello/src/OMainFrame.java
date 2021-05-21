import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class OMainFrame extends JFrame implements ActionListener {


	private JLabel nameLabel;

	JButton Online;

	JButton CPU;

	JButton Option;

	private Othello game; // Othelloオブジェクト
	private Player player; // Playerオブジェクト

	public void actionPerformed(ActionEvent e){

//		if(e.getSource() == this.Online){
//			Client subWindow = new Client(game,player, ModalityType.DOCUMENT_MODAL);
//			subWindow.setLocation(this.getLocation().x + (this.getWidth() - subWindow.getWidth()) / 2, this.getLocation().y + this.getHeight());
//			subWindow.setVisible(true);
//			subWindow.connectServer("localhost", 10001);
//		}
		if(e.getSource() == this.CPU){
			CPUOption subWindow = new CPUOption(this, ModalityType.DOCUMENT_MODAL);
			subWindow.setLocation(this.getLocation().x + (this.getWidth() - subWindow.getWidth()) / 2, this.getLocation().y + this.getHeight());
			subWindow.setVisible(true);
		}
		if(e.getSource() == this.Option){
			Option subWindow = new Option(this, ModalityType.DOCUMENT_MODAL);
			subWindow.setLocation(this.getLocation().x + (this.getWidth() - subWindow.getWidth()) / 2, this.getLocation().y + this.getHeight());
			subWindow.setVisible(true);
		}
	}

	public OMainFrame(){
		this.setTitle("メインウインドウ");
		this.setSize(8 * 45 + 25, 8 * 45 + 230);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(5, 1));

		this.nameLabel = new JLabel("REVERSI");
		this.add(this.nameLabel);

		this.nameLabel = new JLabel(" ");
		this.add(this.nameLabel);

		this.Online = new JButton("対人対戦");
		this.Online.addActionListener(this);
		this.add(this.Online);

		this.CPU = new JButton("CPU対戦");
		this.CPU.addActionListener(this);
		this.add(this.CPU);

		this.Option = new JButton("設定");
		this.Option.addActionListener(this);
		this.add(this.Option);
	}

}