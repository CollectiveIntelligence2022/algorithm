#include <string>
#include <vector>
#include <sstream>
#include <algorithm>
#include <iostream>

using namespace std;

vector<int> dp[3][2][2][2];

vector<int> solution(vector<string> info, vector<string> query) {
    vector<int> answer;
    for(auto a:info){
        istringstream ss(a);
        string strBuffer;
        vector<string> v;
        while(getline(ss,strBuffer,' ')){
            v.push_back(strBuffer);
        }
        int i,j,t,k;
        //입력에 따라 i,j,t,k를 할당, 첫 알파벳으로 모두 구분하기 때문에 첫 알파벳 비교만 함
        switch(v[0][0]){
            case 'j' : i=0; break;
            case 'c' : i=1; break;
            case 'p': i=2;
        }
        j=v[1][0]=='b'?0:1;
        t=v[2][0]=='j'?0:1;
        k=v[3][0]=='c'?0:1;
        dp[i][j][t][k].push_back(stoi(v[4]));
    }
    //이분 탐색을 위한 정렬
    for(int i=0;i<3;i++){
        for(int j=0;j<2;j++){
            for(int t=0;t<2;t++){
                for(int k=0;k<2;k++)
                    sort(dp[i][j][t][k].begin(),dp[i][j][t][k].end());
            }
        }
    }
    for(auto a: query){
        istringstream ss(a);
        string strBuffer;
        vector<string> v;
        while(getline(ss,strBuffer,' ')){
            v.push_back(strBuffer);
        }
        // 입력에 - 이 들어오면 모든 경우를 보기 때문에 그것을 vector로 표현
       vector<int> i,j,t,k;
        switch(v[0][0]){
            case 'j' : i={0}; break;
            case 'c' : i={1}; break;
            case 'p': i={2}; break;
            case '-' : i={0,1,2};
        }
        switch(v[2][0]){
            case 'b':j={0}; break;
            case 'f':j={1}; break;
            case '-' :j={0,1};
        }
       switch(v[4][0]){
            case 'j':t={0}; break;
            case 's':t={1}; break;
            case '-' :t={0,1};
        }
        switch(v[6][0]){
            case 'c':k={0}; break;
            case 'p':k={1}; break;
            case '-':k={0,1};
        }
        int cnt=0;
       //가능한 모든 조합에 대해 이분탐색으로 lower_bound 찾기
       for(auto ii:i){
           for(auto jj:j){
               for(auto tt:t){
                   for(auto kk:k){
                        auto res=lower_bound(dp[ii][jj][tt][kk].begin(),dp[ii][jj][tt][kk].end(),stoi(v[7]));
                        //lower_bound의 결과는 Iterator이기 때문에 벡터 시작 주소를 빼 index 구함
                        int index=res-dp[ii][jj][tt][kk].begin();
                        //벡터 길이에서 시작 인덱스를 뺀 것이 가능한 사람의 수이기 때문에 누적함
                        cnt+=(int)dp[ii][jj][tt][kk].size()-index;
                   }
               }
           }
       }
       //누적 결과 입력
        answer.push_back(cnt);
    }
    return answer;
}
