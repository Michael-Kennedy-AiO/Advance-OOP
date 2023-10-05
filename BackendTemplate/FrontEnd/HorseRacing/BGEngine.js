class MessageSending{
	constructor(CMD){
		this.cmd=CMD;
		this.data=[];
		this.currentWriting=0;
		this.writeShort(CMD);
	}

	writeByte(byteValue){this.data[this.currentWriting++] = byteValue & 0xFF;}
	writeShort(shortValue){
		this.data[this.currentWriting++] = (shortValue>>>8) & 0xFF;
		this.data[this.currentWriting++] = shortValue & 0xFF;
	}
	writeInt(intValue){
		this.data[this.currentWriting++] = (intValue>>>24) & 0xFF;
		this.data[this.currentWriting++] = (intValue>>>16) & 0xFF;
		this.data[this.currentWriting++] = (intValue>>>8) & 0xFF;
		this.data[this.currentWriting++] = intValue & 0xFF;
	}
	writeFloat(floatValue){
		let _dataView = new DataView(new ArrayBuffer(4));
		_dataView.setFloat32(0,floatValue);
		this.data[this.currentWriting++] = _dataView.getUint8(0);
		this.data[this.currentWriting++] = _dataView.getUint8(1);
		this.data[this.currentWriting++] = _dataView.getUint8(2);
		this.data[this.currentWriting++] = _dataView.getUint8(3);
	}
	writeLong(longValue){
		this.data[this.currentWriting++] = (longValue>>>56) & 0xFF;
		this.data[this.currentWriting++] = (longValue>>>48) & 0xFF;
		this.data[this.currentWriting++] = (longValue>>>40) & 0xFF;
		this.data[this.currentWriting++] = (longValue>>>32) & 0xFF;
		this.data[this.currentWriting++] = (longValue>>>24) & 0xFF;
		this.data[this.currentWriting++] = (longValue>>>16) & 0xFF;
		this.data[this.currentWriting++] = (longValue>>>8) & 0xFF;
		this.data[this.currentWriting++] = longValue & 0xFF;
	}
	writeDouble(doubleValue){
		let _dataView = new DataView(new ArrayBuffer(8));
		_dataView.setFloat64(0,doubleValue);
		this.data[this.currentWriting++] = _dataView.getUint8(0);
		this.data[this.currentWriting++] = _dataView.getUint8(1);
		this.data[this.currentWriting++] = _dataView.getUint8(2);
		this.data[this.currentWriting++] = _dataView.getUint8(3);
		this.data[this.currentWriting++] = _dataView.getUint8(4);
		this.data[this.currentWriting++] = _dataView.getUint8(5);
		this.data[this.currentWriting++] = _dataView.getUint8(6);
		this.data[this.currentWriting++] = _dataView.getUint8(7);
	}
	writeString(strValue){
		let _dataString = new TextEncoder().encode(strValue);
		this.writeShort(_dataString.length);
		for(let i=0;i<_dataString.length;i++)
			this.data[this.currentWriting++] = _dataString[i] & 0xFF;
	}
}
class MessageReceiving{
	constructor(dataReceiving){
		this.arrBuffer=dataReceiving;
		this.currentreading = 2;
		this.bufferDataview = new DataView(dataReceiving);
		this.cmd = this.bufferDataview.getInt16(0);
	}
	readByte(){return this.bufferDataview.getInt8(this.currentreading++);}
	readShort(){
		let shortResult = this.bufferDataview.getInt16(this.currentreading);
		this.currentreading+=2;
		return shortResult;
	}
	readInt(){
		let intResult = this.bufferDataview.getInt32(this.currentreading);
		this.currentreading+=4;
		return intResult;
	}
	readLong(){
		let l0 = this.bufferDataview.getInt8(this.currentreading++) & 0xFF;
		let l1 = this.bufferDataview.getInt8(this.currentreading++) & 0xFF;
		let l2 = this.bufferDataview.getInt8(this.currentreading++) & 0xFF;
		let l3 = this.bufferDataview.getInt8(this.currentreading++) & 0xFF;
		let l4 = this.bufferDataview.getInt8(this.currentreading++) & 0xFF;
		let l5 = this.bufferDataview.getInt8(this.currentreading++) & 0xFF;
		let l6 = this.bufferDataview.getInt8(this.currentreading++) & 0xFF;
		let l7 = this.bufferDataview.getInt8(this.currentreading++) & 0xFF;
		return (l0 << 56) + (l1 << 48) + (l2 << 40) + (l3 << 32) + (l4 << 24) + (l5 << 16) + (l6 << 8) + l7;
	}
	readString(){
		let strLength = this.bufferDataview.getUint16(this.currentreading);
		this.currentreading+=2;
		let _buffStr = new DataView(this.arrBuffer, this.currentreading, strLength);
		this.currentreading+=strLength;
		return new TextDecoder("utf-8").decode(_buffStr);
	}
}


