/*
Q04-3-1
�ռ� ������ ���� 04-2�� �ذ��Ͽ��°�? 
��ø� �ص� �����ڸ� �������� ���� ��Ȳ�̱� ������ 
������ �ʱ�ȭ �Լ��� ���� �� ȣ���ؼ�
Point, Circle, Ring Ŭ������ ��ü�� �ʱ�ȭ�Ͽ���.
�� �� ������ �信 ���ؼ� ��� Ŭ������ �����ڸ� ������ ����. 
*/

#include <iostream>
#include <cstring>
using namespace std;

class Point
{
	private:
		int xpos, ypos;
	public:
		Point(int x, int y) : xpos(x), ypos(y) //void init ��� Point ������ ����, xpos(x), ypos(y) ������� �ʱ�ȭ  
		{
			//xpos=x;
			//ypos=y;
		}
		void ShowPointInfo() const
		{
			cout<<"["<<xpos<<", "<<ypos<<"]"<<endl;
		}
};

class Circle
{
	private:
		int rad; //Radius
		Point center; // center of circle
	public:
		Circle(int x, int y, int r) : center(x, y) //void init ��� Circle ������ ���� 
		{
			rad=r;
			//center.Init(x, y);
		}
		
		void ShowCircleInfo() const
		{
			cout<<"radius: "<<rad<<endl;
			center.ShowPointInfo();
		}
};

class Ring
{
	private:
		Circle inCircle;
		Circle outCircle;
	public:
	Ring (int inX, int inY, int inR, int outX, int outY, int outR) :inCircle(inX, inY, inR), outCircle(outX, outY, outR)
	 //Ring ������, void init ��� Ring ������ ���� ������� �ʱ�ȭ 
	{
		//inCircle.Init(inX, inY, inR);
		//outCircle.Init(outX, outY, outR);
		}	
	
	void ShowRingInfo() const
	{
		cout<<"Inner Circle info..."<<endl;
		inCircle.ShowCircleInfo();
		cout<<"Outter Circle Info..."<<endl;
		outCircle.ShowCircleInfo();
	}
	
};

int main(void)
{
	Ring ring(1, 1, 4, 2, 2, 9); //Ring ring; -> Ring ring(1, 1, 3, 2, 2, 9) 
	//ring.Init(1, 1, 4, 2, 2, 9);
	ring.ShowRingInfo();
	return 0;
}
