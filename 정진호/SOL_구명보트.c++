#include <string>
#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;

bool isSaved[50000];

int findIndex(vector<int> &people,int goal){
    int bottom=0;
    int top=people.size()-1;
    int res=-1;
    while(top>=bottom){
        int middle=(bottom+top)/2;
        if(goal>=people[middle]){
            res=middle;
            bottom=middle+1;
            continue;
        }
        top=middle-1;
    }
    return res;
}

int solution(vector<int> people, int limit) {
    sort(people.begin(),people.end());
    int answer = 0;
    for(int i=people.size()-1;i>=0;i--){
        if(isSaved[i]) continue;
        isSaved[i]=true;
        int pairIndex=findIndex(people,limit-people[i]);
        while(true){
            if(pairIndex==-1) break;
            if(people[pairIndex]>limit-people[i]||isSaved[pairIndex]) {
                --pairIndex;
                continue;
            }
            break;
        }
        answer++;
        if(pairIndex!=-1) isSaved[pairIndex]=true;
    }
    return answer;
}