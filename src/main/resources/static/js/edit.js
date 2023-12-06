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
  console.log('L='+selectedValue);

  // URLSearchParamsを使ってクエリパラメータを生成
  var params = new URLSearchParams();
  params.append('bigCategory', selectedValue);

  // クエリパラメータを含むURLを作成してGETリクエストを送信
  fetch('/edit/category-response?' + params, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
  })
  .then(response => response.json()) // レスポンスをJSONとして解析
    .then(data => {
        // '- childCategory -' を手動で追加
        data.unshift({ middleCategory: '- childCategory -' });
        // 取得したデータを利用してsecondSelectのオプションを変更
        var secondSelect = document.getElementById("secondSelect");
        // 既存のオプションをクリアする
        secondSelect.innerHTML = '';
        // 例: 取得したデータが配列であり、その各要素をoption要素としてsecondSelectに追加する
        data.forEach(item => {
            var option = document.createElement("option");
            option.value = item.middleCategory; // 適切な値を設定する
            option.text = item.middleCategory; // 適切な表示テキストを設定する
            // '- childCategory -' を disabled, selectedにする
            if (item.middleCategory === '- childCategory -') {
              option.disabled = true;
              option.selected = true;
            }
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
  console.log('M=' + selectedValue);

  // URLSearchParamsを使ってクエリパラメータを生成
  var params = new URLSearchParams();
  params.append('middleCategory', selectedValue);

  // クエリパラメータを含むURLを作成してGETリクエストを送信
  fetch('/edit/category-response?' + params, {
          method: 'GET',
          headers: {
              'Content-Type': 'application/json'
          }
      })
      .then(response => response.json()) // レスポンスをJSONとして解析
      .then(data => {
          // '- grandChild -' を手動で追加
          data.unshift({ smallCategory: '- grandChild -' });
          // 取得したデータを利用してthirdSelectのオプションを変更
          var thirdSelect = document.getElementById("thirdSelect");
          // 既存のオプションをクリアする
          thirdSelect.innerHTML = '';
          // 重複を避けるためのマップを作成
          const seen = new Set();     
          // 例: 取得したデータが配列であり、その各要素をoption要素としてthirdSelectに追加する
          data.forEach(item => {
              // item.smallCategory が重複していないかチェックし、重複していなければオプションを追加
              if (!seen.has(item.smallCategory)) {
                  seen.add(item.smallCategory);
                  var option = document.createElement("option");
                  option.value = item.category; // 適切な値を設定する
                  option.text = item.smallCategory; // 適切な表示テキストを設定する
                  // '- childCategory -' を disabled, selectedにする
                  if (item.smallCategory === '- grandChild -') {
                    option.disabled = true;
                    option.selected = true;
                  }
                  thirdSelect.appendChild(option);
              }
          });
      })
      .catch(error => {
          console.error('エラーが発生しました', error);
      });
});

document.getElementById("thirdSelect").addEventListener("change", function() {
  var selectedValue = this.value;
  console.log('S='+selectedValue);
});