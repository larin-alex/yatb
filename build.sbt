organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo"         at "http://repo.spray.io/",
  "sonatype releases"  at "http://oss.sonatype.org/content/repositories/releases/",
  "sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/",
  "Artima Maven Repository" at "http://repo.artima.com/releases"
)

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  val sprayJSONV = "1.3.2"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-client"     % sprayV,
    "io.spray"            %%  "spray-http"     % sprayV,
    "io.spray"            %%  "spray-httpx"     % sprayV,
    "io.spray"            %%  "spray-util"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "io.spray"            %%  "spray-json"    % sprayJSONV,
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.json4s"          %%  "json4s-jackson" % "3.2.11",
    "javax.inject" % "javax.inject" % "1",
    "com.typesafe.slick" %% "slick" % "3.1.1",
    "com.google.inject" % "guice" % "4.0",
    "org.xerial"          %   "sqlite-jdbc"   % "3.7.2"
  )
}

//Revolver.settings
//seq(Revolver.settings: _*)
