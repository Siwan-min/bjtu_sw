#ifndef __POINT_H_
#define __POINT_H_

class Point
{
	private:
		int x;
		int y;
	public:
		Point(const int &xpos, const int &ypos); //constructor of Point
		int GetX() const; //Xvalue return
		int GetY() const; //Yvalue return
		bool SetX(int xpos); //Xvalue set
		bool SetY(int ypos); //Yvalue set
};
#endif


