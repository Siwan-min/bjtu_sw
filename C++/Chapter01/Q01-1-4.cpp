#include<iostream>

int main(void)
{
	int num;
	while(num>=0)
	{
		
		std::cout<<"�Ǹ� �ݾ��� ���� ������ �Է�(-1 to end): ";
		std::cin>>num; 
		if(num<0)
		{
			std::cout<<"���α׷��� �����մϴ�"<<std::endl;
			break;
			
		}
		else
		{
			std::cout<<"�̹��� �޿�: "<<num*0.12+50<<std::endl;
		}
	}
	return 0;
}
