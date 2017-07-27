# hotfix
simple hotfix framework.
## 工程结构
### app
主工程，包含着待修复的代码；
### buildSrc
groovy 工程，依赖 ``javaassit`` 进行动态注入 hotfixstub 工程代码到宿主工程；
### hotfixlib
hotfix 的核心代码，主要类为 ``HotFix.java``；
### hotfixstub
插桩库，依赖 gradle 对宿主工程进行字节码层级的代码注入，防止类在 ``dexopt`` 时被打上 ``pre-verified`` 标志，绕过 ``dvmResolveClass`` 的校验；

## 注意事项
- ``/gradle/wrapper/gradle-wrapper.properties`` 中必须配置 ``distributionUrl`` 字段为 ``https\://services.gradle.org/distributions/gradle-2.2-all.zip``；
- 顶级的 ``build.gradle`` 必须制定 ``gradle`` 版本为 ``classpath 'com.android.tools.build:gradle:1.3.0'``；

## 小结
- Dalvik（一般 API Level 21 下）才会有 ``pre-verify`` 标志，才需要插桩操作来绕过校验，Art 下并没有这个 ``pre-verify`` 操作，也意味着可以不用进行插桩操作；
