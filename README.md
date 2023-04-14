# [VSE-Back-Manage-System](https://github.com/PrettyMagnolia/VSE-Back-Manage-System)

虚拟仿真实验平台后端

## 快速开始

- 仓库地址：https://github.com/PrettyMagnolia/VSE-Back-Manage-System-Back
- API测试：http://139.196.226.104:8001/api/swagger-ui.html

## OSS云存储

[`OssService`](./src/main/java/com/backend/vse/service/OssService.java)
内两个函数，`String uploadFile(MultipartFile file);`用于存储文件，`String uploadLongText(String content,String key);`
用于存储文本字符串

## 注意事项

- 时间格式先统一为 `yyyy-[m]m-[d]d hh:mm:ss[.f...].`
- 时间类型为`Timestamp`

