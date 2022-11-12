# app-chat-server-launcher

## 概述

提供专属的聊天服务器。

仅提供在线聊天、离线保存服务，不提供消息拉取等功能（该功能直接利用web接口实现）。

## 协议

### 协议格式

| 魔数     | 版本号    | 消息类型   | 请求序号  | 消息长度  | 消息内容  |
|--------|--------|--------|-------|-------|-------|
| 0xAC66 | 1byte  | 1byte  | 2byte | 4byte | ?byte |

魔数(~~刷算法的后遗症2333~~)占俩字节


#### 消息类型

所有协议中的内容，最后都会被转换为`pers.xds.wtuapp.chat.message.Message`的实现类，
每一条消息都用于用一个唯一的消息类型代替

| 类型id |    消息名称     |              实现类               |        备注         |
|:----:|:-----------:|:------------------------------:|:-----------------:|
|  0   |   登录请求消息    |      `AuthRequestMessage`      |                   |
|  1   |   聊天请求消息    |      `ChatRequestMessage`      |                   |
|  2   | 服务器返回请求成功响应 | `ServerSuccessResponseMessage` |                   |
|  3   |  服务器返回失败响应  |  `ServerFailResponseMessage`   |                   |
|  4   |   聊天请求响应    |     `ChatResponseMessage`      | 该消息为主动推送给在线客户端的消息 |
|  5   |  同步在线消息请求   |      `SyncRequestMessage`      |                   |
|  6   |   多组消息响应    |   `MultiChatResponseMessage`   |                   |
|  7   |  获取消息状态消息   |  `QueryReceiveStatusMessage`   |                   |
|  8   |   状态同步消息    |      `SyncRequestMessage`      | 返回当前已经接收到的最大消息id  |


实现类在`pers.xds.wtuapp.chat.message`包下

### 协议解析过程

为了节省空间，在消息传输的时候使用了`Google`的`protobuf`。但由于生成`protobuf`的类很大，
有些只有一个属性的消息就不编码，直接传输过去。

#### 消息解码管理类
`pers.xds.wtuapp.chat.message.common.MessageDecoderManager`类专门用于管理消息的解析。

消息在这里需要先静态注册一个解析器，之后消息在根据消息类型，找到对应的解析器来进行解码。有些消息是单向的，并不会去注册解析器。

