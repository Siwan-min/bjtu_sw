#include <stdio.h>

typedef struct { 
	char studentId[30];
	char name[100];
	int grade;
	char major[51];
} Student;

int main(void) {
	// 1) ����ü�� ������ ������ ���Ǵ� ��� ���� ������ ������ �� ȭ��ǥ(->)�� �����.
	Student *s = malloc(sizeof(Student)); //����ü ������ ������ ���� 
	strcpy(s->studentId, "20153157");
	strcpy(s->name, "�νÿ�");
	s->grade = 4;
	strcpy(s->major, "��ǻ�ͱ�����");
	printf("�й�: %s\n", s->studentId);//����ü ���� ���
	printf("�̸�: %s\n", s->name);
	printf("�г�: %d\n", s->grade);
	printf("�а�: %s\n", s->major);
	system("pause");
	return 0;
}


