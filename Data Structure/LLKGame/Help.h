#pragma once
#include "afxext.h"


// CHelp dialog

class CHelp : public CDialogEx
{
	DECLARE_DYNAMIC(CHelp)

public:
	CHelp(CWnd* pParent = NULL);   // constructor
	virtual ~CHelp();

// dialog data
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_HELP };
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	afx_msg void OnPaint();
	DECLARE_MESSAGE_MAP()
public:
	CDC* sysDC;						//systemDC
	CBitmap m_backBM;				//background
	CDC m_backDC;				    //backgroundDC

	CBitmap m_cacheBM;              //cache
	CDC m_cacheDC;                  //cacheDC

	virtual BOOL OnInitDialog();
	CBitmapButton known;
	afx_msg void OnBnClickedButton1();
};
