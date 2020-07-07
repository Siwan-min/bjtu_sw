
// LLKGameDlg.h : header file
//

#pragma once
#include "afxcmn.h"
#include "afxwin.h"
#include "afxext.h"


// CLLKGameDlg dialog
class CLLKGameDlg : public CDialogEx
{
// construction
public:
	CLLKGameDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_LLKGAME_DIALOG };
#endif

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support


// Implementation
protected:
	HICON m_hIcon;

	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()

private:
	CDC* sysDC;						//system DC
	CBitmap m_backBM;				//background
	CDC m_backDC;				    //background DC
	CBitmap m_beginBM;				//init back
	CDC m_beginDC;				    //init backDC
	CBitmap m_frameBM;				//3Dframe
	CDC	m_frameDC;				    //3D DC
	CBitmap m_animalBM;				//animal
	CDC	m_animalDC;			    	//animalDC		    	

	CBitmap m_cacheBM;              //cache
	CDC m_cacheDC;					//cacheDC

	CBitmap m_resortBM;             //resort button
	CDC m_resortDC;                 //resortDC
	CBitmap m_tipBM;                //tip button
	CDC	m_tipDC;                    //tipDC
	CBitmap m_numBM;                //num È½¼ö
	CDC	m_numDC;                    //numDC
	CBitmap m_pauseBM;              //pause button 
	CDC	m_pauseDC;                  //pause DC
	CBitmap m_homeBM;               //main menu
	CDC	m_homeDC;                   //main menuDC
	CBitmap m_scoreBM;              //score
	CDC	m_scoreDC;                  //scoreDC
	CBitmap m_whileBM;              //score
	CDC	m_whileDC;                  //score DC


	CRect reLineRect;				//erase line
	CPoint tempDrawPosFir;			//middle point
	CPoint tempDrawPosSec;			//middle point

public:
	int sortTimes;					//sort times
	int timer;						//timer
	int tiptimes;                   //tip times
    int score;
	int *m_map;					    //map
	int **tempMap;	                //2 dimensional array about map
	int	m_firstPos;				    //first position
	bool m_started;			        //whether start
	bool ishide;                    //whether hide
	bool ispause;                   //whether pause
	bool ismusic;                   //whether have music
	bool isbackmusic;               //whether have backmusic
	int level;                      //game level
	int MAP_SIZE;                   //map size
	int MAP_ROW;                    //map row
	int MAP_LINE;                   //map line
	int ANI_NUM;                    //animal number
	int ANI_KIND;                   //animal kind
	int MAIN_OFFSET;                //main offset
	void DrawCache(CRect rect);
	void InitMap(int *map);
	void ResortMap(int *map);
	void CreatTempMap();
	void DeleteTempMap();
	void ClearTempMap();
	void Level1();
    void Level2();
	void Level3();
	void LLKGameCall(CPoint point);
	void ResortButtonCall();
	void TipButtonCall();
	void MainMenuCall();
	void PauseButtonCall();
	void DrawConnectLine(int first, int second);
	bool IsWin();
	bool IsLose();
	bool IsDead();
	bool ConnectOK(int firstPos, int secondPos);
	bool IsConnected(int firstX, int firstY, int secondX, int secondY);
	afx_msg void OnBnClickedButton1();
	afx_msg void OnBnClickedButton2();
	afx_msg void OnBnClickedButton3();
	afx_msg void OnBnClickedButton4();
	afx_msg void OnBnClickedButton5();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnNMCustomdrawProgress(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	CProgressCtrl proCtrl;
	CBitmapButton start;
	CBitmapButton rank;
	CBitmapButton set;
	CBitmapButton help;
	CBitmapButton about;
	CBitmapButton easy;
	CBitmapButton normal;
	CBitmapButton difficult;
	CBitmapButton butRestart;
	afx_msg void OnBnClickedButton7();
	afx_msg void OnBnClickedButton8();
	afx_msg void OnBnClickedButton9();
	afx_msg void OnBnClickedButton10();
};
