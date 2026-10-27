pipeline {
    agent any
    options { timestamps(); disableConcurrentBuilds() }
    triggers { pollSCM('H/5 * * * *') }
    environment {
        STUDENT = 'Daulet Bazarali'
        GROUP_NAME = 'IT2-2308'
        STUDENT_ID = '37392'
        IMAGE_NAME = 'bazarali-daulet-student-api'
        IMAGE_TAG = 'v1.0-37392'
	JAVA_HOME = '/Users/nurasylzapsarbek/Library/Java/JavaVirtualMachines/ms-21.0.10/Contents/Home'
        PATH = "/Users/nurasylzapsarbek/Library/Java/JavaVirtualMachines/ms-21.0.10/Contents/Home/bin:/opt/homebrew/bin:/usr/local/bin:/Users/nurasylzapsarbek/.docker/bin:${env.PATH}"
    }
    stages {
        stage('Checkout') {
            steps { checkout scm; sh 'git log -1 --oneline' }
        }
        stage('Build') {
            steps { sh 'chmod +x gradlew && ./gradlew --no-daemon clean bootJar -x test' }
        }
        stage('Test') {
            steps { sh './gradlew --no-daemon test' }
        }
        stage('Docker Build') {
            steps { sh 'docker build -t "$IMAGE_NAME:$IMAGE_TAG" .' }
        }
        stage('Verify') {
            steps {
                sh 'docker image inspect "$IMAGE_NAME:$IMAGE_TAG" --format "IMAGE VERIFIED: {{.RepoTags}}"'
                echo "Student: ${env.STUDENT} | ${env.GROUP_NAME} | ${env.STUDENT_ID}"
            }
        }
    }
    post {
        success { echo 'BUILD SUCCESS: Bazarali Daulet Student API' }
        failure { echo 'BUILD FAILURE: Inspect the first failed stage in Console Output' }
    }
}
