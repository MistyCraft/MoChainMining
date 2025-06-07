# MoChainMining

一个基于权限的轻量好用的连锁挖矿插件！

## 插件介绍

MoChainMining是一个轻量好用的连锁挖矿插件

文档地址：[OpenMoPlugins](https://openmo.molab.top/bukkit/moChainMining/)

 - 基于权限控制玩家能否连锁挖矿
 - 命令一键启动连锁/关闭连锁
 - 玩家失去连锁挖矿权限时，插件会自动关闭他的连锁挖矿
 - 异步的方块连锁算法

## 安装

1. 下载插件
2. 将jar文件放入plugins文件夹中
3. 重启服务器

## 指令列表

玩家指令需要有`MoChainMining.command`权限

插件重载命令需要有`MoChainMining.command.admin`权限

支持的指令：`/chain-mine` `/chainmine` `/cm`

| 命令         | 用处        | 权限                          |
|------------|-----------|-----------------------------|
| /cm        | 打开/关闭连锁挖矿 | MoChainMining.command       |
| /cm toggle | 切换连锁挖矿状态  | MoChainMining.command       |
| /cm on     | 启用连锁挖矿    | MoChainMining.command       |
| /cm off    | 关闭连锁挖矿    | MoChainMining.command       |
| /cm reload | 重载插件      | MoChainMining.command.admin |

## 其他

如果你有建议/Bug反馈，可以在[Github Issue](https://github.com/moran0710/MoChainMining/issues)反馈

反馈前请先查看是否有人提出了相同问题
