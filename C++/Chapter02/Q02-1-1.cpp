#include <iostream>
using namespace std;

void IncreOne(int &num)
{
	num++;
}
void InverSign(int &num)
{
	num*=-1;
}

int main(void)
{
	int val;
	cout<<"숫자를 입력하세요: ";
	cin>>val; 
	IncreOne(val); 
	cout<<"1증가: "<<val<<endl;
	
	InverSign(val);
	cout<<"부호 반대: "<<val<<endl;
	return 0;
}
