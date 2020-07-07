
// LLKGameDlg.cpp : implementation file
//
#include "stdafx.h"
#include "LLKGame.h"
#include "LLKGameDlg.h"
#include "afxdialogex.h"
#include "ADD.h"
#include "Help.h"
#include "Record.h"
#include <mmsystem.h>	
#pragma comment(lib, "WINMM.LIB")

#ifdef _DEBUG
#define new DEBUG_NEW
#define UNUSE -1                        //no picture
#define FRAME_WIDTH	46					//3Dbackground width	이미지 배경 크기
#define FRAME_HIGHT	56					//3Dbackground hight
#define ANIMAL_WIDTH 39					//animal_width	이미지 크기
#define ANIMAL_HIGHT 39					//animal_hight	
#define RESORT_WIDTH 40                 //resort	아이템 크기
#define	RESORT_HIGHT 40                 //resort
#define ID_TIMER 2056                   //timer ID
#endif

// CLLGameDIg dialog

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// dialog data
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_ABOUTBOX };
#endif

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

// implement
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(IDD_ABOUTBOX)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()




CLLKGameDlg::CLLKGameDlg(CWnd* pParent /*=NULL*/)	//게임 DIALOG
	: CDialogEx(IDD_LLKGAME_DIALOG, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
	level = 1;
	sortTimes = 3;
	tiptimes = 3;
	MAP_SIZE = 84;
	MAP_ROW = 7;
	MAP_LINE = 12;
	ANI_NUM = 4;
	ANI_KIND = 21;
	MAIN_OFFSET = 100;
	m_map = new int[MAP_SIZE];
	m_started = false;
	timer = 0;
	score = 0;
	ishide = false;
}

void CLLKGameDlg::DoDataExchange(CDataExchange* pDX)	//데이터 교환
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_PROGRESS1, proCtrl);
	DDX_Control(pDX, IDC_BUTTON1, start);
	DDX_Control(pDX, IDC_BUTTON2, rank);
	DDX_Control(pDX, IDC_BUTTON3, set);
	DDX_Control(pDX, IDC_BUTTON4, help);
	DDX_Control(pDX, IDC_BUTTON5, about);
	DDX_Control(pDX, IDC_BUTTON7, easy);
	DDX_Control(pDX, IDC_BUTTON8, normal);
	DDX_Control(pDX, IDC_BUTTON9, difficult);
	DDX_Control(pDX, IDC_BUTTON10, butRestart);
}

BEGIN_MESSAGE_MAP(CLLKGameDlg, CDialogEx)	//메세지
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_LBUTTONDOWN()
	ON_BN_CLICKED(IDC_BUTTON1, &CLLKGameDlg::OnBnClickedButton1)
	ON_BN_CLICKED(IDC_BUTTON2, &CLLKGameDlg::OnBnClickedButton2)
	ON_BN_CLICKED(IDC_BUTTON3, &CLLKGameDlg::OnBnClickedButton3)
	ON_BN_CLICKED(IDC_BUTTON4, &CLLKGameDlg::OnBnClickedButton4)
	ON_BN_CLICKED(IDC_BUTTON5, &CLLKGameDlg::OnBnClickedButton5)
	ON_WM_TIMER()
	ON_NOTIFY(NM_CUSTOMDRAW, IDC_PROGRESS1, &CLLKGameDlg::OnNMCustomdrawProgress)
	ON_BN_CLICKED(IDC_BUTTON7, &CLLKGameDlg::OnBnClickedButton7)
	ON_BN_CLICKED(IDC_BUTTON8, &CLLKGameDlg::OnBnClickedButton8)
	ON_BN_CLICKED(IDC_BUTTON9, &CLLKGameDlg::OnBnClickedButton9)
	ON_BN_CLICKED(IDC_BUTTON10, &CLLKGameDlg::OnBnClickedButton10)
END_MESSAGE_MAP()


// CLLKGameDlg  message handler

