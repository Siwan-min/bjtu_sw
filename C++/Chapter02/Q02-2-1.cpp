#include <iostream>
using namespace std;

int main(void)
{
	const int num=12;
	const int *ptr=&num; //포인터 변수를 선언해서 변수를 가리킴 
	const int *(&ref)=ptr;//포인터의 변수를 참조하는 참조 
	
	cout<<"포인터의 변수의 값"<<*ptr<<endl;
	cout<<"참조자의 값"<<*ref<<endl;
	return 0;
}
