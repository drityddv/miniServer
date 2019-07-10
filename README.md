# miniServer
小型游戏服务器项目

# 数据库启动条件
*   mysql
*   用户名:root
*   密码:root
*   port:3306
*   hibernate拥有db操作权限
*   数据库名:miniServer

# 服务器启动
*   直接启动Start文件的main函数 [port:8000]

# 静态资源文件注意点
*   目前只支持csv,编码utf-8

# 客户端指令
*   查看message.xml表中的资源,key为发包动作 例如[登陆]:[login ddv ddv]然后enter
*   必须进行登陆才能操作[注册例外,但是注册完也要登陆]
*   gm指令可以直接查看GM_Command.java文件
*   服务端报错目前只通过i18n序列号通知,客户端不做翻译 game.common.i18n可以查看序列号含义
