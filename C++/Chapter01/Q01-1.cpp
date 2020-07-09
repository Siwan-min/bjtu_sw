#include <iostream>

int main(void)
{
	int val1, val2, val3, val4, val5;
	
	std::cout<< "1번째 정수 입력: ";
	std::cin>>val1;
	
	std::cout<< "2번째 정수 입력: ";
	std::cin>>val2; 
	
	std::cout<< "3번째 정수 입력: ";
	std::cin>>val3;
	
	std::cout<< "4번째 정수 입력: ";
	std::cin>>val4;
	
	std::cout<< "5번째 정수 입력: ";
	std::cin>>val5;
	
	int result=val1+val2+val3+val4+val5;
	std::cout<<"덧셈결과: "<<result<<std::endl; 
	return 0; 
}
