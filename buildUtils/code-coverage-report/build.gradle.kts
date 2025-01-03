plugins {
    id("codecontrol.buildlogic.jacoco-aggregation-conventions")
}


dependencies {
    jacocoAggregation(project(":impexia-core"))
    jacocoAggregation(project(":impexia-jpa"))
    jacocoAggregation(project(":impexia-meta"))
    jacocoAggregation(project(":impexia-importing"))
    jacocoAggregation(project(":impexia-exporting"))
    jacocoAggregation(project(":impexia-csv"))
    jacocoAggregation(project(":impexia-sql"))

}



