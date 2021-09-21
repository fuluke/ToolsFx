group = "me.leon.toolsfx"
version = "1.0.0"

plugins {
    `java-library`
}
val tornadofx_version: String by rootProject
javafx {
    //latest version https://mvnrepository.com/artifact/org.openjfx/javafx-controls
    version = "17"
    modules = listOf(
        "javafx.controls",
        "javafx.swing",
        "javafx.web",
//            if you use javafx.fxml,then uncomment it
//            'javafx.fxml'
    )
}

dependencies {
    api("no.tornado:tornadofx:$tornadofx_version")
}