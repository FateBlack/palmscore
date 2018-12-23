### 微信端登陆
```
POST /wx/people/login
```
参数
```
account 教工号
password
types   1评委   2选手

```
返回  评委 1 选手 2
```
{
     "code": 0,
     "msg": "成功",
     "data": {
         "groups": 3,
         "types" : 1              //1 评委 ，2选手
         "id" : 34               //选手或评委id
     }
 }
```

### 选手主页
```
GET /wx/player/index
```
参数
```
player_id 选手主键

```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "id": 1,                //活动主键
            "name": "教师大赛",
            "startTime": "2018-12-15",
            "endTime": "2018-12-28",
            "uploadTime": "2018-12-14",
            "state": 1                  //1:已上传 ，2:未上传，3:已结束 
        },
        {
            "id": 2,
            "name": "教师大赛B",
            "startTime": "2018-12-1",
            "endTime": "2018-12-3",
            "uploadTime": "2018-12-14",
            "state": 2
        }
    ]
}
```
### 评委主页
```
GET /wx/rater/index
```
参数
```
rater_id  评委主键id
```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "activity_password": 123123,      //活动实时评分密码
        "list": [
            {
                "id": 66,
                "name": "老李",
                "activityName": "教师大赛A",
                "order": 1
            },
            {
                "id": 77,
                "name": "老王",
                "activityName": "教师大赛B",
                "order": 2
            }
        ]
    }
}

```

### 选手文件目录
```
    POST /wx/player/file_page
```
参数 暂无
```

```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "items": [
            "ppt",
            "教案"
        ]
    }
}
```

### 选手文件上传
```
POST /wx/player/file_upload
```
参数
```
id

filepath[]

```
返回  
```
{
   "code": 0,
   "msg": "成功",
   "data":null
}

```
### 选手文件修改
```
POST /wx/player/file_update
```
参数
```
id

filepath[]

```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data":null
}

```
   


### 选手个人参赛信息
```
GET /wx/player/player_info
```
参数
```
id 选手主键 
```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "id": 2,
        "name": "风清扬",
        "workplace": "教研室",
        "course": "C语言",
        "state": 1        //1运行上传  2不允许
        "file_list": [
            "http://h.hiphotos.baidu.com/image/pic/item/902397dda144ad340668b847d4a20cf430ad851e.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea5c0e3c23d139b6003bf3b374.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg"
        ]
    }
}

```


### 评委评分列表
```
 GET /wx/rater/score_list
```
参数
```
rater_id 评委主键
groups  组别
```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "activity_password": 123123,
        "list": [
            {
                "id": 66,
                "name": "老李",
                "activityName": "教师大赛A",
                "order": 1,
                "score_state": 1   //评分状态: 1已评分，2未评分
            },
            {
                "id": 77,
                "name": "老王",
                "activityName": "教师大赛B",
                "order": 2,
                "score_state": 1
            }
        ]
    }
}
```


### 评委打分页面
```
 GET /wx/rater/mark_page
```
参数
```
无
```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "name": "教案",
            "rate": 0.7
            "fileUpload": 1  // 1:需要上传文件 ，2:不需要
        },
        {
            "name": "技术",
            "rate": 0.3
            "fileUpload": 2
        }
    ]
}
```

###评委打分按钮
```
POST /wx/people/mark
```
参数
```
playerId 选手主键
raterId  评委主键
score    总分

itemName ["教案","现场"]   评分项名称
scoreItem [66，88]    评分项分数

groups 组别

```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": null

}
```

### 结果 排名 界面
```
 GET /wx/people/rank
```
参数
```
groups 组别
```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "orders": 3,
            "name": "老牛头",
            "totalScore": 88.4,
            "rank": 1
        },
        {
            "orders": 6,
            "name": "八百",
            "totalScore": 77.9,
            "rank": 2
        }
    ]
}
```
###修改密码
```
POST /wx/people/password_edit
```
参数
```
types （修改类型 1评委 2选手） 
id（选手或评委id）
password（原密码）
rePassword（修改密码）
```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": null

}
{
    "code": 510,
    "msg": "原密码输入不正确",
    "data": null
}
```

### 评委打分情况 对应选手 情况
```
GET /wx/people/score_info
```
参数
```
player_id 
rater_id
```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "id": 4,
            "playerId": 60,
            "score": 11,
            "itemName": "教案"
        },
        {
            "id": 5,
            "playerId": 60,
            "score": 11,
            "itemName": "演讲"
        }
    ]
}
```

### 选手打分情况
```
GET /wx/player/score_info
```
参数
```
player_id 

```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "id": 4,
            "playerId": 60,
            "score": 11,
            "itemName": "教案"
        },
        {
            "id": 5,
            "playerId": 60,
            "score": 11,
            "itemName": "演讲"
        }
    ]
}
```