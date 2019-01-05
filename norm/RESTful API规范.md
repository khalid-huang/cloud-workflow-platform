## 规范层面
+ 书写规范
+ 资源服务接口规范
+ 应用统一接口规范
+ 动作服务接口规范
+ 其他情况

## 例子场景
+ 下面的例子都基于如下的场景进行
    ![](http://7xncgn.com1.z0.glb.clouddn.com/17-12-7/98501260.jpg)
		
## 书写规范
+ 所有的URI都必须对应资源（名词）
+ 所有的模型名词统一使用复数
+ 当URL中某个片段由多个单词组成时，使用“-”来隔断单词，不使用"\_"，比如featured-post，而不是featured_post


## 资源服务接口规范（URI规范）

### 获取资源
+ 指定资源集合
	- /hotels
+ 指定具体某个资源
	- /hotels/656bcee2-28d2-404b-891b
+ 在资源表示中建立关联
	- /hotels/656bcee2-28d2-404b-891b/rooms/4
+ 定位子资源（被聚集的对象会被映射成子资源，比如Room是被Hotel聚集的）
	- /hotels/656bcee2-28d2-404b-891b/rooms/4
+ 

### 获取资源属性
+ 获取指定资源的指定属性
	-  /hotels/656bcee2-28d2-404b-891b?fields=classification
+ 获取指定资源的多个属性
	- /hotels/656bcee2-28d2-404b-891b?fields=classification, name


### 过滤资源信息
> + 过滤信息包含按属性过滤，也包含按数量过滤
> + limit 表示指定返回记录的数量
> + offset 表示返回记录的开始位置
> + page 表示指定第几页
> + per_page 表示每一页的记录数
> + 上述的几个参数都应该设置默认值，当客户端无传入相应的值时，使用默认值进行数据返回

+ 根据单个过滤条件进行过滤信息获取资源
	- /hotels?classification=Comfort 
+ 根据多个过滤条件进行过滤信息获取资源
	- /hotels?classification=Comfort&name=singleHotel
+ 过滤条件支持非等于操作
	- /hotels/656bcee2-28d2-404b-891b/rooms?price\<200
+  传入offset和limit
	- /hotels?offset=10&limit=5 (偏移为10,获取前5个的资源)
+ 传入offset
	- /hotels?offset=10 (以10为偏移量，获取默认个数的资源)
+ 传入limit
	- /hotes?limit=5 (使用默认的偏移量，获取前5个资源)
+ 传入page
	- /hotels?page=5（使用默认的per_page，返回第5页的内容）
+  传入per_page
	- /hotels?per_page=100 （以per_page为记录数返回默认的第page页）
	
	
### 按排序获取资源
> + sortby 表示按哪个属性进行排序
> + order 表示按什么顺序，值为asc表示升序，值为des表示降序

+ 按单个字段的升序排列获取资源列表 
	- /hotels/656bcee2-28d2-404b-891b/rooms?sortby=price&order=asc  
+ 按单个字段的降序排列获取资源列表
	- /hotels/656bcee2-28d2-404b-891b/rooms?sortby=price&order=des  


## 应用统一接口
> + PUT与POST必须区分使用，两者表示的意义是不同的，要区分幂等性; 
> + 状态码的返回必须准确
> + 所有的异常必须映射到error payload中，详见错误处理

### 典型用法
#### GET
+ 典型用法
	- 获取表示
	- 变更时获取表示
+ 典型状态码
	- 200（OK） - 表示已在响应中发出
	- 204（无内容） - 资源有空表示
	- 301（Moved Permanently） - 资源的URI已被更新
	- 303（See Other） - 其他（如，负载均衡）
	- 304（not modified）- 资源未更改（缓存）
	- 400 （bad request）- 指代坏请求（如，参数错误）
	- 404 （not found）- 资源不存在
	- 406 （not acceptable）- 服务端不支持所需表示
	- 500 （internal server error）- 通用错误响应
	- 503 （Service Unavailable）- 服务端当前无法处理请求
+ 是安全操作
+ 是幂等操作

#### DELETE
+ 典型用法
	- 删除资源
+ 典型状态码
	- 200 （OK）- 资源已被删除
	- 301 （Moved Permanently）- 资源的URI已更改
	- 303 （See Other）- 其他，如负载均衡
	- 400 （bad request）- 指代坏请求
	- 404 （not found）- 资源不补存在
	- 409 （conflict）- 通用冲突
	- 500 （internal server error）- 通用错误响应
	- 503 （Service Unavailable）- 服务端当前无法处理请求
+ 非安全操作
+ 是幂等操作

#### PUT
+ 典型用法
	- 用客户端管理的实例号创建一个资源
	- 通过替换的方式更新资源 (**但要求前端提供的一定是一个完整的资源对象**)
+ 典型状态码
	- 200 （OK）- 如果已存在资源被更改
	- 201 （created）- 如果新资源被创建
	- 301（Moved Permanently）- 资源的URI已更改
	- 303 （See Other）- 其他（如，负载均衡）
	- 400 （bad request）- 指代坏请求
	- 404 （not found）- 资源不存在
	- 406 （not acceptable）- 服务端不支持所需表示/p>
	- 409 （conflict）- 通用冲突
	- 412 （Precondition Failed）- 前置条件失败（如执行条件更新时的冲突）
	- 415 （unsupported media type）- 接受到的表示不受支持
	- 500 （internal server error）- 通用错误响应
	- 503 （Service Unavailable）- 服务当前无法处理请求
+ 非安全操作
+ 是幂等操作

#### PATCH
+ 典型用法
	- 通过替换的方式更新资源 (**用来更新局部资源，不要求传入整个资源属性**)
+ 典型状态码
	- 200 （OK）- 如果已存在资源被更改
	- 201 （created）- 如果新资源被创建
	- 301（Moved Permanently）- 资源的URI已更改
	- 303 （See Other）- 其他（如，负载均衡）
	- 400 （bad request）- 指代坏请求
	- 404 （not found）- 资源不存在
	- 406 （not acceptable）- 服务端不支持所需表示/p>
	- 409 （conflict）- 通用冲突
	- 412 （Precondition Failed）- 前置条件失败（如执行条件更新时的冲突）
	- 415 （unsupported media type）- 接受到的表示不受支持
	- 500 （internal server error）- 通用错误响应
	- 503 （Service Unavailable）- 服务当前无法处理请求
+ 非安全操作
+ 是幂等操作

#### POST
+ 典型用法
	- 使用服务端管理的（自动产生）的实例号创建资源
	- 创建子资源
	- 部分更新资源（非直接替换，比如加1操作）
+ 典型状态码
	- 200（OK）- 如果现有资源已被更改
	- 201（created）- 如果新资源被创建
	- 202（accepted）- 已接受处理请求但尚未完成（异步处理）
	- 301（Moved Permanently）- 资源的URI被更新
	- 303（See Other）- 其他（如，负载均衡）
	- 400（bad request）- 指代坏请求
	- 404 （not found）- 资源不存在
	- 406 （not acceptable）- 服务端不支持所需表示
	- 409 （conflict）- 通用冲突
	- 412 （Precondition Failed）- 前置条件失败（如执行条件更新时的冲突）
	- 415 （unsupported media type）- 接受到的表示不受支持
	- 500 （internal server error）- 通用错误响应
	- 503 （Service Unavailable）- 服务当前无法处理请求
+ 非安全操作
+ 非幂等操作

### 错误处理
> 所有的异常都应该被映射到error payloads中

```javascript
{
  "errors":  {
    "userMessage": "Sorry, the requested resource does not exist",
    "internalMessage": "No hotel found in the database",
    "code": 34
   }
}
```

### 资源的CRUD
+ 创建
	- 创建新资源
		* POST   /hotels
+ 获取
	- 获取资源集合
		* GET /hotels
	- 获取指定资源
		* GET /hotels/656bcee2-28d2-404b-891b
	- 根据过滤信息获取资源
		* GET /hotels/?classification=Comfort  #获取classification为Comfort的hotels集合 (多过滤条件使用&连接)
	- 获取资源的特定属性
		* GET  /hotels/656bcee2-28d2-404b-891b?field=classification #(多属性过滤使用逗号隔开)
	- 获取资源的多个特定属性
		*  GET  /hotels/656bcee2-28d2-404b-891b?field=classification,name
+ 更新
	- 以替换的方式更新已存在资源的信息
		*  PUT /hotels/656bcee2-28d2-404b-891b  #要求前端传入整个hotel信息
		*  PATCH /hotels/656bcee2-28d2-404b-891b  #可以传入hotel部分信息
+ 删除
	- 删除指定的资源
		* DELETE /hotels/656bcee2-28d2-404b-891b

## 动作服务接口规范
> 对于实在不适合抽象为资源的接口，按照动作服务接口设计
> 如下只列举出常用的动作服务接口

+ 帐户登录动作
	- POST  /login
	 ```javascipt
   body {
			 username: XXX,
			 password: XXX
	}
	 ```

+ 帐户注销动作
	- POST /logout
	```javascript
	body {
		
	}
	```
	
+ 帐户验证动作


## 其他情况
### 非资源请求
+ 非资源请求指API调用并不涉及资源，比如计算，翻译，转换
	- GET /calculate?para1=23&para2=423



#### 参考
+ https://segmentfault.com/a/1190000011516151
+ http://www.infoq.com/cn/articles/designing-restful-http-apps-roth
+ http://www.ruanyifeng.com/blog/2014/05/restful_api.html
+ https://www.cnblogs.com/xiaoyaojian/p/4612503.html
+ https://segmentfault.com/a/1190000008697972



