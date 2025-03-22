pipeline{
    agent any
    tools {
        maven 'Maven Apache'
        nodejs 'NodeJs'
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

//         stage('Build Frontend') {
//              steps {
//                  dir('app-frontend') {
//                      // Install dependencies
//                      sh 'npm install'
//
//                      // Build project
//                      sh 'npm run build -- --configuration=production'
//
//                      //Run unit test
//                      // sh 'npm test -- --watch=false --browsers=ChromeHeadless'
//                  }
//              }
//         }

        stage('Build Backend') {
            steps {
                dir('app-backend') {
                    sh 'mvn clean compile verify'
                }
            }
        }

        stage('Verify Jacoco Exec') {
            steps {
                dir('app-backend/report/target') {
                    sh 'ls -l'
                }
            }
        }

        stage("Deploy"){
            steps {
                echo "Deploy app... "
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

                githubNotify(context: 'CI/CD', status: 'SUCCESS')
            }
            echo 'Backend build completed successfully!'
        }
        failure {
            script {
                githubNotify(context: 'CI/CD', status: 'FAILURE')
            }
            echo 'Backend build failed.'
        }
    }
}
