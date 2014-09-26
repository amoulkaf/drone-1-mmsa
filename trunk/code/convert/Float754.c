#include <stdio.h>
#include <stdlib.h>

int main(){
	float var = 0.8;
	int var754 = *(int*)(&var);
	printf("%f --> %d\n", var, var754);
	return EXIT_SUCCESS;
}
