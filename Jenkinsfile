pipeline{
    agent any
    tools {
        maven 'Maven Apache'
    }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PROFILES = 'tests'
    }

    stages {

        stage('Checkout') {
            steps {
                script {
                    echo "Checking out branch: ${env.BRANCH_NAME}"
                    git url: 'https://github.com/9601dani/Revista-AyD2-GrupoA.git', branch: env.BRANCH_NAME, credentialsId: 'github-pat-global'
                }
            }
        }

        stage('Test Frontend') {
        steps {
            dir('app-frontend') {
                sh 'rm -rf node_modules && npm install'
                sh 'ng test --watch=false --browsers=ChromeHeadless --no-sandbox'
            }
        }
    }

        stage("Deploy"){
            steps {
                echo "Deploy app... "
            }
        }

        stage('Verify Jacoco Exec') {
            steps {
                dir('app-backend/report/target') {
                    sh 'ls -l'
                }
            }
        }

    }
    post {
        success {
            script {
                jacoco (
                    execPattern: '**/target/*.exec',
                    classPattern: '**/target/classes',
                    sourcePattern: '**/src/main/java',
                    exclusionPattern: '**/target/test-classes',
                    changeBuildStatus: true,
                    minimumLineCoverage: '85'
                )

            }
            echo 'Backend build completed successfully!'
        }
        failure {
            echo 'Backend build failed.'
        }
    }
}
