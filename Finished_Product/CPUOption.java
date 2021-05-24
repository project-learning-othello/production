import java.awt.Color;
import java.awt.Container;
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
import javax.swing.border.LineBorder;


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

	private JRadioButton RButtonBlack, RButtonWhite;

	String mode;

	LineBorder border = new LineBorder(Color.RED, 2, true);

	ImageIcon BlackImg, WhiteImg;

	private Container c;

	public CPUOption(Othello game, Player player, Option option, OMainFrame mainFrame, ModalityType mt){
		super(mainFrame, mt);
		this.mainFrame = mainFrame;

		this.game = game;
		this.player = player;
		this.option = option;

		c = getContentPane(); //フレームのペインを取得
		c.setBackground(option.getBackColor()); // 背景色


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

		r11.setBorder(border);
		r12.setBorderPainted(false);

		this.r11.setActionCommand("");
		this.r11.addActionListener(this);
		this.r12.setActionCommand("");
		this.r12.addActionListener(this);

		r11.setBounds(40, 180, 80, 50);
		r12.setBounds(200, 180, 80, 50);
		this.add(r11);
		this.add(r12);

		BlackImg = new ImageIcon(option.getBlackImage());
		WhiteImg = new ImageIcon(option.getWhiteImage());

		RButtonBlack = new JRadioButton(BlackImg);
		RButtonWhite = new JRadioButton(WhiteImg);

		RButtonBlack.setBounds(130, 180, 50, 50);
		RButtonWhite.setBounds(290, 180, 50, 50);

		add(RButtonBlack);
		add(RButtonWhite);

		this.nameLabel = new JLabel("CPUの強さ");
		nameLabel.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		nameLabel.setBounds(110, 260, 300, 50);
		this.add(this.nameLabel);

		bg2.add(r21);
		bg2.add(r22);
		bg2.add(r23);

		r21.setBorder(border);
		r22.setBorderPainted(false);
		r23.setBorderPainted(false);

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
			r12.setBorderPainted(false);
			r11.setBorderPainted(true);
			r11.setBorder(border);

		}
		if(e.getSource() == this.r12){
			player.setColor("white");
			r11.setBorderPainted(false);
			r12.setBorderPainted(true);
			r12.setBorder(border);

		}
		if(e.getSource() == this.r21){
			mode = "easy";

			r22.setBorderPainted(false);
			r23.setBorderPainted(false);
			r21.setBorderPainted(true);
			r21.setBorder(border);

		}
		if(e.getSource() == this.r22){
			mode = "normal";

			r21.setBorderPainted(false);
			r23.setBorderPainted(false);
			r22.setBorderPainted(true);
			r22.setBorder(border);

		}
		if(e.getSource() == this.r23){
			mode = "hard";

			r21.setBorderPainted(false);
			r22.setBorderPainted(false);
			r23.setBorderPainted(true);
			r23.setBorder(border);

		}
		if(e.getSource() == this.start){
			cpu = new Computer(player.getColor(), mode);
			Client subWindow = new Client(game,player,option, cpu, false, ModalityType.DOCUMENT_MODAL);
			subWindow.setLocation(this.getLocation().x, this.getLocation().y);
			subWindow.setVisible(true);
		}
	}

}