job("Java maven app with DSL"){
    
    description("This app will be builded on kenkins with the help of DSL")

    //Where Jenkins will the code have?
    scm{
        git("https://github.com/macloujulian/simple-java-maven-app.git", "master") { node ->
            node / gitConfigName("Marco Antonio Ag")
            node / gitConfigEmail("marcontonio.98@hotmail.com")
        }
    }

    //The nature app and how will build and run it - Javen Maven
    steps{
        //Build it
        maven{
            mavenInstallation("maven-jenkins")
            goals("-B -DskipTests clean package")
        }
        //Test it
        maven{
            mavenInstallation("maven-jenkins")
            goals("test")
        }
        //Run it
        shell('''
            java -jar "/var/jenkins_home/workspace/Java maven app with DSL/target/my-app-1.0-SNAPSHOT.jar" 
        ''')
    }

    //What after the app will do?
    publishers{
        archiveArtifacts("target/*.jar")                //Take every .jar generated and make them avalaible in Jenkins
        archiveJunit("target/surefire-reports/*.xml")   //Take every .xml generated and make them avalaible in Jenkins
        //Notifiers (slack messages)
        mailer('marcontonio.98@hotmail.com', false, true)
        slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(true)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }

}