BOOL CLLKGameDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();


	// IDM_ABOUTBOX 
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}


	SetIcon(m_hIcon, TRUE);			// big table set
	SetIcon(m_hIcon, FALSE);		// small table set

	CRect rect;
	GetClientRect(rect);

	sysDC = GetDC();
	m_backDC.CreateCompatibleDC(sysDC);
	m_backBM.LoadBitmap(IDB_BMP_BACKGROUND);
	m_backDC.SelectObject(m_backBM);

	//initialize background

	m_beginDC.CreateCompatibleDC(sysDC);
	m_beginBM.LoadBitmap(IDB_BMP_BEGINBACK);
	m_beginDC.SelectObject(m_beginBM);

	//initialize line rectangle

	m_frameDC.CreateCompatibleDC(sysDC);
	m_frameBM.LoadBitmap(IDB_BMP_3DFRAME);
	m_frameDC.SelectObject(m_frameBM);

	//initialize animal
	m_animalDC.CreateCompatibleDC(sysDC);
	m_animalBM.LoadBitmap(IDB_BMP_ANIMALS);
	m_animalDC.SelectObject(m_animalBM);

	//initialize resort
	m_resortDC.CreateCompatibleDC(sysDC);
	m_resortBM.LoadBitmap(IDB_BMP_RESORT);
	m_resortDC.SelectObject(m_resortBM);

	//init tip
	m_tipDC.CreateCompatibleDC(sysDC);
	m_tipBM.LoadBitmap(IDB_TIP);
	m_tipDC.SelectObject(m_tipBM);

	//init num
	m_numDC.CreateCompatibleDC(sysDC);
	m_numBM.LoadBitmap(IDB_NUM);
	m_numDC.SelectObject(m_numBM);

	//init pause
	m_pauseDC.CreateCompatibleDC(sysDC);
	m_pauseBM.LoadBitmap(IDB_PAUSE);
	m_pauseDC.SelectObject(m_pauseBM);

	//init home
	m_homeDC.CreateCompatibleDC(sysDC);
	m_homeBM.LoadBitmap(IDB_HOME);
	m_homeDC.SelectObject(m_homeBM);

	//init score
	m_scoreDC.CreateCompatibleDC(sysDC);
	m_scoreBM.LoadBitmap(IDB_SCORE);
	m_scoreDC.SelectObject(m_scoreBM);

	m_whileDC.CreateCompatibleDC(sysDC);
	m_whileBM.LoadBitmap(IDB_WHILE);
	m_whileDC.SelectObject(m_whileBM);

	//init cache
	m_cacheDC.CreateCompatibleDC(sysDC);
	m_cacheBM.CreateCompatibleBitmap(sysDC, rect.Width(), rect.Height());
	m_cacheDC.SelectObject(m_cacheBM);


	//init
	start.LoadBitmaps(IDB_START, IDB_START1, IDB_START, IDB_START1);  
	start.SizeToContent();  
	rank.LoadBitmaps(IDB_RANK, IDB_RANK1, IDB_RANK, IDB_RANK1);     
	rank.SizeToContent();  
	set.LoadBitmaps(IDB_SET, IDB_SET1, IDB_SET, IDB_SET1);    
	set.SizeToContent();  
	help.LoadBitmaps(IDB_HELP, IDB_HELP1, IDB_HELP, IDB_HELP1);   
	help.SizeToContent();  
	about.LoadBitmaps(IDB_ABOUT, IDB_ABOUT1, IDB_ABOUT, IDB_ABOUT1);   
	about.SizeToContent();  
	easy.LoadBitmaps(IDB_EASY, IDB_EASY1, IDB_EASY, IDB_EASY1);
	easy.SizeToContent();
	normal.LoadBitmaps(IDB_NORMAL, IDB_NORMAL1, IDB_NORMAL, IDB_NORMAL1);
	normal.SizeToContent();
	difficult.LoadBitmaps(IDB_DIFFICULT, IDB_DIFFICULT1, IDB_DIFFICULT, IDB_DIFFICULT1);
	difficult.SizeToContent();
	butRestart.LoadBitmaps(IDB_RESTART, IDB_RESTART1, IDB_RESTART, IDB_RESTART1);
	butRestart.SizeToContent();

	proCtrl.ShowWindow(SW_HIDE);
	butRestart.ShowWindow(SW_HIDE);
	easy.ShowWindow(SW_HIDE);
	normal.ShowWindow(SW_HIDE);;
	difficult.ShowWindow(SW_HIDE);
	CreatTempMap();
	mciSendString(_T("open res\\FloralLife.mp3 type mpegvideo alias file "), NULL, 0, NULL);//turn on sound
	return TRUE;  // TRUE
}

void CLLKGameDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}


void CLLKGameDlg::OnPaint()
{
	CPaintDC dc(this);

	CRect rect;
	GetClientRect(rect);

	bool win = IsWin();
	bool timeOut = IsLose();

	if (!isbackmusic)
	{
		mciSendString(_T("play file"), NULL, 0, NULL);//sound
	}

	//started
	if (m_started == true && !win && timeOut == false)
	{
		DrawCache(rect);
		dc.BitBlt(0, 0, rect.Width(), rect.Height(), &m_cacheDC, 0, 0, SRCCOPY);

		dc.SetBkMode(TRANSPARENT);
		dc.SetTextColor(RGB(0, 0, 0));
		wchar_t wc[16];
		if (score >= 0)
		{
			//now score
			wsprintfW(wc, L"%d", score);
			dc.TextOutW(670, 540, wc);
		}
		else
		{
			//at least now score is 0
			dc.TextOutW(670, 540, L"0");
		}


	}
	else
	{
		dc.StretchBlt(0, 0, rect.Width(), rect.Height(), &m_backDC, 0, 0, 800, 600, SRCCOPY);
		//win
		if (win == true && m_started == true && timeOut == false)
		{
			dc.StretchBlt(0, 0, rect.Width(), rect.Height(), &m_backDC, 0, 0, 800, 600, SRCCOPY);

			this->KillTimer(ID_TIMER);
			if (ismusic)
			{
				PlaySound(_T("C:\\Users\\success.wav"), NULL, SND_FILENAME | SND_ASYNC);
			}
			CADD adddlg;
			if (ishide)
			{
				DeleteTempMap();
				delete[]m_map;
				CreatTempMap();
				m_map = new int[MAP_SIZE];
				m_started = false;
				timer = 0;
				ishide = false;
				adddlg.DoModal();
			}
			ishide = true;
			start.ShowWindow(SW_NORMAL);
			rank.ShowWindow(SW_NORMAL);
			set.ShowWindow(SW_NORMAL);
			help.ShowWindow(SW_NORMAL);
			about.ShowWindow(SW_NORMAL);
		}


		//lose
		if (timeOut == true && m_started == true && win == false)
		{
			dc.StretchBlt(0, 0, rect.Width(), rect.Height(), &m_backDC, 0, 0, 800, 600, SRCCOPY);
			this->KillTimer(ID_TIMER);
			if (ishide)
			{
				DeleteTempMap();
				delete[]m_map;
				CreatTempMap();
				m_map = new int[MAP_SIZE];
				m_started = false;
				timer = 0;
				ishide = false;
				if (!ismusic)
				{
					PlaySound(_T("C:\\Users\\fail.wav"), NULL, SND_FILENAME | SND_ASYNC);
				}
				MessageBox(L"you lose！");
			}
			ishide = true;
			start.ShowWindow(SW_NORMAL);
			rank.ShowWindow(SW_NORMAL);
			set.ShowWindow(SW_NORMAL);
			help.ShowWindow(SW_NORMAL);
			about.ShowWindow(SW_NORMAL);
		}
		if (ishide)
		{
			proCtrl.ShowWindow(SW_HIDE);
			return;
		}
	}
	CDialog::OnPaint();
	
}






