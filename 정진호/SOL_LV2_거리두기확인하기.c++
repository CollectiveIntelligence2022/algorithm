#include <string>
#include <vector>
#include <iostream>
#include <queue>
#include <string.h>
using namespace std;

struct info{
    int x,y,depth;
};
const int dx[]={0,0,-1,1};
const int dy[]={-1,1,0,0};
vector<int> solution(vector<vector<string>> places) {
    vector<int> answer;
    for(auto n:places){
        bool isAble=true;
        for(int i=0;i<5&&isAble;i++){
            for(int j=0;j<5&&isAble;j++){
                if(n[i][j]=='P'){
                    //사람만나면 depth 2짜리 bfs 실행
                    queue<info> que;
                    bool isVisited[5][5];
                    memset(isVisited,0,sizeof(isVisited));
                    que.push({j,i,0});
                    isVisited[i][j]=true;
                    while(!que.empty()){
                        info cur=que.front();
                        que.pop();
                        //칸막이가 있으면 진행 불가한 상황에서도 depth 2안에서 사람을 만나면 거리두기를 지키지 않은 것
                        if(cur.depth!=0&&n[cur.y][cur.x]=='P') {
                            isAble=false;
                            break;
                        }
                        if(cur.depth==2) continue;
                        for(int dir=0;dir<4;dir++){
                            int nx=cur.x+dx[dir];
                            int ny=cur.y+dy[dir];
                            if(!(nx>=0&&nx<5&&ny>=0&&ny<5)) continue;
                            //칸막이 있으면 진행 불가
                            if(n[ny][nx]=='X'||isVisited[ny][nx]) continue;
                            que.push({nx,ny,cur.depth+1});
                            isVisited[ny][nx]=true;
                        }
                    }
                }
            }
        }
        answer.push_back(isAble);
    }
    return answer;
}