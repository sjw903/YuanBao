# datacenter

数据中心库，包括本地读写框架，网络框架，数据加解密，网络框架模版等。

## 当前版本
版本 | 作者 | 日期 
---|---|---
1.0.0 | 杨飞 | 2021.03.03

## 使用教程

### 集成方式
 该组件支持使用maven自动集成，使用tiandy-config来引用。
 1. 在build gradle 中增加参数
  ```
rootProject.ext.set("LIB_DATACENTER", DEVELOP ? getLatestVersion(GROUP_ID, LIB_DATACENTER_ARTIFACTID,''):getReleaseVersion(GROUP_ID, LIB_DATACENTER_ARTIFACTID,versionProperties.LIB_DATACENTER_VERSION))

 ```
  2. 整体集成组件
  ```
    implementation "${LIB_DATACENTER}"
 ```
 3. 单独使用其中的某个组件
 ```
  <moudle name="datacenter" version="1.0.0" />
```
 ### 使用方法
1. 引入所需头文件
```
import com.tiandy.datacenter.;
```
2. 调用接口
 ```


1.  建一个请求类  

例子：public interface IloginRequst{}



2. 接口方法名自己定义

@Request(url = "url",method = MethodDef.POST, mediaType = MediaDef.BODYSTRING)
@Response(parse = ParseDef.STRING)
void login(@Input LoginReq input, @Output @Nullable String output, @DynamicHeader Map<String, String> dynamicHeader);

3.  参数

请求头：@DynamicHeader 传入一个map

入参：@Input  必须封装成一个bean

出参：@Output 出参需要对应 @Response parse的类型使用。 字符串的话@Output是传入String；Model的话就是一个bean


请求类型：@Request
                    method请求方法 (post/get等） 毕传 

                    mediaType 参数类型 （body/表单等）毕传

                     url 请求路径，可以不赋值 ，如果不赋值则改为方法入参里面传入

返回值类型 : @Response 字符串ParseDef.STRING , 类ParseDef.MODEL， 其他JSONARRAY，JSONOBJECT

请求地址：

               方式一、@BaseUrl 全局的地址，如果赋值，作用域是此请求类中所有请求地址都用这个。
               方式二、在@Request里面赋值，作用域是此接口方法。
               方式三、不赋值url，在impl实现类里面使用参数传入

注意：参数名尽量使用input，output等标准名称


第二步：

编译，在/build/generated/ap_generated_sources/debug/out文件夹下生成java文件，文件名= 请求类名+Impl

第三步：

调用

XXRequstImpl.xx 方法名 ，按照生成的方法，依次传入参数
例子：
loginRequstImpl.login(context, input, listener, headers);
```

## 更新记录
### 2021-03-04 规范readme文档
### 2020  创建工程
组件名称 | 描述



