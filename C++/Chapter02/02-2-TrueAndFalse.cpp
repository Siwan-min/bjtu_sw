#include <iostream>
using namespace std;

int main(void)
{
	int num=10;
	int i=0;
	
	cout<<"true: "<<true<<endl; // Ű���� true�� false�� �ֿܼ� ������� ���� ��³����� Ȯ���ϱ� ���� �����̴�.  
	cout<<"false: "<<false<<endl;
	
	while(true) //���ѷ����� �����ϱ� ���ؼ� true�� ���, 1�� ��� ��� �� �� �ִ�. 
	{
		cout<<i++<<' ';
		if(i>num)
		break;
	}
	cout<<endl;
	
	cout<<"sizeof 1: "<<sizeof(1)<<endl; //��� 1�� ���� Ȯ���ϱ� ���� ���� 
	cout<<"sizeof 0: "<<sizeof(0)<<endl; // ��� 0�� ���� Ȯ���ϱ� ���� ���� 
	cout<<"sizeof true: "<<sizeof(true)<<endl; // ���� ������ �ǹ��ϴ� ������ true�� false�� ũ�⸦ Ȯ���ϱ� ���� ����.  
	cout<<"sizeof false: "<<sizeof(false)<<endl;
	return 0;
}
