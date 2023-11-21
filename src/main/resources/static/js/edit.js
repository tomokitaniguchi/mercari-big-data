'use strict';

// 大カテゴリーから順番に選択
function handleFirstSelectChange(select) {
  var secondSelect = document.getElementById("secondSelect");
  var thirdSelect = document.getElementById("thirdSelect");
  secondSelect.disabled = !select.value; // 一つ目の選択が行われていない場合、二つ目の選択を無効化
  thirdSelect.disabled = true; // 三つ目の選択を常に無効化する
  
  if (secondSelect.disabled) {
      thirdSelect.selectedIndex = 0; // 三つ目の選択をリセット
  }
}

function handleSecondSelectChange(select) {
  var thirdSelect = document.getElementById("thirdSelect");
  thirdSelect.disabled = !select.value; // 二つ目の選択が行われていない場合、三つ目の選択を無効化
}


