#include <iostream>
#include <vector>
#include <queue>
#define MAX 32000
using namespace std;

int cnt[MAX+1];
int N,M;
vector<int> stuOrder[MAX+1];
void func(){
    queue<int> zeroQue;
    for(int i=1;i<=N;i++){
        if(!cnt[i]) zeroQue.push(i);
    }
    while(!zeroQue.empty()){
        int sitStu=zeroQue.front();
        zeroQue.pop();
        cout<<sitStu<<" ";
        for(int behindStu:stuOrder[sitStu]){
            if(!--cnt[behindStu]) zeroQue.push(behindStu);
        }
    }
}

int main(){
    cin>>N>>M;
    int a,b;
    while(M--){
        scanf("%d %d",&a,&b);
        ++cnt[b];
        stuOrder[a].push_back(b);
    }
    func();
}