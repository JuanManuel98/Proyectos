#include <stdio.h>
#include <stdlib.h>
void leermat(int mat[10][10]){
	int i, j;
	for (i=0; i<9; i++)
		for(j=0; j<9; j++)
			scanf("%d", &mat[i][j]);
}

void imprimir (int mat[10][10]){
	int i, j;
	for(i=0; i<9; i++){
		for(j=0; j<9; j++)
			if (mat[i][j]!=0)
				printf("%2d ", mat[i][j]);
			else
				printf(" . ");
		printf("\n");
	}
	printf("\n");
}

void conteo(int sudo[10][10], int fil[10][10], int col[10][10], int r[10][10]){
	int i, j, num, reg=0, jj, ii;
	for (i=0; i<9; i++)
		for(j=0; j<9; j++)
			if(sudo[i][j]!=0){
				num=sudo[i][j];
				fil[i][num-1]=num;
				col[num-1][j]=num;
			}
	for(i=3; i<=9; i+=3)
		for(j=3; j<=9; j+=3){
			for(ii=i-3; ii<i; ii++)
				for(jj=j-3; jj<j; jj++)
					if(sudo[ii][jj]!=0){
						num=sudo[ii][jj];
						r[reg][num-1]=num;
					}
			reg++;
		}
}

void solvesudo(int sudo[10][10], int fil[10][10], int col[10][10], int reg[10][10]){
	conteo(sudo, fil, col, reg);
	int i, j, ceros=0;
	for (i=0; i<9; i++)
		for(j=0; j<9; j++)
			if(sudo[i][j]== 0)
				ceros+=1;
	int f, c;
	while(ceros){
		int r=0;
		for(f=3; f<=9; f+=3){
			for(c=3; c<=9; c+=3){
				int num;
				for(num=1; num<=9; num++){
					int cop[10][10]={0};
					for (i=f-3; i<f;i++ )
						for(j=c-3; j<c; j++)
							if(sudo[i][j]!=0)
								cop[i][j]=num;
					for(i=0; i<9; i++){
						if (col[num-1][i]!=0){
							for (j=0; j<9; j++)
								cop[j][i]=num;
						}
						if(fil[i][num-1]!=0){
							for (j=0; j<9; j++)
								cop[i][j]=num;
						}
					}
					int z=0;
					for(i=f-3; i<f; i++)
						for(j=c-3; j<c; j++)
							if(cop[i][j]==0)
								z+=1;
					/*
						imprimir(sudo);
						imprimir (cop);
						imprimir(reg);
						imprimir (fil);
						imprimir (col);
						printf("\n\t\t%d  %d  %d",f, c, z);
						sleep(4);
					*/
					if(z==1&&reg[r][num-1]==0){
						for(i=f-3; i<f; i++)
							for(j=c-3; j<c; j++)
								if(cop[i][j]==0)
									sudo[i][j]=num;
						conteo(sudo, fil, col, reg);
						//imprimir (sudo);
						ceros--;
						num=1;
					}
				}
				//printf("%d\n", r);
				//sleep(4);
				r++;
			}
		}
	}
}
int main(){
	int sudo[10][10]={0}, fil[10][10]={0},col[10][10]={0}, reg[10][10]={0};
	leermat(sudo);
	imprimir (sudo);
	solvesudo(sudo, fil, col, reg);
	imprimir(sudo);
	return 0;
}
