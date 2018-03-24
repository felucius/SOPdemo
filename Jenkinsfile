def CONTAINER_NAME="SOP6demo"
def CONTAINER_TAG="latest"

node {
    git url: 'https://github.com/felucius/SOPdemo.git'

    stage('Initialize'){
        def dockerHome = tool 'Docker'
        def mavenHome  = tool 'Maven3'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
    }

    stage('Checkout') {
        checkout scm
    }

    stage('Build'){
        sh "mvn clean install"
    }

    stage('Sonar'){
        try {
            sh "mvn sonar:sonar -Dsonar.host.url=localhost:9000/sonar"
        } catch(error){
            echo "The sonar server could not be reached ${error}"
        }
    }

    stage('Docker-compose'){
        try {
            sh "sudo docker-compose down"
        }catch(error){}
        try {
            sh "sudo docker-compose up -d"
        }catch(error){}
    }
}
