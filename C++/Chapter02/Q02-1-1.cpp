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
	cout<<"���ڸ� �Է��ϼ���: ";
	cin>>val; 
	IncreOne(val); 
	cout<<"1����: "<<val<<endl;
	
	InverSign(val);
	cout<<"��ȣ �ݴ�: "<<val<<endl;
	return 0;
}
