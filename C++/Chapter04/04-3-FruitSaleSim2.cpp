#include <iostream>
using namespace std;

class FruitSeller
{
	private: //private : 외부에서 임의로 호출하여 변경 할 수 없음. 
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
		 	cout<<"남은 사과: "<<numOfApples<<endl;
			cout<<"판매 수익: "<<myMoney<<endl<<endl; 
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
 			myMoney=money; //57행에서 5000원을 입력하였다. money에 5000원이 입력(초기화)된다.  
			numOfApples=0;	
		 }
		void BuyApples(FruitSeller &seller, int money)
		{
			numOfApples+=seller.SaleApples(money);
			myMoney-=money;
		}
		void ShowBuyResult() const
		{
			cout<<"현재 잔액: "<<myMoney<<endl;
			cout<<"사과 개수: "<<numOfApples<<endl<<endl; 
		}
  };
  
  int main(void)
  {
  	FruitSeller seller(1000, 20, 0); //FruitSeller 생성자를 기반으로 seller 이라는 객체를 생성 
  	FruitBuyer buyer(5000); //FruitBuyer 생성자를 기반으로 buyer이라는 객체를 생성하고 그 곳에다 5000 값을 초기화 하여 money에 5000 값 입력.  
  	buyer.BuyApples(seller, 2000);
  	
  	cout<<"과일 판매자의 현황"<<endl;
  	seller.ShowSalesResult();
  	cout<<"과일 구매자의 현황"<<endl;
  	buyer.ShowBuyResult();
  	return 0;
   } 
