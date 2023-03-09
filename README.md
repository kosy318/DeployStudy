# 배포 방법
> 참고 자료 : [@wlwlsus 이 정리한 포팅 매뉴얼](https://relieved-cave-3bc.notion.site/Porting-Manual-932047becc7a4711b37f1af2e913ec80)
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

### 1. WSL 사용하여 EC2에 SSH 연결

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
        

### 2. **EC2 초기 설정**
1. sudo apt update: APT 패키지 관리자가 사용하는 로컬 패키지 리스트를 최신 버전으로 업데이트하는 명령어입니다. 시스템에 설치된 패키지를 최신 상태로 유지하기 위해 필요한 업데이트가 있는지 확인할 수 있습니다.

2. sudo apt upgrade: 시스템에 설치된 모든 패키지를 최신 버전으로 업그레이드합니다. 업그레이드 할 패키지의 목록이 표시되며, 업그레이드를 계속할 것인지 묻는 메시지가 표시됩니다.

3. sudo apt install build-essential: C/C++ 컴파일러를 비롯하여 빌드 과정에서 필요한 다양한 도구와 라이브러리를 설치하는 명령어입니다. C/C++ 프로그램을 컴파일하거나 라이브러리를 빌드하는 데 필요한 패키지들이 자동으로 설치됩니다. build-essential 패키지와 의존성 패키지들이 설치됩니다.
```bash
$ sudo apt update
$ sudo apt upgrade
$ sudo apt install build-essential
```

### 3. 한국으로 시간 설정

```bash
$ sudo ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

# 시간 확인
$ date
```

![image](https://user-images.githubusercontent.com/77595685/220799065-ec51e9c5-a370-4313-badd-8d0b415ed481.png)

# 수동 배포
## 2. EC2 환경 설정
### 1. Java 17 설치
```bash
$ sudo apt install openjdk-17-jdk
```

```bash
sudo update-java-alternatives --list   # 설치된 자바 버전 목록 표시
sudo update-java-alternatives --set <자바 버전 이름>   # 원하는 자바 버전으로 변경
```
## 3. RDS
직접 데이터베이스를 설치해서 다루게 되면 모니터링, 알람, 백업, HA 구성 등을 모두 직접 해야하는데 AWS에는 이러한 것들을 모두 지원하는 관리형 서비스 RDS를 제공한다.<br>
하드웨어 프로비저닝, 데이터베이스 설정, 패치 및 백업과 같은 잦은 운영 작업을 자동화하여 개발자가 개발에 집중할 수 있게 지원하는 서비스이다.<br>

### 1. RDS 인스턴스 
![image](https://user-images.githubusercontent.com/77595685/223888034-da5bc1cb-bb26-41f3-98e0-7efc48425823.png)
![image](https://user-images.githubusercontent.com/77595685/223891168-122f6983-c5ce-4a82-89a2-b544c81bece4.png)
![image](https://user-images.githubusercontent.com/77595685/223891215-1f2981e2-d37d-480e-80b8-7607347b66bf.png)
![image](https://user-images.githubusercontent.com/77595685/223891491-6fe5be17-77ad-4b08-80a6-1d0295733a4f.png)
![image](https://user-images.githubusercontent.com/77595685/223891628-9b180817-88a5-4983-8e4e-4ba9daad2e68.png)

### 2. RDS 파라미터 설정
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


# 자동 배포
## 2. EC2 환경 설정

### 1. Docker 설치

1. 기본 설정, 사전 설치

```bash
$ sudo apt update
$ sudo apt install apt-transport-https ca-certificates curl software-properties-common
```

2. 자동 설치 스크립트 활용

- 리눅스 배포판 종류를 자동으로 인식하여 Docker 패키지를 설치해주는 스크립트를 제공

```bash
$ sudo wget -qO- https://get.docker.com/ | sh
```

3. Docker 서비스 실행하기 및 부팅 시 자동 실행 설정

```bash
$ sudo systemctl start docker
$ sudo systemctl enable docker
```

4. Docker 그룹에 현재 계정 추가

```bash
$ sudo usermod -aG docker ${USER}
$ sudo systemctl restart docker
```

- sudo를 사용하지 않고 docker를 사용할 수 있다.
- docker 그룹은 root 권한과 동일하므로 꼭 필요한 계정만 포함
- 현재 계정에서 로그아웃한 뒤 다시 로그인

5. Docker 설치 확인

```bash
$ docker -v
```

## 3. Jenkins 초기 세팅 및 테스트

- 젠킨스에 접속하기 전에 `/var/run/docker.sock` 에 대한 권한을 설정해주어야 합니다.
- 초기 `/var/run/docker.sock`의 권한이 **소유자와 그룹 모두 root**였기 때문에 이제 그룹을 root에서 `docker`로 변경해줄겁니다.
- 먼저, jenkins로 실행됐던 컨테이너의 bash를 root 계정으로 로그인 하기전에, 현재 실행되고 있는 컨테이너의 정보들을 확인할 수 있는 명령어를 입력해 아이디를 확인하겠습니다.

```
docker ps -a
```

![https://images.velog.io/images/hind_sight/post/7897203d-6b06-458c-a7ff-db706d2c38e9/image.png](https://images.velog.io/images/hind_sight/post/7897203d-6b06-458c-a7ff-db706d2c38e9/image.png)

- 우리가 방금 생성한 컨테이너의 ID는 **0bcdb8~** 입니다. 도커는 다른 컨테이너 ID와 겹치지 않는 부분까지 입력하면 해당 컨테이너로 알아서 매핑해줍니다.

```
docker exec -it -u root 컨테이너ID /bin/bash
```

`exec`는 컨테이너에 명령어를 실행시키는 명령어인데, /bin/bash와 옵션 -it를 줌으로써 컨테이너의 쉘에 접속할 수 있습니다.

이제 정말로 root 계정으로 컨테이너에 접속하기 위해 컨테이너ID에 0bc를 입력해 실행합니다.

![https://images.velog.io/images/hind_sight/post/dc9722a9-1959-47ac-947e-167f134686a2/image.png](https://images.velog.io/images/hind_sight/post/dc9722a9-1959-47ac-947e-167f134686a2/image.png)

- root 계정으로 로그인이 잘 되었습니다. 이제 그룹을 바꾸기 위해 다음 명령어를 실행해줍니다.

```
chown root:docker /var/run/docker.sock
```

- 그리고 이제 쉘을 exit 명령어로 빠져나온 후 다음 명령어를 실행해 컨테이너를 재실행해줍니다.

```
docker restart [컨테이너 ID]
```

- Jenkins 패스워드 확인

```
docker logs [jenkins 컨테이너 ID]
```

- docker logs 컨테이너 id를 입력해 로그를 출력하면 initialAdminPassword가 출력됩니다. 이 패스워드를 입력해주면 됩니다.

![https://images.velog.io/images/hind_sight/post/92531a06-5df6-4983-82ae-aa5be65f560c/image.png](https://images.velog.io/images/hind_sight/post/92531a06-5df6-4983-82ae-aa5be65f560c/image.png)

- 보안 그룹 설정을 해야 {public ip}:9090 에 접근할 수 있습니다.
    - EC2 \> 보안그룹

![image](https://user-images.githubusercontent.com/77595685/220798853-af647702-ebba-4f0b-baa1-ea8c8dc04d96.png)<br>

`인바운드 규칙 편집` 클릭

![image](https://user-images.githubusercontent.com/77595685/220798865-96b343c9-9a45-4158-b2d5-95da4f4b8a86.png)<br>

`규칙 추가` 클릭 → 유형: `TCP`, 포트 범위: {등록할 포트 번호} 

---

![https://images.velog.io/images/hind_sight/post/56e653e2-20b7-4c6a-a50e-dd998d61dd37/image.png](https://images.velog.io/images/hind_sight/post/56e653e2-20b7-4c6a-a50e-dd998d61dd37/image.png)

- 정상적으로 입력했다면 플러그인 설치가 나오는데, 우리는 Install suggested plugins를 선택합니다.

![https://images.velog.io/images/hind_sight/post/058f2ff5-86ab-4b79-b2d2-6ce9819ce3ba/image.png](https://images.velog.io/images/hind_sight/post/058f2ff5-86ab-4b79-b2d2-6ce9819ce3ba/image.png)

- 설치가 완료되면, 어드민 계정 생성창이 나오고, 본인이 사용하실 정보들을 입력해줍시다.

![create.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6111157d-c24c-48ff-b123-42823c00081f/create.png)

![https://images.velog.io/images/hind_sight/post/c43f4bfb-1dd4-458c-84d7-a507fc4b5bd8/image.png](https://images.velog.io/images/hind_sight/post/c43f4bfb-1dd4-458c-84d7-a507fc4b5bd8/image.png)

- 앞으로 이 url로 젠킨스에 접속하시면 됩니다.

### Jenkins Github 연동

1. github token 발급

![image](https://user-images.githubusercontent.com/77595685/220803986-64abbe06-4ce8-4457-a830-b5e0d7257db9.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804052-29a95ca6-ae0b-4dbc-8840-aaf10944c00e.png)<br>

- repo와 gist 선택

![image](https://user-images.githubusercontent.com/77595685/220804100-8968d402-56d7-442f-bade-830fbfed3bec.png)<br>

2. jenkins와 git 연동

![image](https://user-images.githubusercontent.com/77595685/220804268-3a6f1c90-97cc-4e23-9365-75b601f1a85a.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804344-a666dfa7-8d8d-4f35-bc89-84ee240ae328.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804382-9ed564ce-9d42-4973-9a67-934a7c743dde.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804432-c86bac44-8291-48c8-bbe3-8b20d4a8c04c.png)<br>

3. plugin 설치

![image](https://user-images.githubusercontent.com/77595685/220806331-3344afb8-cf99-4417-b58b-d509615d0796.png)<br>

4. jenkins에 git project 추가

![image](https://user-images.githubusercontent.com/77595685/220804524-7fb6acc6-da3f-4964-a942-6e06b050f1ff.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804572-135a05db-8640-47bd-b7eb-7ff04de8e934.png)<br>

![image](https://user-images.githubusercontent.com/77595685/220804646-00c0aa16-cb9f-461e-8575-0fa3fb5e9922.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804686-28512a6a-704c-4e25-8cbc-222196198983.png)<br>

![image](https://user-images.githubusercontent.com/77595685/220804720-7ec06a23-68cb-4eab-a4f2-ad2143cded52.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804748-6b24b737-5010-4302-88f6-1f9c976c6dbd.png)<br>

- Build에서 Execute shell을 선택해줍니다.
- 저장소와 연동되어 폴더 내의 start.sh 파일이 실행됩니다.

![image](https://user-images.githubusercontent.com/77595685/220806513-f563996d-6a47-4797-8e31-fe92e41e0cd4.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220806594-fa60906e-3726-4df7-8b2d-7030d358e515.png)<br>

## 4. 기본 파일 작성

- 우선 docker-compose를 설치합니다.
    ```bash
    sudo apt install docker-compose
    ```

- jenkins container에 들어가서도 docker-compose를 설치합니다.
    - root 계정으로 들어가 설치합니다.
    ```bash
    docker exec -itu 0 {container ID} bash
    apt install docker-compose
    ```
    
- jenkins에서 처음 실행시키는 start.sh 파일을 작성합니다.
    ```yaml
    docker-compose -f docker-compose.yml pull

    COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose.yml up --build -d

    docker rmi -f $(docker images -f "dangling=true" -q) || true
    ```

- start.sh에서 실행시키는 docker-compose.yml 파일을 작성합니다.
    ```yaml
    version: "3"
    services:
      zookeeper:
        image: wurstmeister/zookeeper
        container_name: zookeeper
        ports:
          - "2181:2181"
      kafka:
        image: wurstmeister/kafka
        container_name: kafka
        ports:
          - "9092:9092"
        environment:
          KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
          KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
        volumes:
          - /var/run/docker.sock:/var/run/docker.sock
      server:
        container_name: server
        build:
          context: ./deployTest
        ports:
          - 8080:8080
        environment:
          - TZ=Asia/Seoul
    ```
    
- 실행시키는 프로젝트 내부에 Dockerfile 파일을 작성합니다.
    ```
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

- 빌드 시
    - docker 설치 후 /var/run/docker.sock의 permission denied 발생하는 경우
    ```bash
    sudo chmod 666 /var/run/docker.sock
    ```
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
