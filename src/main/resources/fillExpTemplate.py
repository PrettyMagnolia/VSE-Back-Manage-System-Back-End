# -*- coding: utf-8 -*-
import io
import sys
import json
from docxtpl import DocxTemplate

sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

def fill_template(data):
    # data

    return 0


if __name__ == '__main__':
    expData = sys.argv[1]
    expDict = json.loads(expData)
    template_docx = f'{expDict["experiment_id"]}-template.docx'
    out_filename = f'{expDict["experiment_id"]}-{expDict["reporter_name"]}-{expDict["reporter_id"]}-{expDict["submit_time"]}.docx'
    print('模板：'+template_docx)
    print('生成名称：'+out_filename)
    # print(expDict['experiment_id'])
    # print(expDict['submit_time'])
    # print(expDict['reporter_id'])
    # print(expDict['reporter_name'])
    fill_template(expData)


