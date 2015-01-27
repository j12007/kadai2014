package threeDimentionalCG;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import myVectorUtil.Vect3;
import myVectorUtil.Vect3DBZException;

public class ThreeDimentionalCG extends Frame implements ActionListener,MouseListener {

	//���j���[
	private MenuBar mbar = new MenuBar();
	private Menu menuFile = new Menu("�t�@�C��");
	private MenuItem mitemSave = new MenuItem("�ۑ�");  //(1)
	private MenuItem mitemExit = new MenuItem("�I��");

	//���̍��W�C�ݒ�
	private double centerX = 200.0;  //(2) ���̒��S��x���W
	private double centerY = 150.0;  //(3) ���̒��S��y���W
	private double r = 100.0;        //(4) ���̔��ar
	private double r2 = 10000.0;     //(5) ���̔��a(r)��2��
	private int offsetX = 60;        //(6) ���]��
	private int offsetY = 210;       //(7) ��]��

	//�p�l��
	private Panel pnlSettings = new Panel( new GridLayout(6,2) );
	private Panel pnlButton = new Panel( new FlowLayout() );

	//�{�^���E�e�L�X�g�t�B�[���h
	private Button btnRedraw = new Button("�ĕ`��");
	private TextField txIa = new TextField("0.4");
	private TextField txWa = new TextField("0.5");
	private TextField txI = new TextField("1.0");
	private TextField txWd = new TextField("0.6");
	private TextField txWs = new TextField("0.3");
	private TextField txN = new TextField("6.0");

	//�����̌���(x,y)
		//�����������ʒu(200+60,150+210)
		private Point mousePt = new Point(260, 360);

	//�摜�C���[�W
	private BufferedImage image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
	//(8)
	//main���\�b�h
	public static void main(String args[]){
		ThreeDimentionalCG fm = new ThreeDimentionalCG();
		fm.setSize(550, 550);
		fm.setVisible(true);
	}

