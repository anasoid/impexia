version = "0.0.1-SNAPSHOT"


plugins {
    alias(libs.plugins.sonar)
}

sonar {
    properties {
        property("sonar.projectKey", "anasoid_impexia")
        property("sonar.organization", "anasoid-org")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.exclusions", "**/generated/**, **.gradle.kts, com/opencsv/**")
        property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "${rootDir}/buildUtils/code-coverage-report/build/reports/jacoco/testCodeCoverageReport/testCodeCoverageReport.xml"
        )
    }
}
allprojects {
    group = "org.anasoid.jmeter.cloud"
    version = rootProject.version
}
