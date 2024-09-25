# NewWeatherOpenApiPJ
날씨 및 생활지수 앱

###
기존 [WeatherOpenApiPj](https://github.com/disuns/WeatherOpenApiPJ) 의 Migration

## **기본 기능**
- 인트로 화면 : permission 체크를 하여 GPS를 사용할 권한을 받는다
- 로딩 화면 : 데이터 통신중 화면 터치 방지 및 로딩중인 상태를 나타낸다
- 시작화면(실시간 예보 화면) : GPS/LBS와 네이버맵을 통해 얻어온 주소를 기반으로 실시간 예보, 3일간의 시간예보, 주간예보을 나타낸다
- 대기예보 화면 : 주소를 기반으로한 가장 가까운 측정소의 대기예보 및 전국의 미세먼지 예보와 시간별 미세먼지 예측모델을 나타낸다
- 주소 검색 화면 : 네이버맵을 이용하여 원하는 지점의 주소를 얻어온다

## **사용 기술**
- OkHttp3 + Retrofit2: OpenAPI 요청 및 응답 처리
- GPS Permission 체크: 위치 권한 요청 및 관리
- Compose: UI/UX 구현
- MVVM: ViewModel과 Repository를 기반으로 한 데이터 관리
- Glide: 이미지 로딩 및 캐싱
- Hilt: 의존성 주입
- StateFlow: 통신 및 UI 상태 관리
- PreviewParameterProvider: Compose Preview 테스트
- Kotlin DSL + Version Catalog: 빌드 및 버전 관리

## **화면 구성**
### **인트로 화면**
<img src = "https://github.com/user-attachments/assets/c89f3f4f-72ed-4a4d-8dda-6f829737e7ad" width = "50%" height = "50%">

### **로딩 화면**
<img src = "https://github.com/user-attachments/assets/c1edd5dd-f1ad-43fe-bc4c-de42cac587e6" width = "50%" height = "50%">

#### 배치
- Dialog, CircularProgressIndicator: 로딩 중 화면을 구현하여 데이터 통신 중 터치 방지

### **시작화면**(실시간 예보 화면)

<img src = "https://github.com/user-attachments/assets/6e456c7f-be71-4159-822e-669942959e2b" width = "50%" height = "50%">
                                                                                                                                               
#### 배치
- LazyColumn, Card, HorizontalPager: 실시간 예보, 3일간 시간 예보, 주간 예보를 제공하는 기본 UI 요소
- Modifier.graphicsLayer: UI 효과 적용
- BottomNavigation: 하단 메뉴 바를 생성하여 주요 화면 간의 네비게이션 제공

### **대기예보 화면**

<img src = "https://github.com/user-attachments/assets/b1252b18-f314-4905-b787-d4ba9d94a23e" width = "50%" height = "50%">

#### 배치
- Card, ExposedDropdownMenuBox, ExposedDropdownMenu, DropdownMenuItem: 스피너 UI를 사용하여 주소 기반 대기 예보 제공
- LazyColumn: 스크롤 가능한 대기 예보 화면 구현
- BottomNavigation: 하단 메뉴 바를 생성하여 주요 화면 간의 네비게이션 제공

### **주소 검색 화면**

<img src = "https://github.com/user-attachments/assets/aa6070a5-217f-4784-b74c-17d3f65e9a6b" width = "50%" height = "50%">

#### 배치
- NAVER Map Compose 라이브러리: 지도를 통한 주소 검색 기능 및 위치 마커 표시 기능 제공
- BasicTextField, Box, IconButton: 사용자가 직접 주소를 입력할 수 있는 검색 UI 구현

## **작업 중 주요 이슈**