	//�R���X�g���N�^
	public ThreeDimentionalCG(){

		//�I���{�^����L���ɂ���
		addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent evt) {
						System.exit(0);
					}
				}
				);
		//�^�C�g����ݒ�
		setTitle("3DCG");
		//���C�A�E�g�̐ݒ�
		setLayout(new FlowLayout());

		//�R���|�[�l���g��o�^
		menuFile.add(mitemSave);
		menuFile.add(mitemExit);
		mbar.add(menuFile);
		setMenuBar(mbar);

		//�R���|�[�l���g��o�^ (�ǉ�)
		//�p�l����o�^
		add(pnlSettings);
		add(pnlButton);

		//�ݒ�p�l���Ƀ��x���ƃe�L�X�g�t�B�[���h��o�^
		pnlSettings.add(new Label("Ia"));
		pnlSettings.add( txIa );
		pnlSettings.add(new Label("Wa"));
		pnlSettings.add( txWa );
		pnlSettings.add(new Label("I"));
		pnlSettings.add( txI );
		pnlSettings.add(new Label("Wd"));
		pnlSettings.add( txWd );
		pnlSettings.add(new Label("Ws"));
		pnlSettings.add( txWs );
		pnlSettings.add(new Label("n"));
		pnlSettings.add( txN );
		//�{�^���p�l���Ƀ{�^����o�^
		pnlButton.add(btnRedraw);



		//���X�i�[�N���X�̓o�^
		mitemSave.addActionListener(this);
		mitemExit.addActionListener(this);
		btnRedraw.addActionListener(this);
		addMouseListener(this);


	}

	//�C�x���g�n���h��
	public void actionPerformed(ActionEvent ev) {

		//�I�� ��I�������Ƃ��̏���
		if (ev.getSource() == mitemExit){
			//�v���O�������I������
			System.exit(0);
		}

		//�ۑ� ��I�������Ƃ��̏���
		else if (ev.getSource() == mitemSave){  //(9)

			// �摜�t�@�C���̏����o��
			//�t�@�C�����̎擾
			FileDialog fdialog = new FileDialog(this, "�ۑ�", FileDialog.SAVE);
			fdialog.setVisible(true);
			String fileName = fdialog.getDirectory()+fdialog.getFile();

			//png�t�@�C�����J���CreadImage�ɓǂݍ���
			try {
				//image��png�`���Ńt�@�C���ɕۑ�����
				ImageIO.write( image, "png", new File( fileName ));
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		//�ĕ`��{�^�����������Ƃ��̏��� (�ǉ�)
		else if (ev.getSource() == btnRedraw){

			//��ʂ��ĕ`�悷��
			repaint();
		}

	}

	//paint���\�b�h
	public void paint(Graphics g){

		//�����̖��邳�C���˗���ǂݍ���
		double Ia = Double.parseDouble(txIa.getText());
		double Wa = Double.parseDouble(txWa.getText());
		double I  = Double.parseDouble(txI.getText());
		double Wd = Double.parseDouble(txWd.getText());
		double Ws = Double.parseDouble(txWs.getText());
		double n  = Double.parseDouble(txN.getText());


		//�摜��`��
		for (int i = 0; i < 400; i++){    //(10)
			for (int j = 0; j < 300; j++){  //(11)

				//�摜�̑S�Ẵh�b�g�ɂ��āC���̓������O�����𒲂ׂ�
				//���̒��S����C���݂̓_�܂ł�x�����̋���
				double rx = i - centerX;  //(12)
				//���̒��S����C���݂̓_�܂ł�y�����̋���
				double ry = j - centerY;  //(13)
				// rx��2��
				double rx2 = Math.pow( rx, 2);  //(14)
				// ry��2��
				double ry2 = Math.pow( ry, 2);  //(15)
				// ���̒��S����̋�����2��
				double l2 = rx2 + ry2;        //(16)

				// ���������a��菬������΋��ʏ�̓_(2��Ŕ�r���Ă���)
				if (l2 <= r2){  //(17)
					//���𔒂ŕ`��
					//���̃V�F�[�f�B���O
					try { 
						//���ʂ̕`��
						//���̒��S����C���݂̓_(���ʏ�)�܂ł�z�����̋���(rx)�̂Q��
						double rz2 = r2 - (rx2 + ry2); 
						//�� rz2���[����菬������΁C�[�������Ă���
						if (rz2 < 0.0) rz2 = 0.0; 
						//���̒��S����C���݂̓_(���ʏ�)�܂ł�z�����̋���
						double rz = Math.sqrt(rz2);
						//�ʂ̖@���x�N�g�� (�P�ʃx�N�g��)
						Vect3 unitNormalV = new Vect3(rx, ry, rz);
						//unitNormalV��P�ʃx�N�g���ɂ��Ă���
						unitNormalV = unitNormalV.unitVector();
						
						//�}�E�X�̈ʒu��������̌�����ݒ肷�� (�ǉ�)
						//�����x�N�g����x��������
						double rayX = (mousePt.getX() - offsetX) - centerX;  //(1)
						//�����x�N�g����y��������
						double rayY = (mousePt.getY() - offsetY) - centerY;  //(2)
						//ray_x�̂Q��
						double rayL2 = rayX*rayX + rayY*rayY;  //(3)
						//�����x�N�g����z��������(0.0�ŏ�����)
						double rayZ = 0.0;   //(4)
						//�����̈ʒu������̓_�Ȃ�Cz�������������߂�
						if (rayL2 <= r2) {   //(5)
							rayZ = Math.sqrt( r2 - rayL2 );
						}
						//�����x�N�g���̌�����ݒ�
						Vect3 rayV = new Vect3(rayX, rayY, rayZ);  //(6)
						
						//���ˌ��̌����̃x�N�g����P�ʃx�N�g���ɕϊ����Ă���
						rayV = rayV.unitVector();
						//���ˌ��Ɩʂ̖@���̂Ȃ��p�Ƃ̗]��
						double cosTheta = unitNormalV.cos(rayV);
						
						//�g�U���˂ɂ�閾�邳 (0.0�ŏ�����)
						double Id = 0.0;
						//���ʔ��˂ɂ�閾�邳 (0.0�ŏ�����)
						double Is = 0.0;
						
						//cosTheta�����Ȃ�C�ʂ͓��ˌ��̉A�ɂȂ��Ă���̂ŁCIs��Id�͂O
						if (cosTheta >= 0.0){
							
							//�g�U���˂̌v�Z
							Id = I * Wd * cosTheta;
							
							//���ʔ��˂̌v�Z
							//�����̌���(�f�B�X�v���C�ɐ���:��������)
							Vect3 eyeV = new Vect3(0.0, 0.0, 1.0);
							//�����˕����x�N�g���̖@����������
							Vect3 refNormalV = unitNormalV.multi( 
									unitNormalV.scalarProduct(rayV) );
							//�����˕����x�N�g��
							Vect3 refV = refNormalV.multi( 2.0 ).sub( rayV );
							//�����˕����Ǝ��������̂Ȃ��p���̗]��
							double cosAlpha = refV.cos(eyeV);
							//�����˕�������X�O�x�ȏ�(cosAlpha����)�̂Ƃ��́C���邳�̓[��
							if (cosAlpha >= 0.0){
								Is = I * Ws * Math.pow(cosAlpha, n);
							}
						}
						
						//���邳�̍��v
						double Ir = Wa * Ia + Id + Is;
						//�� ���邳�̍ő�l�͂P
						if (Ir > 1.0) Ir = 1.0;
						//�h�b�g�̐F���L������Color�N���X�I�u�W�F�N�g�̐���
						Color color = new Color( (int)(255*Ir), (int)(255*Ir), (int)(255*Ir) );
						//�h�b�g�̐F��ݒ�
						image.setRGB( i, j, color.getRGB() );
					}
					catch(Vect3DBZException ex){
						// ��O��������ꂽ�甒��`��
						Color color = new Color(255, 255, 255);
						image.setRGB( i, j, color.getRGB() );
					}
				}



				else {
					// �w�i�����ŕ`��
					image.setRGB( i, j, Color.BLACK.getRGB() ); //(19)
				}
			}
		}

		//BufferedImage�I�u�W�F�N�g�̉摜image��\������
		if (image != null){
			g.drawImage(image, offsetX, offsetY, this);   //(20)
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		//�}�E�X���N���b�N���ꂽ���W���擾
				mousePt = e.getPoint();   //(1)
				
				//��ʂ��ĕ`�悷��
				repaint();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}
}
