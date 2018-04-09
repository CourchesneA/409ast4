#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/* Size of the DFA */
#define MAXSTATES 5
/* Number of characters in the alphabet */
#define ALPHABETSIZE 4
/* Size of the string to match against.  You may need to adjust this. */
#define STRINGSIZE 100000000

/* State transition table (ie the DFA) */
int stateTable[MAXSTATES][ALPHABETSIZE];
int **optimisticMap;
int n;

/* Initialize the table */
void initTable() {
    int start = 0;
    int accept = 3;
    int reject = 4;

    /* Note that characters values are assumed to be 0-based. */
    stateTable[0][0] = 1;      
    stateTable[0][1] = reject; 
    stateTable[0][2] = reject; 
    stateTable[0][3] = reject; 
    
    stateTable[1][0] = 1;      
    stateTable[1][1] = 2;      
    stateTable[1][2] = reject; 
    stateTable[1][3] = reject; 
    
    stateTable[2][0] = reject; 
    stateTable[2][1] = 2;      
    stateTable[2][2] = 3;      
    stateTable[2][3] = 3;      
    
    stateTable[3][0] = 1;      
    stateTable[3][1] = reject; 
    stateTable[3][2] = 3;      
    stateTable[3][3] = 3;      
    
    // reject state
    stateTable[4][0] = reject; 
    stateTable[4][1] = reject; 
    stateTable[4][2] = reject; 
    stateTable[4][3] = reject; 
}


/* Construct a sample string to match against.  Note that this uses characters, encoded in ASCII,
   so to get 0-based characters you'd need to subtract 'a'. */
char *buildString() {
    int i;
    char *s = (char *)malloc(sizeof(char)*(STRINGSIZE));
    if (s==NULL) {
        printf("\nOut of memory!\n");
        exit(1);
    }
    int max = STRINGSIZE-3;

    /* seed the rnd generator (use a fixed number rather than the time for testing) */
    srand((unsigned int)time(NULL)); 

    /* And build a long string that might actually match */
    int j=0;
    while(j<max) {
        s[j++] = 'a';
        while (rand()%1000<997 && j<max) 
            s[j++] = 'a';
        if (j<max)
            s[j++] = 'b';
        while (rand()%1000<997 && j<max) 
            s[j++] = 'b';
        if (j<max)
            s[j++] = (rand()%2==1) ? 'c' : 'd';
        while (rand()%1000<997 && j<max) 
            s[j++] = (rand()%2==1) ? 'c' : 'd';
    }
    s[max] = 'a';
    s[max+1] = 'b';
    s[max+2] = (rand()%2==1) ? 'c' : 'd';
    return s;
}

/**Given a string and an initial state, output ending state**/
int processPart(char *strPart,int initialState){
		int currentState = initialState;
		int i;
		for (i = 0; i < strlen(strPart); i++){
				if(currentState == 4) return 4;
				int c = strPart[i]-'a';
				currentState = stateTable[currentState][c];
		}
		return currentState;
}

char* getStringPart(char* str, int id){
		int strSize = strlen(str)/n + ((id ==0) ? strlen(str)%n : 0);
		int startIndex = strSize*id + ((id == 0) ? 0 : strlen(str)%n);
		char* substr = (char*)malloc(strSize);
		memcpy(substr, &str[startIndex], strSize);
		substr[strSize+1] = '\0';
		return substr;
		//TODO free
}

int main(int argc, char **argv){
		if(argc != 2)
		{
			printf("Error, there should be one parameter, %d found",argc);
			return 1;
		}
		n = atoi(argv[1]);
    initTable();
		optimisticMap = malloc(n* sizeof(int*));
		for(int i=0; i < n; i++){
			optimisticMap[i] = malloc(MAXSTATES * sizeof(int));
		}

		char *teststr = "abcdabcabc";

		printf("String to split: %s\nin %d threads\n",teststr,n);
		for(int i=0; i<n; i++){
				char* s = getStringPart(teststr,i);
				printf("Thread %d: %s\n",i,s);
		}


		//int finalState = processPart(teststr,0);
		//printf("The final state is %d",finalState);
    //char *s = buildString();
    //printf("Built: [0]=%c and [%d]=%c\n",s[0],(int)(STRINGSIZE-1),s[STRINGSIZE-1]);

		for(int i = 0; i< n; i++){
				free(optimisticMap[i]);
		}
		free(optimisticMap);
		return 0;
}


