import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class Option extends JFrame implements ActionListener {


	private JLabel nameLabel;

	private ImageIcon whiteIcon;

	private ImageIcon blackIcon;

	ButtonGroup bg1 = new ButtonGroup();

	ButtonGroup bg2 = new ButtonGroup();

	JRadioButton r11 = new JRadioButton(blackIcon);
	JRadioButton r12 = new JRadioButton(whiteIcon);
	JRadioButton r13 = new JRadioButton(whiteIcon);

	JRadioButton r21 = new JRadioButton(blackIcon);
	JRadioButton r22 = new JRadioButton(blackIcon);
	JRadioButton r23 = new JRadioButton(blackIcon);

	public void actionPerformed(ActionEvent e){

		if(e.getSource() == this.r11){
			//石のデザイン変更；
		}
		if(e.getSource() == this.r12){
			//石のデザイン変更；
		}
		if(e.getSource() == this.r13){
			//石のデザイン変更；
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

	public Option(OMainFrame mainFrame, ModalityType mt){

		this.setTitle("メインウインドウ");
		this.setSize(8 * 45 + 25, 8 * 45 + 230);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(5, 1));

		this.nameLabel = new JLabel("設定");
		this.add(this.nameLabel);

		this.nameLabel = new JLabel("石のデザインの変更");
		this.add(this.nameLabel);

		bg1.add(r11);
		bg1.add(r12);
		bg1.add(r13);
		this.r11.setActionCommand("");
		this.r11.addActionListener(this);
		this.r12.setActionCommand("");
		this.r12.addActionListener(this);
		this.r13.setActionCommand("");
		this.r13.addActionListener(this);
		this.add(r11);
		this.add(r12);
		this.add(r13);

		this.nameLabel = new JLabel("盤面変更");
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
		this.add(r21);
		this.add(r22);
		this.add(r23);

	}

}
