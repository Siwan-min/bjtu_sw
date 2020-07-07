// Help.cpp 

#include "stdafx.h"
#include "LLKGame.h"
#include "Help.h"
#include "afxdialogex.h"
#include "LLKGameDlg.h"
#include <mmsystem.h>	
#pragma comment(lib, "WINMM.LIB")

// CHelp dialog

IMPLEMENT_DYNAMIC(CHelp, CDialogEx)

CHelp::CHelp(CWnd* pParent /*=NULL*/)
	: CDialogEx(IDD_HELP, pParent)
{

}

CHelp::~CHelp()
{
}

BOOL CHelp::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	CRect rect;
	GetClientRect(rect);

	//도움말 배경사진 초기화 //initialize help background
	sysDC = GetDC();
	m_backDC.CreateCompatibleDC(sysDC);
	m_backBM.LoadBitmap(IDB_HELPBACK);
	m_backDC.SelectObject(m_backBM);

	//cache initialize
	m_cacheDC.CreateCompatibleDC(sysDC);
	m_cacheBM.CreateCompatibleBitmap(sysDC, rect.Width(), rect.Height());
	m_cacheDC.SelectObject(m_cacheBM);

	((CButton*)GetDlgItem(IDC_CHECK1))->SetCheck(BST_CHECKED);
	((CButton*)GetDlgItem(IDC_CHECK2))->SetCheck(BST_CHECKED);

	return TRUE;
}

//insert background
void CHelp::OnPaint()
{
	CPaintDC dc(this);

	CRect rect;
	GetClientRect(rect);
	m_cacheDC.StretchBlt(0, 0, rect.Width(), rect.Height(), &m_backDC, 0, 0, 400, 500, SRCCOPY);
	dc.BitBlt(0, 0, rect.Width(), rect.Height(), &m_cacheDC, 0, 0, SRCCOPY);

	CDialog::OnPaint();
}

void CHelp::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_BUTTON1, known);
}


BEGIN_MESSAGE_MAP(CHelp, CDialogEx)
	ON_BN_CLICKED(IDC_BUTTON1, &CHelp::OnBnClickedButton1)
	ON_WM_PAINT()
END_MESSAGE_MAP()


// CHelp 메시지 처리 코드 // message handler code


void CHelp::OnBnClickedButton1()
{
	CLLKGameDlg *parent = (CLLKGameDlg *)GetParent();
	if (BST_CHECKED == IsDlgButtonChecked(IDC_CHECK1))
	{
		parent->isbackmusic = false;
		mciSendString(_T("play file"), NULL, 0, NULL);//sound
		// select
	}
	else
	{
		parent->isbackmusic = true;
		mciSendString(_T("stop file"), NULL, 0, NULL);//sound
	}

	if (BST_CHECKED == IsDlgButtonChecked(IDC_CHECK2))
	{
		parent->ismusic = false;
		// select
	}
	else
	{
		parent->ismusic = true;
	}

	CHelp::OnOK();

}
