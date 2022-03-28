#include <iostream>
#include <cmath>
using namespace std;

typedef unsigned long long ull;
ull N,K,Q;
ull findDepth(ull n){
    if(n==1) return n;
    ull bottom=1;
    //최대 깊이가 log k (N*(K-1)+1)
    //등비수열 합 식에서 유도함 (K^최대깊이-1)/(K-1)>N 
    //로그로 최대깊이 계산
    ull ceiling=log(N*(K-1)+1)/log(K)+1;
    ull result=0;
   
    while(bottom<=ceiling){
        ull mid=(bottom+ceiling)/2;
        ull res=(pow(K,mid)-1)/(K-1);
        if(res>=n) {
        	ceiling=mid-1;
        	result=mid;
        }
        else{
            bottom=mid+1;
        }
    }
    //이분 탐색을 통해 현재 index의 depth를 찾아냄
    return result;
}

ull findParent(ull depth, ull index){
    if(depth==2) return 1;
    //등비수열합 공식을 사용해서 부모의 Index를 찾음
    ull res=(pow(K,depth-1)-1)/(K-1);
    ull cIndex=index-res;
    ull pres=(pow(K,depth-2)-1)/(K-1);
    //현재 Depth에서의 index의 순번을 기준으로 부모 노드의 index를 계산
    ull pIndex=pres+(cIndex-1)/K+1;
    return pIndex;
}
int main(){
	cin>>N>>K>>Q;
    ull a,b;
    while(Q--){
        scanf("%llu %llu",&a,&b);
        if(K==1){
    		ull gap;
    		if(a>b) gap=a-b;
    		else gap=b-a;
    		printf("%llu\n",gap);
    		continue;
    	}
        ull aDepth=findDepth(a);
        ull bDepth=findDepth(b);
        
        //depth가 더 깊은 것을 찾아냄
        ull &deeper=aDepth>bDepth?a:b;
        ull &dDepth=aDepth>bDepth?aDepth:bDepth;
        ull gap;
        
        if(aDepth>bDepth) gap=aDepth-bDepth;
        else gap=bDepth-aDepth;
        
        ull cnt=0;
        //더 깊은 Depth를 나머지 친구와 같아지도록 부모를 타고 올라옴
        for(ull i=0;i<gap;i++){
            deeper=findParent(dDepth--,deeper);
            cnt++;
        }
        //depth가 같아진 이후로는 같이 Depth를 올리며 부모가 같아질 때까지 트리를 타올라감
        while(a!=b){
            a=findParent(aDepth--,a);
            b=findParent(bDepth--,b);
            cnt+=2;
        }
		printf("%llu\n",cnt);
    }
}
