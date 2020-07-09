#include <iostream>
using namespace std;

int main(void)
{
	int num=10;
	int i=0;
	
	cout<<"true: "<<true<<endl; // 키워드 true와 false를 콘솔에 출력했을 때의 출력내용을 확인하기 위한 문장이다.  
	cout<<"false: "<<false<<endl;
	
	while(true) //무한루프를 형성하기 위해서 true를 사용, 1을 대신 사용 할 수 있다. 
	{
		cout<<i++<<' ';
		if(i>num)
		break;
	}
	cout<<endl;
	
	cout<<"sizeof 1: "<<sizeof(1)<<endl; //상수 1의 값을 확인하기 위한 문장 
	cout<<"sizeof 0: "<<sizeof(0)<<endl; // 상수 0의 값을 확인하기 위한 문장 
	cout<<"sizeof true: "<<sizeof(true)<<endl; // 참과 거짓을 의미하는 데이터 true와 false의 크기를 확인하기 위한 문장.  
	cout<<"sizeof false: "<<sizeof(false)<<endl;
	return 0;
}
