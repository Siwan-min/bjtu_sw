#include<iostream>

void swap(int *num1, int *num2) //매개변수의 자료형이 다르기 때문에 오버라이딩이 가능하다
{
	int temp = *num1;
	*num1 = *num2;
	*num2 = temp; 
 } 
 
 void swap(char *ch1, char *ch2) //매개변수의 자료형이 다르기 때문에 오버라ㅓ이딩이 가능하다
 {
 	char temp = *ch1;
 	*ch1 = *ch2;
 	*ch2 = temp;
  } 
  
void swap(double *dbl1, double *dbl2) //매개변수의 자료형이 다르기 때문에 오버라이딩이 가능하다
{
	double temp = *dbl1;
	*dbl1 = *dbl2;
	*dbl2 = temp;
 } 
 
int main(void) //문제에서 주어진 main 함수
{
	int num1 = 20, num2 = 30;
	std::cout<<num1<<' '<<num2<<std::endl;
	std::cout<< "swap 함수 실행 결과" << std::endl;
	swap(&num1, &num2);
	std::cout<<num1<<' '<<num2 << std::endl<<std::endl; 
	
	char ch1 = 'A', ch2 = 'Z';
	std::cout << ch1 << ' ' << ch2 << std::endl;
	std::cout << "swap 함수 실행 결과 "<< std::endl;
	swap(&ch1, &ch2);
	std::cout << ch1 << ' ' << ch2 << std::endl<<std::endl;
	
	double dbl1 = 1.111, dbl2 = 5.555;
	std::cout << dbl1 << ' ' <<dbl2 << std::endl;
	std::cout << "swap 함수 실행 결과" << std::endl;
	swap(&dbl1, &dbl2);
	std::cout << dbl1 << ' ' <<dbl2 <<std::endl;
	return 0;  
 } 
