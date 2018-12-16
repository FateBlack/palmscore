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
        "type" : 1  
    }
}
```

### 选手主页
```
GET /wx/player/index
```
参数
```


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

```
返回  
```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "id": 1,                 //选手主键
            "name": "老李",
            "activityName": "教师大赛A",
            "score": 66
        },
        {
            "id": 2,
            "name": "老王",
            "activityName": "教师大赛B",
            "score": 0
        },
        {
            "id": 3,
            "name": "老赵",
            "activityName": "教师大赛C",
            "score": 22
        }
    ]
}

```

### 选手文件上传
```
POST /wx/player/file_upload
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
    "data": [
        {
            "id": 1,                 //选手主键
            "name": "老李",
            "activityName": "教师大赛A",
            "score": 66
        },
        {
            "id": 2,
            "name": "老王",
            "activityName": "教师大赛B",
            "score": 0
        },
        {
            "id": 3,
            "name": "老赵",
            "activityName": "教师大赛C",
            "score": 22
        }
    ]
}

```