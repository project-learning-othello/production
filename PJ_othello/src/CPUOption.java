import java.awt.GridLayout;
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

	private JLabel nameLabel;

	private ImageIcon whiteIcon;

	private ImageIcon blackIcon;

	JButton start;

	OMainFrame mainFrame;

	ButtonGroup bg1 = new ButtonGroup();

	ButtonGroup bg2 = new ButtonGroup();

	JRadioButton r11 = new JRadioButton("先手",blackIcon);
	JRadioButton r12 = new JRadioButton("後手",whiteIcon);

	JRadioButton r21 = new JRadioButton("easy");
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
			mode="easy";
		}
		if(e.getSource() == this.r22){
			mode="normal";
		}
		if(e.getSource() == this.r23){
			mode="hard";
		}
		if(e.getSource() == this.start){
			VSCPU subWindow = new VSCPU(game,player,mode, ModalityType.DOCUMENT_MODAL);
			subWindow.setLocation(this.getLocation().x + (this.getWidth() - subWindow.getWidth()) / 2, this.getLocation().y + this.getHeight());
			subWindow.setVisible(true);
		}
	}

	public CPUOption(OMainFrame mainFrame, ModalityType mt){
		super(mainFrame, mt);

		game = new Othello();
		player = new Player();

		whiteIcon = new ImageIcon("White.jpg");
		blackIcon = new ImageIcon("Black.jpg");

		this.mainFrame = mainFrame;

		this.setTitle("CPU設定");
		this.setSize(8 * 45 + 25, 8 * 45 + 230);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(6, 1));

		this.nameLabel = new JLabel("CPU設定");
		this.add(this.nameLabel);

		this.nameLabel = new JLabel("先手後手");
		this.add(this.nameLabel);

		bg1.add(r11);
		bg1.add(r12);
		this.r11.setActionCommand("black");
		this.r11.addActionListener(this);
		this.r12.setActionCommand("white");
		this.r12.addActionListener(this);
		this.add(r11);
		this.add(r12);

		this.nameLabel = new JLabel("CPUの強さ");
		this.add(this.nameLabel);

		bg2.add(r21);
		bg2.add(r22);
		bg2.add(r23);
		this.r21.setActionCommand("easy");
		this.r21.addActionListener(this);
		this.r22.setActionCommand("normal");
		this.r22.addActionListener(this);
		this.r23.setActionCommand("hard");
		this.r23.addActionListener(this);
		this.add(r21);
		this.add(r22);
		this.add(r23);

		this.start = new JButton("対戦開始");
		this.start.addActionListener(this);
		this.add(this.start);

	}

}