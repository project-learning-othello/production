import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class CPUOption extends JDialog implements ActionListener{

	private Othello game; // Othelloオブジェクト
	private Player player; // Playerオブジェクト
	private Computer cpu;

	private JLabel nameLabel;

	private ImageIcon whiteIcon;

	private ImageIcon blackIcon;

	JButton start;

	OMainFrame mainFrame;

	ButtonGroup bg1 = new ButtonGroup();

	ButtonGroup bg2 = new ButtonGroup();

	JRadioButton r11 = new JRadioButton("先手",true);
	JRadioButton r12 = new JRadioButton("後手");

	JRadioButton r21 = new JRadioButton("easy",true);
	JRadioButton r22 = new JRadioButton("normal");
	JRadioButton r23 = new JRadioButton("hard");

	String mode;

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == this.r11){
			player.setColor("black");
		}
		if(e.getSource() == this.r12){
			player.setColor("white");
		}
		if(e.getSource() == this.r21){
			cpu = new Computer(player.getColor(), "easy");
		}
		if(e.getSource() == this.r22){
			cpu = new Computer(player.getColor(), "normal");
		}
		if(e.getSource() == this.r23){
			cpu = new Computer(player.getColor(), "hard");
		}
		if(e.getSource() == this.start){
			VSCPU subWindow = new VSCPU(game,player,cpu, ModalityType.DOCUMENT_MODAL);
			subWindow.setLocation(this.getLocation().x + this.getWidth() , this.getLocation().y);
			subWindow.setVisible(true);
		}
	}

	public CPUOption(OMainFrame mainFrame, ModalityType mt){
		super(mainFrame, mt);

		whiteIcon = new ImageIcon("White.jpg");
		blackIcon = new ImageIcon("Black.jpg");

		this.mainFrame = mainFrame;
		game = new Othello();
		player = new Player();

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

		r11.setBounds(90, 180, 50, 50);
		r12.setBounds(220, 180, 50, 50);
		this.add(r11);
		this.add(r12);

		this.nameLabel = new JLabel("CPUの強さ");
		nameLabel.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		nameLabel.setBounds(110, 260, 300, 50);
		this.add(this.nameLabel);

		bg2.add(r21);
		bg2.add(r22);
		bg2.add(r23);
		this.r21.setActionCommand("");
		this.r21.addActionListener(this);
		this.r22.setActionCommand("");
		this.r22.addActionListener(this);
		this.r23.setActionCommand("");
		this.r23.addActionListener(this);
		r21.setBounds(60, 340, 70, 50);
		r22.setBounds(140, 340, 70, 50);
		r23.setBounds(250, 340, 70, 50);
		this.add(r21);
		this.add(r22);
		this.add(r23);

		this.start = new JButton("対戦開始");
		this.start.addActionListener(this);
		start.setBounds(130, 430,90,50);
		this.add(this.start);

	}

}