HCURSOR CLLKGameDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}


//back ground
void CLLKGameDlg::DrawCache(CRect rect)
{
	//3D color
	int ran;
	switch (level)
	{
	case 1:ran = 1; break;
	case 2:ran = 2; break;
	case 3:srand(GetTickCount());
		ran = (rand()*0.1 + rand()*0.01 + rand()*0.001);
		ran = ran % 6; break;
	}
	m_cacheDC.StretchBlt(0, 0, rect.Width(), rect.Height(), &m_beginDC, 0, 0, 800, 600, SRCCOPY);
	
	//restart button and num
	m_cacheDC.BitBlt(MAIN_OFFSET, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_resortDC, RESORT_WIDTH, 0, SRCAND);
	m_cacheDC.BitBlt(MAIN_OFFSET, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_resortDC, 0, 0, SRCPAINT);
	m_cacheDC.BitBlt(MAIN_OFFSET+ RESORT_WIDTH, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH*2, RESORT_HIGHT, &m_numDC, RESORT_WIDTH*2, RESORT_HIGHT*sortTimes, SRCAND);
	m_cacheDC.BitBlt(MAIN_OFFSET+ RESORT_WIDTH, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH*2, RESORT_HIGHT, &m_numDC, 0, RESORT_HIGHT*sortTimes, SRCPAINT);


	//hint button and num
	m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH*4, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_tipDC, RESORT_WIDTH, 0, SRCAND);
	m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH*4, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_tipDC, 0, 0, SRCPAINT);
	m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH*5, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH*2, RESORT_HIGHT, &m_numDC, RESORT_WIDTH * 2, RESORT_HIGHT*tiptimes, SRCAND);
	m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH*5, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH*2, RESORT_HIGHT, &m_numDC, 0, RESORT_HIGHT*tiptimes, SRCPAINT);

	//pause button
	if (ispause)
	{
		m_cacheDC.BitBlt(MAIN_OFFSET +100, MAIN_OFFSET , RESORT_WIDTH*10, RESORT_HIGHT*10, &m_whileDC, RESORT_WIDTH*10+8, 0, SRCAND);
		m_cacheDC.BitBlt(MAIN_OFFSET +100, MAIN_OFFSET , RESORT_WIDTH*10, RESORT_HIGHT*10, &m_whileDC, 0+8, 0, SRCPAINT);
		m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH * 16, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_pauseDC, RESORT_WIDTH, RESORT_HIGHT, SRCAND);
		m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH * 16, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_pauseDC, 0, 0, SRCPAINT);
	}
	else
	{
		m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH * 16, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_pauseDC, RESORT_WIDTH, RESORT_HIGHT, SRCAND);
		m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH * 16, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_pauseDC, 0, RESORT_HIGHT, SRCPAINT);
	}

	//main menu
	m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH * 17+10, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_homeDC, RESORT_WIDTH, 0, SRCAND);
	m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH * 17+10, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_homeDC, 0, 0, SRCPAINT);

	//score
	int rank = score /100;
	if (rank >= 5)
	{
		rank = 5;
	}
	m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH * 12 + 10, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_scoreDC, RESORT_WIDTH, rank*RESORT_HIGHT, SRCAND);
	m_cacheDC.BitBlt(MAIN_OFFSET + RESORT_WIDTH * 12 + 10, MAIN_OFFSET + 10 * RESORT_HIGHT, RESORT_WIDTH, RESORT_HIGHT, &m_scoreDC, 0, rank*RESORT_HIGHT, SRCPAINT);

	//block and animal
	for (int i = 0; i<MAP_ROW; i++)
	{
		for (int j = 0; j<MAP_LINE; j++)
		{
			int pos = i*MAP_LINE + j;		 //map array pos					

			if (m_map[pos] == UNUSE)                                     
			{
				continue;                   // extinction pos don't use map                              
			}
			int posX = j*FRAME_WIDTH + MAIN_OFFSET;			 //horizontal coordinates			
			int posY = i*FRAME_HIGHT + MAIN_OFFSET;			 // vertical coordinates			

			if (!ispause)
			{
				//frame
				m_cacheDC.BitBlt(posX, posY, FRAME_WIDTH, FRAME_HIGHT, &m_frameDC, 0, 0, SRCAND);
				m_cacheDC.BitBlt(posX, posY, FRAME_WIDTH, FRAME_HIGHT, &m_frameDC, 0, FRAME_HIGHT*(ran+1), SRCPAINT);

				//draw animal
				m_cacheDC.BitBlt(posX, posY, ANIMAL_WIDTH, ANIMAL_HIGHT, &m_animalDC, ANIMAL_WIDTH, m_map[pos] * ANIMAL_HIGHT, SRCAND);
				m_cacheDC.BitBlt(posX, posY, ANIMAL_WIDTH, ANIMAL_HIGHT, &m_animalDC, 0, m_map[pos] * ANIMAL_HIGHT, SRCPAINT);
			}
		}
	}
}



