/*
Q04-3-1
앞서 제시한 문제 04-2를 해결하였는가? 
당시만 해도 생성자를 설명하지 않은 상황이기 때문에 
별도의 초기화 함수를 정의 및 호출해서
Point, Circle, Ring 클래스의 객체를 초기화하였다.
이 때 구현한 답에 대해서 모든 클래스에 생성자를 정의해 보자. 
*/

#include <iostream>
#include <cstring>
using namespace std;

class Point
{
	private:
		int xpos, ypos;
	public:
		Point(int x, int y) : xpos(x), ypos(y) //void init 대신 Point 생성자 생성, xpos(x), ypos(y) 멤버변수 초기화  
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
		Circle(int x, int y, int r) : center(x, y) //void init 대신 Circle 생성자 생성 
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
	 //Ring 생성자, void init 대신 Ring 생성자 생성 멤버변수 초기화 
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
