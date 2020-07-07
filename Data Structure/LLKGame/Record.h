#pragma once
#include "afxcmn.h"


// CRecord dialog

class CRecord : public CDialogEx
{
	DECLARE_DYNAMIC(CRecord)

public:
	CRecord(CWnd* pParent = NULL);   // constructor
	virtual ~CRecord();

#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_RECORD };
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
public:
	virtual BOOL OnInitDialog();
	void ReadRecord();
	afx_msg void OnBnClickedOk();
	afx_msg void OnBnClickedCancel();
	CListCtrl list;
	afx_msg void OnBnClickedButton1();
	typedef struct Rec
	{
		CString name;
		CString score;
		CString level;
	} Rec;
	Rec rec[11];
	void sortRec(Rec rec[]);
	afx_msg void OnLvnItemchangedList1(NMHDR *pNMHDR, LRESULT *pResult);
};
