// ADD.cpp 

#include "stdafx.h"
#include "LLKGame.h"
#include "ADD.h"
#include "afxdialogex.h"
#include"LLKGameDlg.h"


// CADD 뚤뺐움

IMPLEMENT_DYNAMIC(CADD, CDialogEx)

CADD::CADD(CWnd* pParent /*=NULL*/)
	: CDialogEx(IDD_ADD, pParent)
	, name(_T(""))
{
	name = _T("");
	score = 0;
	level = 0;
}

CADD::~CADD()
{
}

void CADD::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT1, name);
	DDX_Text(pDX, IDC_EDIT2, score);
	DDX_Text(pDX, IDC_EDIT3, level);
}


BEGIN_MESSAGE_MAP(CADD, CDialogEx)
	ON_BN_CLICKED(IDOK, &CADD::OnBnClickedOk)
	ON_BN_CLICKED(IDCANCEL, &CADD::OnBnClickedCancel)
END_MESSAGE_MAP()

BOOL CADD::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	//parent 를 통해서 게임 레벨 / 점수를 얻는다. ( 계산방식 현재 스코어 + 남은시간)
	CLLKGameDlg *parent = (CLLKGameDlg *)GetParent();
	level = parent->level;
	score = parent->score+ 50-parent->timer *parent->level;
	UpdateData(FALSE);
	return TRUE;  
}
// CADD message handler code

void CADD::OnBnClickedOk()
{
	UpdateData(TRUE);
	if (name != _T(""))
	{
		CStdioFile mFile;
		CFileException mExcept;
		if (!mFile.Open(_T("record.txt"), CFile::modeWrite, &mExcept))
			mFile.Open(_T("record.txt"), CFile::modeCreate | CFile::modeWrite, &mExcept);
		CString str;
		mFile.SeekToEnd();
		//문서의 마지막면 기록하고 써내기
		str.Format(_T("%s\n"), name);
		mFile.WriteString(str);
		str.Format(_T("%d\n"), score);
		mFile.WriteString(str);
		str.Format(_T("%d\n"), level);
		mFile.WriteString(str);
		MessageBox(L"saving success");
		CDialog::OnOK();
	}
	else
		AfxMessageBox(_T("input save!"));

}


void CADD::OnBnClickedCancel()
{
	CDialogEx::OnCancel();
}
