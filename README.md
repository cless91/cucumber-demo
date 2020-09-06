build the jenkins docker image
```
cd deploy
docker build . -t myjenkins --build-arg GITHUB_ACCESS_TOKEN=$GITHUB_ACCESS_TOKEN
docker-compose down && docker-compose up
```
It :
- starts automatically a `Jenkins` container
- with a `multi-branch pipeline` pre-configured with this very project.
- runs a selenium test that performs a login error on `LinkedIn`
- records a video of the test.

Next: 
- add a Jira or a Service Now to the `docker-compose`. 
- upload the test result with the video for some task or userstory about
- upload the test scenario / description with the actual `Gherkin` executed 