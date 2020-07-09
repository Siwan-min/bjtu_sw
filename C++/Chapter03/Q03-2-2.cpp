/*
Q03-2-2
���ڿ� ������ ���ο� �����ϴ� Printer��� �̸��� Ŭ������ ����������. �� Ŭ������ �� ���� ����� ������ ����.
-���ڿ� ����
-���ڿ� ���  
*/
#include <iostream>
#include <cstring>
using namespace std;

class Printer
{
	private:
	
		char str[30]; //��� ���� 
	
	
	public:
	
		void SetString(const char* s);
		void ShowString();
	//��� �Լ�  
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
