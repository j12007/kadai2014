package threeDimentionalCG;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import myVectorUtil.Vect3;
import myVectorUtil.Vect3DBZException;

public class ThreeDimentionalCG extends Frame implements ActionListener,MouseListener {

	//メニュー
	private MenuBar mbar = new MenuBar();
	private Menu menuFile = new Menu("ファイル");
	private MenuItem mitemSave = new MenuItem("保存");  //(1)
	private MenuItem mitemExit = new MenuItem("終了");

	//球の座標，設定
	private double centerX = 200.0;  //(2) 球の中心のx座標
	private double centerY = 150.0;  //(3) 球の中心のy座標
	private double r = 100.0;        //(4) 球の半径r
	private double r2 = 10000.0;     //(5) 球の半径(r)の2乗
	private int offsetX = 60;        //(6) 左余白
	private int offsetY = 210;       //(7) 上余白

	//パネル
	private Panel pnlSettings = new Panel( new GridLayout(6,2) );
	private Panel pnlButton = new Panel( new FlowLayout() );

	//ボタン・テキストフィールド
	private Button btnRedraw = new Button("再描画");
	private TextField txIa = new TextField("0.4");
	private TextField txWa = new TextField("0.5");
	private TextField txI = new TextField("1.0");
	private TextField txWd = new TextField("0.6");
	private TextField txWs = new TextField("0.3");
	private TextField txN = new TextField("6.0");

	//光源の向き(x,y)
		//※光源初期位置(200+60,150+210)
		private Point mousePt = new Point(260, 360);

	//画像イメージ
	private BufferedImage image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
	//(8)
	//mainメソッド
	public static void main(String args[]){
		ThreeDimentionalCG fm = new ThreeDimentionalCG();
		fm.setSize(550, 550);
		fm.setVisible(true);
	}

