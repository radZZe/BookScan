plugins{
    id(Dependencies.hiltAgp) version Versions.hilt apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }

//    dependencies {
//        classpath(Dependencies.hiltAgp)
//    }
}