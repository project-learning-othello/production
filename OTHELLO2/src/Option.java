import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Color;



public class Option extends JFrame implements ActionListener {


	private JLabel nameLabel1, nameLabel2, nameLabel3, nameLabel4;

	ButtonGroup bg1 = new ButtonGroup();

	ButtonGroup bg2 = new ButtonGroup();

	JRadioButton r11;
	JRadioButton r12;
	JRadioButton r13;

	JRadioButton r21;
	JRadioButton r22;
	JRadioButton r23;

	private String StoneImage1, StoneImage2;

	ImageIcon icon11, icon12, icon13, icon21, icon22, icon23,
	 icon11_selected, icon12_selected, icon13_selected, icon21_selected, icon22_selected, icon23_selected;

	LineBorder border;

	public Option(OMainFrame mainFrame, ModalityType mt){

		this.setTitle("メインウインドウ");
		this.setSize(8 * 45 + 25, 8 * 45 + 230);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setLayout(new FlowLayout());
		this.setLayout(null);

		StoneImage1 = "White.jpg";
		StoneImage2 = "Black.jpg";
		
		nameLabel1 = new JLabel("設定");
		//nameLabel1.setBackground(Color.RED);

		//nameLabel1.setPreferredSize(new Dimension(400, 200));
		nameLabel1.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		nameLabel1.setBounds(150, 20, 150, 50);
		add(nameLabel1);

		border = new LineBorder(Color.BLACK, 2, true);

		nameLabel4 = new JLabel("戻る");
		nameLabel4.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 15));
		nameLabel4.setBounds(20, 20, 50, 40);
		nameLabel4.setBorder(border);
		nameLabel4.setHorizontalAlignment(JLabel.CENTER);
		add(nameLabel4);
		

		nameLabel2 = new JLabel("石のデザインの変更");
		nameLabel2.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		nameLabel2.setBounds(50, 100, 300, 50);
		add(nameLabel2);

		icon11 = new ImageIcon("Black.jpg");
		icon12 = new ImageIcon("Five_B.jpg");
		icon13 = new ImageIcon("Star_B.jpg");
		icon21 = new ImageIcon("BackGround1.jpg");
		icon22 = new ImageIcon("BackGround2.jpg");
		icon23 = new ImageIcon("BackGround3.jpg");

		icon11_selected = new ImageIcon("Black_O.jpg");
		icon12_selected = new ImageIcon("Five_O.jpg");
		icon13_selected = new ImageIcon("Star_O.jpg");
		icon21_selected = new ImageIcon("BackGround1_O.jpg");
		icon22_selected = new ImageIcon("BackGround2_O.jpg");
		icon23_selected = new ImageIcon("BackGround3_O.jpg");

		r11 = new JRadioButton(icon11, true);
		r12 = new JRadioButton(icon12);
		r13 = new JRadioButton(icon13);
		r21 = new JRadioButton(icon21, true);
		r22 = new JRadioButton(icon22);
		r23 = new JRadioButton(icon23);

		bg1.add(r11);
		bg1.add(r12);
		bg1.add(r13);
		r11.setActionCommand("");
		r11.addActionListener(this);
		r12.setActionCommand("");
		r12.addActionListener(this);
		r13.setActionCommand("");
		r13.addActionListener(this);

		r11.setBounds(80, 200, 50, 50);
		r11.setSelectedIcon(icon11_selected);
		add(r11);

		r12.setBounds(160, 200, 50, 50);
		r12.setSelectedIcon(icon12_selected);
		add(r12);

		r13.setBounds(240, 200, 50, 50);
		r13.setSelectedIcon(icon13_selected);
		add(r13);



		nameLabel3 = new JLabel("背景変更");
		nameLabel3.setFont(new Font( "ＭＳ ゴシック" , Font.BOLD, 30));
		nameLabel3.setBounds(125, 320, 300, 50);
		add(nameLabel3);



		bg2.add(r21);
		bg2.add(r22);
		bg2.add(r23);
		r21.setActionCommand("");
		r21.addActionListener(this);
		r22.setActionCommand("");
		r22.addActionListener(this);
		r23.setActionCommand("");
		r23.addActionListener(this);

		r21.setBounds(80, 420, 50, 50);
		r21.setSelectedIcon(icon21_selected);
		add(r21);

		r22.setBounds(160, 420, 50, 50);
		r22.setSelectedIcon(icon22_selected);
		add(r22);

		r23.setBounds(240, 420, 50, 50);
		r23.setSelectedIcon(icon23_selected);
		add(r23);

	}

	public String getWhiteImage(){
		return StoneImage1;
	} 

	public String getBlackImage(){
		return StoneImage2;
	}

	public void actionPerformed(ActionEvent e){

		if(e.getSource() == this.r11){
			//石のデザイン変更；
			StoneImage1 = "White.jpg";
			StoneImage2 = "Black.jpg";
		}
		if(e.getSource() == this.r12){
			//石のデザイン変更；
			StoneImage1 = "Five_W.jpg";
			StoneImage2 = "Five_B.jpg";
		}
		if(e.getSource() == this.r13){
			//石のデザイン変更；
			StoneImage1 = "Star_W.jpg";
			StoneImage2 = "Star_B.jpg";
		}
		if(e.getSource() == this.r21){
			//盤面のデザイン変更；
		}
		if(e.getSource() == this.r22){
			//盤面のデザイン変更；
		}
		if(e.getSource() == this.r23){
			//盤面のデザイン変更；
		}
	}

}