//to initialize a dimension, random number.
void CLLKGameDlg::InitMap(int *map)									
{
	CByteArray arr;
	for (int i = 0; i<ANI_KIND; i++)
	{
		for (int j = 0; j <ANI_NUM; j++)
		{
			arr.Add(i);
		}
	}

	for (int i = 0; i<MAP_SIZE; i++)
	{
		int len = arr.GetSize();
		int ran;
		srand(GetTickCount());
		ran = (rand()*0.1 + rand()*0.01 + rand()*0.001);
		ran = ran%len;
		*(map + i) = arr.GetAt(ran);
		arr.RemoveAt(ran);
	}
}

//dimension of a row
void CLLKGameDlg::ResortMap(int *map)												
{
	CArray<int> arr;

	for (int i = 0; i < MAP_SIZE; i++)
	{
		if (*(map + i) != UNUSE)														
		{
			arr.Add(*(map + i));
		}
	}

	for (int i = 0; i<MAP_SIZE; i++)
	{
		if (*(map + i) != UNUSE)														
		{
			int len = arr.GetSize();
			int ran;
			srand(GetTickCount());
			ran = (rand()*0.1 + rand()*0.01 + rand()*0.001);
			ran = ran%len;
			*(map + i) = arr.GetAt(ran);
			arr.RemoveAt(ran);
		}
	}
}





//create a second dimension.
void CLLKGameDlg::CreatTempMap()													
{
	tempMap = new int*[MAP_ROW + 2];
	for (int i = 0; i<MAP_ROW + 2; i++)
	{
		tempMap[i] = new int[MAP_LINE + 2];
	}

}

//the release of two dimension
void CLLKGameDlg::DeleteTempMap()													
{
	for (int i = 0; i<MAP_ROW + 2; i++)
	{
		delete[]tempMap[i];
	}
	delete[]tempMap;
}

//initialization two dimension
void CLLKGameDlg::ClearTempMap()								
{
	for (int i = 0; i <MAP_ROW + 2; i++)
	{
		for (int j = 0; j <MAP_LINE + 2; j++)
		{
			tempMap[i][j] = UNUSE;
		}
	}
}





//is it has been unable to elimination again
bool CLLKGameDlg::IsDead()
{
	if (IsWin() == true)
	{
		return false;
	}

	for (int i = 0; i<MAP_SIZE; i++)
	{
		if (m_map[i] != UNUSE)
		{
			for (int j = i + 1; j<MAP_SIZE; j++)
			{
				if (m_map[j] == UNUSE)
				{
					continue;
				}

				if (m_map[i] == m_map[j] && ConnectOK(i, j) == true)
				{
					return false;
				}
			}
		}
	}
	return true;
}

//is the winner
bool CLLKGameDlg::IsWin()															
{
	for (int i = 0; i< MAP_SIZE; i++)
	{
		if (*(m_map + i) != UNUSE)
		{
			return false;
		}
	}
	return true;
}

//whether filure
bool CLLKGameDlg::IsLose()
{
	bool dead = IsDead();

	if (dead == true&& m_started == true)
	{
		ResortButtonCall();
		return false;
	}

	if (timer >= 50|| dead == true)
	{
		return true;
	}
	else
	{
		return false;
	}
}



//can two points even in the same row or column
bool CLLKGameDlg::IsConnected(int firstX, int firstY, int secondX, int secondY)
{
	if (firstX != secondX && firstY != secondY)										// if be not in the oneline , don't work
	{
		return false;
	}

	if (firstX == secondX && firstY == secondY)										// tow point can same pos
	{
		return true;
	}

	if (firstX == secondX)															// if  same col
	{
		if (firstY > secondY)														// fist be at least 
		{
			int temp = firstY;
			firstY = secondY;
			secondY = temp;
		}

		for (int i = firstY + 1; i < secondY; i++)
		{
			if (tempMap[firstX][i] != UNUSE)
			{
				return false;														//If a little bit, was closed.
			}
		}
	}

	if (firstY == secondY)															// If in the same line.
	{
		if (firstX > secondX)														//Minimum point as first X.
		{
			int temp = firstX;
			firstX = secondX;
			secondX = temp;
		}

		for (int i = firstX + 1; i < secondX; i++)
		{
			if (tempMap[i][firstY] != UNUSE)
			{
				return false;														//If a little bit, was closed.
			}
		}
	}

	if (tempMap[firstX][firstY] != UNUSE || tempMap[secondX][secondY] != UNUSE)		//When two points for UNUSE can, or different.
	{
		return false;
	}

	return true;                                                                    //The above conditions are satisfied to return to TRUE.
}


