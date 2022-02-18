#include <iostream>
#include <unordered_set>
#define ABS(a) ((a)<0?-(a):(a))
#define MAX 100
using namespace std;

int n,l;
int map[2*MAX+1][MAX+1];

bool checkRoad(int index){
    int pre=map[index][0];
    bool hasSlope[MAX+1];
    for(int i=0;i<n;i++) hasSlope[i]=false;
    for(int col=1;col<n;col++){
        if(ABS(map[index][col]-pre)>1) return false;
        if(map[index][col]==pre-1){
            int nx=col-1;
            for(int cnt=0;cnt<l;cnt++){
                nx+=1;
                if(nx>=n) return false;
                if(map[index][nx]!=pre-1||hasSlope[nx]) return false;
                hasSlope[nx]=true;
            }
        }
        else if(map[index][col]==pre+1){
            int nx=col;
            for(int cnt=0;cnt<l;cnt++){
                nx-=1;
                if(nx<0) return false;
                if(map[index][nx]!=pre||hasSlope[nx]) return false;
                hasSlope[nx]=true;
            }
        }
        pre=map[index][col];
    }
    return true;
}

int main(){
    cin>>n>>l;
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            scanf("%d",&map[i][j]);
        }
    }
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            map[n+i][j]=map[j][i];
        }
    }
    int cnt=0;
    for(int i=0;i<2*n;i++){
        if(checkRoad(i)) cnt++;
    }
    cout<<cnt<<endl;
}