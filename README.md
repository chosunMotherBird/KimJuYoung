# KimJuYoung
김주영 프로젝트 레포지토리

21 12 03

1. 8:53분에 git push 했으나 지금 readme 파일이 없어짐
2. 아무래도 git push 를 하는데 문제가 생긴것 같음

어제 내용 다시 적을예정
21. 12. 02

1. 일단 집에서 하던 프로젝트를 개인 레포지토리에 올려보려 시도.
2. cd 디렉토리 해서 git bash 경로를 변경해줌
3. remote 설정을 해서 원격 저장소 개인 레포지토리로 이동
4. git remote add origin 주소
4. branch 를 master에서 main으로 이동하는게 편할 것 같아서 main 으로 브랜치 이동함
5. git init
6. git add .
7. git commit -m "커밋메시지"
8. git push origin main 
9. 끗

그리고 팀 프로젝트에 있는 내 레포지토리로도 저장
1. remote 가 지금 내 개인원격레포지토리 이므로 
2. remote를 끊음 git remove origin
3. remote를 팀 프로젝트에 있는 내 레포지토리로 이동
4. git remote add origin 주소
5. 나머지는 동일

+ git push origin main 이 작동이 안되고 git push origin +main 을 사용해 강제적으로 push를 해줘야 했는데
+ 지금 이게 readme 파일이 문제인것 같음.
+ 그래서 지금 쓰고있는 readme파일을 저장한 후 clone으로 다른 로컬디렉토리에 프로젝트를 다운받아보고 그걸 test 레포지토리에 넣어볼 예쩡

test에 넣는 과정
1. 일단 바탕화면에 아무 폴더를 만듦.
2. git bash 에서 이 디렉토리로 이동
3. git init // 자꾸 까먹는데 git만들려면 해야함
4. 그리고 팀 프로젝트에 있는 내 레포지토리에 있는 프로젝트를 받아와야함
5. git clone https://github.com/chosunMotherBird/KimJuYoung.git
6. 그 다음 remote를 test로 함
7. git remote add origin https://github.com/chosunMotherBird/test.git
8. test에서 브랜치를 만들
9. git branch juyoungtest
10. git add .
11. git commit -m "메시지"
12. git push origin juyoungtest 에 정상적으로 push 완료
13. "+" 기호를 안붙여도 정상적으로 작동되는 걸 보면 readme 가 문제인것 같음
 + 일단 혹시 모르니 세세하게 적어놓을 예정임.

21  12 03 
1. 카카오 지도 api 추가.
2. avd에서는 안됨 arm 기반이 아니라서 그렇다고함.
3. 핸드폰으로 실행해본 결과 정상적으로 작동.
 
21 12 04 
1. asynctask 로 network 처리를 하는게 좋다고 들음.
2. charger 서버에 접속하기 위한 처리를 비동기처리로 진행함
3. 팀장님이 준 server code로 localhost 서버를 돌려 charger에 대한 정보를 가져옴
4. 여기서는 map을 사용하지 않고 textview를 활용해 avd 에서 charger 정보를 textview에 settext 되는지만 확인.
5. 내 핸드폰으로 하는게 아님.
6. callback 함수에서 dubug 를 통해 정상적으로 작동하는 것을 확인 , chargerlist와 jsonarray를 통해 처리 완료
7. 마커를 추가하는데 안됨. 내일

21 12 05
+ **비동기처리를 debug 하는 방법을 알지 못함.**
2. 일단 카카오맵은 avd로 실행되지 않기 떄문에 내 핸드폰으로 localhost를 접속해야함
3. ipconfig로 현재 ipv4 주소를 받아 내 핸드폰에서 charger 서버에 접속 완료.
4. chargerlist와 json을 처리했는데도 마커가 뜨지 않음.
5. 이유는 marker의 위도경도 설정하는 함수에서
6. MapPoint.mapPointWithCONGCoord() 와 mapPointWithGeocoord()를 바꿔서 계속 마커가 안떴음.
7. CongCoord는 위도경도가 아닌 다른 좌표로 설정하는 함수이고, GeocCoord는 위도 경도 좌표로 설정.
8. 충전소들 마커위치까지 다중마커 설정완료

21 12 12
일주일 간 진행 상황
1. 카카오맵으로 하던것을 구글맵으로 바꿔서 진행함.
   이유는 카카오맵이 사용하기 힘듦과 함수에 대한 설명이 매우 부족해서
2. 구글맵 마커, 마커 클릭시 cardview
3. 검색화면, 검색 결과 클릭시 메인에서 해당 위치 찾아주는 기능
4. 검색, 위치 추적 등 모든 결과는 서버에서 가져오는 것임.

21 12 15
지난 4일간 진행 상황
1. 로그인, 회원가입 기능 완료
2. 카드뷰에서 "자세한 정보"를 누를 시 충전소 상세정보를 위한 DetailActivity 로 이동
3. editText 1줄만 적도록 추가
4. 메인화면과 상호작용하는 엑티비티들은 인텐트를 보낼 경우 startActivityForRequest 로 하도록 수정
5. package 정리와 class 이름 수정.