//Even if two points.
bool CLLKGameDlg::ConnectOK(int firstPos, int secondPos)
{
	bool retVal = false;

	for (int i = 1, k = 0; i<MAP_ROW + 1; i++)									   //a map will be placed in temporary 2 dimension of the convenient judgment.
	{
		for (int j = 1; j < MAP_LINE + 1; j++)										//The two dimension outside are UNUSE the group.
		{																			// In this way to the edge of the block can be good for whether or not up to.
			tempMap[i][j] = *(m_map + k);
			k++;
		}
	}

	int firstX = firstPos / MAP_LINE + 1;										// Number one dimension in the second set of the row angular.
	int firstY = firstPos%MAP_LINE + 1;								           // Number one dimension in the second group in the corner.
	int secondX = secondPos / MAP_LINE + 1;										//The second point in two dimension of row angular.
	int secondY = secondPos%MAP_LINE + 1;										//The second in two dimension of the corner.

	tempMap[firstX][firstY] = UNUSE;											//Place the current two points were set to UNUSE, because only judge whether up to the criteria for empty when it can be up to two points.
	tempMap[secondX][secondY] = UNUSE;											//(Because it's at all in order to solve, inflection point, whether for judgment must undergo when UNUSE).

	for (int i = 0; i < MAP_ROW + 2; i++)										//vertical dimension traversal two groups.
	{
		if (retVal && (i > firstX))
		{
			break;
		}

		if (!IsConnected(firstX, firstY, i, firstY))								// If up to the vertical section. Go on, or to explain this path was closed.
		{
			continue;
		}

		if (!IsConnected(i, firstY, i, secondY))									//Since this to the level of judgment and in second with a line on whether up to.
		{
			continue;
		}

		if (!IsConnected(i, secondY, secondX, secondY))
		{
			continue;
		}

		tempDrawPosFir.x = firstY*FRAME_WIDTH + (MAIN_OFFSET - FRAME_WIDTH) + FRAME_WIDTH / 2;
		tempDrawPosFir.y = i*FRAME_HIGHT + (MAIN_OFFSET - FRAME_HIGHT) + FRAME_HIGHT / 2;
		tempDrawPosSec.x = secondY*FRAME_WIDTH + (MAIN_OFFSET - FRAME_WIDTH) + FRAME_WIDTH / 2;
		tempDrawPosSec.y = i*FRAME_HIGHT + (MAIN_OFFSET - FRAME_HIGHT) + FRAME_HIGHT / 2;

		retVal = true;
	}

	if (retVal == true)
	{
		return retVal;
	}

	for (int i = 0; i < MAP_LINE + 2; i++)											//transverse traversal.
	{
		if (retVal && (i > firstY))													// When as long as you can make any sense, saw more than in the shortest path to judge the first point, don't.，
																					//Because when the two points in a line when standing next to the shortest, thus can be sure whether there is such a path.
		{
			break;
		}

		if (!IsConnected(firstX, firstY, firstX, i))
		{
			continue;
		}

		if (!IsConnected(firstX, i, secondX, i))
		{
			continue;
		}

		if (!IsConnected(secondX, i, secondX, secondY))
		{
			continue;
		}

		tempDrawPosFir.x = i*FRAME_WIDTH + (MAIN_OFFSET - FRAME_WIDTH) + FRAME_WIDTH / 2;
		tempDrawPosFir.y = firstX*FRAME_HIGHT + (MAIN_OFFSET - FRAME_HIGHT) + FRAME_HIGHT / 2;
		tempDrawPosSec.x = i*FRAME_WIDTH + (MAIN_OFFSET - FRAME_WIDTH) + FRAME_WIDTH / 2;
		tempDrawPosSec.y = secondX*FRAME_HIGHT + (MAIN_OFFSET - FRAME_HIGHT) + FRAME_HIGHT / 2;

		retVal = true;
	}

	if (retVal != true)																	//There is no path at the time.
	{
		tempMap[firstX][firstY] = m_map[firstPos];									    //When the judgment still want to go back to when elimination.
		tempMap[secondX][secondY] = m_map[secondPos];
		return retVal;
	}
	else																				// Is there a path, has recorded the most short.
	{
		return retVal;
	}
}




//painting.
void CLLKGameDlg::DrawConnectLine(int first, int second)
{
	CPoint firPoint, secPoint;
	firPoint.x = (first%MAP_LINE)*FRAME_WIDTH + MAIN_OFFSET + FRAME_WIDTH / 2;
	firPoint.y = (first / MAP_LINE)*FRAME_HIGHT + MAIN_OFFSET + FRAME_HIGHT / 2;
	secPoint.x = (second%MAP_LINE)*FRAME_WIDTH + MAIN_OFFSET + FRAME_WIDTH / 2;
	secPoint.y = (second / MAP_LINE)*FRAME_HIGHT + MAIN_OFFSET + FRAME_HIGHT / 2;

	CClientDC dc(this);
	CPen borderPen(PS_SOLID, 4, RGB(255, 20, 147));
	CPen *pOldPen = dc.SelectObject(&borderPen);

	if (tempDrawPosFir.x == tempDrawPosSec.x && tempDrawPosFir.y == tempDrawPosSec.y)						//two points in the process.
	{
		dc.MoveTo(firPoint);
		dc.LineTo(secPoint);
	}
	else
	{
		dc.MoveTo(firPoint);
		dc.LineTo(tempDrawPosFir);
		dc.LineTo(tempDrawPosSec);
		dc.LineTo(secPoint);
	}
	dc.SelectObject(pOldPen);
}

//According to the position of the mouse to click the appropriate correlation function.
void CLLKGameDlg::OnLButtonDown(UINT nFlags, CPoint point)
{
	if (m_started == false || IsWin() || IsLose())
	{
		return;
	}

	if (point.x <= MAIN_OFFSET + RESORT_WIDTH * 18 + 10 && point.x >= MAIN_OFFSET + RESORT_WIDTH * 17 + 10 && point.y <= MAIN_OFFSET + 11 * RESORT_HIGHT&& point.y >= MAIN_OFFSET + 10 * RESORT_HIGHT)
	{
		MainMenuCall();
	}

	if (point.x <= MAIN_OFFSET + RESORT_WIDTH * 17+10 && point.x >= MAIN_OFFSET +RESORT_WIDTH * 16 + 10 && point.y <= MAIN_OFFSET + 11 * RESORT_HIGHT&& point.y >= MAIN_OFFSET + 10 * RESORT_HIGHT)
	{
		PauseButtonCall();
	}

	if (ispause)
	{
		return;
	}

	if (point.x <= MAIN_OFFSET + RESORT_WIDTH * 3 && point.x >= MAIN_OFFSET && point.y <= MAIN_OFFSET + 11 * RESORT_HIGHT&& point.y >= MAIN_OFFSET + 10 * RESORT_HIGHT)
	{
		ResortButtonCall();
		return;
	}

	if (point.x <= MAIN_OFFSET + RESORT_WIDTH * 7 && point.x >= MAIN_OFFSET+RESORT_WIDTH * 4 && point.y <= MAIN_OFFSET + 11 * RESORT_HIGHT&& point.y >= MAIN_OFFSET + 10 * RESORT_HIGHT)
	{
		TipButtonCall();
		return;
	}

	if (point.x < MAIN_OFFSET || point.y < MAIN_OFFSET || point.x >(MAIN_OFFSET + MAP_LINE*FRAME_WIDTH) || point.y >(MAIN_OFFSET + MAP_ROW*FRAME_HIGHT))
	{
		return;
	}
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	LLKGameCall(point);
}

