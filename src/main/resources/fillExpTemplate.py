# -*- coding: utf-8 -*-
import io
import sys
import os
import json
import docxtpl
from docxtpl import DocxTemplate
import oss2
import time
import requests
from docx.shared import Mm

sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

def upload_to_oss(out_final_path:str):
    access_key_id = os.getenv('OSS_TEST_ACCESS_KEY_ID', 'LTAI5tMGRu9r799jsSPzZQCW ')
    access_key_secret = os.getenv(
        'OSS_TEST_ACCESS_KEY_SECRET', 'bieHLw8OxSNB0DHCLgRPTslZH4NsoM')
    bucket_name = os.getenv('OSS_TEST_BUCKET', 'vse1')
    endpoint = os.getenv('OSS_TEST_ENDPOINT',  'oss-cn-shanghai.aliyuncs.com')
    # 初始化bucket
    bucket = oss2.Bucket(oss2.Auth(access_key_id, access_key_secret), endpoint, bucket_name)
    key = os.path.basename(out_final_path)
    print(out_final_path)
    print(key)
    try:
        result = bucket.put_object_from_file(key, out_final_path)
        return result.status
    except Exception as e:
        print(e)
        return -1

def insert_img(image_url_dict,tpl):
    context = {}
    for (key,value) in image_url_dict.items():
        # print(key)
        response = requests.get(value)
        # print(response.status_code)
        # image_path = os.path.join(os.path.abspath('.'),'tmp',f"{key}.jpg")
        image_path =  f"{key}.jpg"
        try:
            f = open(image_path, 'wb')
            f.write(response.content)
        except Exception as e:
            print(e)
        f.close()
        insert_image = docxtpl.InlineImage(
            tpl, image_path, width=Mm(150), height=Mm(80)
        )
        context[key]=insert_image
    # print("img_context:")
    # print(context)
    print('写入成功')
    return context

if __name__ == '__main__':
    exp_data = sys.argv[1]
    # print(exp_data)
    exp_dict = json.loads(exp_data)
    # print(exp_dict)
    
    template_docx = f'{exp_dict["experiment_id"]}-template.docx'
    timeArray = time.localtime(exp_dict['submit_time']/1000)
    localTime = time.strftime("%y%m%d", timeArray)
    out_filename = f'{exp_dict["experiment_id"]}-{exp_dict["reporter_name"]}-{exp_dict["reporter_id"]}.docx'
    timeArray = time.localtime(exp_dict['submit_time']/1000)
    localTime = time.strftime("%Y-%m-%d", timeArray)
    exp_dict['submit_time'] = localTime
    # print('模板：'+template_docx)
    # print('生成名称：'+out_filename)
    # print(expDict)
    template_final_path = os.path.join(os.path.abspath('.'),
                                       'template',
                                       template_docx)
    out_final_path = os.path.join(os.path.abspath('.'),
                                  'report',
                                  out_filename)

    tpl = DocxTemplate(template_final_path)

    # 如果有img
    if(exp_dict["image"]):
        # print("image")
        img_dict = exp_dict["image"]
        img_context = insert_img(exp_dict["image"],tpl)
        exp_dict.update(img_context)
        del exp_dict["image"]
        # print(exp_dict)
        try:
            tpl.render(context=exp_dict)
            tpl.save(out_final_path)
        except Exception as e:
            print(e)
        print("保存成功")
        # for key in img_dict.keys():
        #     os.remove(f"./{key}.jpg")
        # print("删除图片成功")
    else:
        tpl.render(context=exp_dict)
        tpl.save(out_final_path)
    # print("上传中")
    status_code = upload_to_oss(out_final_path)
    print(status_code)
    
