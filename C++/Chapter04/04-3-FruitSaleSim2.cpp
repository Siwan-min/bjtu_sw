#include <iostream>
using namespace std;

class FruitSeller
{
	private: //private : �ܺο��� ���Ƿ� ȣ���Ͽ� ���� �� �� ����. 
		int APPLE_PRICE;
		int numOfApples;
		int myMoney; 
	public:
		FruitSeller(int price, int num, int money) //constructor 
		{
			APPLE_PRICE=price;
			numOfApples=num;
			myMoney=money;
		 }
		 int SaleApples(int money)
		 {
			int num=money/APPLE_PRICE;
			numOfApples-=num;
			myMoney+=money;
			return num;
		  } 
		 void ShowSalesResult() const
		 {
		 	cout<<"���� ���: "<<numOfApples<<endl;
			cout<<"�Ǹ� ����: "<<myMoney<<endl<<endl; 
		 }
 };
 
 class FruitBuyer
 {
 	private:
 		int myMoney;
 		int numOfApples;
 	public:
 		FruitBuyer(int money) //constructor
 		{
 			myMoney=money; //57�࿡�� 5000���� �Է��Ͽ���. money�� 5000���� �Է�(�ʱ�ȭ)�ȴ�.  
			numOfApples=0;	
		 }
		void BuyApples(FruitSeller &seller, int money)
		{
			numOfApples+=seller.SaleApples(money);
			myMoney-=money;
		}
		void ShowBuyResult() const
		{
			cout<<"���� �ܾ�: "<<myMoney<<endl;
			cout<<"��� ����: "<<numOfApples<<endl<<endl; 
		}
  };
  
  int main(void)
  {
  	FruitSeller seller(1000, 20, 0); //FruitSeller �����ڸ� ������� seller �̶�� ��ü�� ���� 
  	FruitBuyer buyer(5000); //FruitBuyer �����ڸ� ������� buyer�̶�� ��ü�� �����ϰ� �� ������ 5000 ���� �ʱ�ȭ �Ͽ� money�� 5000 �� �Է�.  
  	buyer.BuyApples(seller, 2000);
  	
  	cout<<"���� �Ǹ����� ��Ȳ"<<endl;
  	seller.ShowSalesResult();
  	cout<<"���� �������� ��Ȳ"<<endl;
  	buyer.ShowBuyResult();
  	return 0;
   } 