//elimination.
void CLLKGameDlg::LLKGameCall(CPoint point)													
{
	int curX = point.x - MAIN_OFFSET;
	int curY = point.y - MAIN_OFFSET;
	int borderX = (curX - curX%FRAME_WIDTH) + MAIN_OFFSET;
	int borderY = (curY - curY%FRAME_HIGHT) + MAIN_OFFSET;
	int curPos = UNUSE;

	curPos = (curX / FRAME_WIDTH) + (curY / FRAME_HIGHT)*MAP_LINE;

	if (m_map[curPos] == UNUSE || m_firstPos == curPos)
	{
		return;
	}

	if ((m_firstPos != UNUSE) && (curPos != m_firstPos) && (m_map[curPos] == m_map[m_firstPos]) && ConnectOK(m_firstPos, curPos))
	{
		CClientDC dc(this);
		CPen borderPen(PS_SOLID, 4, RGB(255, 20, 147));
		CPen *pOldPen = dc.SelectObject(&borderPen);

		dc.MoveTo(borderX , borderY  + 1);							
		dc.LineTo(borderX , borderY  + FRAME_HIGHT - 4);
		dc.LineTo(borderX  + FRAME_WIDTH - 4, borderY  + FRAME_HIGHT - 4);
		dc.LineTo(borderX  + FRAME_WIDTH - 4, borderY );
		dc.LineTo(borderX , borderY  + 1);

		DrawConnectLine(m_firstPos, curPos);
		if (!ismusic)
		{
			PlaySound(_T("C:\\Users\\bingo.wav"), NULL, SND_FILENAME | SND_ASYNC);
		}
		m_map[curPos] = UNUSE;
		m_map[m_firstPos] = UNUSE;

		tempMap[curPos / MAP_LINE + 1][curPos%MAP_LINE + 1] = UNUSE;
		tempMap[m_firstPos / MAP_LINE + 1][m_firstPos%MAP_LINE + 1] = UNUSE;

		score += level * 10;

		m_firstPos = UNUSE;
		Sleep(100);
		Invalidate(FALSE);
		return;
	}

	if (m_firstPos != UNUSE)														//the rectangular box area had not previously is not valid.
	{
		InvalidateRect(&reLineRect);
	}

	m_firstPos = curPos;

	CClientDC dc(this);
	CPen borderPen(PS_SOLID, 4, RGB(255, 20, 147));
	CPen *pOldPen = dc.SelectObject(&borderPen);

	dc.MoveTo(borderX , borderY  + 1);							//red frame of painting.
	dc.LineTo(borderX , borderY  + FRAME_HIGHT - 4);
	dc.LineTo(borderX  + FRAME_WIDTH - 4, borderY  + FRAME_HIGHT - 4);
	dc.LineTo(borderX  + FRAME_WIDTH - 4, borderY );
	dc.LineTo(borderX , borderY  + 1);

	reLineRect.SetRect(borderX, borderY, borderX + FRAME_WIDTH, borderY + FRAME_HIGHT);	//a record of the rectangular box area.

	dc.SelectObject(pOldPen);
}





//painted.
void CLLKGameDlg::OnNMCustomdrawProgress(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMCUSTOMDRAW pNMCD = reinterpret_cast<LPNMCUSTOMDRAW>(pNMHDR);
	*pResult = 0;
}

//timing control functions.
void CLLKGameDlg::OnTimer(UINT_PTR nIDEvent)
{
	timer++;
	proCtrl.SetPos(100 - timer * 2);

	if (timer >= 50)
	{
		this->KillTimer(ID_TIMER);
		Invalidate(true);
	}
}





//Sequential rearrangements.
void CLLKGameDlg::ResortButtonCall()												
{
	if (sortTimes == 0)
	{
		return;
	}
	PlaySound(_T("C:\\Users\\Resort.wav"), NULL, SND_FILENAME | SND_ASYNC);
	CClientDC dc(this);
	CPen borderPen(PS_SOLID, 4, RGB(255, 0, 0));
	CPen *pOldPen = dc.SelectObject(&borderPen);


	dc.MoveTo(MAIN_OFFSET, MAIN_OFFSET + 10 * RESORT_HIGHT);
	dc.LineTo(MAIN_OFFSET+ RESORT_WIDTH*3, MAIN_OFFSET + 10 * RESORT_HIGHT);
	dc.LineTo(MAIN_OFFSET + RESORT_WIDTH*3, MAIN_OFFSET + 11 * RESORT_HIGHT);
	dc.LineTo(MAIN_OFFSET, MAIN_OFFSET + 11 * RESORT_HIGHT);
	dc.LineTo(MAIN_OFFSET, MAIN_OFFSET + 10 * RESORT_HIGHT);

	Sleep(100);
	sortTimes--;
	score -= 5;
	ResortMap(m_map);
	ClearTempMap();

	dc.SelectObject(pOldPen);
	Invalidate(FALSE);
}


