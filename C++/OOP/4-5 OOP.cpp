#include <iostream>
#include <cstring>

using namespace std;
const int NAME_LEN = 20;

void ShowMenu(void); //메뉴 출력
void MakeAccount(void); //계좌개설을 위한 함수
void DepositMoney(void); //입금
void WithdrawMoney(void); //출금
void ShowAllAccInfo(void); //잔액조회

enum{MAKE=1, DEPOSIT, WITHDRAW, INQUIRE, EXIT};

class Account
{
private: 
	int accID;
	int balance;
	char *cusName;//

public:
	Account(int ID, int money, char *name)
		:accID(ID), balance(money)
	{
		cusName = new char[strlen(name) + 1];
		strcpy(cusName, name);
	}

	int GetAccID() { return accID; }

	void Deposit(int money)
	{
		balance += money;
	}

	int Withdraw(int money) //출금액 반환, 부족 시 0 반환
	{
		if (balance < money)
			return 0;

		balance -= money;
		return money;
	}

	void ShowAccInfo()
	{
		cout << "계좌ID: " << accID << endl;
		cout << "이름: " << cusName << endl;
		cout << "잔액: " << balance << endl;
	}
	~Account()
	{
		delete[]cusName;
	}
};

Account *accArr[100]; //Account 저장을 위한 배열
int accNum = 0; //저장된 Account 수

int main(void)
{
	int choice;

	while (1)
	{
		ShowMenu();
		cout << "선택: ";
		cin >> choice;
		cout << endl;

		switch (choice)
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
			cout << "Illegal selection.." << endl;
		}
	}
	return 0;
}

void ShowMenu(void)
{
	cout << "-----Menu-----" << endl;
	cout << "1. Create Account" << endl;
	cout << "2. Deposit" << endl;
	cout << "3. Withdraw" << endl;
	cout << "4. Show all information" << endl;
	cout << "5. End program" << endl;
}

void MakeAccount(void)
{
	int id;
	char name[NAME_LEN];
	int balance;

	cout << "[Create Account]" << endl;
	cout << "Account ID: "; cin >> id;
	cout << "Name: "; cin >> name;
	cout << "Deposit amount: "; cin >> balance;
	cout << endl;

	accArr[accNum++] = new Account(id, balance, name);
	/*
	accArr[accNum].accID = id;
	accArr[accNum].balance = balance;
	strcpy(accArr[accNum].cusName, name);
	accNum++;
	*/
}

void DepositMoney(void)
{
	int money;
	int id;
	cout << "[Deposit]" << endl;
	cout << "Account ID: "; cin >> id;
	cout << "Deposit amount: "; cin >> money;

	for (int i = 0; i < accNum; i++)
	{
		if(accArr[i]->GetAccID()==id)
		//if (accArr[i].accID == id)
		{
			accArr[i]->Deposit(money);
			//accArr[i].balance += money;
			cout << "Complete deposit" << endl << endl;
			return;
		}
	}
	cout << "Invalid ID" << endl << endl;
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
		if(accArr[i]->GetAccID()==id)
		//if(accArr[i].accID==id)
		{
			if(accArr[i]->Withdraw(money)==0)
			//if(accArr[i].balance<money)
			{
				cout<<"Insufficient balance"<<endl<<endl;
				return;
			}
			
			
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
		accArr[i]->ShowAccInfo();
		cout<<endl;
		//cout<<"Account ID: "<<accArr[i].accID<<endl;
		//cout<<"Name: "<<accArr[i].cusName<<endl;
		//cout<<"Balance: "<<accArr[i].balance<<endl<<endl;
	}
}
