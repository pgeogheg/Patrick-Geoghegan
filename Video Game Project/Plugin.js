(function(){

	var dungeonDetails = [
		{ name: 'Forest Dungeon', map_ids:[12,13,14,15,16,17,18,19,20], troops:[1,2] }
	];

	var parameters = PluginManager.parameters('pd_plugin_test');

	var $dataAllMap = [];
	$dataAllMap[0] = null;
	var $dataPseudoMaps = [];
	var responseMapData = null;

	var psuedoMapOffset = 200;

	var _DataManager_loadDatabase_pd = DataManager.loadDatabase;
	DataManager.loadDatabase = function() {
		_DataManager_loadDatabase_pd.call(this);
		this.loadAllMapData();
	};

	var _DataManager_loadMapData_pd = DataManager.loadMapData;
	DataManager.loadMapData = function(mapId) {
	    if (mapId > 0) {
	    	if (mapId >= psuedoMapOffset && mapId < psuedoMapOffset + 100) {
	    		console.log("ENTERING:");
	    		console.log(mapId);
				$dataMap = $dataPseudoMaps[mapId - psuedoMapOffset];
	    	} else {
	    		var filename = 'Map%1.json'.format(mapId.padZero(3));
	    		this.loadDataFile('$dataMap', filename);
	    	}
	    } else {
	        this.makeEmptyMap();
	    }
	};

	var _DataManager_loadDataFile_pd = DataManager.loadDataFile;
	DataManager.loadDataFile = function(name, src) {
		var xhr = new XMLHttpRequest();
		var url = 'data/' + src;
		xhr.open('GET', url);
		xhr.overrideMimeType('application/json');
		xhr.onload = function() {
		    if (xhr.status < 400) {
		    	if (name === '$dataAllMap') {
		    		$dataAllMap[parseInt(src.slice(3,6))] = (xhr.responseText); 
		    	}
		        window[name] = JSON.parse(xhr.responseText);
		        DataManager.onLoad(window[name]);
		    }
		};
		xhr.onerror = function() {
		    DataManager._errorUrl = DataManager._errorUrl || url;
		};
		window[name] = null;
		xhr.send();
	};

	DataManager.loadAllMapData = function() {
		for (var i = 1; i < 21; i++){
			var url = 'Map%1.json'.format(i.padZero(3));
			this.loadDataFile('$dataAllMap', url);
		}
	};

	pd_startDungeonConstruction = function() {
		var layout = new FloorLayout(5);
		layout.setup();
		layout.constructWithGraph();
		layout.addNodeDetailsWithGraph();
		layout.startWithGraph();
	};

	function RoomNode(id) {
		this._id = id;
		this._data = null;
		this._up = false;
		this._down = false;
		this._left = false;
		this._right = false;
	};

	RoomNode.prototype.hasUp = function() {
		return this._up;
	};

	RoomNode.prototype.hasDown = function() {
		return this._down;
	};

	RoomNode.prototype.hasLeft = function() {
		return this._left;
	};

	RoomNode.prototype.hasRight = function() {
		return this._right;
	};

	function FloorLayout(size) {
		this._rooms = new Array(size);
		$dataPseudoMaps = new Array(size);
		for (var i = 0; i < this._rooms.length; i++){
			for (var j = 0; j < this._rooms.length; j++){
				if (j === 0) {
					this._rooms[i] = new Array(size);
					$dataPseudoMaps[i] = new Array(size);
				}
				this._rooms[i][j] = null;
				$dataPseudoMaps[i][j] = null;
			}
		}
		this._floorSize = size;
		this._start = null;
		this._size = 0;
		this._roomNodes = [];
		this._pseudoMapData = {};
		this._startX = Math.floor(Math.random()*this._floorSize);
		this._startY = Math.floor(Math.random()*this._floorSize);
	};

	FloorLayout.prototype.startWithGraph = function() {
		console.log("start()");
		console.log(this._start);
		console.log("FLOOR SIZE:");
		console.log(this._size);
		console.log("GRAPH");
		console.log(this._rooms);
		this.printFloor();
		console.log("PSEUDO MAPS");
		console.log($dataPseudoMaps);
		var transferId = this._start._id;
		var transferX = Math.floor($dataPseudoMaps[0].width / 2);
		var transferY = Math.floor(($dataPseudoMaps[0].height / 2) + 1);
		$gamePlayer.reserveTransfer(transferId, transferX, transferY, 2, 0);
	};

	FloorLayout.prototype.setup = function() {
		this._start = new RoomNode(psuedoMapOffset + this._size);
		this._size = 1;
		this._roomNodes.push(this._start);
		this._rooms[this._startX][this._startY] = this._start;
	};

	FloorLayout.prototype.construct = function() {
		this.findNodeNeighbours(this._start, 1);
	};

	FloorLayout.prototype.constructWithGraph = function() {
		this.findNodeNeighboursWithGraph(this._startX, this._startY, 1);
	};

	FloorLayout.prototype.addNodeDetailsWithGraph = function() {
		for (var i = 0; i < this._floorSize; i++){
			for (var j = 0; j < this._floorSize; j++){
				if (this._rooms[i][j] !== null){
					this.addOverlayWithGraph(i, j);
				}
			}
		}
		for (var i = 0; i < this._floorSize; i++){
			for (var j = 0; j < this._floorSize; j++){
				if (this._rooms[i][j] !== null){
					this.addEventsWithGraph(i, j);
				}
			}
		}
	};

	FloorLayout.prototype.addEventsWithGraph = function(nodeX, nodeY) {
		console.log("addEventsWithGraph(" + nodeX + ", " + nodeY + ")");
		console.log("%c" + this._rooms[nodeX][nodeY]._id, "color:green");
		console.log(this._rooms[nodeX][nodeY]._data);
		for (var i = 0; i < dungeonDetails[0].troops.length; i++){
			var troopId = dungeonDetails[0].troops[i]
			this._rooms[nodeX][nodeY]._data.encounterList[i] = {"regionSet":[], "troopId": $dataTroops[troopId], "weight":5};
		}
		this._rooms[nodeX][nodeY]._data.encounterStep = 30;
		if (this._rooms[nodeX][nodeY] !== null) {
			var transferId = -1;
			var transferX = -1;
			var transferY = -1;
			var theEvents = this._rooms[nodeX][nodeY]._data.events;
			for (var i = 0; i < theEvents.length; i++) {
				if (theEvents[i] !== null) {
					if (theEvents[i].name === "doorN") {
						if (this._rooms[nodeX][nodeY]._up) {
							var checkEvents = this._rooms[nodeX - 1][nodeY]._data.events;
							for (var j = 0; j < checkEvents.length; j++) {
								if (checkEvents[j] !== null &&
									checkEvents[j].name === "doorS") {
									transferId = (this._rooms[nodeX - 1][nodeY]._id);
									transferX = (checkEvents[j].x);
									transferY = (checkEvents[j].y - 1);
								}
							}
						} 
					}
					else if (theEvents[i].name === "doorS") {
						if (this._rooms[nodeX][nodeY]._down) {
							var checkEvents = this._rooms[nodeX + 1][nodeY]._data.events;
							for (var j = 0; j < checkEvents.length; j++) {
								if (checkEvents[j] !== null &&
									checkEvents[j].name === "doorN") {
									transferId = (this._rooms[nodeX + 1][nodeY]._id);
									transferX = (checkEvents[j].x);
									transferY = (checkEvents[j].y + 1);

								}
							}
						} 
					}
					else if (theEvents[i].name === "doorW") {
						if (this._rooms[nodeX][nodeY]._left) {
							var checkEvents = this._rooms[nodeX][nodeY - 1]._data.events;
							for (var j = 0; j < checkEvents.length; j++) {
								if (checkEvents[j] !== null &&
									checkEvents[j].name === "doorE") {
									transferId = (this._rooms[nodeX][nodeY - 1]._id);
									transferX = (checkEvents[j].x - 1);
									transferY = (checkEvents[j].y);
								}
							}
						} 
					}
					else if (theEvents[i].name === "doorE") {
						if (this._rooms[nodeX][nodeY]._right) {
							var checkEvents = this._rooms[nodeX][nodeY + 1]._data.events;
							for (var j = 0; j < checkEvents.length; j++) {
								if (checkEvents[j] !== null &&
									checkEvents[j].name === "doorW") {
									transferId = (this._rooms[nodeX][nodeY + 1]._id);
									transferX = (checkEvents[j].x + 1);
									transferY = (checkEvents[j].y);
								}
							}
						} 
					}
				}
				if (transferId !== -1) {
					var page = theEvents[i].pages[0];
					page.directionFix = true;
					page.image.tileId = 0;
					page.image.characterName = "!Door2";
					page.image.direction = 8;
					page.image.pattern = 0;
					page.image.characterIndex = 0;
					page.list = new Array(2);
					page.list[0] = {};
					page.list[0].code = 355;
					page.list[0].indent = 0;
					page.list[0].parameters = ["$gamePlayer.reserveTransfer(" + 
						transferId + ", " + transferX + ", " + transferY + ", 0, 0);"];
					page.list[1] = {};
					page.list[1].code = 0;
					page.list[1].indent = 0;
					page.list[1].parameters = [];
					page.moveFrequency = 3;
					page.moveSpeed = 3;
					page.moveType = 3;
					page.priorityType = 1;
					page.stepAnime = true;
					page.trigger = 1;
				}
				transferId = -1;
				transferX = -1;
				transferY = -1;
			}
			$dataPseudoMaps[this._rooms[nodeX][nodeY]._id - psuedoMapOffset] = this._rooms[nodeX][nodeY]._data;
		}
	};

	FloorLayout.prototype.storeDataWithGraph = function() {
		for (var i = 0; i < this._floorSize; i++) {
			for (var j = 0; j < this._floorSize; j++) {
				if (this._rooms[i][j] !== null){
					$dataPseudoMaps[this._rooms[nodeX][nodeY]._id - psuedoMapOffset] = this._rooms._data.mapData;
				}
			}
		}
	}

	FloorLayout.prototype.addOverlayWithGraph = function(nodeX, nodeY) {
		console.log("addOverlayWithGraph(" + nodeX + ", " + nodeY + ")");
		var possibleMaps = [];
		var possibleMapsNums = [];
		var mData = {};
		for (var i = 0; i < dungeonDetails[0].map_ids.length; i++){
			var index = (dungeonDetails[0].map_ids[i]);
			var compareData = $dataAllMap[index];
			if (this.checkMatchWithGraph(nodeX, nodeY, compareData)) {
				possibleMaps.push(compareData);
				possibleMapsNums.push(dungeonDetails[0].map_ids[i]);
			}
		};
		var randIndex = Math.floor(Math.random() * possibleMaps.length);
		var overlay = possibleMaps[randIndex];
		var overlayIndex = possibleMapsNums[randIndex];
		this._rooms[nodeX][nodeY]._data = JSON.parse($dataAllMap[overlayIndex]);
	};

	FloorLayout.prototype.checkMatchWithGraph = function(nodeX, nodeY, mapData) {
		var mapObj = JSON.parse(mapData);
		var northBool = false;
		var southBool = false;
		var eastBool = false;
		var westBool = false;
		for (var i = 0; i < mapObj.events.length; i++) {
			var eventCheck = mapObj.events[i];
			if (eventCheck) {
				if (eventCheck.name === "doorN") northBool = true;
				if (eventCheck.name === "doorS") southBool = true;
				if (eventCheck.name === "doorE") eastBool = true;
				if (eventCheck.name === "doorW") westBool = true;
			}
		};
		if (this._rooms[nodeX][nodeY]._up) {
			if (!northBool) return false;
		}
		if (this._rooms[nodeX][nodeY]._down) {
			if (!southBool) return false;
		}
		if (this._rooms[nodeX][nodeY]._left) {
			if (!westBool) return false;
		}
		if (this._rooms[nodeX][nodeY]._right) {
			if (!eastBool) return false;
		}
		return true;
	};

	FloorLayout.prototype.findNodeNeighboursWithGraph = function(nodeX, nodeY, chance) {
		console.log("findNodeNeighboursWithGraph(" + nodeX + ", " + nodeY + ", " + chance +")");
		if (nodeY != 0 && this._rooms[nodeX][nodeY - 1] === null && 
			Math.floor((Math.random()*chance) + 1) === 1) {
			var newNode = new RoomNode(psuedoMapOffset + this._size);
			this._size++;
			this._rooms[nodeX][nodeY - 1] = newNode;
			//this._rooms[nodeX][nodeY - 1]._right = true;
			//this._rooms[nodeX][nodeY]._left = true;
			this.findNodeNeighboursWithGraph(nodeX, nodeY - 1, chance + 1);
		}
		if (nodeY != this._floorSize - 1 && this._rooms[nodeX][nodeY + 1] === null && 
			Math.floor((Math.random()*chance) + 1) === 1) {
			var newNode = new RoomNode(psuedoMapOffset + this._size);
			this._size++;
			this._rooms[nodeX][nodeY + 1] = newNode;
			//this._rooms[nodeX][nodeY + 1]._left = true;
			//this._rooms[nodeX][nodeY]._right = true;
			this.findNodeNeighboursWithGraph(nodeX, nodeY + 1, chance + 1);
		}
		if (nodeX != this._floorSize - 1 && this._rooms[nodeX + 1][nodeY] === null && 
			Math.floor((Math.random()*chance) + 1) === 1) {
			var newNode = new RoomNode(psuedoMapOffset + this._size);
			this._size++;
			this._rooms[nodeX + 1][nodeY] = newNode;
			//this._rooms[nodeX + 1][nodeY]._up = true;
			//this._rooms[nodeX][nodeY]._down = true;
			this.findNodeNeighboursWithGraph(nodeX + 1, nodeY, chance + 1);
		}
		if (nodeX != 0 && this._rooms[nodeX - 1][nodeY] === null && 
			Math.floor((Math.random()*chance) + 1) === 1) {
			var newNode = new RoomNode(psuedoMapOffset + this._size);
			this._size++;
			this._rooms[nodeX - 1][nodeY] = newNode;
			//this._rooms[nodeX - 1][nodeY]._down = true;
			//this._rooms[nodeX][nodeY]._up = true;
			this.findNodeNeighboursWithGraph(nodeX - 1, nodeY, chance + 1);
		}
		if (nodeY != 0 && this._rooms[nodeX][nodeY - 1] !== null) this._rooms[nodeX][nodeY]._left = true;
		if (nodeY != this._floorSize - 1 && this._rooms[nodeX][nodeY + 1] !== null) this._rooms[nodeX][nodeY]._right = true;
		if (nodeX != this._floorSize - 1 && this._rooms[nodeX + 1][nodeY] !== null) this._rooms[nodeX][nodeY]._down = true;
		if (nodeX != 0 && this._rooms[nodeX - 1][nodeY] !== null) this._rooms[nodeX][nodeY]._up = true;
	};

	FloorLayout.prototype.primBuild = function() {
		var maze = [];
		maze.push({x:0, y:0});
		var prev = maze[0];
		var index = 0;
		var thisX = maze[index].x;
		var thisY = maze[index].y;
		while (maze.length !== 0) {
			this._rooms[thisX][thisY] = new RoomNode(psuedoMapOffset + this._size);
			this._size++;
			if (thisX !== 0 && this._rooms[thisX - 1][thisY] === null) maze.unshift({x:thisX - 1, y:thisY});
			if (thisX !== this._floorSize - 1 && this._rooms[thisX + 1][thisY] === null) maze.unshift({x:thisX + 1, y:thisY});
			if (thisY !== 0 && this._rooms[thisX][thisY - 1] === null) maze.unshift({x:thisX, y:thisY - 1});
			if (thisY !== this._floorSize - 1 && this._rooms[thisX][thisY + 1] === null) maze.unshift({x:thisX, y:thisY + 1});
			prev = maze[index];
			maze.splice(index, 1);
			index = Math.floor(Math.random() * maze.length);
			thisX = maze[index].x;
			thisY = maze[index].y;
			if (thisX > prev.x) this._rooms[prev.x][prev.y]._down = true;
			else if (thisX < prev.x) this._rooms[prev.x][prev.y]._up = true;
			else if (thisY > prev.y) this._rooms[prev.x][prev.y]._right = true;
			else this._rooms[prev.x][prev.y]._left = true;
		}
	};

	FloorLayout.prototype.size = function(){
		return this._size;
	};

	FloorLayout.prototype.printFloor = function() {
		var layoutString = "\n";
		for (var i = 0; i < this._floorSize; i++){
			for (var j = 0; j < this._floorSize; j++){
				layoutString += "|";
				if (this._rooms[i][j] === null) layoutString += "   ";
				else layoutString += this._rooms[i][j]._id;
				layoutString += "|";
			}
			layoutString += "\n";
		}
		console.log(layoutString);
	};
})();