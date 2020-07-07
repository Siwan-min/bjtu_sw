// Record.cpp 

#include "stdafx.h"
#include "LLKGame.h"
#include "Record.h"
#include "afxdialogex.h"

// CRecord dialog

IMPLEMENT_DYNAMIC(CRecord, CDialogEx)

CRecord::CRecord(CWnd* pParent /*=NULL*/)
	: CDialogEx(IDD_RECORD, pParent)
{

}

CRecord::~CRecord()
{
}

void CRecord::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_LIST1, list);
}


BEGIN_MESSAGE_MAP(CRecord, CDialogEx)
	ON_BN_CLICKED(IDOK, &CRecord::OnBnClickedOk)
	ON_BN_CLICKED(IDCANCEL, &CRecord::OnBnClickedCancel)
	ON_BN_CLICKED(IDC_BUTTON1, &CRecord::OnBnClickedButton1)
	ON_NOTIFY(LVN_ITEMCHANGED, IDC_LIST1, &CRecord::OnLvnItemchangedList1)
END_MESSAGE_MAP()


// CRecord dialog data
BOOL CRecord::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	list.InsertColumn(0, _T(""), LVCFMT_CENTER, 40);
	list.InsertColumn(1, _T("NAME"), LVCFMT_CENTER, 100);
	list.InsertColumn(2, _T("SCORE"), LVCFMT_CENTER, 70);
	list.InsertColumn(3, _T("LEVEL"), LVCFMT_CENTER, 70);

	LONG lStyle = list.SendMessage(LVM_GETEXTENDEDLISTVIEWSTYLE);
	lStyle |= LVS_EX_FULLROWSELECT | LVS_EX_GRIDLINES | LVS_EX_HEADERDRAGDROP;
	list.SendMessage(LVM_SETEXTENDEDLISTVIEWSTYLE, 0, (LPARAM)lStyle);

	ReadRecord();
	return TRUE;
}

//Read the records.
void CRecord::ReadRecord(void)
{
	CStdioFile mFile;
	CFileException mExcept;
	CString readstr;
	CString IDstr;
	int i = 0;
	if (!mFile.Open(_T("record.txt"), CFile::modeRead, &mExcept))
		mFile.Open(_T("record.txt"), CFile::modeCreate | CFile::modeRead, &mExcept);

	for (int i = 0; i < 11; i++)
	{
		while (mFile.ReadString(readstr))
		{
			rec[i].name = readstr;
			mFile.ReadString(readstr);
			rec[i].score = readstr;
			mFile.ReadString(readstr);
			rec[i].level = readstr;
			break;
		}
	}

	sortRec(rec);

	for (int i = 0; i < 10; i++)
	{
		if (rec[i].score != _T(""))
		{
			IDstr.Format(_T("%d"), (i + 1));
			list.InsertItem(i, IDstr);
			list.SetItemText(i, 1, rec[i].name);
			list.SetItemText(i, 2, rec[i].score);
			list.SetItemText(i, 3, rec[i].level);
		}
	}

}

// sort
void CRecord::sortRec(Rec rec[])
{
	for (int i = 0; i < 11; i++)
	{
		for (int j = 0;( i+j )< 10; j++)
		{
			if (_ttoi(rec[j].score) < _ttoi(rec[j+1].score))
			{
				Rec temp = rec[j];
				rec[j] = rec[j + 1];
				rec[j + 1] = temp;
			}
		}
	}

}

//OK
void CRecord::OnBnClickedOk()
{

	CDialogEx::OnOK();
}

//Cancel
void CRecord::OnBnClickedCancel()
{
	CDialogEx::OnCancel();

}

//clean record.
void CRecord::OnBnClickedButton1()
{
	if (AfxMessageBox(_T("Delete record."), MB_OKCANCEL) == IDOK)
	{
		list.DeleteAllItems();
		// remove files to create an identically named file (record).
		CStdioFile mFile;
		CFileException mExcept;
		mFile.Open(_T("record.txt"), CStdioFile::modeCreate, &mExcept);
	}

}


void CRecord::OnLvnItemchangedList1(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMLISTVIEW pNMLV = reinterpret_cast<LPNMLISTVIEW>(pNMHDR);
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
	*pResult = 0;
}