	//コンストラクタ
	public ThreeDimentionalCG(){

		//終了ボタンを有効にする
		addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent evt) {
						System.exit(0);
					}
				}
				);
		//タイトルを設定
		setTitle("3DCG");
		//レイアウトの設定
		setLayout(new FlowLayout());

		//コンポーネントを登録
		menuFile.add(mitemSave);
		menuFile.add(mitemExit);
		mbar.add(menuFile);
		setMenuBar(mbar);

		//コンポーネントを登録 (追加)
		//パネルを登録
		add(pnlSettings);
		add(pnlButton);

		//設定パネルにラベルとテキストフィールドを登録
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
		//ボタンパネルにボタンを登録
		pnlButton.add(btnRedraw);



		//リスナークラスの登録
		mitemSave.addActionListener(this);
		mitemExit.addActionListener(this);
		btnRedraw.addActionListener(this);
		addMouseListener(this);


	}

	//イベントハンドラ
	public void actionPerformed(ActionEvent ev) {

		//終了 を選択したときの処理
		if (ev.getSource() == mitemExit){
			//プログラムを終了する
			System.exit(0);
		}

		//保存 を選択したときの処理
		else if (ev.getSource() == mitemSave){  //(9)

			// 画像ファイルの書き出し
			//ファイル名の取得
			FileDialog fdialog = new FileDialog(this, "保存", FileDialog.SAVE);
			fdialog.setVisible(true);
			String fileName = fdialog.getDirectory()+fdialog.getFile();

			//pngファイルを開き，readImageに読み込む
			try {
				//imageをpng形式でファイルに保存する
				ImageIO.write( image, "png", new File( fileName ));
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		//再描画ボタンを押したときの処理 (追加)
		else if (ev.getSource() == btnRedraw){

			//画面を再描画する
			repaint();
		}

	}

	//paintメソッド
	public void paint(Graphics g){

		//光源の明るさ，反射率を読み込む
		double Ia = Double.parseDouble(txIa.getText());
		double Wa = Double.parseDouble(txWa.getText());
		double I  = Double.parseDouble(txI.getText());
		double Wd = Double.parseDouble(txWd.getText());
		double Ws = Double.parseDouble(txWs.getText());
		double n  = Double.parseDouble(txN.getText());


		//画像を描画
		for (int i = 0; i < 400; i++){    //(10)
			for (int j = 0; j < 300; j++){  //(11)

				//画像の全てのドットについて，球の内側か外側かを調べる
				//球の中心から，現在の点までのx方向の距離
				double rx = i - centerX;  //(12)
				//球の中心から，現在の点までのy方向の距離
				double ry = j - centerY;  //(13)
				// rxの2乗
				double rx2 = Math.pow( rx, 2);  //(14)
				// ryの2乗
				double ry2 = Math.pow( ry, 2);  //(15)
				// 球の中心からの距離の2乗
				double l2 = rx2 + ry2;        //(16)

				// 距離が半径より小さければ球面上の点(2乗で比較している)
				if (l2 <= r2){  //(17)
					//球を白で描画
					//球のシェーディング
					try { 
						//球面の描画
						//球の中心から，現在の点(球面上)までのz方向の距離(rx)の２乗
						double rz2 = r2 - (rx2 + ry2); 
						//※ rz2がゼロより小さければ，ゼロを入れておく
						if (rz2 < 0.0) rz2 = 0.0; 
						//球の中心から，現在の点(球面上)までのz方向の距離
						double rz = Math.sqrt(rz2);
						//面の法線ベクトル (単位ベクトル)
						Vect3 unitNormalV = new Vect3(rx, ry, rz);
						//unitNormalVを単位ベクトルにしておく
						unitNormalV = unitNormalV.unitVector();
						
						//マウスの位置から光源の向きを設定する (追加)
						//光源ベクトルのx方向成分
						double rayX = (mousePt.getX() - offsetX) - centerX;  //(1)
						//光源ベクトルのy方向成分
						double rayY = (mousePt.getY() - offsetY) - centerY;  //(2)
						//ray_xの２乗
						double rayL2 = rayX*rayX + rayY*rayY;  //(3)
						//光源ベクトルのz方向成分(0.0で初期化)
						double rayZ = 0.0;   //(4)
						//光源の位置が球上の点なら，z方向成分を求める
						if (rayL2 <= r2) {   //(5)
							rayZ = Math.sqrt( r2 - rayL2 );
						}
						//光源ベクトルの向きを設定
						Vect3 rayV = new Vect3(rayX, rayY, rayZ);  //(6)
						
						//入射光の向きのベクトルを単位ベクトルに変換しておく
						rayV = rayV.unitVector();
						//入射光と面の法線のなす角θの余弦
						double cosTheta = unitNormalV.cos(rayV);
						
						//拡散反射による明るさ (0.0で初期化)
						double Id = 0.0;
						//鏡面反射による明るさ (0.0で初期化)
						double Is = 0.0;
						
						//cosThetaが負なら，面は入射光の陰になっているので，IsとIdは０
						if (cosTheta >= 0.0){
							
							//拡散反射の計算
							Id = I * Wd * cosTheta;
							
							//鏡面反射の計算
							//視線の向き(ディスプレイに垂直:ｚ軸方向)
							Vect3 eyeV = new Vect3(0.0, 0.0, 1.0);
							//正反射方向ベクトルの法線方向成分
							Vect3 refNormalV = unitNormalV.multi( 
									unitNormalV.scalarProduct(rayV) );
							//正反射方向ベクトル
							Vect3 refV = refNormalV.multi( 2.0 ).sub( rayV );
							//正反射方向と視線方向のなす角αの余弦
							double cosAlpha = refV.cos(eyeV);
							//正反射方向から９０度以上(cosAlphaが負)のときは，明るさはゼロ
							if (cosAlpha >= 0.0){
								Is = I * Ws * Math.pow(cosAlpha, n);
							}
						}
						
						//明るさの合計
						double Ir = Wa * Ia + Id + Is;
						//※ 明るさの最大値は１
						if (Ir > 1.0) Ir = 1.0;
						//ドットの色を記憶したColorクラスオブジェクトの生成
						Color color = new Color( (int)(255*Ir), (int)(255*Ir), (int)(255*Ir) );
						//ドットの色を設定
						image.setRGB( i, j, color.getRGB() );
					}
					catch(Vect3DBZException ex){
						// 例外が投げられたら白を描画
						Color color = new Color(255, 255, 255);
						image.setRGB( i, j, color.getRGB() );
					}
				}



				else {
					// 背景を黒で描画
					image.setRGB( i, j, Color.BLACK.getRGB() ); //(19)
				}
			}
		}

		//BufferedImageオブジェクトの画像imageを表示する
		if (image != null){
			g.drawImage(image, offsetX, offsetY, this);   //(20)
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		//マウスがクリックされた座標を取得
				mousePt = e.getPoint();   //(1)
				
				//画面を再描画する
				repaint();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
