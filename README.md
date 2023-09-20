# fastmaj

A Richii Mahjong game core simulator for redevelop or AI training

本项目包含一个java实现的日式立直麻将的游戏核心，提供完整游戏流程，役种判定，向听计算等实现接口，可用于二次开发或者作为AI训练环境。该项目没有UI界面，采用单线程进行游戏模拟。

### 规划

* 0.8.x 会进行大范围的代码整理调整重构 `new!`

* 0.7.x 实现了大部分功能，并进行了测试 `2022-12-13`
* ...

项目咸暇缓慢开发中

### 支持

* 天凤规则下的所有役种判定
* 向听数计算
* 和了计算（符数，番数，分数）
* 一个单线程游戏执行流
* 天凤牌谱解析器
* 及其他一些小东西...

### 状态

当前代码经过3个天凤位的全牌谱测试，还有部分规则没有支持，如包牌逻辑，天和时的复合役满等，可用做参考。

### 代办

- [ ] 使用事件工厂类代替new，复用所有默认事件，减小创建对象的消耗
