#include <string>
#include <vector>
#include <unordered_map>
#include <algorithm>
#include <queue>
#include <iostream>

using namespace std;

struct info{
    int musicNum;
    int stCnt;
    bool operator>(const info& b)const{
        if(stCnt!=b.stCnt) return stCnt<b.stCnt;
        return musicNum>b.musicNum;
    }
};
vector<pair<int,int>> streamingCnt;
priority_queue<info,vector<info>,greater<info>> pq[100];

vector<int> solution(vector<string> genres, vector<int> plays) {
    vector<int> answer;
    unordered_map<string,int> hashMap;
    int index=0;
    for(string gen:genres){
        if(hashMap.find(gen)==hashMap.end()) hashMap.insert({gen,index++});
    }
    for(int i=0;i<index;i++) {
        streamingCnt.push_back({0,i});
    }
    for(int i=0;i<genres.size();i++){
        auto genIter=hashMap.find(genres[i]);
        int genIndex=genIter->second;
        streamingCnt[genIndex].first+=plays[i];
        pq[genIndex].push({i,plays[i]});
    }
    sort(streamingCnt.begin(),streamingCnt.end(),greater<pair<int,int>>());
    for(auto gen:streamingCnt){
        int genIndex=gen.second;
        int cnt=0;
        while(!pq[genIndex].empty()&&cnt<2){
            auto curTop=pq[genIndex].top();
            pq[genIndex].pop();
            answer.push_back({curTop.musicNum});
            cnt++;
        }
    }
    return answer;
}