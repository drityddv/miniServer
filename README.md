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
*   客户端直接启动Client文件的main函数

# 客户端指令
*   指令选项通过数字[1,2,3...]+[enter]选择
*   命令参数都通过换行输入,例如登陆先输入账号enter确认,再输入密码enter确认.
*   地图现在只有两张地图 1:新手村 2:暴风城
*   必须进行登陆才能操作[注册例外,但是注册完也要登陆]
*   gm指令可以直接查看GM_Command.java文件
*   服务端报错目前只通过i18n序列号通知,客户端不做翻译 game.common.i18n可以查看序列号含义
*   客户端指令步骤图在doc文件夹中

#   指定步骤示例
*   注册
*   ![Image text](http://pr6emg3up.bkt.clouddn.com/register.png)
*   登陆
*   ![Image text](http://pr6emg3up.bkt.clouddn.com/login.png)
*   进入地图
*   ![Image text](http://pr6emg3up.bkt.clouddn.com/enterMap.png)
*   地图内移动
*   ![Image text](http://pr6emg3up.bkt.clouddn.com/moveMap.png)
*   gmCommand
*   ![Image text](http://pr6emg3up.bkt.clouddn.com/gmCommand.png)