//Return to the tip button
void CLLKGameDlg::TipButtonCall()
{
	if (tiptimes == 0)
	{
		return;
	}
	PlaySound(_T("C:\\Users\\tip.wav"), NULL, SND_FILENAME | SND_ASYNC);
	for (int i = 0; i < MAP_SIZE; i++)
	{
		for (int j = 0; j < MAP_SIZE; j++)
		{
			if ((m_map[j] != -1) && (m_map[i] != -1) && (j != i) && (m_map[j] == m_map[i]) && ConnectOK(i, j))
			{
				int firstY = i / MAP_LINE + 1;
				int firstX = i % MAP_LINE + 1;
				int secondY = j / MAP_LINE + 1;
				int secondX = j % MAP_LINE + 1;
				CClientDC dc(this);
				CPen borderPen(PS_SOLID, 4, RGB(0, 0, 255));
				CPen *pOldPen = dc.SelectObject(&borderPen);


				dc.MoveTo(MAIN_OFFSET + RESORT_WIDTH * 4, MAIN_OFFSET + 10 * RESORT_HIGHT);
				dc.LineTo(MAIN_OFFSET + RESORT_WIDTH * 7, MAIN_OFFSET + 10 * RESORT_HIGHT);
				dc.LineTo(MAIN_OFFSET + RESORT_WIDTH * 7, MAIN_OFFSET + 11 * RESORT_HIGHT);
				dc.LineTo(MAIN_OFFSET + RESORT_WIDTH * 4, MAIN_OFFSET + 11 * RESORT_HIGHT);
				dc.LineTo(MAIN_OFFSET + RESORT_WIDTH * 4, MAIN_OFFSET + 10 * RESORT_HIGHT);


				dc.MoveTo(MAIN_OFFSET + (firstX - 1)*FRAME_WIDTH, MAIN_OFFSET + (firstY - 1)*FRAME_HIGHT);							// red frame of painting.
				dc.LineTo(MAIN_OFFSET + (firstX)*FRAME_WIDTH - 4, MAIN_OFFSET + (firstY - 1)*FRAME_HIGHT);
				dc.LineTo(MAIN_OFFSET + (firstX)*FRAME_WIDTH - 4, MAIN_OFFSET + (firstY)*FRAME_HIGHT - 4);
				dc.LineTo(MAIN_OFFSET + (firstX - 1)*FRAME_WIDTH, MAIN_OFFSET + (firstY)*FRAME_HIGHT - 4);
				dc.LineTo(MAIN_OFFSET + (firstX - 1)*FRAME_WIDTH, MAIN_OFFSET + (firstY - 1)*FRAME_HIGHT);


				dc.MoveTo(MAIN_OFFSET + (secondX - 1)*FRAME_WIDTH, MAIN_OFFSET + (secondY - 1)*FRAME_HIGHT);							//red frame of painting.
				dc.LineTo(MAIN_OFFSET + (secondX)*FRAME_WIDTH - 4, MAIN_OFFSET + (secondY - 1)*FRAME_HIGHT);
				dc.LineTo(MAIN_OFFSET + (secondX)*FRAME_WIDTH - 4, MAIN_OFFSET + (secondY)*FRAME_HIGHT - 4);
				dc.LineTo(MAIN_OFFSET + (secondX - 1)*FRAME_WIDTH, MAIN_OFFSET + (secondY)*FRAME_HIGHT - 4);
				dc.LineTo(MAIN_OFFSET + (secondX - 1)*FRAME_WIDTH, MAIN_OFFSET + (secondY - 1)*FRAME_HIGHT);

				//m_map[j] = UNUSE;
				//m_map[i] = UNUSE;

				//tempMap[firstY][firstX] = UNUSE;
				//tempMap[secondY][secondX] = UNUSE;
				//Sleep(100);
				//Invalidate(FALSE);
				tiptimes--;
				score -= 10;
				return;
			}
		}

	}
}

//Return to the main menu.
void CLLKGameDlg::MainMenuCall()	//메인메뉴
{
	CPaintDC dc(this);

	CRect rect;
	GetClientRect(rect);
	dc.StretchBlt(0, 0, rect.Width(), rect.Height(), &m_backDC, 0, 0, 800, 600, SRCCOPY);

	this->KillTimer(ID_TIMER);
	DeleteTempMap();
	delete[]m_map;
	CreatTempMap();
	m_map = new int[MAP_SIZE];
	m_started = false;
	timer = 0;
	ishide = true;

	Invalidate(FALSE);
	start.ShowWindow(SW_NORMAL);
	rank.ShowWindow(SW_NORMAL);
	set.ShowWindow(SW_NORMAL);
	help.ShowWindow(SW_NORMAL);
	about.ShowWindow(SW_NORMAL);
}

///Return to the pauseButton
void CLLKGameDlg::PauseButtonCall()
{
	CClientDC dc(this);
	CPen borderPen(PS_SOLID, 4, RGB(242, 156, 177));
	CPen *pOldPen = dc.SelectObject(&borderPen);


	dc.MoveTo(MAIN_OFFSET + RESORT_WIDTH * 16, MAIN_OFFSET + 10 * RESORT_HIGHT);
	dc.LineTo(MAIN_OFFSET + RESORT_WIDTH * 17, MAIN_OFFSET + 10 * RESORT_HIGHT);
	dc.LineTo(MAIN_OFFSET + RESORT_WIDTH * 17, MAIN_OFFSET + 11 * RESORT_HIGHT);
	dc.LineTo(MAIN_OFFSET + RESORT_WIDTH * 16, MAIN_OFFSET + 11 * RESORT_HIGHT);
	dc.LineTo(MAIN_OFFSET + RESORT_WIDTH * 16, MAIN_OFFSET + 10 * RESORT_HIGHT);


	if (ispause)
	{
		this->SetTimer(ID_TIMER, 2000 * level, NULL);
		proCtrl.SetPos(100 - timer * 2);
		ispause = false;
	}
	else
	{
		this->KillTimer(ID_TIMER);
		ispause = true;
	}
	Sleep(100);
	Invalidate(FALSE);
}





