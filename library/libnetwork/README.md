# network

网络层基础封装，地测是使用okhttp进行封装，后期可以更换网络库，只要符合上层的标准就可以，使用方法见wiki文档。

## 当前版本
版本 | 作者 | 日期 
---|---|---
1.0.0 | 杨飞 | 2021.03.03

## 使用教程

### 集成方式
 该组件支持使用maven自动集成，使用tiandy-config来引用。
 1. 在build gradle 中增加参数
  ```
  rootProject.ext.set("LIB_NETWORK", DEVELOP ? getLatestVersion(GROUP_ID, LIB_NETWORK_ARTIFACTID,''):getReleaseVersion(GROUP_ID, LIB_NETWORK_ARTIFACTID,versionProperties.LIB_NETWORK_VERSION))

 ```
  2. 整体集成组件
  ```
    implementation "${LIB_NETWORK}"
 ```
 3. 单独使用其中的某个组件
 ```
  <moudle name="network" version="1.0.0" />
```
 ### 使用方法
1. 引入所需头文件
```
import com.xx.network.HttpUtil;
```
2. 调用接口
 ```
 //示例
  HttpUtil.post("http://www.jd.com")
    .tag(context)
    .headers(internalHeaders)
    .setBodyString("application/json;charset=UTF-8", input)
    .enqueue(callBack);}
```
 
## 更新记录
### 2021-03-04 规范readme文档
### 2021-03-03  创建工程
组件名称 | 描述
  

