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

        stage('Clean') {
                steps {
                    cleanWs()
                }
            }

        stage('Checkout') {
            steps {
                checkout scm
//                 script {
//                     echo "Checking out branch: ${env.BRANCH_NAME}"
//                     git url: 'https://github.com/9601dani/Revista-AyD2-GrupoA.git', branch: env.BRANCH_NAME, credentialsId: 'e214b507-5b13-4651-96ac-433d7032b3f6'
//                 }
            }
        }

/*        stage('Build Frontend') {
             steps {
                 dir('app-frontend') {
                     // Install dependencies
                     sh 'npm install'

                     sh 'ls -l'

                     // Generate environment files
                     sh 'mkdir -p src/environments'

                     sh """
                       echo \"export const environment = {\n  production: true,\n  API_URL: '\${API_URL}'\n};\" > src/environments/environment.ts
                     """


                     // Build project
                     sh 'npm run build'

                     //Run unit test
                     sh 'npm run test'
                 }
             }
        } */

        stage('Build Backend') {
            steps {
                dir('app-backend') {
                    sh 'mvn clean package verify'
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

        stage("Deploy develop") {
            when {
                branch "develop"
            }
            steps {
                echo "Deploy app... "
                // sh 'touch test.txt'
                sh 'ls -l'
                sh "./deploy.sh develop"
                sshagent(credentials : ['jenkins-ssh']) {
                    sh 'ssh -o StrictHostKeyChecking=no $VM_USERNAME@$DEV_IP uptime'
                    sh 'ssh -v $VM_USERNAME@$DEV_IP'
                    sh 'scp -r deploy $VM_USERNAME@$DEV_IP:/home/$VM_USERNAME/'
                    sh 'ssh -o StrictHostKeyChecking=no $VM_USERNAME@$DEV_IP "bash /home/$VM_USERNAME/deploy/serve.sh"'
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
