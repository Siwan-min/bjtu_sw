#include <iostream>
using namespace std;

class SimpleClass
{
	private:
		int num1; 
		int num2;
	public:
		SimpleClass(int n1=0, int n2=0) //생성자: 클래스 이람과 같다. 생성자는 한번 사용하고 끝남.  
		{
			num1=n1;
			num2=n2;
		}
		void ShowData() const
		{
			cout<<num1<<' '<<num2<<endl;
		}
 };
 
 int main(void)
 {
 	SimpleClass sc1(); // 함수의 원형(형태)  선언 
 	SimpleClass mysc=sc1();
 	mysc.ShowData();
 	return 0;
  } 
  
SimpleClass sc1()
{
	SimpleClass sc(20, 30);
	return sc;
}
