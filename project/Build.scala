import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "bSmileApi"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
         "com.google.code.gson" % "gson" % "2.2.2",
      	 "mysql" % "mysql-connector-java" % "5.1.21"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
