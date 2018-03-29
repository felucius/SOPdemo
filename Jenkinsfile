def CONTAINER_NAME="SOP6demo"
def CONTAINER_TAG="latest"
def buildInfo
def server = Artifactory.server "artifactoryID"
def rtMaven = Artifactory.newMavenBuild()

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

    stage ('Artifactory configuration') {
        rtMaven.tool = "Maven3" // Tool name from Jenkins configuration
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server
        buildInfo = Artifactory.newBuildInfo()
        buildInfo.env.capture = true
        rtMaven.run pom: 'pom.xml', goals: 'clean install', buildInfo: buildInfo
        server.publishBuildInfo buildInfo
    }

    stage('Docker-compose --> .war file'){
        try {
            sh "sudo -S docker-compose down"
             sh "sudo -S docker-compose rm -f"
        }catch(error){}
        try {
            sh "sudo -S docker-compose up -d"
        }catch(error){}
    }
}
