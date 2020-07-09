
#include <iostream>
#include <cstring>

using namespace std;
const int NAME_LEN=20;

void ShowMenu(void); //Output Menum
void MakeAccount(void); // This funcetion is to make an account  
void DepositMoney(void); // Deposit
void WithdrawMoney(void); // Withdraw
void ShowAllAccInfo(void); //Balance 


enum {MAKE=1, DEPOSIT = 2, WITHDRAW = 3, INQUIRE =4 , EXIT=5};



typedef struct
{
	int accID; //Account number;
	int balance; //Balance
	char cusName[NAME_LEN]; // customer name
} 
Account;

Account accArr[100]; // THis array is to save account 
int accNum=0; //Saved The number of Account

int main(void)
{
	int choice;
	
	while(1)
	{
		ShowMenu();
		cout<<"¼±ÅÃ: ";
		cin>>choice;
		cout<<endl;
		
		switch(choice)
		{
			case MAKE:
				MakeAccount();
				break;
			case DEPOSIT:
				DepositMoney();
				break;
			case WITHDRAW:
				WithdrawMoney();
				break;
			case INQUIRE:
				ShowAllAccInfo();
				break;
			case EXIT:
				return 0;
			default:
				cout<<"Illegal selection.."<<endl;
		 } 
	}
	return 0;
}

void ShowMenu(void)
{
	cout<<"-----Menu-----"<<endl;
	cout<<"1. Create Account"<<endl;
	cout<<"2. Deposit"<<endl;
	cout<<"3. Withdraw"<<endl;
	cout<<"4. Show all information" <<endl;
	cout<<"5. End program"<<endl;
}

void MakeAccount(void)
{
	int id;
	char name[NAME_LEN];
	int balance;
	
	cout<<"[Create Account]"<<endl;
	cout<<"Account ID: ";cin>>id;
	cout<<"Name: "; cin>>name;
	cout<<"Deposit amount: ";cin>>balance;
	cout<<endl;
	
	accArr[accNum].accID=id;
	accArr[accNum].balance=balance;
	strcpy(accArr[accNum].cusName, name);
	accNum++;
}

void DepositMoney(void)
{
	int money;
	int id;
	cout<<"[Deposit]"<<endl;
	cout<<"Account ID: ";cin>>id;
	cout<<"Deposit amount: ";cin>>money;
	
	for(int i=0; i<accNum; i++)
	{
		if(accArr[i].accID==id)
		{
			accArr[i].balance+=money;
			cout<<"Complete deposit"<<endl<<endl;
			return;
		}
	}
	cout<<"Invalid ID"<<endl<<endl;
}

void WithdrawMoney(void)
{
	int money;
	int id;
	cout<<"[Withdraw]"<<endl;
	cout<<"Account ID: "; cin>>id;
	cout<<"Withdraw amount: "; cin>>money;
	
	for(int i=0; i<accNum; i++)
	{
		if(accArr[i].accID==id)
		{
			if(accArr[i].balance<money)
			{
				cout<<"Insufficient balance"<<endl<<endl;
				return;
			}
			
			accArr[i].balance-=money;
			cout<<"Complete withdraw"<<endl<<endl;
			return;
		}
	}
	cout<<"Invalid ID"<<endl<<endl;
}

void ShowAllAccInfo(void)
{
	for(int i=0; i<accNum; i++)
	{
		cout<<"Account ID: "<<accArr[i].accID<<endl;
		cout<<"Name: "<<accArr[i].cusName<<endl;
		cout<<"Balance: "<<accArr[i].balance<<endl<<endl;
	}
}
