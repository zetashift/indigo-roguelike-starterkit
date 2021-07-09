import scala.sys.process._
import scala.language.postfixOps

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val roguescave =
  (project in file("."))
    .enablePlugins(ScalaJSPlugin, SbtIndigo)
    .settings(
      name := "roguescave",
      version := "0.0.1",
      scalaVersion := "3.0.0",
      organization := "roguescave",
      libraryDependencies ++= Seq(
        "org.scalameta" %%% "munit" % "0.7.26" % Test
      ),
      testFrameworks += new TestFramework("munit.Framework"),
      Test / scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
      showCursor := true,
      title := "A Roguelike in Indigo Adventure!",
      gameAssetsDirectory := "assets",
      windowStartWidth := 550,
      windowStartHeight := 400,
      libraryDependencies ++= Seq(
        "io.indigoengine" %%% "indigo-json-circe" % "0.9.0",
        "io.indigoengine" %%% "indigo"            % "0.9.0",
        "io.indigoengine" %%% "indigo-extras"     % "0.9.0"
      )
      // scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) } // required for parcel, but will break indigoRun & indigoBuild
    )
    .settings(
      Compile / sourceGenerators += Def.task {
        TileCharGen
          .gen(
            "DfTiles", // Class/module name.
            "roguescave", // fully qualified package name
            (Compile / sourceManaged).value, // Managed sources (output) directory for the generated classes
            16, // Character width
            16  // Character height
          )
      }.taskValue
    )
    .settings(
      code := { "code ." ! }
    )

// To use indigoBuild or indigoRun, first comment out the line above that says: `scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }`
addCommandAlias("runGame", ";compile;fastOptJS;indigoRun")
addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuild")

lazy val code =
  taskKey[Unit]("Launch VSCode in the current directory")
