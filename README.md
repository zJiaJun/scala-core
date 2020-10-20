# Scala ecosystem

## 环境
  * java 1.8
  * scala 2.12.12
  * gradle 6.5
  * scalikejdbc 3.5.0
  
## 使用

  ```
  1. git clone git@github.com:zJiaJun/scala-core.git
  2. cd scala-core
  3. ./gradlew build
  ```
  
## checkStyle
   * 使用[scalafmt](https://scalameta.org/scalafmt/)和[spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle#scalafmt)统一编码风格
   
   * <details><summary>setup with gradle plugin</summary>
   
        ```
        buildscript {
            dependencies {
                classpath "com.diffplug.spotless:spotless-plugin-gradle:$versions.spotlessPlugin"
            }
        }
        
        apply plugin: "com.diffplug.gradle.spotless"
        spotless {
            scala {
                target '**/*.scala'
                scalafmt("$versions.scalafmt").configFile('checkstyle/.scalafmt.conf')
            }
        }
        ```
        
        ```
        spotlessPlugin.version = 3.28.1
        scalafmt.version = 1.5.1
        具体请看build.gradle和dependencies.gradle
        ```
  </details>

    
## core
  * 一些学习和研究scala过程中的例子
  
## scalike
  * A tidy SQL-based DB access library for Scala developers
  * <details><summary>官方文档例子学习</summary>
    1. [IndexExample](https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/IndexExample.scala)
    2. [ConfigExample](https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/ConfigExample.scala)
    3. [ConnectionPoolExample](https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/ConnectionPoolExample.scala)
    4. [OperationExample](https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/OperationExample.scala)
    5. [TransactionExample](https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/TransactionExample.scala)
    6. [AutoSessionExample](https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/AutoSessionExample.scala)
    7. [SQLInterpolationExample](https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/SQLInterpolationExample.scala)
    8. [QueryDSLExample](https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/QueryDSLExample.scala)
  </details>