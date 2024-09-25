# NewWeatherOpenApiPJ
날씨 및 생활지수 앱

###
기존 [WeatherOpenApiPj](https://github.com/disuns/WeatherOpenApiPJ) 의 Migration

## **기본 기능**
시작화면(실시간 예보 화면) : GPS/LBS와 네이버맵을 통해 얻어온 주소를 기반으로 실시간 예보, 3일간의 시간예보, 주간예보을 나타낸다   
대기예보 화면 : 주소를 기반으로한 가장 가까운 측정소의 대기예보 및 전국의 미세먼지 예보와 시간별 미세먼지 예측모델을 나타낸다   
주소 검색 화면 : 네이버맵을 이용하여 원하는 지점의 주소를 얻어온다   

## **사용 기술**
OkHttp3 + Retrofit2를 이용하여 OpenAPI의 요청과 응답
GPS Permission 체크
Compose를 활용한 UI/UX 컨트롤
MVVM 기반 개발 (ViewModel, Repository를 기반한 REST 데이터 관리)   
Glide를 활용한 Image 관리
Hilt를 활용한 의존성 관리
StateFlow를 활용한 통신 및 UI 상태 관리
일부 통신에 테스트 코드 구현
PreviewParameterProvider를 이용한 Compose Preview 활용
Extention을 활용한 기능 구현
Kotlin DSL과 Version Catalog를 활용한 버전 및 gradle 관리

## **화면 구성**
### **인트로 화면**
<img src = "https://github.com/user-attachments/assets/c89f3f4f-72ed-4a4d-8dda-6f829737e7ad" width = "50%" height = "50%">

#### 기능
permission 체크를 위한 인트로

### **로딩 화면**
<img src = "https://github.com/user-attachments/assets/c1edd5dd-f1ad-43fe-bc4c-de42cac587e6" width = "50%" height = "50%">

#### 배치
Dialog와 CircularProgressIndicator를 활용한 로딩화면 제작

#### 기능
데이터 통신중 화면 터치 방지 및 로딩중 상태 표현

### **시작화면**(실시간 예보 화면)

<img src = "https://github.com/user-attachments/assets/6e456c7f-be71-4159-822e-669942959e2b" width = "50%" height = "50%">
                                                                                                                                               
#### 배치
LazyColumn, Card, HorizontalPager를 기본으로 활용하여 UI/UX 구현
Modifier.graphicsLayer를 활용한 UI 효과 적용

#### 기능
현재 주소를 기준으로 한 현재 날씨, 1시간 단위 3일간의 날씨, 주간날씨 표현

### **대기예보 화면**

<img src = "https://github.com/user-attachments/assets/b1252b18-f314-4905-b787-d4ba9d94a23e" width = "50%" height = "50%">

#### 배치
Card, ExposedDropdownMenuBox, ExposedDropdownMenu, DropdownMenuItem 를 활용한 Spinner 활용 및 약간의 커스텀
LazyColumn을 이용한 ScrollView 활용

#### 기능
현재 주소를 기준으로 측정소 대기 예보, 전국 대기 예보 및 시간대별 예측모델

### **주소 검색 화면**

<img src = "https://github.com/user-attachments/assets/aa6070a5-217f-4784-b74c-17d3f65e9a6b" width = "50%" height = "50%">

#### 배치
NAVER Map Compose 라이브러리를 통한 화면 출력
BasicTextField, Box, IconButton 등을 활용한 SearchView 구현

#### 기능
현재 날씨, 1시간 단위 3일간의 날씨, 주간날씨 표현

## **작업 중 주요 이슈**
