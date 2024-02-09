# -*- coding: utf-8 -*-
import io
import sys
import os
import json
from docxtpl import DocxTemplate
import oss2
import time

import yaml

sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')


def fill_template(template_docx, out_filename, data):
    # data
    # print("填充开始")
    tpl = DocxTemplate(template_docx)
    tpl.render(context=data)
    tpl.save(out_filename)
    # print("填充完成")
    return 0


def upload_to_oss(out_final_path):
    # print("上传到阿里云oss开始")
    # 加载配置文件
    config_path = os.path.join(os.path.abspath('.'),
                                       'src/main/resources',
                                       'application.yml')
    with open(config_path, 'r', encoding='utf-8') as f:
        result = yaml.load(f.read(), Loader=yaml.FullLoader)
    access_key_id = os.getenv('OSS_TEST_ACCESS_KEY_ID', result['aliyun']['oss']['accessKeyId'])
    access_key_secret = os.getenv(
        'OSS_TEST_ACCESS_KEY_SECRET', result['aliyun']['oss']['accessKeySecret'])
    bucket_name = os.getenv('OSS_TEST_BUCKET', result['aliyun']['oss']['bucketName'])
    endpoint = os.getenv('OSS_TEST_ENDPOINT',  result['aliyun']['oss']['endpoint'])
    for param in (access_key_id, access_key_secret, bucket_name, endpoint):
        assert '<' not in param, '请设置参数：' + param
    # 初始化bucket
    bucket = oss2.Bucket(oss2.Auth(access_key_id, access_key_secret), endpoint, bucket_name)
    key = out_final_path
    result = bucket.put_object_from_file(key, out_final_path)
    return result.status
    # 下载到本地验证，仅供测试~2024/2/1测试通过
    # result = bucket.get_object_to_file(key,os.path.abspath('.'),
    #                                    'src/main/resources',
    #                                    'out.docx')
    # result = bucket.get_object_to_file(key,'out.docx')
    # print(result.status)


if __name__ == '__main__':
    expData = sys.argv[1]
    # print(expData)
    expDict = json.loads(expData)
    # print(expDict)
    template_docx = f'{expDict["experiment_id"]}-template.docx'
    timeArray = time.localtime(expDict['submit_time']/1000)
    localTime = time.strftime("%y%m%d", timeArray)
    out_filename = f'{expDict["experiment_id"]}-{expDict["reporter_name"]}-{expDict["reporter_id"]}-{localTime}.docx'
    timeArray = time.localtime(expDict['submit_time']/1000)
    localTime = time.strftime("%Y-%m-%d", timeArray)
    expDict['submit_time'] = localTime

    # print('模板：'+template_docx)
    # print('生成名称：'+out_filename)
    # print(expDict)
    # print(os.path.join(os.path.abspath('.'),
    #                    'src/main/resources',
    #                    template_docx))
    # print(os.path.join(os.path.abspath('.'),
    #                    'src/main/resources',
    #                    out_filename))
    template_final_path = os.path.join(os.path.abspath('.'),
                                       'src/main/resources',
                                       template_docx)
    out_final_path = os.path.join(os.path.abspath('.'),
                                  'src/main/resources',
                                  out_filename)

    fill_template(template_final_path,
                  out_final_path,
                  expDict)

    status_code = upload_to_oss(out_final_path)

    print(status_code)
