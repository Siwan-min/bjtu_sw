#include <iostream>
using namespace std;

void SwapByRef2(int &ref1, int &ref2)
{
	int temp=ref1; //ref1 의 값을 temp에 저장함 
	ref1=ref2; //ref2의 값을 ref1에 저장함 
	ref2=temp; //다시 temp의 값을 ref2에 저장함 
	//ref1 과 ref2 값의 교환이 이루어 진다.  
}

int main(void)
{
	int val1=10;
	int val2=20;
	
	SwapByRef2(val1, val2);
	cout<<"val1: "<<val1<<endl;
	cout<<"val2: "<<val2<<endl;
	return 0;
}

/*
Result
val1: 20
val2: 10 
*/
