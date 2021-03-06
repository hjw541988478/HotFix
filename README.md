# hotfix
简单支持代码热修复的框架。
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
- 顶级的 ``build.gradle`` 必须指定 ``gradle`` 版本为 ``classpath 'com.android.tools.build:gradle:1.3.0'``，是为了在宿主工程中 ``gradle`` 的 ``dex`` 方法可用；

## 小结
- Dalvik（一般 API Level 21 下）才会有 ``pre-verify`` 标志，才需要插桩操作来绕过校验，Art 下并没有这个 ``pre-verify`` 操作，也意味着可以不用进行插桩操作。

## 博客
1. [代码热修复探究(Part 1)](http://huangjiawen.cn/android/%E4%BB%A3%E7%A0%81%E7%83%AD%E4%BF%AE%E5%A4%8D%E6%8E%A2%E7%A9%B6(Part%201)/)
2. [资源热修复探究(Part 2)](http://huangjiawen.cn/android/%E8%B5%84%E6%BA%90%E7%83%AD%E4%BF%AE%E5%A4%8D%E6%8E%A2%E7%A9%B6(Part%202)/)
3. [SO 库修复探究(Part 3)](http://huangjiawen.cn/android/SO%E5%BA%93%E7%83%AD%E4%BF%AE%E5%A4%8D%E6%8E%A2%E7%A9%B6(Part%203)/)