//simple level
void CLLKGameDlg::Level1()													
{
	DeleteTempMap();
	delete[] m_map;

	level = 1;

	timer = 0;
	proCtrl.SetPos(100);
	this->SetTimer(ID_TIMER, 2000 * level, NULL);

	sortTimes = 3;
	tiptimes = 3;
	MAP_SIZE = 112;
	MAP_ROW = 7;
	MAP_LINE = 16;
	ANI_NUM = 16;
	ANI_KIND = 7;
	MAIN_OFFSET = 130;

	CreatTempMap();
	m_map = new int[MAP_SIZE];

	InitMap(m_map);
	m_firstPos = UNUSE;

	ClearTempMap();
	Invalidate(FALSE);
}

//level 2
void CLLKGameDlg::Level2()													
{
	DeleteTempMap();
	delete[]m_map;

	level = 2;

	timer = 0;
	proCtrl.SetPos(100);
	this->SetTimer(ID_TIMER, 2000 * level, NULL);

	sortTimes = 4;
	tiptimes = 4;
	MAP_SIZE = 112;
	MAP_ROW = 7;
	MAP_LINE = 16;
	ANI_NUM = 8;
	ANI_KIND = 14;
	MAIN_OFFSET = 130;

	CreatTempMap();
	m_map = new int[MAP_SIZE];

	InitMap(m_map);
	m_firstPos = UNUSE;

	ClearTempMap();
	Invalidate(FALSE);
}

//level 3
void CLLKGameDlg::Level3()													
{
	DeleteTempMap();
	delete[]m_map;

	level = 3;

	timer = 0;
	proCtrl.SetPos(100);
	this->SetTimer(ID_TIMER, 2000 * level, NULL);

	sortTimes = 5;
	tiptimes = 5;
	MAP_SIZE = 112;
	MAP_ROW = 7;
	MAP_LINE = 16;
	ANI_NUM = 4;
	ANI_KIND = 28;
	MAIN_OFFSET = 130;

	CreatTempMap();
	m_map = new int[MAP_SIZE];

	InitMap(m_map);
	m_firstPos = UNUSE;

	ClearTempMap();
	Invalidate(FALSE);
}




//game start button
void CLLKGameDlg::OnBnClickedButton1()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	ispause = false;
	score = 0;
	start.ShowWindow(SW_HIDE);
	rank.ShowWindow(SW_HIDE);
	set.ShowWindow(SW_HIDE);
	help.ShowWindow(SW_HIDE);
	about.ShowWindow(SW_HIDE);

	butRestart.ShowWindow(SW_NORMAL);
	easy.ShowWindow(SW_NORMAL);
	normal.ShowWindow(SW_NORMAL);
	difficult.ShowWindow(SW_NORMAL);
}

// record button
void CLLKGameDlg::OnBnClickedButton2()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	CRecord recorddlg;
	recorddlg.DoModal();
}

//exit button
void CLLKGameDlg::OnBnClickedButton3()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	if (MessageBox(_T("Really?"), _T("Exit"), MB_OKCANCEL) == IDOK)
	{
		exit(0);
	}
}

//help button
void CLLKGameDlg::OnBnClickedButton4()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	CHelp dlg;
	dlg.DoModal();
}

//about button
void CLLKGameDlg::OnBnClickedButton5()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	CAboutDlg about;
	about.DoModal();
}




//simple mode button
void CLLKGameDlg::OnBnClickedButton7()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	butRestart.ShowWindow(SW_HIDE);
	easy.ShowWindow(SW_HIDE);
	normal.ShowWindow(SW_HIDE);
	difficult.ShowWindow(SW_HIDE);
	
	ishide = false;
	Level1();
	m_started = true;
	proCtrl.ShowWindow(SW_NORMAL);
	Invalidate(FALSE);
}

//common mode button
void CLLKGameDlg::OnBnClickedButton8()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	butRestart.ShowWindow(SW_HIDE);
	easy.ShowWindow(SW_HIDE);
	normal.ShowWindow(SW_HIDE);
	difficult.ShowWindow(SW_HIDE);
	
	ishide = false;
	Level2();
	m_started = true;
	proCtrl.ShowWindow(SW_NORMAL);
	Invalidate(FALSE);
}

//hard mode button
void CLLKGameDlg::OnBnClickedButton9()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	butRestart.ShowWindow(SW_HIDE);
	easy.ShowWindow(SW_HIDE);
	normal.ShowWindow(SW_HIDE);
	difficult.ShowWindow(SW_HIDE);
	
	ishide = false;
	Level3();
	m_started = true;
	proCtrl.ShowWindow(SW_NORMAL);
	Invalidate(FALSE);
}

//return button
void CLLKGameDlg::OnBnClickedButton10()
{
	if (!ismusic)
	{
		PlaySound(_T("C:\\Users\\sel.wav"), NULL, SND_FILENAME | SND_ASYNC);
	}
	butRestart.ShowWindow(SW_HIDE);
	easy.ShowWindow(SW_HIDE);
	normal.ShowWindow(SW_HIDE);
	difficult.ShowWindow(SW_HIDE);
	start.ShowWindow(SW_NORMAL);
	rank.ShowWindow(SW_NORMAL);
	set.ShowWindow(SW_NORMAL);
	help.ShowWindow(SW_NORMAL);
	about.ShowWindow(SW_NORMAL);
}
