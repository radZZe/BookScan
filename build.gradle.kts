plugins{
    id(Dependencies.hiltAgp) version Versions.hilt apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

//    dependencies {
//        classpath(Dependencies.hiltAgp)
//    }
}