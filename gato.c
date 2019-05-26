#include <stdio.h>
#include <string.h>
void Imp(char c[]){
	int i;
	for(i=0;i<9;i++){
        printf("%2c ", c[i]);
        if (i==2|i==5)
            printf("\n___________\n");
        else if (i!=8)
            printf("|");
	}
	printf("\n\n");
}
void turno(int i, char gato[10]){
    int k;
    switch(i){
        case 2:{ gato[1]='O';
            gato[6]='X';
            Imp(gato);
            scanf("%d", &k);
            switch (k){
                case 3:{ gato[2]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 4:{gato[3]='O';
                    gato[4]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 3:{ gato[2]='O';
                            gato[8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 6:{gato[5]='O';
                            gato[8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 8:{gato[7]='O';
                            gato[8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 9:{gato [8]='O';
                            gato[2]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                    }
                    break;
                }
                case 5:{ gato[4]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 6:{ gato[5]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 8:{ gato[7]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 9:{ gato[8]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
            }
        break;
        }
        case 3:{ gato[2]='O';
            gato[8]='X';
            Imp(gato);
            scanf("%d",&k);
            switch (k){
                case 2:{gato[1]='O';
                    gato[4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 4:{gato[3]='O';
                    gato[4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 5:{gato[4]='O';
                    gato[6]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch (k){
                        case 2:{gato[1]='O';
                            gato[3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 4:{gato[3]='O';
                            gato[7]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 6:{gato[5]='O';
                            gato[3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 8:{gato[7]='O';
                            gato[3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                    }
                    break;
                }
                case 6:{gato[5]='O';
                    gato[4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 7:{gato[6]='O';
                    gato[4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 8:{gato[7]='O';
                    gato[4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }

            }
        break;
        }
        case 4: {gato[3]='O';
            gato[2]='X';
            Imp(gato);
            scanf("%d",&k);
            switch(k){
                case 2:{gato[1]='O';
                    gato[4]='X';
                    Imp(gato);
                    scanf("%d", &k);
                    switch(k){
                        case 6:{gato[5]='O';
                            gato [8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                        break;
                        }
                        case 7:{gato[6]='O';
                            gato [8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                        break;
                        }
                        case 8:{gato[7]='O';
                            gato [8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                        break;
                        }
                        case 9:{gato[8]='O';
                            gato [6]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                        break;
                        }
                    }
                break;
                }
                case 5:{gato[4]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                break;
                }
                case 6:{gato[5]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                break;
                }
                case 7:{gato[6]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                break;
                }
                case 8:{gato[7]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                break;
                }
                case 9:{gato[8]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                break;
                }
            }
        break;
        }
        case 5: {gato[4]='O';
            gato[8]='X';
            Imp(gato);
            scanf("%d",&k);
            switch(k){
                case 2: {gato[1]='O';
                    gato[7]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 3:{gato[2]='O';
                            gato[6]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 4:{gato[3]='O';
                            gato[6]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 6:{gato[5]='O';
                            gato[6]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 7:{gato[6]='O';
                            gato[2]='X';
                            Imp(gato);
                            scanf("%d", &k);
                            switch (k){
                                case 4:{gato[3]='O';
                                    gato[5]='X';
                                    Imp(gato);
                                    printf("\t\tPerdiste");
                                break;
                                }
                                case 6:{gato[5]='O';
                                    gato[3]='X';
                                    Imp(gato);
                                    printf("\t\tEmpate");
                                }
                            }
                        }
                    }
                break;
                }
                case 3: {gato[2]='O';
                    gato[6]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 2:{gato[1]='O';
                            gato[3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 4:{gato[3]='O';
                            gato[7]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 6:{gato[5]='O';
                            gato[3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 8:{gato[7]='O';
                            gato[3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                    }
                break;
                }
                case 4: {gato[3]='O';
                    gato[5]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 2:{gato[1]='O';
                            gato[2]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 3:{gato [2]='O';
                            gato[6]='X';
                            Imp(gato);
                            scanf("%d",&k);
                            switch(k){
                                case 2:{gato[1]='O';
                                    gato[7]='X';
                                    Imp(gato);
                                    printf("\t\tPerdiste");
                                break;
                                }
                                case 8:{gato[7]='O';
                                    gato[1]='X';
                                    Imp(gato);
                                    printf("\t\tEmpate");
                                }
                            }
                        break;
                        }
                        case 7:{gato[6]='O';
                            gato[2]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 8:{gato[7]='O';
                            gato[2]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                    }
                break;
                }
                case 6: {gato[5]='O';
                    gato[3]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 2:{gato[1]='O';
                            gato[6]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 3:{gato[2]='O';
                            gato[6]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 7:{gato[6]='O';
                            gato[2]='X';
                            Imp(gato);
                            scanf("%d", &k);
                            switch(k){
                                case 2:{gato[1]='O';
                                    Imp(gato);
                                    printf("\t\tEmpate");
                                break;
                                }
                                case 8:{gato[7]='O';
                                    gato[1]='X';
                                    Imp(gato);
                                    printf("\t\tPerdiste");
                                break;
                                }
                                }
                        break;
                        }
                        case 8:{gato[7]='O';
                            gato[6]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                    }
                break;
                }
                case 7: {gato[6]='O';
                    gato[2]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 2:{gato[1]='O';
                            gato[5]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 4:{gato[3]='O';
                            gato[5]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 6:{gato[5]='O';
                            gato[1]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 8:{gato[7]='O';
                            gato[5]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                    }
                break;
                }
                case 8: {gato[7]='O';
                    gato[1]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 3:{gato[2]='O';
                            gato[6]='X';
                            Imp(gato);
                            scanf("%d",&k);
                            switch (k){
                                case 4:{gato[3]='O';
                                    Imp(gato);
                                    printf("\t\tEmpate\n");
                                break;
                                }
                                case 6:{gato[5]='X';
                                    gato[3]='X';
                                    Imp(gato);
                                    printf("\t\tPerdiste");
                                break;
                                }
                            }
                        break;
                        }
                        case 4:{gato[3]='O';
                            gato[2]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 6:{gato[5]='O';
                            gato[2]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                        case 7:{gato[6]='O';
                            gato[2]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                        break;
                        }
                    }
                break;
                }
            }
        break;
        }
        case 6: {gato[5]='O';
            gato[6]='X';
            Imp(gato);
            scanf("%d", &k);
            switch(k){
                case 2:{gato[1]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 3:{gato[2]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 4:{gato[3]='O';
                    gato[4]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 2:{gato[1]='O';
                            gato[2]='X';
                            Imp(gato);
                            printf("\t\tPerdiste");
                            break;
                        }
                        case 3:{gato[2]='O';
                            gato[8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 8:{gato[7]='O';
                            gato[3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 9:{gato[8]='O';
                            gato[3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                    }
                    break;
                }
                case 5:{gato[4]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 8:{gato[7]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 9:{gato[8]='O';
                    gato[3]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
            }
        break;
        }
        case 7: {gato[6]='O';
            gato[8]='X';
            Imp(gato);
            scanf("%d",&k);
            switch(k){
                case 2:{gato[1]='O';
                    gato [4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 3:{gato[2]='O';
                    gato [4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 4:{gato[3]='O';
                    gato [4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 5:{gato[4]='O';
                    gato [2]='X';
                    Imp(gato);
                    scanf("%d", &k);
                    switch (k){
                        case 2:{gato[1]='O';
                            gato[5]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                        break;
                        }
                        case 4:{gato[3]='O';
                            gato[5]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                        break;
                        }
                        case 6:{gato[5]='O';
                            gato[1]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                        break;
                        }
                        case 8:{gato[7]='O';
                            gato[5]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                        break;
                        }
                    }
                    break;
                }
                case 6:{gato[5]='O';
                    gato [4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 8:{gato[7]='O';
                    gato [4]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
            }
        break;
        }
        case 8: {gato[7]='O';
            gato[2]='X';
            Imp(gato);
            scanf("%d",&k);
            switch(k){
                case 2:{gato[1]='O';
                    gato [4]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch(k){
                        case 4:{gato[3]='O';
                            gato [8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 6:{gato[5]='O';
                            gato [8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 7:{gato[6]='O';
                            gato [8]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 9:{gato[8]='O';
                            gato [6]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                    }
                    break;
                }
                case 4:{gato[3]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 5:{gato[4]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 6:{gato[5]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 7:{gato[6]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 9:{gato[8]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
            }
            break;
            }
        case 9: {gato[8]='O';
            gato[2]='X';
            Imp(gato);
            scanf("%d",&k);
            switch(k){
                case 2:{gato[1]='O';
                    gato [6]='X';
                    Imp(gato);
                    scanf("%d",&k);
                    switch (k){
                        case 4:{gato[3]='O';
                            gato [4]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 5:{gato[4]='O';
                            gato [3]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 6:{gato[7]='O';
                            gato [4]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                        case 8:{gato[7]='O';
                            gato [4]='X';
                            Imp(gato);
                            printf("\t\tPerdiste\n");
                            break;
                        }
                    }
                    break;
                }
                case 4:{gato[3]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 5:{gato[4]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 6:{gato[5]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 7:{gato[6]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
                case 8:{gato[7]='O';
                    gato [1]='X';
                    Imp(gato);
                    printf("\t\tPerdiste\n");
                    break;
                }
            }
    }
    }
}

int main (){
    char gato[10]={'X','2','3','4','5','6','7','8','9'};
    int i=0, j;
    Imp(gato);
    scanf("%d", &j);
    turno(j, gato);
    return 0;
}
