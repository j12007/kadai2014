package myVectorUtil;

//�R�����x�N�g���������N���X
public class Vect3 {
	
	//�t�B�[���h
	// �x�N�g���̂R����������
	private double x, y, z;
	
	// �R���X�g���N�^
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
	
	//���\�b�h
	// x���W��ݒ�
	public void setX(double x){
		this.x = x;
	}
	// y���W��ݒ�
	public void setY(double y){
		this.y = y;
	}
	// z���W��ݒ�
	public void setZ(double z){
		this.z = z;
	}
	// ���W(x, y, z)��ݒ�
	public void set(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	// x���W���擾
	public double getX(){
		return x;
	}
	// y���W���擾
	public double getY(){
		return y;
	}
	// z���W���擾
	public double getZ(){
		return z;
	}
	
	// �x�N�g���̘a�����߂�
	public Vect3 add(Vect3 v){
		Vect3 va = new Vect3( x+v.getX(), y+v.getY(), z+v.getZ() );
		return va;
	}
	// �x�N�g���̍������߂�
	public Vect3 sub(Vect3 v)
	{
		Vect3 vs = new Vect3( x-v.getX(), y-v.getY(), z-v.getZ() );
		return vs;
	}
	// �x�N�g���̎����{�����߂�
	public Vect3 multi(double a){
		Vect3 v = new Vect3(x*a, y*a, z*a);
		return v;
	}
	// �x�N�g���̒��������߂�
	public double length(){
		double len2 = x*x + y*y + z*z;
		return Math.sqrt(len2);
	}
	// �P�ʃx�N�g�������߂�
	// �� �x�N�g���̒������[���Ȃ��O�𑗏o����
	public Vect3 unitVector() throws Vect3DBZException{
		double l = length();
		if (l == 0) throw new Vect3DBZException();
		Vect3 uv = new Vect3( x/l, y/l, z/l);
		return uv;
	}
	// �Q�̃x�N�g���̓��ς����߂�
	public double scalarProduct(Vect3 v2){
		double sp = x*v2.getX() + y*v2.getY() + z*v2.getZ();
		return sp;
	}
	// �Q�̃x�N�g���̊O�ς����߂�
	public Vect3 vectorProduct(Vect3 v2)
	{
		Vect3 vp = new Vect3();
		vp.setX( y * v2.getZ() - z * v2.getY() );
		vp.setY( z * v2.getX() - x * v2.getZ() );
		vp.setZ( x * v2.getY() - y * v2.getX() );
		return vp;
	}
	// �Q�̃x�N�g���̗]�������߂�
	public double cos(Vect3 v2) throws Vect3DBZException{
		Vect3 uv1 = unitVector();
		Vect3 uv2 = v2.unitVector();
		double cs =  uv1.scalarProduct(uv2);
		return cs;
	}
	// �Q�̃x�N�g���̐��������߂�
	public double sin(Vect3 v2) throws Vect3DBZException{
		Vect3 uv1 = unitVector();
		Vect3 uv2 = v2.unitVector();
		double sn =  uv1.vectorProduct(uv2).length();
		return sn;
	}
}