#include <iostream>
#include <vector>
#define MAX 1000000
#define INF 1000001

using namespace std;

vector<int> edge[MAX+1];
bool isVisited[MAX+1];
int N;
//contain, except
pair<int,int> recul(int index){
   int containThis=0;
   int exceptThis=0;
   isVisited[index]=true;
   for(int next:edge[index]){
       if(isVisited[next]) continue;
       auto res=recul(next);
       containThis+=min(res.first,res.second);
       exceptThis+=res.first;
   }
   return {1+containThis,exceptThis};
}

int main(){
    cin>>N;
    int a,b;
    for(int i=0;i<N-1;i++){
        cin>>a>>b;
        edge[a].push_back(b);
        edge[b].push_back(a);
    }
    auto res=recul(1);
    cout<<min(res.first,res.second)<<endl;
}