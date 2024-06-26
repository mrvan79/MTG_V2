let ArrayJob = new Array();
// 内容入力フィールドを取得
const purpose = document.getElementById("purpose");
// 内容情報を取得する非同期関数
async function callPurpose() {
    try {
        const apiUrl = "/purpose";
        const response = await fetch(apiUrl);
        if (!response.ok) {
            //APIを呼び出しできない場合 input box 表示されます。
        }
        const data = await response.json();
        data.map((e) => {
            //APIを呼び出し場合 選択ボックスを表示されます。
            let option = document.createElement('option');
            option.value = e.id;
            option.text = e.purpose;
            purpose.appendChild(option);
        });
    } catch (error) {
        //APIを呼び出しできない場合 input box 表示されます。
    }
}
// 担当者入力フィールドを取得
const charge = document.getElementById("charge");
// 担当者を取得する非同期関数
async function callCharge() {
    try {
        const apiUrl = "/charge";
        const response = await fetch(apiUrl);
        if (!response.ok) {
            //APIを呼び出しできない場合 input box 表示されます。
        }
        const data = await response.json();
        data.map((e) => {
            //APIを呼び出し場合 選択ボックスを表示されます。
            let option = document.createElement('option');
            option.value = e.id;
            option.text = e.charge;
            charge.appendChild(option);
        });
    } catch (error) {
        //APIを呼び出しできない場合 input box 表示されます。
    }
}
// 内容情報と担当者を非同期で取得し、フィールドに設定
(async () => {
    await callPurpose();
    await callCharge();
})();
// 送信ボタンを取得
const btn = document.getElementById("submit");
// メッセージ表示エリアを取得
const msg = document.getElementById("msg");
// 予約日付入力フィールドを取得
const date_plan = document.getElementById("date_plan");
// 予約日付入力時に実行されるリスナーを設定
date_plan.addEventListener('input', checkDay);
// 曜日表示フィールドを取得
const date_day = document.getElementById("date_day");
/**
 * 現在の日付に基づいて、曜日を計算し表示フィールドに設定する関数
 */
function setDay() {
    // 曜日の日本語表記を配列で定義
    const dayJapanese = ["日", "月", "火", "水", "木", "金", "土"];
    // 選択された日付から曜日を計算
    const dayOfWeek = dayJapanese[new Date(date_plan.value).getDay()];
    // 曜日表示フィールドに設定
    date_day.value = dayOfWeek;
}
/**
 * 過去の選択日付と現在の日付を比較する関数 
 * @returns {boolean} 過去の日付が現在の日付以下である場合はtrue、それ以外の場合はfalseを返します。
 */
function compareDates() {
    // 現在の日付を取得してISOString形式に変換し、日付部分のみを抽出
    const d1 = new Date(new Date().toISOString().substring(0, 10));
    // 選択された日付をDateオブジェクトに変換
    const d2 = new Date(date_plan.value);
    // 比較して結果を返す
    return d1 <= d2;
}
// 予約日付の検証と表示更新を行う関数
function checkDay() {
    if (compareDates()) {//正しい場合
        msg.textContent = '　';
        setDay();
        checkInput();
        btn.disabled = false;//送信ボタンを有効化
    } else {
        msg.textContent = '過去の日付を選択しないでください。';
        setDay();
        btn.disabled = true;//送信ボタンを無効化
    }
}
// 初期状態で曜日を表示
setDay();
// 開始時刻入力フィールドを取得
const time_start = document.getElementById("time_start");
// 開始時刻入力時に実行されるリスナーを設定
time_start.addEventListener('input', checkInput);

