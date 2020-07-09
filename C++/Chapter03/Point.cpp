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
		cout<<"��� ������ �� ����"<<endl; 
		return false;
	}
	x=xpos;
	return true;
}
bool Point::SetY(int ypos)
{
	if(0>ypos||ypos>100)
	{
		cout<<"���Ƴ� ������ �� ����"<<endl;
		return false; //yvalue won't be returned and the comment prints out
	}
	y=ypos;
	return true; 
}