class BGWebsocket{
	#websocket;
	constructor(){
		this.isPause=false;
	}
	start(_ip,_port){
		gameEngine.sessionId=-1;
		this.#websocket = new WebSocket("ws://"+_ip+":"+_port);
		this.#websocket.binaryType = "arraybuffer";

		let currentBGWS=this;
		this.#websocket.onopen = function(e) {
			let _mgInit = new MessageSending(0);
			_mgInit.writeShort(-1);
			currentBGWS.send(_mgInit);
		};
		this.#websocket.onclose = ()=>{if(this.onDisconnect)this.onDisconnect();};
		
		this.#websocket.onmessage = function(event) {//
			gameEngine.lastTimeWebsocket = Date.now();
			let _data = event.data;
			if (_data instanceof ArrayBuffer) {// binary frame
				let messageReceiving = new MessageReceiving(_data);
				console.log("Client receive CMD("+messageReceiving.cmd+") : "+(_data.byteLength-2));
				if(gameEngine.sessionId==-1){//Lần đầu nhận sessionId
					if(messageReceiving.readByte()==1){
						gameEngine.sessionId = messageReceiving.readShort();
						currentBGWS.SecWebSocketKey = messageReceiving.readString();
						console.log("Connection success SessionId("+gameEngine.sessionId+") : "+currentBGWS.SecWebSocketKey);
						if(gameEngine.onRealtimeConnectSuccess)
							gameEngine.onRealtimeConnectSuccess();
					}else
						gameEngine.closeRealtime();
				}else
					if(gameEngine.onWSBinary[messageReceiving.cmd])
						gameEngine.onWSBinary[messageReceiving.cmd](messageReceiving);
					else
						console.log("CMD("+messageReceiving.cmd+") is not Process. Please setup gameEngine.onWSBinary["+messageReceiving.cmd+"]= (messageReceiving)=>{};");
			}else{// text frame


			}
		};
		
		this.#websocket.onerror = function(error) {console.log("=====> this.#websocket.onerror = function(error) : "+error);};
	}
	setReceiver(CMD,funReceive){

	}
	isRunning(){return this.#websocket && (this.#websocket.readyState==WebSocket.OPEN || this.#websocket.readyState==WebSocket.CONNECTING);}/*CONNECTING(0) - OPEN(1) - CLOSING(2) - CLOSED(3)*/
	send(message){
		console.log("Client send CMD("+message.cmd+") : " + (message.currentWriting-2) + " byte");
		this.#websocket.send(new Int8Array(message.data,message.currentWriting));
	}
	release(){this.#websocket.close();}
}





/*tạo ra sự chuyển đổi giữa các màn hình*/
class GameScene {constructor(_sceneName){this.sceneName = _sceneName;}onInit(){}onUpdate(){}onRelease(){}}

class BGEngine{//Lớp cha chứa vòng lặp game
	#ctxMain;
	#canvasBuffer;
	#timeFPS;
	#countFPS;
	constructor(){
		this.onWSBinary = [];
		this.onWSString = [];
		this.gameloop = new GameScene("SceneDefault");
		this.#timeFPS = Date.now();
		this.#countFPS=0;
		this.FPS=0;
	}
	changeScene(_scene){
		let _old = this.gameloop;
		_scene.ctx=this.ctx;
		_scene.onInit();
		this.gameloop = _scene;
		_old.onRelease();
		console.log("Change Scene "+_old.sceneName+" → "+_scene.sceneName);
	}
	tickGameLoop(){
		this.ctx.fillStyle = "black";
		this.ctx.fillRect(0, 0, this.WIDTH, this.HEIGHT);
		this.gameloop.onUpdate();
		this.#ctxMain.drawImage(this.#canvasBuffer, 0, 0);//draw buffer to Main Canvas
		
		if(Date.now()-this.#timeFPS>1000){
			this.#timeFPS = Date.now();
			this.FPS = this.#countFPS;
			this.#countFPS=0;
		}else
			this.#countFPS++;
	}
	initCanvas(canvasMain){
		this.WIDTH = canvasMain.width;
		this.HEIGHT = canvasMain.height;
		///////////////////////////////Tạo ra DoubleBuffer
		if(this.#canvasBuffer)
			this.#canvasBuffer.remove();				
		this.#canvasBuffer = document.createElement('canvas');
		this.#canvasBuffer.width = canvasMain.width;
		this.#canvasBuffer.height = canvasMain.height;
		///////////////////////////////Sử dụng context để vẽ
		this.#ctxMain = canvasMain.getContext('2d');
		this.ctx = this.#canvasBuffer.getContext('2d');
	}


	startRealtime(_ip,_port){
		if(this.websocket)
			this.websocket.release();
		this.websocket = new BGWebsocket();
		this.websocket.start(_ip,_port);
	}
	send(messageSending){
		if(this.websocket)
			if(this.websocket.isRunning()){
				this.websocket.send(messageSending);
			}else
				alert("Websocket is Close");
		else
			alert("Websocket is not init");
	}
	closeRealtime(){if(this.websocket)this.websocket.release();}
}

const gameEngine = new BGEngine();
const loopId = setInterval(function(){gameEngine.tickGameLoop();}, 1);//Vòng lặp game gọi mỗi 1ms
//clearInterval(loopId);