function checkInput() {
    callAPI();
    checkTimeActive(time_start.value);
    if (checkTimeRegister(ArrayJob, time_start.value)) {
        msg.textContent = `${time_start.value}に予約がありました。`;
        btn.disabled = true;//送信ボタンを無効化
    } else if (checkTimeRegister(ArrayJob, time_end.value) || checkTinmeTow()) {
        msg.textContent = `${time_start.value}～${time_end.value}に予約がありました。`;
        btn.disabled = true;//送信ボタンを無効化
    } else if (checkTowDay()) {
        msg.textContent = `開始時刻より終了時刻を選択してください。`;
        btn.disabled = true;//送信ボタンを無効化
    } else if (checkTimeRegister(ArrayJob, time_end.value) || checkTinmeTow()) {
        msg.textContent = `${time_start.value}～${time_end.value}に予約がありました。`;
        btn.disabled = true;//送信ボタンを無効化
    } else if (time_end.value != "") {
        msg.textContent = `　`;
        btn.disabled = false;//送信ボタンを有効化
        checkTimeActive(time_end.value);
    } else {
        msg.textContent = `終了時間を入力してください。`;
        btn.disabled = true;//送信ボタンを有効化
    }
    checkTimeActive(time_start.value);
}
/**
 * 時刻が指定された範囲内にあるかを検証する関数
 * @param {String} startTime 開始時刻
 * @param {String} endTime 終了時刻
 * @param {String} checkTime 検証する時刻
 * @returns {boolean} 時刻が範囲内にある場合はtrue、それ以外の場合はfalseを返します。
 */
function isTimeBetween(startTime, endTime, checkTime) {
    const start = new Date(date_plan.value + 'T' + startTime + 'Z').getTime();
    const end = new Date(date_plan.value + 'T' + endTime + 'Z').getTime();
    const check = new Date(date_plan.value + 'T' + checkTime + 'Z').getTime();
    return start <= check && check <= end;
}
/**
 * 予約開始時間と時刻を検証して予約があるか確認する関数
 * @param {Array} obj APIから
 * @param {String} checkTime 
 * @returns 
 */
function checkTimeRegister(obj, checkTime) {
    let valueCheck = false;
    obj.forEach(e => {
        if (isTimeBetween(e.time_start, e.time_end, checkTime) && date_plan.value == e.date_plan) {
            valueCheck = true;
        }
    });
    return valueCheck;
}
// 終了時刻入力フィールドを取得
const time_end = document.getElementById("time_end");
// 終了時刻入力時に実行されるリスナーを設定
time_end.addEventListener('input', checkInput);
/**
 * 開始時刻と終了時刻の範囲を検証する関数
 * @param {String} checkTimeStart 開始時刻
 * @param {String} checkTimeEnd 終了時刻
 * @returns {boolean} 時刻の範囲が正しい場合はtrue、それ以外の場合はfalseを返します。
 */
function isTimeBetweenTow(checkTimeStart, checkTimeEnd) {
    const start = new Date(date_plan.value + 'T' + time_start.value + 'Z').getTime();
    const end = new Date(date_plan.value + 'T' + time_end.value + 'Z').getTime();
    const checkStart = new Date(date_plan.value + 'T' + checkTimeStart + 'Z').getTime();
    const checkEnd = new Date(date_plan.value + 'T' + checkTimeEnd + 'Z').getTime();
    return start <= checkStart && end >= checkEnd;
}
// 開始時刻と終了時刻の範囲を検証する関数を実行し、結果を返す関数
function checkTinmeTow() {
    let valueCheck = false;
    ArrayJob.forEach(e => {
        if (isTimeBetweenTow(e.time_start, e.time_end) && date_plan.value == e.date_plan) {
            valueCheck = true;
        }
    });
    return valueCheck;
}
/**
 * 開始時刻と終了時刻を比較して、終了時刻が開始時刻より前かどうかを検証する関数
 * @returns {boolean} 終了時刻が開始時刻より前の場合はtrue、それ以外の場合はfalseを返します。
 */
function checkTowDay() {
    const start = new Date(date_plan.value + 'T' + time_start.value + 'Z').getTime();
    const end = new Date(date_plan.value + 'T' + time_end.value + 'Z').getTime();
    return start >= end;
}
//編集ID取得
const editID = document.getElementById("editid").value;
//call api job
function callAPI() {
    const apiUrl = `/kk/getjobapi?id=${editID}`;
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            ArrayJob = data;
        })
}
callAPI();
/**
 * 営業時間の検証、営業時間以外場合はエラーメッセージを表示されます。
 * @param {string} str 時間（hh:mm）
 */
function checkTimeActive(str) {
    const start = new Date(date_plan.value + 'T' + '09:00' + 'Z').getTime();
    let check = new Date(date_plan.value + 'T' + str + 'Z').getTime();
    const end = new Date(date_plan.value + 'T' + '21:00' + 'Z').getTime();
    if (start > check || check > end) {
        msg.textContent = "営業時間：09:00～21:00 です。";
        btn.disabled = true;//送信ボタンを有効化
    }
}