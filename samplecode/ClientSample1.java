//�p�b�P�[�W�̃C���|�[�g
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientSample1 extends JFrame implements MouseListener {
	private JButton buttonArray[];//�I�Z���՗p�̃{�^���z��
	private JButton stop, pass; //��~�A�X�L�b�v�p�{�^��
	private JLabel colorLabel; // �F�\���p���x��
	private JLabel turnLabel; // ��ԕ\���p���x��
	private Container c; // �R���e�i
	private ImageIcon blackIcon, whiteIcon, boardIcon; //�A�C�R��

	// �R���X�g���N�^
	public ClientSample1() {
		//�e�X�g�p�ɋǖʏ���������
		String [] grids = 
			{"board","board","board","board","board","board","board","board",
			"board","board","board","board","board","board","board","board",
			"board","board","board","board","board","board","board","board",
			"board","board","board","black","white","board","board","board",
			"board","board","board","white","black","board","board","board",
			"board","board","board","board","board","board","board","board",
			"board","board","board","board","board","board","board","board",
			"board","board","board","board","board","board","board","board"};
		int row = 8; //�I�Z���Ղ̏c���}�X�̐�
		//�E�B���h�E�ݒ�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�E�B���h�E�����ꍇ�̏���
		setTitle("�l�b�g���[�N�ΐ�^�I�Z���Q�[��");//�E�B���h�E�̃^�C�g��
		setSize(row * 45 + 10, row * 45 + 200);//�E�B���h�E�̃T�C�Y��ݒ�
		c = getContentPane();//�t���[���̃y�C�����擾
		//�A�C�R���ݒ�(�摜�t�@�C�����A�C�R���Ƃ��Ďg��)
		whiteIcon = new ImageIcon("White.jpg");
		blackIcon = new ImageIcon("Black.jpg");
		boardIcon = new ImageIcon("GreenFrame.jpg");
		c.setLayout(null);//
		//�I�Z���Ղ̐���
		buttonArray = new JButton[row * row];//�{�^���̔z����쐬
		for(int i = 0 ; i < row * row ; i++){
			if(grids[i].equals("black")){ buttonArray[i] = new JButton(blackIcon);}//�Ֆʏ�Ԃɉ������A�C�R����ݒ�
			if(grids[i].equals("white")){ buttonArray[i] = new JButton(whiteIcon);}//�Ֆʏ�Ԃɉ������A�C�R����ݒ�
			if(grids[i].equals("board")){ buttonArray[i] = new JButton(boardIcon);}//�Ֆʏ�Ԃɉ������A�C�R����ݒ�
			c.add(buttonArray[i]);//�{�^���̔z����y�C���ɓ\��t��
			// �{�^����z�u����
			int x = (i % row) * 45;
			int y = (int) (i / row) * 45;
			buttonArray[i].setBounds(x, y, 45, 45);//�{�^���̑傫���ƈʒu��ݒ肷��D
			buttonArray[i].addMouseListener(this);//�}�E�X�����F���ł���悤�ɂ���
			buttonArray[i].setActionCommand(Integer.toString(i));//�{�^�������ʂ��邽�߂̖��O(�ԍ�)��t������
		}
		//�I���{�^��
		stop = new JButton("�I��");//�I���{�^�����쐬
		c.add(stop); //�I���{�^�����y�C���ɓ\��t��
		stop.setBounds(0, row * 45 + 30, (row * 45 + 10) / 2, 30);//�I���{�^���̋��E��ݒ�
		stop.addMouseListener(this);//�}�E�X�����F���ł���悤�ɂ���
		stop.setActionCommand("stop");//�{�^�������ʂ��邽�߂̖��O��t������
		//�p�X�{�^��
		pass = new JButton("�p�X");//�p�X�{�^�����쐬
		c.add(pass); //�p�X�{�^�����y�C���ɓ\��t��
		pass.setBounds((row * 45 + 10) / 2, row * 45 + 30, (row * 45 + 10 ) / 2, 30);//�p�X�{�^���̋��E��ݒ�
		pass.addMouseListener(this);//�}�E�X�����F���ł���悤�ɂ���
		pass.setActionCommand("pass");//�{�^�������ʂ��邽�߂̖��O��t������
		//�F�\���p���x��
		colorLabel = new JLabel("�F�͖���ł�");//�F����\�����邽�߂̃��x�����쐬
		colorLabel.setBounds(10, row * 45 + 60 , row * 45 + 10, 30);//���E��ݒ�
		c.add(colorLabel);//�F�\���p���x�����y�C���ɓ\��t��
		//��ԕ\���p���x��
		turnLabel = new JLabel("��Ԃ͖���ł�");//��ԏ���\�����邽�߂̃��x�����쐬
		turnLabel.setBounds(10, row * 45 + 120, row * 45 + 10, 30);//���E��ݒ�
		c.add(turnLabel);//��ԏ�񃉃x�����y�C���ɓ\��t��
	}

	// ���\�b�h
	public void connectServer(String ipAddress, int port){	// �T�[�o�ɐڑ�
	}
	public void sendMessage(String msg){	// �T�[�o�ɑ�����𑗐M
	}
	public void receiveMessage(String msg){	// ���b�Z�[�W�̎�M
	}
	public void updateDisp(){	// ��ʂ��X�V����
	}
	public void acceptOperation(String command){	// �v���C���̑������t
	}
  	//�}�E�X�N���b�N���̏���
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//�N���b�N�����I�u�W�F�N�g�𓾂�D�L���X�g��Y�ꂸ��
		String command = theButton.getActionCommand();//�{�^���̖��O�����o��
		System.out.println("�}�E�X���N���b�N����܂����B�����ꂽ�{�^���� " + command + "�ł��B");//�e�X�g�p�ɕW���o��
	}
	public void mouseEntered(MouseEvent e) {}//�}�E�X���I�u�W�F�N�g�ɓ������Ƃ��̏���
	public void mouseExited(MouseEvent e) {}//�}�E�X���I�u�W�F�N�g����o���Ƃ��̏���
	public void mousePressed(MouseEvent e) {}//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏���
	public void mouseReleased(MouseEvent e) {}//�}�E�X�ŉ����Ă����I�u�W�F�N�g�𗣂����Ƃ��̏���
	//�e�X�g�p��main
	public static void main(String args[]){ 
		ClientSample1 oclient = new ClientSample1();
		oclient.setVisible(true);
	}
}