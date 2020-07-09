#include<iostream>
#include"Point.h"

using namespace std;

Point::Point(const int &xpos, const int &ypos)
{
	x=xpos;
	y=ypos;
}

int Point::GetX() const { return x;} // Xvalue is returned
int Point::GetY() const { return y;} // Yvalue is returned

bool Point::SetX(int xpos) 
{
	if(0>xpos||xpos>100)
	{
		cout<<"벗어난 범위의 값 전달"<<endl; 
		return false;
	}
	x=xpos;
	return true;
}
bool Point::SetY(int ypos)
{
	if(0>ypos||ypos>100)
	{
		cout<<"벗아난 범위의 값 전달"<<endl;
		return false; //yvalue won't be returned and the comment prints out
	}
	y=ypos;
	return true; 
}
