#include <iostream>
#define MAX 100
using namespace std;
int n;
int num[MAX+1];
int maxValue=-1000000001;
int minValue=1000000001;
// + - x /
int operandCnt[4];

void recul(int index,int value){
    if(index>=n){
        if(value>maxValue) maxValue=value;
        if(value<minValue) minValue=value;
        return;
    }
    for(int i=0;i<4;i++){
        if(!operandCnt[i]) continue;
        int nValue=0;
        switch(i){
            case 0: nValue=value+num[index]; break;
            case 1: nValue=value-num[index]; break;
            case 2: nValue=value*num[index]; break;
            case 3: nValue=value/num[index]; break;
        }
        --operandCnt[i];
        recul(index+1,nValue);
        ++operandCnt[i];
    }
}

int main(){
    cin>>n;
    for(int i=0;i<n;i++)
        cin>>num[i];
    for(int i=0;i<4;i++)
        cin>>operandCnt[i];
    recul(1,num[0]);
    cout<<maxValue<<endl<<minValue<<endl;
}