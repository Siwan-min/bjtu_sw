#include<iostream>

int main(void)
{
	int num, i;
	int result=0;
	std::cout<<"Please enter the number: ";
	std::cin>>num;
	
	
	while(i<9)
	{
		i++;
		result=i*num;
		std::cout<<result<<std::endl;
	
	}
	
		
	return 0;
}
