#include <iostream>
using namespace std;

int main(void)
{
	const int num=12;
	const int *ptr=&num; //������ ������ �����ؼ� ������ ����Ŵ 
	const int *(&ref)=ptr;//�������� ������ �����ϴ� ���� 
	
	cout<<"�������� ������ ��"<<*ptr<<endl;
	cout<<"�������� ��"<<*ref<<endl;
	return 0;
}
