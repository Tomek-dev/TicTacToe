create();

function create() {
  for (let row = 0; row < 10; row++) {
    buttons[row] = new Array(10);
    fields[row] = new Array(10).fill(null);
    for (let col = 0; col < 10; col++) {
      let button = document.createElement("button");
      button.setAttribute("type", "button");
      button.setAttribute("data-row", row);
      button.setAttribute("data-column", col);
      button.setAttribute("class", "field");
      button.addEventListener('click', pick);
      buttons[row][col] = button;
      board.appendChild(button);
    }
  }
}

function pick(event) {
  const {row, column} = event.target.dataset;
  if(player === turn && fields[row][column] === null){
    fields[row][column] = ((player)? 0 : 1);
    buttons[row][column].innerText = player;
    buttons[row][column].style.color = (player === 'X'? '#444' : '#fff');
    turn = (turn === 'X' ? 'O' : 'X');
    send(row, column, player, (player === 'X' ? 'O' : 'X'));
    console.log(fields)
    if(check() === true){
      win(winner)
      return;
    }
    if(move === 100){
        win('DRAW');
    }
  }
}

function check() {
  for (let row = 0; row < fields.length; row++) {
    for (let col = 0; col < fields[row].length; col++) {
      if(fields[row][col] !== null){
        let result = new Array();
        for (let i = 0; i < 4; i++) {
          result[i] = new Array();
          result[i].push(fields[row][col]);
        }
        for (let i = 1; i < 4; i++) {
          if(col-i>=0) result[0].unshift(fields[row][col-i]);
          if(col+i<10) result[0].push(fields[row][col+i])
          if(row-i>=0) result[1].unshift(fields[row-i][col]);
          if(row+i<10) result[1].push(fields[row+i][col])
          if(row-i>=0 && col+i<10) result[2].unshift(fields[row-i][col+i]);
          if(row+i<10 && col-i>=0) result[2].push(fields[row+i][col-i]);
          if(row-i>=0 && col-i>=0) result[3].unshift(fields[row-i][col-i]);
          if(row+i<10 && col+i<10) result[3].push(fields[row+i][col+i]);
        }
        for (let i = 0; i < 4; i++) {
          let chance = result[i].map(String).join("");
          if(chance.includes("1111") || chance.includes("0000")){
             winner = (chance.includes("1111") ? 'O' : 'X')
             return true;
	      }
        }
      }
    }
  }
  return false;
}