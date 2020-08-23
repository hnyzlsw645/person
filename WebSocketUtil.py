import asyncio
from threading import Thread

import websockets
import platform
import psutil
import json
import requests

class WebSocketUtil:
    # ADDRESS = ('127.0.0.1', 9090)  # 绑定地址
    g_websocket_client = None  # 负责监听的socket

    # 线程接受消息
    async def recieve_msg_listener(self):
        while True:
            recv_text = await g_websocket_client.recv()
            if recv_text:
                print(f'接收到消息：{recv_text}')
                await self.parse_cmd(cmd=recv_text)
            else:
                print(f"... ")


    async def parse_cmd(self,cmd):
        if cmd:
            print(f"cmd:{cmd}")
            await self.report_machine_info()
        else:
            print(f"cmd为空")

    # 上报本机信息
    async def report_machine_info(self):
        _json = {
            "name":platform.platform(),
            "host":"127.0.0.1",
            "mac":"",
            "cpuInfo":psutil.cpu_count()
        }
        # await self.send_msg(_json)
        r = requests.get('http://localhost:9090/socket/push/1?message='+json.dumps(_json))
        print(r.ok)


    # 向服务器端发送认证后的消息
    async def send_msg(self,msg):
        # while True:
        #     _text = input("please enter your context: ")
        #     if _text == "exit":
        #         print(f'you have enter "exit", goodbye')
        #         await websocket.close(reason="user exit")
        #         return False
        #     await websocket.send(_text)
        #     recv_text = await websocket.recv()
        #     print(f"{recv_text}")
        if msg:
            await g_websocket_client.send(json.dumps(msg))
        else:
            print("msg为空，没有发送")


    async def init(self):
        """
        初始化连接
        """
        global g_websocket_client
        async with websockets.connect('ws://127.0.0.1:9090/websocket/'+platform.platform()) as g_websocket_client: # 创建 socket 对象
            await g_websocket_client.send('连接成功')
            # 单独线程等待接受消息
            # thread = Thread(target=self.recieve_msg_listener)
            # thread.setDaemon(True)
            # thread.start()
            await self.recieve_msg_listener()

