plugins {
    base
    id("jacoco-report-aggregation")

}

repositories {
    mavenCentral()
}


reporting {
    reports {
        val testCodeCoverageReport by creating(JacocoCoverageReport::class) { // <.>
            testType.set(TestSuiteType.UNIT_TEST)
        }
    }
}

tasks.check {
    dependsOn(tasks.named<JacocoReport>("testCodeCoverageReport")) // <.>
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(
                files(
                        classDirectories.files.map {
                            fileTree(it).apply {
                                exclude(listOf("org/anasoid/impexia/csv/opencsv/**","**/generated/**" ))
                            }
                        }
                )
        )
    }
}