
// LLKGame.cpp : Defines the class behaviors for the application.
//

#include "stdafx.h"
#include "LLKGame.h"
#include "LLKGameDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CLLKGameApp

BEGIN_MESSAGE_MAP(CLLKGameApp, CWinApp)
	ON_COMMAND(ID_HELP, &CWinApp::OnHelp)
END_MESSAGE_MAP()


// CLLKGameApp constructor

CLLKGameApp::CLLKGameApp()
{
	// manager support restart
	// 관리 재시작
	m_dwRestartManagerSupportFlags = AFX_RESTART_MANAGER_SUPPORT_RESTART;

	// TODO: add construction code here，
	// place all significant initialization in InitInstance
}


// The one and onlu CLLKGameApp object

CLLKGameApp theApp;


// CLLKGameApp initializatioin

BOOL CLLKGameApp::InitInstance()
{
	INITCOMMONCONTROLSEX InitCtrls;
	InitCtrls.dwSize = sizeof(InitCtrls);

	InitCtrls.dwICC = ICC_WIN95_CLASSES;
	InitCommonControlsEx(&InitCtrls);

	CWinApp::InitInstance();


	AfxEnableControlContainer();

	CShellManager *pShellManager = new CShellManager;

	CMFCVisualManager::SetDefaultManager(RUNTIME_CLASS(CMFCVisualManagerWindows));

	SetRegistryKey(_T("making application"));

	CLLKGameDlg dlg;
	m_pMainWnd = &dlg;
	INT_PTR nResponse = dlg.DoModal();
	if (nResponse == IDOK)
	{
		 // nothing
	}
	else if (nResponse == IDCANCEL)
	{
		// nothing
	}
	else if (nResponse == -1)
	{
		TRACE(traceAppMsg, 0, "notice : fail making dialog , down the app\n");
		TRACE(traceAppMsg, 0, "notice : if you use mfc in dialog then can't use #define _AFX_NO_MFC_CONTROLS_IN_DIALOGS.\n");
	}

	if (pShellManager != NULL)
	{
		delete pShellManager;
	}

#ifndef _AFXDLL
	ControlBarCleanUp();
#endif

	return FALSE;
}

