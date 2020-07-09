#include<iostream>

int main(void)
{
	int num;
	while(num>=0)
	{
		
		std::cout<<"판매 금액을 만원 단위로 입력(-1 to end): ";
		std::cin>>num; 
		if(num<0)
		{
			std::cout<<"프로그램을 종료합니다"<<std::endl;
			break;
			
		}
		else
		{
			std::cout<<"이번달 급여: "<<num*0.12+50<<std::endl;
		}
	}
	return 0;
}
