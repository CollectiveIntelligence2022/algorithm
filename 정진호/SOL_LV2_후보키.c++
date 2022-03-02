#include <string>
#include <vector>
#include <unordered_set>
#include <string>
#define MAX 8
using namespace std;

unordered_set<string> tupleSet[MAX+1];
bool hasDuplicated[MAX+1];
int attribute[MAX+1];
vector<vector<int>> available;
int recul(int n,int index,int cnt,int size, vector<vector<string>> &relation){
    if(cnt>=n){
        if(n>2){
            //두개일 땐 비교 없이 담을 수 있음 
            //단일 키를 이미 제외했기 때문
            for(auto a:available){
                //아래는 available에 있는 것과 전체가 일치한 적이 있는지 체크
                bool hasEverything=true;
                for(auto b:a){
                    bool hasItem=false;
                    for(int i=0;i<n;i++){
                        if(attribute[i]==b){
                            hasItem=true;
                            break;
                        }
                    }
                    //available에 있는 거 중에서 하나라도 없다면 hasEveryting은 false;
                    if(!hasItem) {
                        hasEverything=false;
                        break;
                    }
                }
                //하나라도 일치하면 불가능 return 0;
                if(hasEverything) return 0;
            }
        }
        unordered_set<string> tupleSet;
        //hash에 담아 비교
        for(auto info:relation){
            string temp="";
            for(int i=0;i<n;i++)
                temp+=info[attribute[i]]+"/";
            tupleSet.insert(temp);
        }
        //전체를 구분 가능하다면
        if(tupleSet.size()==relation.size()){
            vector<int> temp;
            for(int i=0;i<n;i++){
                temp.push_back(attribute[i]);
            }
            //available에 추가
            available.push_back(temp);
            return 1;
        }
        return 0;
    }
    int c;
    //가능한 컴럼 선택. 단일 키는 이미 제외
    for(c=index;c<size;c++){
        if(hasDuplicated[c]) break;
    }
    if(c>=size) return 0;
    attribute[cnt]=c;
    int res=0;
    //본인 선택 후 진행
    res+=recul(n,c+1,cnt+1,size,relation);
    //본인 선택하지 않고 진행
    res+=recul(n,c+1,cnt,size,relation);
    return res;
}

int solution(vector<vector<string>> relation) {
    int answer = 0;
    int tupleSize=relation[0].size();
    for(auto info:relation){
        for(int i=0;i<tupleSize;i++){
                tupleSet[i].insert(info[i]);
            }
    }
    int dupliCnt=0;
    for(int i=0;i<tupleSize;i++){
        //단일키 가능한 컬럼에 대해서 제외 표시
        if(tupleSet[i].size()==relation.size()){
            ++answer;
            continue;
        }
        hasDuplicated[i]=true;
        dupliCnt++;
    }
    //2개부터 사이즈 늘려가며 체크
    //이는 단일 재귀 호출로 진행하는게 효율적일듯
    for(int j=2;j<=tupleSize;j++){
        answer+=recul(j,0,0,tupleSize,relation);
    }
    return answer;
}