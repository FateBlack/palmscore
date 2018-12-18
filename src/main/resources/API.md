### 微信端登陆
```
POST /wx/people/login
```
参数
```
account 教工号
password

```
返回  评委 1 选手 2
```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "type" : 1              //1 评委 ，2选手
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
            "file_upload": 1
        },
        {
            "id": 2,
            "name": "教师大赛B",
            "startTime": "2018-12-1",
            "endTime": "2018-12-3",
            "uploadTime": "2018-12-14",
            "file_upload": 2
        },
        {
            "id": 3,
            "name": "教师大赛C",
            "startTime": "2018-12-15",
            "endTime": "2018-12-16",
            "uploadTime": "2018-12-14",
            "file_upload": 2
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
            },
            {
                "id": 89,
                "name": "老赵",
                "activityName": "教师大赛C",
                "order": 3
            }
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
id 选手主键  filepath 教案路径
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
            },
            {
                "id": 12,
                "name": "飞飞",
                "activityName": "教师大赛C",
                "order": 3,
                "score_state": 2
            },
            {
                "id": 45,
                "name": "白",
                "activityName": "教师大赛C",
                "order": 4,
                "score_state": 2
            },
            {
                "id": 89,
                "name": "老赵",
                "activityName": "教师大赛C",
                "order": 5,
                "score_state": 1
            }
        ]
    }
}
```