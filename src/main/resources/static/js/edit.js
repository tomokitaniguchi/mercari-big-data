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

// 大カテゴリーに付随した中カテゴリーを非同期通信で取得
document.getElementById("firstSelect").addEventListener("change", function() {
  var selectedValue = this.value;
  console.log(selectedValue);

  // URLSearchParamsを使ってクエリパラメータを生成
  var params = new URLSearchParams();
  params.append('bigCategory', selectedValue);

  // クエリパラメータを含むURLを作成してGETリクエストを送信
  fetch('/mercari-data/edit/response?' + params, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
  })
  .then(response => response.json()) // レスポンスをJSONとして解析
    .then(data => {
        // 取得したデータを利用してsecondSelectのオプションを変更
        var secondSelect = document.getElementById("secondSelect");
        // 既存のオプションをクリアする
        secondSelect.innerHTML = '';
        // 例: 取得したデータが配列であり、その各要素をoption要素としてsecondSelectに追加する
        data.forEach(item => {
            var option = document.createElement("option");
            option.value = item.middleCategory; // 適切な値を設定する
            option.text = item.middleCategory; // 適切な表示テキストを設定する
            secondSelect.appendChild(option);
        });
    })
  .catch(error => {
      console.error('エラーが発生しました', error);
  });
});

// 中カテゴリーに付随した小カテゴリーを非同期通信で取得
document.getElementById("secondSelect").addEventListener("change", function() {
  var selectedValue = this.value;
  console.log(selectedValue);

  // URLSearchParamsを使ってクエリパラメータを生成
  var params = new URLSearchParams();
  params.append('middleCategory', selectedValue);
  
  // クエリパラメータを含むURLを作成してGETリクエストを送信
  fetch('/mercari-data/edit/response?' + params, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
  })
  .then(response => response.json()) // レスポンスをJSONとして解析
    .then(data => {
        // 取得したデータを利用してthirdSelectのオプションを変更
        var thirdSelect = document.getElementById("thirdSelect");
        // 既存のオプションをクリアする
        thirdSelect.innerHTML = '';
        // 例: 取得したデータが配列であり、その各要素をoption要素としてthirdSelectに追加する
        data.forEach(item => {
            var option = document.createElement("option");
            option.value = item.smallCategory; // 適切な値を設定する
            option.text = item.smallCategory; // 適切な表示テキストを設定する
            thirdSelect.appendChild(option);
        });
    })
  .catch(error => {
      console.error('エラーが発生しました', error);
  });
});
