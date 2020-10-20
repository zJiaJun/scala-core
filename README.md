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
    1. <a href="https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/IndexExample.scala" target="_blank">IndexExample</a><br>
    2. <a href="https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/ConfigExample.scala" target="_blank">ConfigExample</a><br>
    3. <a href="https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/ConnectionPoolExample.scala" target="_blank">ConnectionPoolExample</a><br>
    4. <a href="https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/OperationExample.scala" target="_blank">OperationExample</a><br>
    5. <a href="https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/TransactionExample.scala" target="_blank">TransactionExample</a><br>
    6. <a href="https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/AutoSessionExample.scala" target="_blank">AutoSessionExample</a><br>
    7. <a href="https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/SQLInterpolationExample.scala" target="_blank">SQLInterpolationExample</a><br>
    8. <a href="https://github.com/zjiajun/scala-core/blob/master/scalike/src/main/scala/com/github/zjiajun/scalike/QueryDSLExample.scala" target="_blank">QueryDSLExample</a><br>
  </details>