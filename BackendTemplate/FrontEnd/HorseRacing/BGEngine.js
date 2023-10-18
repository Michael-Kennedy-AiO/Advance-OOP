class MessageSending{
	constructor(CMD){
		this.cmd=CMD;
		this.data=[];
		this.currentWriting=0;
		this.writeShort(CMD);
	}
	writeBoolean(booleanValue){
		if(booleanValue)
			this.data[this.currentWriting++] = 1;
		else
			this.data[this.currentWriting++] = 0;
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
	readBoolean(){return this.bufferDataview.getInt8(this.currentreading++)!=0;}
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
	readFloat(){
		let _floatValue = this.bufferDataview.getFloat32(this.currentreading);
		this.currentreading+=4;
		return _floatValue;
	}
	readDouble(){
		let _doubleValue = this.bufferDataview.getFloat64(this.currentreading);
		this.currentreading+=8;
		return _doubleValue;
	}
}



/*tạo ra sự chuyển đổi giữa các màn hình*/
class GameScene {
	constructor(_sceneName){
		this.sceneName = _sceneName;
	}
	onInit(){}
	onUpdate(){}
	onDraw(){}
	//this.mouseX - this.mouseY
	onClick(){
		console.log("Click("+this.mouseX+","+this.mouseY+")");
	}
	onRightClick(){
		console.log("Right Click("+this.mouseX+","+this.mouseY+")");
	}
	onDoubleClick(){
		console.log("Double click");
	}
	onKeyDown(event){
		var name = event.key;
		var code = event.code;
		console.log("onKeyDown : " + name + " = " + code);
	}
	onKeyUp(event){
		var name = event.key;
		var code = event.code;
		console.log("onKeyUp : " + name + " = " + code);
	}
	onRelease(){}
}

class BGEngine{//Lớp cha chứa vòng lặp game
	#ctxMain;
	#canvasBuffer;
	#timeFPS;
	#countFPS;
	#websocket;
	constructor(){
		this.onWSBinary = [];
		this.onWSString = [];
		this.gameloop = new GameScene("SceneDefault");
		this.#timeFPS = Date.now();
		this.#countFPS=0;
		this.FPS=0;
		this.key=[];
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
		this.#ctxMain.drawImage(this.#canvasBuffer, 0, 0);//draw buffer to Main Canvas
		this.ctx.fillStyle = "black";
		this.ctx.fillRect(0, 0, this.WIDTH, this.HEIGHT);
		this.gameloop.currentTimeMillis = Date.now();
		this.gameloop.onUpdate();
		this.gameloop.onDraw();
		
		if(Date.now()-this.#timeFPS>1000){
			this.#timeFPS = Date.now();
			this.FPS = this.#countFPS;
			this.#countFPS=0;
		}else
			this.#countFPS++;
	}
	setupCanvas(canvasMain){
		this.CANVAS = canvasMain;
		this.WIDTH = canvasMain.width;
		this.HEIGHT = canvasMain.height;
		///////////////////////////////Tạo ra DoubleBuffer
		if(!this.#canvasBuffer){
			this.#canvasBuffer = document.createElement('canvas');
			///////////////////////////////Sử dụng context để vẽ
			this.#ctxMain = canvasMain.getContext('2d');
			this.ctx = this.#canvasBuffer.getContext('2d');
			
			canvasMain.addEventListener("contextmenu", (e) => {
				this.gameloop.onRightClick();
				e.preventDefault()
			});
			canvasMain.onclick = (event)=>{this.gameloop.onClick();};
			canvasMain.ondblclick = (event)=>{this.gameloop.onDoubleClick();};
			canvasMain.onmousemove = (event)=>{
				this._rectBoundCanvas = canvasMain.getBoundingClientRect();
				this.gameloop.mouseX = event.clientX - this._rectBoundCanvas.left;
				this.gameloop.mouseY = event.clientY - this._rectBoundCanvas.top;

				this.mouseX = event.clientX - this._rectBoundCanvas.left;
				this.mouseY = event.clientY - this._rectBoundCanvas.top;
			};
		}	
		this.#canvasBuffer.width = canvasMain.width;
		this.#canvasBuffer.height = canvasMain.height;
	}


	startRealtime(_ip,_port){
		if(this.#websocket){
			this.#websocket.close();
			this.#websocket.remove();
		}

		this.channelId=-1;
		this.#websocket = new WebSocket("ws://"+_ip+":"+_port);
		this.#websocket.binaryType = "arraybuffer";

		let _gEinstance = this;
		this.#websocket.onopen = function(e) {
			let _mgInit = new MessageSending(0);
			_mgInit.writeShort(-1);
			_gEinstance.send(_mgInit);
		};
		this.#websocket.onclose = ()=>{if(_gEinstance.onRealtimeDisconnect)_gEinstance.onRealtimeDisconnect();};
		
		this.#websocket.onmessage = function(event) {
			_gEinstance.lastTimeWebsocket = Date.now();
			let _data = event.data;
			if (_data instanceof ArrayBuffer) {// binary frame
				let messageReceiving = new MessageReceiving(_data);
				console.log("Client receive CMD("+messageReceiving.cmd+") : "+(_data.byteLength-2));
				if(_gEinstance.channelId==-1){//Lần đầu nhận channelId
					if(messageReceiving.readByte()==1){
						_gEinstance.channelId = messageReceiving.readShort();
						_gEinstance.SecWebSocketKey = messageReceiving.readString();
						console.log("Connection success ChannelId("+_gEinstance.channelId+") : "+_gEinstance.SecWebSocketKey);
						if(_gEinstance.onRealtimeConnectSuccess)
							_gEinstance.onRealtimeConnectSuccess();
					}else
						_gEinstance.closeRealtime();
				}else
					if(_gEinstance.onMessage && _gEinstance.onMessage[messageReceiving.cmd])
						_gEinstance.onMessage[messageReceiving.cmd](messageReceiving);
					else
						console.log("CMD("+messageReceiving.cmd+") is not Process. Please setup gameEngine.onMessage["+messageReceiving.cmd+"]= (messageReceiving)=>{};");
			}else{// text frame


			}
		}
	}
	send(messageSending){
		if(this.#websocket)
			if(this.#websocket && (this.#websocket.readyState==WebSocket.OPEN || this.#websocket.readyState==WebSocket.CONNECTING))/*CONNECTING(0) - OPEN(1) - CLOSING(2) - CLOSED(3)*/
				this.#websocket.send(new Int8Array(messageSending.data,messageSending.currentWriting));
			else
				alert("Websocket is Close");
		else
			alert("Websocket is not init");
	}
	closeRealtime(){if(this.#websocket)this.#websocket.release();}
}

const gameEngine = new BGEngine();
const loopId = setInterval(function(){gameEngine.tickGameLoop();}, 1);//Vòng lặp game gọi mỗi 1ms
//clearInterval(gameLoop);

window.addEventListener('keydown', function (event) {
	gameEngine.key[event.code]=true;
	gameEngine.gameloop.onKeyDown(event);
});
window.addEventListener('keyup', function (event) {
	gameEngine.key[event.code]=false;
	gameEngine.gameloop.onKeyUp(event);
});
window.addEventListener('resize', function () {
	canvas.width = window.innerWidth - 22;
	canvas.height = window.innerHeight - 22;
	gameEngine.setupCanvas(gameEngine.CANVAS);
});