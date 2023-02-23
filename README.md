# 배포 방법
> 참고 자료 : [@wlwlsus 이 정리한 포팅 매뉴얼](https://relieved-cave-3bc.notion.site/Porting-Manual-932047becc7a4711b37f1af2e913ec80)
## 인스턴스 생성
- 인스턴스 시작 클릭

![image](https://user-images.githubusercontent.com/77595685/220796287-b9916769-1383-4aa3-98ea-c20727c03b44.png)<br>

- 설정

![image](https://user-images.githubusercontent.com/77595685/220796619-4c57b04c-4e69-441a-bbbc-3cd87636f2e3.png)<br>
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

![image](https://user-images.githubusercontent.com/77595685/220798839-9358cedb-007c-4646-ab1c-238d7ce77cdd.png)
![image](https://user-images.githubusercontent.com/77595685/220798853-af647702-ebba-4f0b-baa1-ea8c8dc04d96.png)

`인바운드 규칙 편집` 클릭

![image](https://user-images.githubusercontent.com/77595685/220798865-96b343c9-9a45-4158-b2d5-95da4f4b8a86.png)

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

3. jenkins와 git project 연동

![image](https://user-images.githubusercontent.com/77595685/220804524-7fb6acc6-da3f-4964-a942-6e06b050f1ff.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804572-135a05db-8640-47bd-b7eb-7ff04de8e934.png)<br>

![image](https://user-images.githubusercontent.com/77595685/220804646-00c0aa16-cb9f-461e-8575-0fa3fb5e9922.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804686-28512a6a-704c-4e25-8cbc-222196198983.png)<br>

![image](https://user-images.githubusercontent.com/77595685/220804720-7ec06a23-68cb-4eab-a4f2-ad2143cded52.png)<br>
![image](https://user-images.githubusercontent.com/77595685/220804748-6b24b737-5010-4302-88f6-1f9c976c6dbd.png)<br>




