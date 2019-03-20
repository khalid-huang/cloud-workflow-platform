# -*- coding: utf-8 -*-
# 
import os
import sys
import csv
 
# 初始化编码
reload(sys)
sys.setdefaultencoding('utf-8')
 
# 读取本地csv文件
csv_path = os.path.join(os.path.dirname(os.path.abspath(__file__)),'act_re_procdef.csv')
csv_reader = csv.reader(open(csv_path,'rb'))
csv_reader.next()
i=0
j=0
for row in csv_reader:
	if i == 99:
		print u"CSV文件act_re_procdef_sp%s已生成成功" % j
		j += 1
		i = 0
	# 写入csv
	csv_path = os.path.join(os.path.dirname(os.path.abspath(__file__)),'ram','act_re_procdef_sp_'+str(j)+'.csv')
	csv_file = file(csv_path, 'ab+')
	csv_write = csv.writer(csv_file)
	# 文件不存在则写入头部	
	if os.path.getsize(csv_path)==0:
		csv_write.writerow(['processDefinitionId'])
	# 写入数据
	csv_write.writerow([row[0]])
	csv_file.close()
	i+=1
# 关闭连接