# [VSE-Back-Manage-System](https://github.com/PrettyMagnolia/VSE-Back-Manage-System)

虚拟仿真实验平台后端



## 自动化部署

```yaml
name: Java CI with Maven

on:
  push:
    branches: [ "zst" ]
  pull_request:
    branches: [ "zst" ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven
      - name: Build with Maven
        run: mvn -B package --file pom.xml

      - name: deploy
        uses: cross-the-world/ssh-scp-ssh-pipelines@latest
        with:
          host: ${{ secrets.host }}
          user: ${{secrets.user}}
          pass: ${{secrets.pass}}
          scp: |
            './target/*.jar' => /home/ubuntu/ves/
          last_ssh: |
            nohup java -jar /home/ubuntu/ves*.jar > ves.log 2>&1 &
```
