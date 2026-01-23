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
                    // Use Jenkins built-in variables when available
                    echo "Jenkins GIT_COMMIT: ${env.GIT_COMMIT}"
                    echo "Jenkins GIT_BRANCH: ${env.GIT_BRANCH}"



                    // Safe way to get Git info
                    env.COMMIT_SHA = env.GIT_COMMIT
                    env.BRANCH     = env.GIT_BRANCH
                    env.CHANGED_FILES = bat(returnStdout: true, script: 'git show --name-only --pretty="" HEAD').trim()

                    echo "Commit SHA : ${env.GIT_COMMIT}"
                    echo "Branch     : ${env.GIT_BRANCH}"
                    echo "Changed files:\n${env.CHANGED_FILES}"

                    // Write commit metadata to a new file
                    def metadataFile = "commit_metadata.json"
                    def files = env.CHANGED_FILES ?: ""
                    writeFile file: metadataFile, text: """
{
  "commit_sha": "${env.GIT_COMMIT}",
  "branch": "${env.GIT_BRANCH}",
  "files_changed": "${files.replaceAll("\\n","\\\\n")}"
}
"""
                    echo "Created file: ${metadataFile}"
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
                script {
                    // You can optionally choose a random free port if you like
                    def port = 9000 + new Random().nextInt(1000)
                    echo "Running Spring Boot on port ${port}"

                    bat """
                    set COMMIT_SHA=${env.GIT_COMMIT}
                    java -jar target\\logtocode-0.0.1-SNAPSHOT.jar --server.port=${port}
                    """
                }
            }
        }
    }

    post {
        always {
            script {
                echo "Pipeline finished. commit_metadata.json is saved."
            }
        }
    }
}
