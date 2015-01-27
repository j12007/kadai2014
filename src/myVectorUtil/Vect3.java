package myVectorUtil;

//３次元ベクトルを扱うクラス
public class Vect3 {
	
	//フィールド
	// ベクトルの３軸方向成分
	private double x, y, z;
	
	// コンストラクタ
	// type I
	public Vect3(){
		x = y = z = 0.0;
	}
	// type II
	public Vect3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//メソッド
	// x座標を設定
	public void setX(double x){
		this.x = x;
	}
	// y座標を設定
	public void setY(double y){
		this.y = y;
	}
	// z座標を設定
	public void setZ(double z){
		this.z = z;
	}
	// 座標(x, y, z)を設定
	public void set(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	// x座標を取得
	public double getX(){
		return x;
	}
	// y座標を取得
	public double getY(){
		return y;
	}
	// z座標を取得
	public double getZ(){
		return z;
	}
	
	// ベクトルの和を求める
	public Vect3 add(Vect3 v){
		Vect3 va = new Vect3( x+v.getX(), y+v.getY(), z+v.getZ() );
		return va;
	}
	// ベクトルの差を求める
	public Vect3 sub(Vect3 v)
	{
		Vect3 vs = new Vect3( x-v.getX(), y-v.getY(), z-v.getZ() );
		return vs;
	}
	// ベクトルの実数倍を求める
	public Vect3 multi(double a){
		Vect3 v = new Vect3(x*a, y*a, z*a);
		return v;
	}
	// ベクトルの長さを求める
	public double length(){
		double len2 = x*x + y*y + z*z;
		return Math.sqrt(len2);
	}
	// 単位ベクトルを求める
	// ※ ベクトルの長さがゼロなら例外を送出する
	public Vect3 unitVector() throws Vect3DBZException{
		double l = length();
		if (l == 0) throw new Vect3DBZException();
		Vect3 uv = new Vect3( x/l, y/l, z/l);
		return uv;
	}
	// ２つのベクトルの内積を求める
	public double scalarProduct(Vect3 v2){
		double sp = x*v2.getX() + y*v2.getY() + z*v2.getZ();
		return sp;
	}
	// ２つのベクトルの外積を求める
	public Vect3 vectorProduct(Vect3 v2)
	{
		Vect3 vp = new Vect3();
		vp.setX( y * v2.getZ() - z * v2.getY() );
		vp.setY( z * v2.getX() - x * v2.getZ() );
		vp.setZ( x * v2.getY() - y * v2.getX() );
		return vp;
	}
	// ２つのベクトルの余弦を求める
	public double cos(Vect3 v2) throws Vect3DBZException{
		Vect3 uv1 = unitVector();
		Vect3 uv2 = v2.unitVector();
		double cs =  uv1.scalarProduct(uv2);
		return cs;
	}
	// ２つのベクトルの正弦を求める
	public double sin(Vect3 v2) throws Vect3DBZException{
		Vect3 uv1 = unitVector();
		Vect3 uv2 = v2.unitVector();
		double sn =  uv1.vectorProduct(uv2).length();
		return sn;
	}
}