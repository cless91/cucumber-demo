build the jenkins docker image
```
cd deploy
docker build . -t myjenkins --build-arg GITHUB_ACCESS_TOKEN=$GITHUB_ACCESS_TOKEN
docker-compose down && docker-compose up
```
It starts automatically a `Jenkins` container, with a `multi-branch pipeline` pre-configured with this very project.