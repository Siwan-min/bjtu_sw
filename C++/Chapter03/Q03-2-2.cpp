/*
Q03-2-2
문자열 정보를 내부에 저장하는 Printer라는 이름의 클래스를 디자인하자. 이 클래서의 두 가지 기능은 다음과 같다.
-문자열 저장
-문자열 출력  
*/
#include <iostream>
#include <cstring>
using namespace std;

class Printer
{
	private:
	
		char str[30]; //멤버 변수 
	
	
	public:
	
		void SetString(const char* s);
		void ShowString();
	//멤버 함수  
};

void Printer::SetString(const char*s)
{
	strcpy(str, s);
}

void Printer::ShowString()
{
	cout<<str<<endl;
}

int main(void)
{
	Printer pnt;
	pnt.SetString("Hello world~");
	pnt.ShowString();
	
	pnt.SetString("I love C++");
	pnt.ShowString();
	return 0;
}
