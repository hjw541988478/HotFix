# hotfix
simple hotfix framework.
## app
主工程，包含着待修复的代码；
## buildSrc
groovy 工程，依赖 ``javaassit`` 进行动态注入 hotfixstub 工程代码到宿主工程；
## hotfixlib
hotfix 的核心代码，主要类为 ``HotFix.java``；
## hotfixstub
插桩库，依赖 gradle 对宿主工程进行字节码层级的代码注入，防止类在 ``dexopt`` 时被打上 ``pre-verified`` 标志，绕过 ``dvmResolveClass`` 的校验；
