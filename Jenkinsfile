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
            sh "mvn sonar:sonar"
        } catch(error){
            echo "The sonar server could not be reached ${error}"
        }
    }

    def server = Artifactory.newServer url: SERVER_URL, credentialsId: CREDENTIALS
    def rtMaven = Artifactory.newMavenBuild()
    def buildInfo

    stage ('Clone') {
        git url: 'https://github.com/felucius/SOPdemo.git'
    }

    stage ('Artifactory configuration') {
        rtMaven.tool = MAVEN_TOOL // Tool name from Jenkins configuration
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server
        buildInfo = Artifactory.newBuildInfo()
    }

    stage ('Exec Maven') {
        rtMaven.run pom: 'SOPdemo/pom.xml', goals: 'clean install', buildInfo: buildInfo
    }

    stage ('Publish build info') {
        server.publishBuildInfo buildInfo
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
