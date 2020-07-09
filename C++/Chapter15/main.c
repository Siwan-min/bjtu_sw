#include <stdio.h>

typedef struct { 
	char studentId[30];
	char name[100];
	int grade;
	char major[51];
} Student;

int main(void) {
	// 1) 구조체가 포인터 변수로 사용되는 경우 내부 변수에 접근할 때 화살표(->)를 사용함.
	Student *s = malloc(sizeof(Student)); //구조체 포인터 변수에 접근 
	strcpy(s->studentId, "20153157");
	strcpy(s->name, "민시완");
	s->grade = 4;
	strcpy(s->major, "컴퓨터교육과");
	printf("학번: %s\n", s->studentId);//구조체 내용 출력
	printf("이름: %s\n", s->name);
	printf("학년: %d\n", s->grade);
	printf("학과: %s\n", s->major);
	system("pause");
	return 0;
}


