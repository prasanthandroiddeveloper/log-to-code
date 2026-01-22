pipeline {
    agent any

    environment {
        COMMIT_SHA = ""
        BRANCH = ""
        CHANGED_FILES = ""
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/prasanthandroiddeveloper/log-to-code.git'
            }
        }

        stage('Git Info') {
            steps {
                script {
                    // Jenkins safe way (works even with detached HEAD)
                    env.COMMIT_SHA = powershell(returnStdout: true, script: 'git rev-parse HEAD').trim()
                    env.BRANCH     = powershell(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()

                    // Last commit files
                    env.CHANGED_FILES = powershell(
                        returnStdout: true,
                        script: 'git show --name-only --pretty="" HEAD'
                    ).trim()

                    echo "Commit  : ${env.COMMIT_SHA}"
                    echo "Branch  : ${env.BRANCH}"
                    echo "Changed : ${env.CHANGED_FILES}"
                }
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Run') {
            steps {

            bat """

                set COMMIT_SHA=${env.COMMIT_SHA}
                java -jar target\\logtocode-0.0.1-SNAPSHOT.jar --server.port=9000
            """
            }
        }
    }

    post {
        always {
            script {
                def files = env.CHANGED_FILES ?: ""

                writeFile file: 'commit_metadata.json', text: """
{
  "commit_sha": "${env.COMMIT_SHA}",
  "branch": "${env.BRANCH}",
  "files_changed": "${files.replaceAll("\\n","\\\\n")}"
}
"""
                echo "commit_metadata.json generated"
            }
        }
    }
}