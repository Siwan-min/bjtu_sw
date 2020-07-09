#include <iostream>
#include <cstring>

using namespace std;
const int NAME_LEN = 20;

void ShowMenu(void); //�޴� ���
void MakeAccount(void); //���°����� ���� �Լ�
void DepositMoney(void); //�Ա�
void WithdrawMoney(void); //���
void ShowAllAccInfo(void); //�ܾ���ȸ

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

	int Withdraw(int money) //��ݾ� ��ȯ, ���� �� 0 ��ȯ
	{
		if (balance < money)
			return 0;

		balance -= money;
		return money;
	}

	void ShowAccInfo()
	{
		cout << "����ID: " << accID << endl;
		cout << "�̸�: " << cusName << endl;
		cout << "�ܾ�: " << balance << endl;
	}
	~Account()
	{
		delete[]cusName;
	}
};

Account *accArr[100]; //Account ������ ���� �迭
int accNum = 0; //����� Account ��

int main(void)
{
	int choice;

	while (1)
	{
		ShowMenu();
		cout << "����: ";
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
