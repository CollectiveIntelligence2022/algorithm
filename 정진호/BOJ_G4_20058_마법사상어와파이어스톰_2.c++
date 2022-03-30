#include <iostream>
#include <string.h>
#include <queue>
using namespace std;
int map[1<<6][1<<6];
int N,T,NN;
const int dx[]={0,0,-1,1};
const int dy[]={-1,1,0,0};

bool isInside(int x,int y){
    return x>=0&&x<NN&&y>=0&&y<NN;
}
void rotate(int L){
    int M=1<<L;
    int temp[NN][NN];
    for(int i=0;i<NN;i++){
        for(int j=0;j<NN;j++)
            temp[i][j]=map[i][j];
    }
    for(int i=0;i<NN/M;i++){
        for(int j=0;j<NN/M;j++){
        	int bx=j*M;
        	int by=i*M;
            for(int y=0;y<M;y++){
                for(int x=0;x<M;x++){
                    map[by+y][bx+x]=temp[(i+1)*M-1-x][bx+y];
                }
            }
        }
    }
}

void melt(){
    int temp[NN][NN];
    memset(temp,0,sizeof(temp));

    for(int i=0;i<NN;i++){
        for(int j=0;j<NN;j++){
            int cnt=0;
            for(int dir=0;dir<4;dir++){
                int nx=j+dx[dir];
                int ny=i+dy[dir];
                if(isInside(nx,ny)&&map[ny][nx]>0) cnt++;
            }
            if(cnt<3) --temp[i][j];
        }
    }
    for(int i=0;i<NN;i++){
        for(int j=0;j<NN;j++){
            map[i][j]+=temp[i][j];
            if(map[i][j]<0) map[i][j]=0;
        }
    }
}

int count(){
    bool visited[NN][NN];
    memset(visited,0,sizeof(visited));
    int maxCnt=0;
    for(int i=0;i<NN;i++){
        for(int j=0;j<NN;j++){
            if(!map[i][j]||visited[i][j]) continue;
            int cnt=1;
            queue<pair<int,int>> que;
            que.push({j,i});
            visited[i][j]=true;
            while(!que.empty()){
                auto cur=que.front();
                que.pop();
                for(int dir=0;dir<4;dir++){
                    int nx=cur.first+dx[dir];
                    int ny=cur.second+dy[dir];
                    if(!isInside(nx,ny)||visited[ny][nx]||map[ny][nx]==0) continue;
                    que.push({nx,ny});
                    visited[ny][nx]=true;
                    cnt++;
                }
            }
            maxCnt=max(maxCnt,cnt);
        }
    }
    return maxCnt;
}

int main(){
    cin>>N>>T;
    NN=1<<N;
    for(int i=0;i<NN;i++){
        for(int j=0;j<NN;j++)
            cin>>map[i][j];
    }
    int a;
    while(T--){
        cin>>a;
        rotate(a);
        melt();
    }
    int sum=0;
    for(int i=0;i<NN;i++){
        for(int j=0;j<NN;j++)
            sum+=map[i][j];
    }
    cout<<sum<<endl;
    cout<<count()<<endl;
}