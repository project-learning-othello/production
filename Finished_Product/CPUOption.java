import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CPUOption extends JDialog implements ActionListener{

	private Othello game; // Othelloオブジェクト
	private Player player; // Playerオブジェクト
	private Option option; // Optionオブジェクト
	private Computer cpu;

	private JLabel nameLabel;

	private JButton start;

	private OMainFrame mainFrame;

	private ButtonGroup bg1 = new ButtonGroup();
	private ButtonGroup bg2 = new ButtonGroup();

	private JButton r11 = new JButton("先手");
	private JButton r12 = new JButton("後手");

	private JButton r21 = new JButton("easy");
	private JButton r22 = new JButton("normal");
	private JButton r23 = new JButton("hard");

	String mode;

	public CPUOption(Othello game, Player player, Option option, OMainFrame mainFrame, ModalityType mt){
		super(mainFrame, mt);
		this.mainFrame = mainFrame;

		this.game = game;
		this.player = player;
		this.option = option;

		this.setTitle("CPU設定");
		this.setSize(8 * 45 + 25, 8 * 45 + 230);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);

		this.nameLabel = new JLabel("CPU設定");
		nameLabel.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		nameLabel.setBounds(120, 20, 150, 50);
		this.add(this.nameLabel);

		this.nameLabel = new JLabel("先手後手");
		nameLabel.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		nameLabel.setBounds(115, 100, 300, 50);
		this.add(this.nameLabel);

		bg1.add(r11);
		bg1.add(r12);
		this.r11.setActionCommand("");
		this.r11.addActionListener(this);
		this.r12.setActionCommand("");
		this.r12.addActionListener(this);

		r11.setBounds(90, 180, 80, 50);
		r12.setBounds(200, 180, 80, 50);
		this.add(r11);
		this.add(r12);

		this.nameLabel = new JLabel("CPUの強さ");
		nameLabel.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		nameLabel.setBounds(110, 260, 300, 50);
		this.add(this.nameLabel);

		bg2.add(r21);
		bg2.add(r22);
		bg2.add(r23);
		this.r21.addActionListener(this);
		this.r22.addActionListener(this);
		this.r23.addActionListener(this);
		r21.setBounds(45, 340, 80, 50);
		r22.setBounds(140, 340, 80, 50);
		r23.setBounds(235, 340, 80, 50);
		this.add(r21);
		this.add(r22);
		this.add(r23);

		this.start = new JButton("対戦開始");
		this.start.addActionListener(this);
		start.setBounds(135, 440,90,50);
		this.add(this.start);

		player.setColor("black");
		mode = "easy";
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == this.r11){
			player.setColor("black");
		}
		if(e.getSource() == this.r12){
			player.setColor("white");
		}
		if(e.getSource() == this.r21){
			mode = "easy";
		}
		if(e.getSource() == this.r22){
			mode = "normal";
		}
		if(e.getSource() == this.r23){
			mode = "hard";
		}
		if(e.getSource() == this.start){
			cpu = new Computer(player.getColor(), mode);
			Client subWindow = new Client(game,player,option, cpu, false, ModalityType.DOCUMENT_MODAL);
			subWindow.setLocation(this.getLocation().x, this.getLocation().y);
			subWindow.setVisible(true);
		}
	}

}