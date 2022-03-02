#include <iostream>
#include <queue>
#include <string.h>
#include <stdio.h>
#define MAX 100
#define INF 999999999

using namespace std;

struct info{
    int x,y,dir;
};
int map[MAX+1][MAX+1];
int dp[5][MAX+1][MAX+1];
int point[3][2];
int N,M;
const int dx[]={0,1,-1,0,0};
const int dy[]={0,0,0,1,-1};
const int turn[5][2]={
    {},
    {3,4},
    {3,4},
    {1,2},
    {1,2}
};
bool isInside(int x,int y){
    return x>=1&&x<=M&&y>=1&&y<=N;
}

void bfs(){
    queue<info> que;
    que.push({point[1][0],point[0][0],point[2][0]});
    dp[point[2][0]][point[0][0]][point[1][0]]=0;
    while(!que.empty()){
        auto cur=que.front();
        que.pop();
        int x=cur.x;
        int y=cur.y;
        int curCnt=dp[cur.dir][y][x];
        if(x==point[1][1]&&y==point[0][1]&&cur.dir==point[2][1]) return;
        for(int i=0;i<2;i++){
            int ndir=turn[cur.dir][i];
            int nx=x+dx[ndir];
            int ny=y+dy[ndir];
            if(dp[ndir][y][x]>curCnt+1){
                dp[ndir][y][x]=curCnt+1;
                que.push({x,y,ndir});
            }
        }
        int nx=x+dx[cur.dir];
        int ny=y+dy[cur.dir];
        for(int i=0;i<3&&isInside(nx,ny)&&map[ny][nx]==0;i++){
            if(dp[cur.dir][ny][nx]>curCnt+1){
                que.push({nx,ny,cur.dir});
                dp[cur.dir][ny][nx]=curCnt+1;
            }
            nx+=dx[cur.dir];
            ny+=dy[cur.dir];
        }
    }
}
int main(){
    cin>>N>>M;
    for(int i=1;i<=N;i++){
        for(int j=1;j<=M;j++)
            scanf("%d",&map[i][j]);
    }
    for(int i=0;i<2;i++)
        scanf("%d %d %d",&point[0][i],&point[1][i],&point[2][i]);
    for(int i=0;i<5;i++)
        for(int j=1;j<=N;j++)
            for(int k=1;k<=M;k++)
                dp[i][j][k]=INF;
    bfs();
    cout<<dp[point[2][1]][point[0][1]][point[1][1]]<<endl;
}