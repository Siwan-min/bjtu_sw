class Account
{
	private:
		int accID;
		int balance;
		char * cusName;
		
	public:
		Account(int ID, int money, char *name)
			:accID(ID), balance(money)
		{
			cusName=new char[strlen(name)+1];
			strcpy(cusName, name); //복사생성자 
		}
		
		Account(const Account & ref)
			:accID(ref.accID), balance(ref.balance)
			{
				cusName=new char[strlen(ref.cusName)+1];
				strcpy(cusName, ref.cusName);
			}
			
			int GetAccID() const { return accID; }
			
			void Deposit(int money)
			{
				balance+=money;
			}
			
			int Withdraw(int money)
			{
				if(balance<money)
				return 0;
				
				balance-=money;
				return money;
			}
			
			void ShowAccInfo()
			{
				cout<<"계좌: "<<accID<<endl;
				cout<<"이름: "<<cusName<<endl;
				cout<<"잔액: "<<balance<<endl; 
			}
			
			~Account()
			{
				delete []cusName;
			}
			
};
