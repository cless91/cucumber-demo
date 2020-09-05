job('example-job-from-job-dsl') {
    scm {
        git('git@github.com:cless91/contacts-app.git', 'master')
    }
    triggers {
        cron("* * * * *")
    }
    steps {
        shell("echo 'Hello World'")
    }
}
