#pragma once
#include "afxwin.h"


// CADD dialog

class CADD : public CDialogEx
{
	DECLARE_DYNAMIC(CADD)

public:
	CADD(CWnd* pParent = NULL);   // constructor
	virtual ~CADD();

// dialog data
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_ADD };
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
public:
	virtual BOOL OnInitDialog();
	afx_msg void OnBnClickedOk();
	afx_msg void OnBnClickedCancel();

	CString name;
	int score;
	int level;
};
