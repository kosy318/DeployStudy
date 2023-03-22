# 배포 방법
## 인스턴스 생성
- 인스턴스 시작 클릭

![image](https://user-images.githubusercontent.com/77595685/220796287-b9916769-1383-4aa3-98ea-c20727c03b44.png)<br>

- 설정

![image](https://user-images.githubusercontent.com/77595685/223637589-6989a64f-4488-4d29-822b-908672ff946a.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220796688-02c55458-c835-4bf6-89bc-e105ac49b34b.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220796713-c8095a41-8141-423f-9fa2-8e842ac886c0.png)<br>

- 탄력정 IP 할당 및 연결

![image](https://user-images.githubusercontent.com/77595685/220796991-6c5b8fc3-2c85-4669-8fbc-27b7092ebd39.png)
![image](https://user-images.githubusercontent.com/77595685/220797078-f39753e9-d713-4f35-937d-10ab70b85255.png)


## WSL 설치
> 정리중

## EC2(서버) 접속

### WSL 사용하여 EC2에 SSH 연결

- SSH 접속
1. 최초 접속 시 권한 요구하면 ‘yes’ 입력

```bash
sudo ssh -i [pem키 위치] [접속 계정]@[접속할 도메인]
```

- EC2 편리하게 접속하는 법
    - EC2 정보가 담긴 config파일을 만들어 번거롭게 pem와 도메인 경로를 쓰지 않고 접속할 수 있다.
        - ssh 전용 폴더 생성
        
        ```jsx
        mkdir ~/.ssh 
        cd ~/.ssh // ssh 폴더 생성 및 이동
        cp [로컬 pem 키 위치] ~/.ssh // pem 키 옮기기
        vi config  // config 파일 생성
        ```
        
        - config 내용 추가
        
        ```jsx
        Host [host]
                HostName [서버 ip 주소]
                User ubuntu
                IdentityFile ~/.ssh/[pem키 파일 명].pem
        ```
        
        - 계정에 접속
        
        ```jsx
        ssh [host]
        ```
        

### **EC2 초기 설정**
1. sudo apt update: APT 패키지 관리자가 사용하는 로컬 패키지 리스트를 최신 버전으로 업데이트하는 명령어입니다. 시스템에 설치된 패키지를 최신 상태로 유지하기 위해 필요한 업데이트가 있는지 확인할 수 있습니다.

2. sudo apt upgrade: 시스템에 설치된 모든 패키지를 최신 버전으로 업그레이드합니다. 업그레이드 할 패키지의 목록이 표시되며, 업그레이드를 계속할 것인지 묻는 메시지가 표시됩니다.

3. sudo apt install build-essential: C/C++ 컴파일러를 비롯하여 빌드 과정에서 필요한 다양한 도구와 라이브러리를 설치하는 명령어입니다. C/C++ 프로그램을 컴파일하거나 라이브러리를 빌드하는 데 필요한 패키지들이 자동으로 설치됩니다. build-essential 패키지와 의존성 패키지들이 설치됩니다.
```bash
$ sudo apt update
$ sudo apt upgrade
$ sudo apt install build-essential
```

### 한국으로 시간 설정

```bash
$ sudo ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

# 시간 확인
$ date
```

![image](https://user-images.githubusercontent.com/77595685/220799065-ec51e9c5-a370-4313-badd-8d0b415ed481.png)

# 수동 배포(free tier)
## EC2 환경 설정
### Java 17 설치
```bash
$ sudo apt install openjdk-17-jdk
```

```bash
sudo update-java-alternatives --list   # 설치된 자바 버전 목록 표시
sudo update-java-alternatives --set <자바 버전 이름>   # 원하는 자바 버전으로 변경
```
## RDS
직접 데이터베이스를 설치해서 다루게 되면 모니터링, 알람, 백업, HA 구성 등을 모두 직접 해야하는데 AWS에는 이러한 것들을 모두 지원하는 관리형 서비스 RDS를 제공한다.<br>
하드웨어 프로비저닝, 데이터베이스 설정, 패치 및 백업과 같은 잦은 운영 작업을 자동화하여 개발자가 개발에 집중할 수 있게 지원하는 서비스이다.<br>

### RDS 인스턴스 
![image](https://user-images.githubusercontent.com/77595685/223888034-da5bc1cb-bb26-41f3-98e0-7efc48425823.png)
![image](https://user-images.githubusercontent.com/77595685/223891168-122f6983-c5ce-4a82-89a2-b544c81bece4.png)
![image](https://user-images.githubusercontent.com/77595685/223891215-1f2981e2-d37d-480e-80b8-7607347b66bf.png)
![image](https://user-images.githubusercontent.com/77595685/223891491-6fe5be17-77ad-4b08-80a6-1d0295733a4f.png)
![image](https://user-images.githubusercontent.com/77595685/223891628-9b180817-88a5-4983-8e4e-4ba9daad2e68.png)

### RDS 파라미터 설정
![image](https://user-images.githubusercontent.com/77595685/223891996-9fd30e78-f8c5-4e90-b12d-7063a6850c32.png)

1. 타임존
![image](https://user-images.githubusercontent.com/77595685/223892202-52763cf7-c372-452d-a5e4-e8e85647aafe.png)
![image](https://user-images.githubusercontent.com/77595685/223892351-d4260e99-0069-4151-9b8c-77950f1a774a.png)

2. Character Set
![image](https://user-images.githubusercontent.com/77595685/223892481-a1a86891-e05c-4a92-9024-5e04761e8136.png)
![image](https://user-images.githubusercontent.com/77595685/223892653-7eb813e8-6b09-4f1f-b83c-871477d88d0e.png)
![image](https://user-images.githubusercontent.com/77595685/223892716-aec3b309-a913-4928-8491-8635522bca91.png)

4. Max Connection
RDS의 Max Connection은 인스턴스 사양에 따라 자동으로 정해진다. 프리티어 사양으로는 약 60개 커넥션만 가능해서 좀 더 넉넉한 값으로 지정.<br>
![image](https://user-images.githubusercontent.com/77595685/223892810-c0a2d8b5-389d-4594-8c1f-7bc474f0edb9.png)

5. 파라미터 그룹을 DB에 연결
![image](https://user-images.githubusercontent.com/77595685/223893200-79176caa-1426-4372-950b-ab5a2925bae0.png)
![image](https://user-images.githubusercontent.com/77595685/223893265-46d6068b-a5b0-4068-8a02-dcc74f3838f9.png)
![image](https://user-images.githubusercontent.com/77595685/223893319-d693e1ba-f6a5-4a10-b95f-47dac20e775c.png)

### 내 PC에서 RDS 접속
![image](https://user-images.githubusercontent.com/77595685/223896344-ec887cd6-253c-4bc0-bee9-4dbbb9c5cf05.png)<br>
그룹 ID 복사<br>
![image](https://user-images.githubusercontent.com/77595685/223896613-36577c87-833b-4506-aae9-23ad5825b7b9.png)
![image](https://user-images.githubusercontent.com/77595685/223896739-caba8dfb-9bf5-4de1-b5b0-8ced76a1e198.png)
![image](https://user-images.githubusercontent.com/77595685/223896988-38fc0d6a-028c-448c-b03e-7a7990a16bc0.png)
![image](https://user-images.githubusercontent.com/77595685/223897384-579be369-f85f-48f6-a45a-87a458e6f2f8.png)<br>
ctrl+shift+a<br>
![image](https://user-images.githubusercontent.com/77595685/223898360-2d96fdd5-19cd-4232-b60f-a039850e6d02.png)
![image](https://user-images.githubusercontent.com/77595685/223899627-07884b68-aac6-42e2-806c-07f1ee63efde.png)

### EC2에서 RDS 접속
```bash
$ sudo apt install mariadb-server
$ sudo apt install mariadb-client
$ mysql -u 계정 -p -h Host주소(엔드포인트)
```
### 프로젝트 배포
- 터짐 -> free tier라서
    - 램을 2기가로 늘린다
```bash
sudo dd if=/dev/zero of=/swapfile bs=128M count=16
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
sudo swapon -s
sudo vi /etc/fstab

# 아래 내용 입력 후 빌드
/swapfile swap swap defaults 0 0
```

```bash
$ sudo apt install git
# 버전 확인
$ git --version
$ mkdir ~/app && mkdir ~/app/step1
$ cd ~/app/step1
$ git clone 복사한 주소
$ cd 프로젝트 폴더 내부
$ ./gradlew test
```
만약 다음과 같은 오류가 뜬다면
```bash
$ -bash: ./gradlew: Permission denied
```
다음 명령어로 실행 권한을 추가한 뒤 다시 테스트를 수행
```bash
$ chmod +x ./gradlew
```

- 배포 스크립트 만들기
```bash
vim ~/spring boot 프로젝트 바로 바깥 폴더/deploy.sh
```
아래 내용 작성
```bash
#!/bin/bash

REPOSITORY=/home/ubuntu/spring boot 프로젝트 바로 바깥 폴더
PROJECT_NAME=ttarawa

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"
git pull

echo "> 프로젝트 build 시작"
./gradlew build

echo "> step1 디렉토리 이동"
cd $REPOSITORY

echo "> Build  파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
echo "현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID"]; then
        echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
```
실행 권한 추가 및 실행
```bash
chmod +x ./deploy.sh
./deploy.sh
```

# 자동 배포
## EC2 환경 설정

### Docker 설치

1. 기본 설정, 사전 설치
- 이 명령어는 HTTPS를 사용하여 소프트웨어를 안전하게 다운로드하고, 인증서를 관리하여 보안성을 높이며, 소프트웨어 저장소를 관리할 수 있도록 필요한 패키지를 모두 설치합니다.

```bash
$ sudo apt install apt-transport-https ca-certificates curl software-properties-common
```

1. 자동 설치 스크립트 활용
- 리눅스 배포판 종류를 자동으로 인식하여 Docker 패키지를 설치해주는 스크립트를 제공

```bash
$ sudo wget -qO- https://get.docker.com/ | sh
```

1. Docker 서비스 실행하기 및 부팅 시 자동 실행 설정

```bash
$ sudo systemctl start docker
$ sudo systemctl enable docker
```

1. Docker 그룹에 현재 계정 추가

```bash
$ sudo usermod -aG docker ${USER}
$ sudo systemctl restart docker
```

- sudo를 사용하지 않고 docker를 사용할 수 있다.
- docker 그룹은 root 권한과 동일하므로 꼭 필요한 계정만 포함
- 현재 계정에서 로그아웃한 뒤 다시 로그인
1. Docker 설치 확인

```bash
$ docker -v
```

### Jenkins 설치

```bash
$ mkdir jenkins-docker
$ cd jenkins-docker
$ vi Dockerfile
```

Dockerfile

```bash
FROM jenkins/jenkins:latest
  
USER root

RUN apt-get update \
 && apt-get -y install lsb-release \
 && curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg \
 && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null \
 && apt-get update \
 && apt-get -y install docker-ce docker-ce-cli containerd.io

RUN usermod -aG docker jenkins

USER jenkins
```

```bash
$ docker build -t my-jenkins:0.1 .
$ docker run -d --name jenkins -v /var/run/docker.sock:/var/run/docker.sock -v jenkins:/var/jenkins_home -p 9090:8080 my-jenkins:0.1
$ docker exec -it jenkins bash
```

비밀번호 확인 후 9090포트로 jenkins에 들어갈 수 있다.

```bash
jenkins$ docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

### Jenkins 내부 java 버전 설정

17로 올림

![image](https://user-images.githubusercontent.com/77595685/226778536-2b1acb45-7f42-41c3-b97d-caabeb6702bf.png)

[젠킨스 jdk 버전 11로 올리는 방법](https://www.blog.ecsimsw.com/entry/젠킨스-jdk-버전-11로-올리는-방법)

### Docker-compose 설치

jenkins 안에서 진행

```bash
$ docker exec -itu 0 jenkins bash

jenkins$ curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
jenkins$ chmod +x /usr/local/bin/docker-compose
jenkins$ docker-compose --version
```

### Plugin 설치

![image](https://user-images.githubusercontent.com/77595685/226778563-34f5f5aa-36d0-4a89-a57b-ba909389b352.png)

### 프로젝트에 파일들 추가

### ./start.sh

```bash
docker-compose -f docker-compose.yml pull

COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose.yml up --build -d

docker rmi -f $(docker images -f "dangling=true" -q) || true
```

### ./docker-compose.yml

```yaml
version: '3.8'

services:
  db:
    image: mariadb:10.6.5
    volumes:
      - db-data:/var/lib/mysql
    environment:
      - MYSQL_ROOT_PASSWORD=ssafy605
      - MYSQL_DATABASE=ssafy605
      - MYSQL_USER=ssafy605
      - MYSQL_PASSWORD=ssafy605
    ports:
      - "3306:3306"
  redis:
    image: redis:6.2.6-alpine
    ports:
      - "6379:6379"
  backend:
    build:
      context: ./ttarawa
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    volumes:
      - ./ttarawa:/app
    depends_on:
      - db
      - redis
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mariadb://j8a605.p.ssafy.io:3306/ssafy605
      - SPRING_DATASOURCE_USERNAME=ssafy605
      - SPRING_DATASOURCE_PASSWORD=ssafy605
      - REDIS_HOST=redis

volumes:
  db-data:
```

### ./ttarawa/Dockerfile

```docker
FROM openjdk:17-jdk-slim as builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:17-jdk-slim
COPY --from=builder build/libs/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app.jar"]
```

### General → 소스 코드 관리

gitlab에서 토큰 복사

![image](https://user-images.githubusercontent.com/77595685/226778584-d58bd134-dace-46bd-a103-bf2a8a2eae8a.png)

빨간색 부분에 붙여넣기

![image](https://user-images.githubusercontent.com/77595685/226778603-a0819c33-223e-4e92-aa3d-644ab8208046.png)

![image](https://user-images.githubusercontent.com/77595685/226778611-019826e6-d7ba-457c-aea6-b7b5e5540269.png)

### Webhook 설정

![image](https://user-images.githubusercontent.com/77595685/226778621-d89c8cba-3b4b-48dd-a123-47b14ce5883d.png)

url 복사

![image](https://user-images.githubusercontent.com/77595685/226778631-68b80551-f014-4c5d-a127-b62f784d4a61.png)

생성된 token 복사 후 gitlab project에 붙여넣기

![image](https://user-images.githubusercontent.com/77595685/226778642-ba29339f-0846-4819-a3e6-931ae08048a0.png)

# 무중단 배포 → Nginx

Tags: 배포

1. Rolling Update 배포

💡 새로 배포되어야 하는 버전을 하나씩 순차적으로 적용시키면서 배포하는 방식입니다. 한 번에 모두 배포되는 게 아니기 때문에 배포가 되는 과정에서 옛날 버전과 새로운 버전이 공존합니다. 그렇기 때문에 <b>잘못하면 배포하는 과정 중에 호환성 문제가 생길 수 있습니다</b>.


2. Blue, Green 배포

참고 사이트 : [docker-nginx/README.md at master · twer4774/docker-nginx](https://github.com/twer4774/docker-nginx/blob/master/README.md)
    

💡 Blue 혹은 Green 버전 둘 중 하나로 배포되어 있는 상태에서 새로운 버전을 동시에 띄우고 로드밸런서를 통해서 스위칭하는 방식이며, 한 번에 두 개의 버전을 동시에 띄우기 때문에 시스템 <b>자원이 두배로 든다는 단점이 있습니다.</b>


## Nginx 설치

### nginx-Dockerfile

```bash
FROM nginx:1.11
  
RUN rm -rf /etc/nginx/conf.d/default.conf

COPY ./conf.d/app.conf /etc/nginx/conf.d/app.conf
COPY ./conf.d/nginx.conf /etc/nginx/nginx.conf

VOLUME ["/data", "/etc/nginx", "/var/log/nginx"]

WORKDIR /etc/nginx

CMD ["nginx"]

EXPOSE 80
```

### ./conf.d/app.conf

```bash
server {
    listen 80;
    listen [::]:80;

    server_name "";

    access_log off;

    location / {
        proxy_pass http://docker-app;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto http;
        proxy_max_temp_file_size 0;

        proxy_connect_timeout 150;
        proxy_send_timeout 100;
        proxy_read_timeout 100;

        proxy_buffer_size 8k;
        proxy_buffers 4 32k;
        proxy_busy_buffers_size 64k;
        proxy_temp_file_write_size 64k;
    }
}
```

### ./conf.d/nginx.conf

```bash
daemon off;
user www-data;
worker_processes 2;

error_log /var/log/nginx/error.log warn;
pid /var/run/nginx.pid;

events {
    worker_connections 1024;
    use epoll;
    accept_mutex off;
}

http {
    include /etc/nginx/mime.types;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

    default_type application/octet-stream;

    upstream docker-app {
        least_conn;
        server j8a605.p.ssafy.io:8085 weight=10 max_fails=3 fail_timeout=30s;
        server j8a605.p.ssafy.io:8086 weight=10 max_fails=3 fail_timeout=30s;
    }

    log_format main '$remote_addr - $remote_user [$time_local] "$request"'
    '$status $body_bytes_sent "$http_referer"'
    '"$http_user_agent" "$http_x_forwarded_for"';

    access_log /var/log/nginx/access.log main;

    sendfile on;
    #tcp_nopush

    keepalive_timeout 65;

    client_max_body_size 300m;
    client_body_buffer_size 128k;

    gzip on;
    gzip_http_version 1.0;
    gzip_comp_level 6;
    gzip_min_length 0;
    gzip_buffers 16 8k;
    gzip_proxied any;
    gzip_types text/plain text/css text/xml text/javascript application/xml application/xml+rss application/javascript application/json;
    gzip_disable "MSIE [1-6]\.";
    gzip_vary on;

    #리눅스환경에서 취급하는 호스팅하는 웹서버 경로
    include /etc/nginx/conf.d/*.conf;

}
```

### nginx 실행

```bash
docker build -t docker-nginx:0.1 -f nginx-Dockerfile .

docker run -d --name docker-nginx -p 80:80 docker-nginx:0.1
```

## Spring Boot Application 작성

### application.yml

```bash
spring:
  jpa:
    hibernate:
      ddl-auto: update
      generate-ddl: false
      show-sql: true
  datasource:
    # mariaDB setting
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://j8a605.p.ssafy.io:3306/ssafy605
    username: ssafy605
    password: ssafy605

server:
  port: 8080
```

### docker-compose.blue.yml

```bash
version: '3.8'

services:
  app:
    image: app:0.1
    container_name: app_blue
    environment:
      - "spring_profiles_active=blue"
    ports:
      - "8085:8080"
```

### docker-compose.green.yml

```bash
version: '3.8'

services:
  app:
    image: app:0.2
    container_name: app_green
    environment:
      - "spring_profiles_active=green"
    ports:
      - "8086:8080"
```

## 배포 스크립트 작성

jenkins 내부 execute shell

```bash
cd ttarawa
chmod +x ./gradlew
./gradlew bootJar
chmod +x deploy.sh
./deploy.sh
```

### deploy.sh

```bash
#!/bin/bash

function create_docker_image_blue(){

  echo "> blue docker image 만들기"

  ./gradlew clean build

  docker build -t app:0.1 .

}

function create_docker_image_green(){

  echo "> green docker image 만들기"

  ./gradlew clean build

  docker build -t app:0.2 .
}

function execute_blue(){
    docker ps -q --filter "name=app_blue" || grep -q . && docker stop app_blue && docker rm app_blue || true

    sleep 10

    docker-compose -p app-blue -f docker-compose.blue.yml up -d

    sleep 10

    echo "GREEN:8086 종료"
    docker-compose -p app-green -f docker-compose.green.yml down

    #dangling=true : 불필요한 이미지 지우기
    docker rmi -f $(docker images -f "dangling=true" -q) || true
}

function execute_green(){
  docker ps -q --filter "name=app_green" || grep -q . && docker stop app_green && docker rm app_green || true

    echo "GREEN:8086 실행"
    docker-compose -p app-green -f docker-compose.green.yml up -d

    sleep 10

    echo "BLUE:8085 종료"
    docker-compose -p app-blue -f docker-compose.blue.yml down

    #dangling=true : 불필요한 이미지 지우기
    docker rmi -f $(docker images -f "dangling=true" -q) || true
}

# 현재 사용중인 어플리케이션 확인
# 8086포트의 값이 없으면 8085포트 사용 중
# shellcheck disable=SC2046
RUNNING_GREEN=$(docker ps -aqf "name=app_green")
RUNNING_BLUE=$(docker ps -aqf "name=app_blue")

echo ${RUNNING_GREEN}
echo ${RUNNING_BLUE}

# Blue or Green
if [ -z ${RUNNING_GREEN} ]
  then
    # 초기 실행 : BLUE도 실행중이지 않을 경우
    if [ -z ${RUNNING_BLUE} ]
    then
      echo "구동 앱 없음 => BLUE 실행"

      create_docker_image_blue

      sleep 10

      docker-compose -p app-blue -f docker-compose.blue.yml up -d
	  
    else
      # 8086포트로 어플리케이션 구동
      echo "BLUE:8085 실행 중"

      create_docker_image_green

      execute_green

    fi
else
    # 8085포트로 어플리케이션 구동
    echo "GREEN:8086 실행 중"

    echo "BLUE:8085 실행"

    create_docker_image_blue

    execute_blue

fi

# 새로운 어플리케이션 구동 후 현재 어플리케이션 종료
#kill -15 ${RUNNING_PORT_PID}
```
