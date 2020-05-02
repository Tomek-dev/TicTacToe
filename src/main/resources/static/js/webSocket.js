var stompClient = null;

function connect(event){
	if(username){
		let socket = new SockJS('/ws')
		stompClient = Stomp.over(socket);
		stompClient.connect({}, onConnected, onError);
	}
}

function onConnected() {
    stompClient.subscribe('/user/queue/game', onMessageReceived)
    stompClient.send('/app/game.connect', {}, JSON.stringify({}))
}

function onError(error){
    gameMessage.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    gameMessage.classList.remove('win');
    gameMessage.classList.add('error');
}

function send(row, col, mark, turn) {
    let message = {
        row: row,
        col: col,
        mark: mark,
	    type: 'GAME',
	    turn: turn
    };
    stompClient.send('/app/game/' + id + '.send', {}, JSON.stringify(message));
}

function win(winner){
    let message = {
        winner: winner,
	    type: 'WIN'
    };
    stompClient.send('/app/game/' + id + '.win', {}, JSON.stringify(message));
}

function onMessageReceived(payload){
    let message = JSON.parse(payload.body);
    if(message.type === 'RECONNECT'){
	    stompClient.send('/app/game.reconnect', {}, JSON.stringify({}));
    } else if(message.type === 'DISCONNECT'){
	    gameMessage.innerText = 'The opponent has left from the game.';
	    /*not working*/
    } else if(message.type === 'CREATE'){
	    id = message.id;
	    player = (username === message.x ? 'X' : 'O')
	    playerMark.innerText = player;
	    loading.style.display = 'none';
	    game.style.display = 'flex';
    } else if(message.type === 'STATE'){
        id = message.id;
   	    player = (username === message.x ? 'X' : 'O')
   	    playerMark.innerText = player;
   	    loading.style.display = 'none';
   	    game.style.display = 'flex';
	    load(message.fields);
	    actualTurn.innerText = (data.size()%2 === 0 ? 'O' : 'X');
        turn = (data.size()%2 === 0 ? 'O' : 'X');;
	    /*not working*/
    } else if(message.type === 'GAME'){
        fields[message.row][message.col] = (message.mark === 'X'? 0 : 1);
        buttons[message.row][message.col].innerText = message.mark;
        buttons[message.row][message.col].style.color = (message.mark === 'X'? '#444' : '#fff');
        actualTurn.innerText = message.turn;
        turn = message.turn;
    } else if(message.type === 'WIN'){
        turn = null;
        gameMessage.classList.remove('error');
        gameMessage.classList.add('win');
        gameMessage.innerText = 'The winner is ' + message.winner + '!';
    }
}

document.addEventListener('DOMContentLoaded', function() {
    connect();
});