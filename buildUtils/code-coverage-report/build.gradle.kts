plugins {
    id("buildlogic.jacoco-aggregation-conventions")
}


dependencies {
    jacocoAggregation(project(":impexia-core"))
    jacocoAggregation(project(":impexia-jpa"))
    jacocoAggregation(project(":impexia-meta"))
    jacocoAggregation(project(":impexia-imp"))
    jacocoAggregation(project(":impexia-exp"))
    jacocoAggregation(project(":impexia-csv"))
    jacocoAggregation(project(":impexia-sql"))

}



