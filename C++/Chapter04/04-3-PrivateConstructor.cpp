#include <iostream>
using namespace std;

class AAA
{
	private:
		int num;
	public:
		AAA() : num(0){}
		AAA& CreateInitObj(int n) const
		{
			AAA *ptr=new AAA(n);
			return *ptr;
		}
		void ShowNum() const {cout<<num<<endl;}
	private:
	AAA(int n) : num(n) {}		
};

int main(void)
{
	AAA base; //AAA 라는 클래스에 base 라는 객체를 생성하여 함수를 이용 
	base.ShowNum();
	
	AAA &obj1=base.CreateInitObj(3);
	obj1.ShowNum();
	
	AAA &obj2=base.CreateInitObj(12);
	obj2.ShowNum();
	
	delete &obj1;
	delete &obj2;
	return 0;
}
